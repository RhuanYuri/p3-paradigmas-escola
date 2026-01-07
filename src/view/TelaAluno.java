package view;

import dao.AlunoDAO;
import model.Aluno;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaAluno extends JPanel {

    private JTextField txtNome;
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtDataNascimento;

    private AlunoDAO alunoDAO = new AlunoDAO();

    public TelaAluno() {
        setLayout(new BorderLayout());

        // Cabeçalho
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(new Color(173, 216, 230));
        painelTitulo.setPreferredSize(new Dimension(500, 50));

        JButton btnVoltar = criarBotaoVoltar();

        JLabel lblTitulo = new JLabel("Cadastro de Aluno");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        painelTitulo.add(btnVoltar);
        painelTitulo.add(lblTitulo);

        add(painelTitulo, BorderLayout.NORTH);

        // Formulário
        JPanel painel = new JPanel(new GridLayout(6, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblAjuda = new JLabel("<html><i>Preencha os campos conforme o formato indicado.</i></html>");
        lblAjuda.setForeground(Color.GRAY);
        painel.add(lblAjuda);
        painel.add(new JLabel());

        // Nome
        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        // CPF
        painel.add(new JLabel("CPF:"));
        try {
            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
        } catch (ParseException e) {
            txtCpf = new JFormattedTextField();
        }
        painel.add(txtCpf);

        // Data de nascimento
        painel.add(new JLabel("Data de Nascimento:"));
        try {
            txtDataNascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (ParseException e) {
            txtDataNascimento = new JFormattedTextField();
        }
        painel.add(txtDataNascimento);

        // Botão salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(100, 149, 237));
        btnSalvar.setFocusPainted(false);

        btnSalvar.addActionListener(e -> salvarAluno());

        painel.add(new JLabel());
        painel.add(btnSalvar);

        add(painel, BorderLayout.CENTER);
    }

    private void salvarAluno() {
        String nome = txtNome.getText().trim();
        String cpf = txtCpf.getText().trim();
        try {
            String dataStr = txtDataNascimento.getText().trim();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);

            Date dataNascimento = sdf.parse(dataStr);

            if (nome.isEmpty() || cpf.contains(" ")) {
                JOptionPane.showMessageDialog(this,
                        "Preencha todos os campos corretamente!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Aluno aluno = new Aluno(nome, dataNascimento, cpf);
            alunoDAO.salvar(aluno);

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida! Use o formato dd/MM/aaaa",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
        limparCampos();
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setValue(null);
        txtDataNascimento.setValue(null);
    }

    // Botão voltar
    private JButton criarBotaoVoltar() {
        JButton btn = new JButton("←");
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            MenuPrincipal.getInstance().mostrarMenu();
        });

        return btn;
    }
}