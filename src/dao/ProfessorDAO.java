package dao;

import model.Professor;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    private PessoaDAO pessoaDAO = new PessoaDAO();


    public void salvar(Professor professor) {

        // 1️⃣ salva pessoa
        int pessoaId = pessoaDAO.salvar(professor);
        professor.setPessoaId(pessoaId);

        // 2️⃣ salva professor
        String sql = """
            INSERT INTO professor (codigo, pessoa_id)
            VALUES (?, ?)
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, professor.getMatricula());
            ps.setInt(2, professor.getPessoaId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Professor> listar() {

        List<Professor> professores = new ArrayList<>();

        String sql = """
            SELECT
                pr.id AS professor_id,
                p.id AS pessoa_id,
                p.nome,
                p.data_nascimento,
                p.cpf,
                pr.codigo
            FROM professor pr
            JOIN pessoa p ON p.id = pr.pessoa_id
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("professor_id"),
                        rs.getString("nome"),
                        java.sql.Date.valueOf(rs.getString("data_nascimento")),
                        rs.getString("cpf"),
                        rs.getInt("pessoa_id"),
                        rs.getString("codigo")
                );

                professores.add(professor);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return professores;
    }

    public Professor buscarPorId(int professorId) {

        String sql = """
            SELECT
                pr.id AS professor_id,
                p.id AS pessoa_id,
                p.nome,
                p.data_nascimento,
                p.cpf,
                pr.codigo
            FROM professor pr
            JOIN pessoa p ON p.id = pr.pessoa_id
            WHERE pr.id = ?
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, professorId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Professor(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        java.sql.Date.valueOf(rs.getString("data_nascimento")),
                        rs.getString("cpf"),
                        rs.getInt("pessoa_id"),
                        rs.getString("codigo")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void atualizar(Professor professor) {
        pessoaDAO.atualizar(professor);
        String sql = "UPDATE professor SET codigo = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, professor.getMatricula());
            ps.setInt(2, professor.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(int professorId) {

        String sqlProfessor = "DELETE FROM professor WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlProfessor)) {

            ps.setInt(1, professorId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
