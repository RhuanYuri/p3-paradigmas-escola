package view;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class TelaAluno extends JFrame {

    private JTextField txtNome;
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtDataNascimento;

    public TelaAluno() {
        setTitle("Cadastro de Aluno");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        txtNome.setToolTipText("Digite o nome completo do aluno");
        painel.add(txtNome);

        // CPF
        painel.add(new JLabel("CPF:"));
        try {
            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
        } catch (ParseException e) {
            txtCpf = new JFormattedTextField();
        }
        txtCpf.setToolTipText("Formato: 000.000.000-00");
        painel.add(txtCpf);

        // Data de nascimento
        painel.add(new JLabel("Data de Nascimento:"));
        try {
            txtDataNascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
        } catch (ParseException e) {
            txtDataNascimento = new JFormattedTextField();
        }
        txtDataNascimento.setToolTipText("Formato: dd/MM/aaaa");
        painel.add(txtDataNascimento);

        // Botão salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(100, 149, 237));
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setFocusPainted(false);

        painel.add(new JLabel()); // espaço vazio
        painel.add(btnSalvar);

        add(painel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Botão voltar
    private JButton criarBotaoVoltar() {
        JButton btn = new JButton("←");
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setToolTipText("Voltar ao menu principal");

        btn.addActionListener(e -> {
            dispose(); // fecha a tela atual
            new MenuPrincipal(); // abre a tela principal
        });

        return btn;
    }
}
