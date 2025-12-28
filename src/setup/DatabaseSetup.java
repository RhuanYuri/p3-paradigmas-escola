package setup;

import util.Conexao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void main(String[] args) {

        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS pessoa (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    data_nascimento DATE NOT NULL,
                    cpf VARCHAR(14) UNIQUE NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS aluno (
                    id SERIAL PRIMARY KEY,
                    matricula VARCHAR(100) NOT NULL,
                    pessoa_id INT UNIQUE NOT NULL,
                    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS professor (
                    id SERIAL PRIMARY KEY,
                    codigo VARCHAR(50) NOT NULL,
                    pessoa_id INT UNIQUE NOT NULL,
                    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS turma (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(50) NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS turma_aluno (
                    turma_id INT NOT NULL,
                    aluno_id INT NOT NULL,
                    PRIMARY KEY (turma_id, aluno_id),
                    FOREIGN KEY (turma_id) REFERENCES turma(id) ON DELETE CASCADE,
                    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS nota (
                    id SERIAL PRIMARY KEY,
                    aluno_id INT NOT NULL,
                    turma_id INT NOT NULL,
                    nota1 DOUBLE PRECISION,
                    nota2 DOUBLE PRECISION,
                    nota3 DOUBLE PRECISION,
                    nota4 DOUBLE PRECISION,
                    FOREIGN KEY (aluno_id) REFERENCES aluno(id),
                    FOREIGN KEY (turma_id) REFERENCES turma(id),
                    UNIQUE (aluno_id, turma_id)
                )
            """);

            stmt.execute("""
                INSERT INTO pessoa (nome, data_nascimento, cpf) VALUES
                ('João Silva', '2002-05-10', '111.111.111-11'),
                ('Maria Souza', '2001-08-20', '222.222.222-22'),
                ('Carlos Lima', '1980-03-15', '333.333.333-33')
                ON CONFLICT (cpf) DO NOTHING
            """);


            stmt.execute("""
                INSERT INTO aluno (matricula, pessoa_id) VALUES
                ('MAT001', 1),
                ('MAT002', 2)
                ON CONFLICT DO NOTHING
            """);

            stmt.execute("""
                INSERT INTO professor (codigo, pessoa_id) VALUES
                ('PROF001', 3)
                ON CONFLICT DO NOTHING
            """);

            stmt.execute("""
                INSERT INTO turma (nome) VALUES
                ('Turma A'),
                ('Turma B')
                ON CONFLICT DO NOTHING
            """);


            stmt.execute("""
                INSERT INTO turma_aluno (turma_id, aluno_id) VALUES
                (1, 1),
                (1, 2)
                ON CONFLICT DO NOTHING
            """);

            System.out.println("✅ Banco criado e dados iniciais inseridos com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
