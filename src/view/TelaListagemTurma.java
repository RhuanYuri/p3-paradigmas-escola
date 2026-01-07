package view;

import dao.TurmaDAO;
import model.Professor;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListagemTurma extends JPanel {

  private List<Professor> professoresSelecionados;
  private TurmaDAO turmaDAO = new TurmaDAO();
  private DefaultListModel<Turma> listaModel = new DefaultListModel<>();
  private JList<Turma> listTurmas;

  public TelaListagemTurma(List<Professor> professores) {
    this.professoresSelecionados = professores;
    setLayout(new BorderLayout());

    JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    painelTitulo.setBackground(new Color(173, 216, 230));
    painelTitulo.setPreferredSize(new Dimension(800, 50));

    JButton btnVoltar = new JButton("←");
    btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
    btnVoltar.setBorderPainted(false);
    btnVoltar.setContentAreaFilled(false);
    btnVoltar.setFocusPainted(false);
    btnVoltar.setToolTipText("Voltar");
    btnVoltar.addActionListener(e -> MenuPrincipal.getInstance().mostrarTelaProfessor());

    JLabel lblTitulo = new JLabel("Turmas disponíveis");
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

    painelTitulo.add(btnVoltar);
    painelTitulo.add(lblTitulo);
    add(painelTitulo, BorderLayout.NORTH);

    listTurmas = new JList<>(listaModel);
    listTurmas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listTurmas.setFont(new Font("Arial", Font.PLAIN, 14));
    listTurmas.setFixedCellHeight(30);

    listTurmas.setCellRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
          boolean cellHasFocus) {
        if (value instanceof Turma) {
          Turma t = (Turma) value;
          value = t.getNome() + " (ID: " + t.getId() + ")";
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      }
    });

    add(new JScrollPane(listTurmas), BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    JButton btnVerAlunos = new JButton("Ver Alunos / Vincular");
    btnVerAlunos.setBackground(new Color(100, 149, 237));
    btnVerAlunos.setForeground(Color.WHITE);
    btnVerAlunos.setFocusPainted(false);
    btnVerAlunos.addActionListener(e -> abrirTelaAlunos());

    btnPanel.add(btnVerAlunos);
    add(btnPanel, BorderLayout.SOUTH);

    carregarTurmas();
  }

  private void carregarTurmas() {
    listaModel.clear();
    List<Turma> turmas = turmaDAO.listar();
    for (Turma t : turmas) {
      listaModel.addElement(t);
    }
  }

  private void abrirTelaAlunos() {
    Turma t = listTurmas.getSelectedValue();
    if (t == null) {
      JOptionPane.showMessageDialog(this, "Selecione uma turma para continuar.", "Atenção",
          JOptionPane.WARNING_MESSAGE);
      return;
    }
    MenuPrincipal.getInstance().mostrarTelaAlunos(t);
  }
}
