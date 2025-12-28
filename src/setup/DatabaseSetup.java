package setup;

import util.Conexao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void main(String[] args) {

        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement()) {

            // 1. ATIVAR CHAVES ESTRANGEIRAS NO SQLITE
            stmt.execute("PRAGMA foreign_keys = ON;");

            // 2. CRIAR TABELAS (SERIAL -> INTEGER PRIMARY KEY AUTOINCREMENT)
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS pessoa (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    data_nascimento TEXT NOT NULL,
                    cpf TEXT UNIQUE NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS aluno (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    matricula TEXT NOT NULL,
                    pessoa_id INTEGER UNIQUE NOT NULL,
                    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS professor (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    codigo TEXT NOT NULL,
                    pessoa_id INTEGER UNIQUE NOT NULL,
                    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS turma (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS turma_aluno (
                    turma_id INTEGER NOT NULL,
                    aluno_id INTEGER NOT NULL,
                    PRIMARY KEY (turma_id, aluno_id),
                    FOREIGN KEY (turma_id) REFERENCES turma(id) ON DELETE CASCADE,
                    FOREIGN KEY (aluno_id) REFERENCES aluno(id) ON DELETE CASCADE
                )
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS nota (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    aluno_id INTEGER NOT NULL,
                    turma_id INTEGER NOT NULL,
                    nota1 REAL,
                    nota2 REAL,
                    nota3 REAL,
                    nota4 REAL,
                    FOREIGN KEY (aluno_id) REFERENCES aluno(id),
                    FOREIGN KEY (turma_id) REFERENCES turma(id),
                    UNIQUE (aluno_id, turma_id)
                )
            """);

            // 3. INSERIR DADOS (Usando INSERT OR IGNORE para evitar erros de duplicidade)
            stmt.execute("""
                INSERT OR IGNORE INTO pessoa (id, nome, data_nascimento, cpf) VALUES
                (1, 'João Silva', '2002-05-10', '111.111.111-11'),
                (2, 'Maria Souza', '2001-08-20', '222.222.222-22'),
                (3, 'Carlos Lima', '1980-03-15', '333.333.333-33')
            """);

            stmt.execute("""
                INSERT OR IGNORE INTO aluno (id, matricula, pessoa_id) VALUES
                (1, 'MAT001', 1),
                (2, 'MAT002', 2)
            """);

            stmt.execute("""
                INSERT OR IGNORE INTO professor (id, codigo, pessoa_id) VALUES
                (1, 'PROF001', 3)
            """);

            stmt.execute("""
                INSERT OR IGNORE INTO turma (id, nome) VALUES
                (1, 'Turma A'),
                (2, 'Turma B')
            """);

            stmt.execute("""
                INSERT OR IGNORE INTO turma_aluno (turma_id, aluno_id) VALUES
                (1, 1),
                (1, 2)
            """);

            System.out.println("✅ Banco SQLite configurado e dados inseridos!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}