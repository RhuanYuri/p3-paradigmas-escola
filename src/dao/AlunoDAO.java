package dao;

import model.Aluno;
import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AlunoDAO {

    public void salvar(Aluno aluno){
        String query = "INSERT INTO aluno (matricula) VALUES (?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, aluno.getMatricula());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }
}
