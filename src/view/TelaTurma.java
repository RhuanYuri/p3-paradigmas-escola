package view;

import dao.TurmaDAO;
import model.Turma;

import javax.swing.*;
import java.awt.*;

public class TelaTurma extends JFrame {

    private JTextField txtNomeTurma;
    private JTextField txtCodigoTurma;

    private TurmaDAO turmaDAO = new TurmaDAO();

    public TelaTurma() {
        setTitle("Cadastro de Turma");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- CABEÇALHO ----------
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(new Color(173, 216, 230));
        painelTitulo.setPreferredSize(new Dimension(400, 50));

        JButton btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setToolTipText("Voltar");
        btnVoltar.addActionListener(e -> dispose());

        JLabel lblTitulo = new JLabel("Cadastro de Turma");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        painelTitulo.add(btnVoltar);
        painelTitulo.add(lblTitulo);

        add(painelTitulo, BorderLayout.NORTH);

        // ---------- FORMULÁRIO ----------
        JPanel painelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painelFormulario.add(new JLabel("Nome da Turma:"));
        txtNomeTurma = new JTextField();
        txtNomeTurma.setToolTipText("Exemplo: 3º Ano A");
        painelFormulario.add(txtNomeTurma);

        painelFormulario.add(new JLabel("Código da Turma:"));
        txtCodigoTurma = new JTextField();
        txtCodigoTurma.setToolTipText("Exemplo: T2025-01");
        painelFormulario.add(txtCodigoTurma);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(100, 149, 237));
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvarTurma());

        painelFormulario.add(new JLabel()); // espaço
        painelFormulario.add(btnSalvar);

        add(painelFormulario, BorderLayout.CENTER);

        setVisible(true);
    }

    // ---------- SALVAR ----------
    private void salvarTurma() {
        String nome = txtNomeTurma.getText().trim();
        String codigo = txtCodigoTurma.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Preencha o nome da turma!",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            Turma turma = new Turma();
            turma.setNome(nome);

            // código não está no model → só visual por enquanto
            turmaDAO.salvar(turma);

            JOptionPane.showMessageDialog(
                    this,
                    "Turma cadastrada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao salvar turma:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
