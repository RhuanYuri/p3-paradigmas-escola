package dao;

import model.Aluno;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private PessoaDAO pessoaDAO = new PessoaDAO();

    public void salvar(Aluno aluno) {

        int pessoaId = pessoaDAO.salvar(aluno);
        aluno.setPessoaId(pessoaId);

        // 2️⃣ salva aluno
        String sql = """
            INSERT INTO aluno (matricula, pessoa_id)
            VALUES (?, ?)
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, aluno.getMatricula());
            ps.setInt(2, aluno.getPessoaId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Aluno> listar() {
        List<Aluno> alunos = new ArrayList<>();

        String sql = """
            SELECT 
                a.id AS aluno_id,
                p.id AS pessoa_id,
                p.nome,
                p.data_nascimento,
                p.cpf,
                a.matricula
            FROM aluno a
            JOIN pessoa p ON p.id = a.pessoa_id
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("aluno_id"),
                        rs.getString("nome"),
                        java.sql.Date.valueOf(rs.getString("data_nascimento")),
                        rs.getString("cpf"),
                        rs.getInt("pessoa_id"),
                        rs.getString("matricula")
                );

                alunos.add(aluno);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return alunos;
    }

    public Aluno buscarPorId(int alunoId) {

        String sql = """
            SELECT 
                a.id AS aluno_id,
                p.id AS pessoa_id,
                p.nome,
                p.data_nascimento,
                p.cpf,
                a.matricula
            FROM aluno a
            JOIN pessoa p ON p.id = a.pessoa_id
            WHERE a.id = ?
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alunoId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Aluno(
                        rs.getInt("aluno_id"),
                        rs.getString("nome"),
                        java.sql.Date.valueOf(rs.getString("data_nascimento")),
                        rs.getString("cpf"),
                        rs.getInt("pessoa_id"),
                        rs.getString("matricula")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void atualizar(Aluno aluno) {

        PessoaDAO pessoaDAO = new PessoaDAO();
        pessoaDAO.atualizar(aluno);

        String sql = "UPDATE aluno SET matricula = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, aluno.getMatricula());
            ps.setInt(2, aluno.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(int alunoId) {

        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alunoId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
