package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Professor extends Pessoa {
    private int id;
    private String codigo;

    public Professor(){}

    public Professor(int id, String nome, Date dataNascimento, String cpf, int pessoaId, String codigo){
        super(id=pessoaId, nome, dataNascimento, cpf);
        this.codigo = codigo;
    }

    public Professor(String nome, Date dataNascimento, String cpf, int pessoaId){
        super(id=pessoaId, nome, dataNascimento, cpf);
        this.codigo = gerarMatricula(cpf, nome);
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
        System.out.println("Matricula: " + codigo);
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
        return codigo;
    }

    public void setMatricula(String codigo) {
        this.codigo = codigo;
    }

    public int getPessoaId(){
        return super.getId();
    }

    public void setPessoaId(int pessoaId){
        super.setId(pessoaId);
    }
}
