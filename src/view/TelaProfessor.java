package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import dao.ProfessorDAO;
import model.Professor;

public class TelaProfessor extends JPanel {

    private ProfessorDAO professorDAO = new ProfessorDAO();
    private DefaultListModel<Professor> listaModel = new DefaultListModel<>();
    private JList<Professor> jListProfessores;

    public TelaProfessor() {
        setLayout(new BorderLayout());

        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelTitulo.setBackground(new Color(173, 216, 230));
        painelTitulo.setPreferredSize(new Dimension(800, 50));

        JButton btnVoltar = new JButton("←");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        btnVoltar.setBorderPainted(false);
        btnVoltar.setContentAreaFilled(false);
        btnVoltar.setFocusPainted(false);
        btnVoltar.setToolTipText("Voltar ao menu principal");
        btnVoltar.addActionListener(e -> MenuPrincipal.getInstance().mostrarMenu());

        JLabel lblTitulo = new JLabel("Selecionar Professores");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

        painelTitulo.add(btnVoltar);
        painelTitulo.add(lblTitulo);
        add(painelTitulo, BorderLayout.NORTH);

        listaModel = new DefaultListModel<>();
        jListProfessores = new JList<>(listaModel);
        jListProfessores.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jListProfessores.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                Professor p = (Professor) value;
                return super.getListCellRendererComponent(list, p.getMatricula() + " - " + p.getNome(),
                        index, isSelected, cellHasFocus);
            }
        });

        JScrollPane scroll = new JScrollPane(jListProfessores);
        scroll.setBorder(BorderFactory.createTitledBorder("Professores"));
        add(scroll, BorderLayout.CENTER);

        JButton btnConfirmar = new JButton("Confirmar Seleção");
        btnConfirmar.setBackground(new Color(100, 149, 237));
        btnConfirmar.setForeground(Color.WHITE);
        btnConfirmar.setFocusPainted(false);
        btnConfirmar.addActionListener(e -> {
            if (jListProfessores.getSelectedValuesList().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione ao menos um professor!", "Atenção",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            MenuPrincipal.getInstance().mostrarTelaTurmas(jListProfessores.getSelectedValuesList());
        });

        add(btnConfirmar, BorderLayout.SOUTH);

        carregarProfessores();
    }

    private void carregarProfessores() {
        listaModel.clear();
        List<Professor> professores = professorDAO.listar();
        for (Professor p : professores) {
            listaModel.addElement(p);
        }
    }

    public List<Professor> getProfessoresSelecionados() {
        return jListProfessores.getSelectedValuesList();
    }
}
