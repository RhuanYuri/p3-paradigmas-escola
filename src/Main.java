import util.Conexao;

public class Main{
    public static void main(String[] args){
        try {
            Conexao.getConnection();
            System.out.println("Conectado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
        }


    }
}