package view;

import dao.AlunoDAO;
import dao.TurmaDAO;
import model.Aluno;
import model.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TelaSelecaoAlunoVinculo extends JDialog {

  private Turma turma;
  private TurmaDAO turmaDAO = new TurmaDAO();
  private AlunoDAO alunoDAO = new AlunoDAO();
  private DefaultListModel<Aluno> listModel = new DefaultListModel<>();
  private JList<Aluno> listAlunos;
  private boolean vinculoRealizado = false;

  public TelaSelecaoAlunoVinculo(Frame parent, Turma turma) {
    super(parent, "Vincular Aluno à Turma", true); // Modal
    this.turma = turma;

    setSize(500, 400);
    setLocationRelativeTo(parent);
    setLayout(new BorderLayout());

    // Header
    JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    painelTitulo.setBackground(new Color(173, 216, 230));
    painelTitulo.setPreferredSize(new Dimension(500, 50));
    JLabel lblTitulo = new JLabel("Selecione um aluno para cadastrar na turma");
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
    painelTitulo.add(lblTitulo);
    add(painelTitulo, BorderLayout.NORTH);

    // List
    listAlunos = new JList<>(listModel);
    listAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listAlunos.setCellRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
          boolean cellHasFocus) {
        if (value instanceof Aluno) {
          Aluno a = (Aluno) value;
          value = a.getNome() + " - " + a.getMatricula();
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      }
    });

    add(new JScrollPane(listAlunos), BorderLayout.CENTER);

    // Buttons
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton btnCancelar = new JButton("Cancelar");
    btnCancelar.addActionListener(e -> dispose());

    JButton btnVincular = new JButton("Vincular Selecionado");
    btnVincular.setBackground(new Color(60, 179, 113));
    btnVincular.setForeground(Color.WHITE);
    btnVincular.addActionListener(e -> vincularSelecionado());

    btnPanel.add(btnCancelar);
    btnPanel.add(btnVincular);
    add(btnPanel, BorderLayout.SOUTH);

    carregarAlunosDisponiveis();
  }

  private void carregarAlunosDisponiveis() {
    listModel.clear();
    // try {
    // Usa o método otimizado do DAO para buscar apenas quem NÃO está na turma
    List<Aluno> disponiveis = alunoDAO.listarNaoMatriculadosNaTurma(turma.getId());

    if (disponiveis.isEmpty()) {
      JOptionPane.showMessageDialog(this, "Não há alunos disponíveis para vincular.", "Aviso",
          JOptionPane.INFORMATION_MESSAGE);
      dispose();
      return;
    }

    for (Aluno a : disponiveis) {
      listModel.addElement(a);
    }

    // } catch (Exception e) {
    // JOptionPane.showMessageDialog(this, "Erro ao carregar alunos: " +
    // e.getMessage());
    // }
  }

  private void vincularSelecionado() {
    Aluno selecionado = listAlunos.getSelectedValue();
    if (selecionado == null) {
      JOptionPane.showMessageDialog(this, "Selecione um aluno da lista.", "Atenção", JOptionPane.WARNING_MESSAGE);
      return;
    }

    try {
      // Add relation using direct insert to avoid wiping existing links via
      // 'atualizar'
      turmaDAO.adicionarAluno(turma.getId(), selecionado.getId());

      // Update local object purely for immediate UI reflection if needed,
      // though parent will reload from DB anyway.
      if (turma.getAlunos() == null)
        turma.setAlunos(new ArrayList<>());
      turma.getAlunos().add(selecionado);

      JOptionPane.showMessageDialog(this, "Aluno vinculado com sucesso!");
      vinculoRealizado = true;
      dispose();

    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Erro ao vincular: " + e.getMessage());
    }
  }

  public boolean isVinculoRealizado() {
    return vinculoRealizado;
  }
}
