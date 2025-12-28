package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Aluno extends Pessoa {
    private int id;
    private String matricula;

    public Aluno(int id, String nome, java.sql.Date dataNascimento, String cpf, int pessoaId, String matricula){
        super(id=pessoaId, nome, dataNascimento, cpf);
        this.matricula = matricula;
    }

    public Aluno(){}

    public Aluno(String nome, Date dataNascimento, String cpf, int pessoaId){
        super(id=pessoaId, nome, dataNascimento, cpf);
        this.matricula = gerarMatricula(cpf, nome);
    }


    String gerarMatricula(String cpf, String nome) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String data = LocalDateTime.now().format(formatter);

        return cpf + data + nome;
    }

    @Override
    void imprimirDados(){
        System.out.println("Nome: " + getNome());
        System.out.println("Data de nascimento: " + getDataNascimento());
        System.out.println("CPF: " + getCpf());
        System.out.println("Matricula: " + matricula);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getPessoaId(){
        return super.getId();
    }

    public void setPessoaId(int pessoaId){
        super.setId(pessoaId);
    }
}
