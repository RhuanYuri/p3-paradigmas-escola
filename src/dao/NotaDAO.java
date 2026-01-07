package dao;

import model.Nota;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public void salvar(Nota nota) {
        String sql = """
                    INSERT INTO nota (aluno_id, turma_id, nota1, nota2, nota3, nota4)
                    VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nota.getAlunoId());
            ps.setInt(2, nota.getTurmaId());
            ps.setDouble(3, nota.getNota1());
            ps.setDouble(4, nota.getNota2());
            ps.setDouble(5, nota.getNota3());
            ps.setDouble(6, nota.getNota4());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // READ ONE (por aluno e turma)
    public Nota buscarPorAlunoETurma(int alunoId, int turmaId) {
        String sql = """
                    SELECT * FROM nota
                    WHERE aluno_id = ? AND turma_id = ?
                """;

        Nota nota = null;

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alunoId);
            ps.setInt(2, turmaId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                nota = new Nota(
                        rs.getInt("id"),
                        rs.getInt("aluno_id"),
                        rs.getInt("turma_id"),
                        rs.getDouble("nota1"),
                        rs.getDouble("nota2"),
                        rs.getDouble("nota3"),
                        rs.getDouble("nota4"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nota;
    }

    // READ ALL (todas as notas da turma)
    public List<Nota> listarPorTurma(int turmaId) {
        List<Nota> notas = new ArrayList<>();

        String sql = "SELECT * FROM nota WHERE turma_id = ?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, turmaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Nota nota = new Nota(
                        rs.getInt("id"),
                        rs.getInt("aluno_id"),
                        rs.getInt("turma_id"),
                        rs.getDouble("nota1"),
                        rs.getDouble("nota2"),
                        rs.getDouble("nota3"),
                        rs.getDouble("nota4"));
                notas.add(nota);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return notas;
    }

    // UPDATE
    public void atualizar(Nota nota) {
        String sql = """
                    UPDATE nota
                    SET nota1 = ?, nota2 = ?, nota3 = ?, nota4 = ?
                    WHERE aluno_id = ? AND turma_id = ?
                """;

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, nota.getNota1());
            ps.setDouble(2, nota.getNota2());
            ps.setDouble(3, nota.getNota3());
            ps.setDouble(4, nota.getNota4());
            ps.setInt(5, nota.getAlunoId());
            ps.setInt(6, nota.getTurmaId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    public void deletar(int alunoId, int turmaId) {
        String sql = "DELETE FROM nota WHERE aluno_id = ? AND turma_id = ?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, alunoId);
            ps.setInt(2, turmaId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
