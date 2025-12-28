package dao;

import model.Pessoa;
import util.Conexao;

import java.sql.*;

public class PessoaDAO {

    public int salvar(Pessoa pessoa) {

        String sql = """
            INSERT INTO pessoa (nome, data_nascimento, cpf)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pessoa.getNome());
            ps.setDate(2, new java.sql.Date(pessoa.getDataNascimento().getTime()));
            ps.setString(3, pessoa.getCpf());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    public void atualizar(Pessoa pessoa) {

        String sql = """
            UPDATE pessoa
            SET nome = ?, data_nascimento = ?, cpf = ?
            WHERE id = ?
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pessoa.getNome());
            ps.setDate(2, new java.sql.Date(pessoa.getDataNascimento().getTime()));
            ps.setString(3, pessoa.getCpf());
            ps.setInt(4, pessoa.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
