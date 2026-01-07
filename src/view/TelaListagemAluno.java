package view;

import dao.TurmaDAO;
import model.Aluno;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class TelaListagemAluno extends JPanel {

    private Turma turma;
    private TurmaDAO turmaDAO = new TurmaDAO();

    private DefaultListModel<Aluno> listaModel = new DefaultListModel<>();
    private JList<Aluno> listAlunos;

    public TelaListagemAluno(Turma turma) {
        this.turma = turma;
        setLayout(new BorderLayout());

        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(new Color(173, 216, 230));
        painelTitulo.setPreferredSize(new Dimension(800, 50));

        JButton btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> MenuPrincipal.getInstance().mostrarTelaTurmas(Collections.emptyList())); // Empty
                                                                                                                  // list
                                                                                                                  // here
                                                                                                                  // effectively
                                                                                                                  // just
                                                                                                                  // shows
                                                                                                                  // list,
                                                                                                                  // ideal
                                                                                                                  // would
                                                                                                                  // be
                                                                                                                  // keeping
                                                                                                                  // history

        JLabel lblTitulo = new JLabel("Alunos em " + turma.getNome());
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        painelTitulo.add(btnVoltar);
        painelTitulo.add(lblTitulo);
        add(painelTitulo, BorderLayout.NORTH);

        listAlunos = new JList<>(listaModel);
        listAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAlunos.setFont(new Font("Arial", Font.PLAIN, 14));
        listAlunos.setFixedCellHeight(30);

        listAlunos.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                if (value instanceof Aluno) {
                    Aluno a = (Aluno) value;
                    value = a.getNome() + " (Matrícula: " + a.getMatricula() + ")";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        add(new JScrollPane(listAlunos), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnVincular = new JButton("Cadastrar");
        btnVincular.setBackground(new Color(60, 179, 113));
        btnVincular.setForeground(Color.WHITE);
        btnVincular.setFocusPainted(false);
        btnVincular.addActionListener(e -> abrirTelaVinculo());

        JButton btnLancarNotas = new JButton("Lançar Notas");
        btnLancarNotas.setBackground(new Color(100, 149, 237));
        btnLancarNotas.setForeground(Color.WHITE);
        btnLancarNotas.setFocusPainted(false);
        btnLancarNotas.addActionListener(e -> abrirTelaNotas());

        btnPanel.add(btnVincular);
        btnPanel.add(btnLancarNotas);
        add(btnPanel, BorderLayout.SOUTH);

        carregarAlunos();
    }

    private void carregarAlunos() {
        listaModel.clear();
        Turma t = turmaDAO.buscarPorId(this.turma.getId());
        if (t != null) {
            this.turma = t;
            if (t.getAlunos() != null) {
                for (Aluno a : t.getAlunos()) {
                    listaModel.addElement(a);
                }
            }
        }
    }

    private void abrirTelaVinculo() {
        // cast MenuPrincipal to Frame to invoke dialog correctly
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        TelaSelecaoAlunoVinculo dialog = new TelaSelecaoAlunoVinculo(parentFrame, turma);
        dialog.setVisible(true);

        if (dialog.isVinculoRealizado()) {
            carregarAlunos();
        }
    }

    private void abrirTelaNotas() {
        Aluno a = listAlunos.getSelectedValue();
        if (a == null) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno da lista.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        MenuPrincipal.getInstance().mostrarTelaNotas(turma, a);
    }
}
