package view;

import dao.TurmaDAO;
import model.Professor;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaTurma extends JFrame {

    private JTextField txtNomeTurma;
    private JTextField txtCodigoTurma;
    private List<Professor> professoresSelecionados = new ArrayList<>();
    private DefaultListModel<String> listaProfessoresModel = new DefaultListModel<>();
    private JList<String> jListProfessores;

    private TurmaDAO turmaDAO = new TurmaDAO();

    public TelaTurma() {
        setTitle("Cadastro de Turma");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Cabeçalho
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(new Color(173, 216, 230));
        painelTitulo.setPreferredSize(new Dimension(500, 50));

        JButton btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setToolTipText("Voltar ao menu principal");
        btnVoltar.addActionListener(e -> dispose());

        JLabel lblTitulo = new JLabel("Cadastro de Turma");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        painelTitulo.add(btnVoltar);
        painelTitulo.add(lblTitulo);
        add(painelTitulo, BorderLayout.NORTH);

        // Formulário
        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painelFormulario.add(new JLabel("Nome da Turma:"));
        txtNomeTurma = new JTextField();
        painelFormulario.add(txtNomeTurma);

        painelFormulario.add(new JLabel("Código da Turma:"));
        txtCodigoTurma = new JTextField();
        painelFormulario.add(txtCodigoTurma);

        // Lista de professores selecionados
        listaProfessoresModel = new DefaultListModel<>();
        jListProfessores = new JList<>(listaProfessoresModel);
        JScrollPane scrollProfessores = new JScrollPane(jListProfessores);
        scrollProfessores.setBorder(BorderFactory.createTitledBorder("Professores Selecionados"));
        painelFormulario.add(scrollProfessores);

        JButton btnSelecionarProfessores = new JButton("Selecionar Professores");
        btnSelecionarProfessores.addActionListener(e -> abrirTelaProfessor());
        painelFormulario.add(btnSelecionarProfessores);

        // Botão salvar
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(new Color(100, 149, 237));
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvarTurma());
        painelFormulario.add(btnSalvar);

        add(painelFormulario, BorderLayout.CENTER);

        setVisible(true);
    }

    private void abrirTelaProfessor() {
        TelaProfessor telaProfessor = new TelaProfessor();
        telaProfessor.setVisible(true);

        // Atualiza lista de professores ao fechar a TelaProfessor
        telaProfessor.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                professoresSelecionados = telaProfessor.getProfessoresSelecionados();
                atualizarListaProfessores();
            }
        });
    }

    private void atualizarListaProfessores() {
        listaProfessoresModel.clear();
        for (Professor p : professoresSelecionados) {
            listaProfessoresModel.addElement(p.getMatricula() + " - " + p.getNome());
        }
    }

    private void salvarTurma() {
        String nome = txtNomeTurma.getText().trim();
        String codigo = txtCodigoTurma.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha o nome da turma!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Turma turma = new Turma();
        turma.setNome(nome);
        turma.setAlunos(new ArrayList<>()); // Professores não vão para "alunos"

        turmaDAO.salvar(turma);

        JOptionPane.showMessageDialog(this, "Turma cadastrada com sucesso!");
        dispose();
    }
}
