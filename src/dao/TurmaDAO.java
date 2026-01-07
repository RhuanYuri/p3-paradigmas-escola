package dao;

import model.Aluno;
import model.Turma;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    private AlunoDAO alunoDAO = new AlunoDAO();

    public void salvar(Turma turma) {

        String sqlTurma = "INSERT INTO turma (nome) VALUES (?)";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement psTurma = conn.prepareStatement(sqlTurma, Statement.RETURN_GENERATED_KEYS)) {

            psTurma.setString(1, turma.getNome());
            psTurma.executeUpdate();

            ResultSet rs = psTurma.getGeneratedKeys();
            if (rs.next()) {
                turma.setId(rs.getInt(1));
            }

            if (turma.getAlunos() != null) {
                salvarAlunosDaTurma(conn, turma);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Turma> listar() {

        List<Turma> turmas = new ArrayList<>();

        String sql = "SELECT * FROM turma";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));

                turma.setAlunos(buscarAlunosDaTurma(turma.getId()));
                turmas.add(turma);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return turmas;
    }

    public Turma buscarPorId(int turmaId) {

        String sql = "SELECT * FROM turma WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, turmaId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));
                turma.setAlunos(buscarAlunosDaTurma(turmaId));
                return turma;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public void atualizar(Turma turma) {

        String sql = "UPDATE turma SET nome = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, turma.getNome());
            ps.setInt(2, turma.getId());
            ps.executeUpdate();

            removerAlunosDaTurma(conn, turma.getId());
            salvarAlunosDaTurma(conn, turma);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletar(int turmaId) {

        String sqlRelacionamento = "DELETE FROM turma_aluno WHERE turma_id = ?";
        String sqlTurma = "DELETE FROM turma WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement psRel = conn.prepareStatement(sqlRelacionamento);
                PreparedStatement psTurma = conn.prepareStatement(sqlTurma)) {

            psRel.setInt(1, turmaId);
            psRel.executeUpdate();

            psTurma.setInt(1, turmaId);
            psTurma.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adicionarAluno(int turmaId, int alunoId) {
        String sql = "INSERT INTO turma_aluno (turma_id, aluno_id) VALUES (?, ?)";

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, turmaId);
            ps.setInt(2, alunoId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar aluno na turma: " + e.getMessage(), e);
        }
    }

    private void salvarAlunosDaTurma(Connection conn, Turma turma) throws SQLException {

        String sql = "INSERT INTO turma_aluno (turma_id, aluno_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Aluno aluno : turma.getAlunos()) {
                ps.setInt(1, turma.getId());
                ps.setInt(2, aluno.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void removerAlunosDaTurma(Connection conn, int turmaId) throws SQLException {

        String sql = "DELETE FROM turma_aluno WHERE turma_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, turmaId);
            ps.executeUpdate();
        }
    }

    private List<Aluno> buscarAlunosDaTurma(int turmaId) {

        List<Aluno> alunos = new ArrayList<>();

        String sql = """
                    SELECT a.id
                    FROM aluno a
                    JOIN turma_aluno ta ON ta.aluno_id = a.id
                    WHERE ta.turma_id = ?
                """;

        try (Connection conn = Conexao.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, turmaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                alunos.add(alunoDAO.buscarPorId(rs.getInt("id")));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return alunos;
    }
}
