package model;

public class Nota {

    private int id;
    private int alunoId;
    private int turmaId;
    private double nota1;
    private double nota2;
    private double nota3;
    private double nota4;

    public Nota() {}

    public Nota(int id, int alunoId, int turmaId,
                double nota1, double nota2, double nota3, double nota4) {
        this.id = id;
        this.alunoId = alunoId;
        this.turmaId = turmaId;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
    }

    public Nota(int alunoId, int turmaId,
                double nota1, double nota2, double nota3, double nota4) {
        this.alunoId = alunoId;
        this.turmaId = turmaId;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.nota3 = nota3;
        this.nota4 = nota4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(int turmaId) {
        this.turmaId = turmaId;
    }

    public double getNota1() {
        return nota1;
    }

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public double getNota2() {
        return nota2;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public double getNota3() {
        return nota3;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public double getNota4() {
        return nota4;
    }

    public void setNota4(double nota4) {
        this.nota4 = nota4;
    }

    public double calcularMedia() {
        return (nota1 + nota2 + nota3 + nota4) / 4;
    }
}
