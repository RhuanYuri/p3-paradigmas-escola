package view;

import dao.NotaDAO;
import model.Aluno;
import model.Nota;
import model.Turma;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroNota extends JPanel {

  private Aluno aluno;
  private Turma turma;
  private NotaDAO notaDAO = new NotaDAO();
  private Nota notaExistente;

  private JTextField txtNota1, txtNota2, txtNota3, txtNota4;
  private JLabel lblMedia;

  public TelaCadastroNota(Turma turma, Aluno aluno) {
    this.turma = turma;
    this.aluno = aluno;
    setLayout(new BorderLayout());

    JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
    painelTitulo.setBackground(new Color(173, 216, 230));
    painelTitulo.setPreferredSize(new Dimension(400, 50));

    JButton btnVoltar = new JButton("←");
    btnVoltar.setFont(new Font("Arial", Font.BOLD, 16));
    btnVoltar.setBorderPainted(false);
    btnVoltar.setContentAreaFilled(false);
    btnVoltar.setFocusPainted(false);
    btnVoltar.addActionListener(e -> MenuPrincipal.getInstance().mostrarTelaAlunos(turma));

    JLabel lblTitulo = new JLabel("Lançamento de Notas");
    lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));

    painelTitulo.add(btnVoltar);
    painelTitulo.add(lblTitulo);
    add(painelTitulo, BorderLayout.NORTH);

    JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

    txtNota1 = new JTextField("0.0");
    txtNota2 = new JTextField("0.0");
    txtNota3 = new JTextField("0.0");
    txtNota4 = new JTextField("0.0");
    lblMedia = new JLabel("0.00");
    lblMedia.setFont(new Font("Arial", Font.BOLD, 16));
    lblMedia.setForeground(new Color(0, 100, 0));

    formPanel.add(new JLabel("Nota 1:"));
    formPanel.add(txtNota1);
    formPanel.add(new JLabel("Nota 2:"));
    formPanel.add(txtNota2);
    formPanel.add(new JLabel("Nota 3:"));
    formPanel.add(txtNota3);
    formPanel.add(new JLabel("Nota 4:"));
    formPanel.add(txtNota4);
    formPanel.add(new JLabel("Média Final:"));
    formPanel.add(lblMedia);

    add(formPanel, BorderLayout.CENTER);

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton btnSalvar = new JButton("Salvar Notas");
    btnSalvar.setBackground(new Color(100, 149, 237));
    btnSalvar.setForeground(Color.WHITE);
    btnSalvar.setPreferredSize(new Dimension(200, 40));
    btnSalvar.setFocusPainted(false);
    btnSalvar.addActionListener(e -> salvarNotas());
    btnPanel.add(btnSalvar);

    add(btnPanel, BorderLayout.SOUTH);

    carregarNotas();
  }

  private void carregarNotas() {
    try {
      notaExistente = notaDAO.buscarPorAlunoETurma(aluno.getId(), turma.getId());
      if (notaExistente != null) {
        txtNota1.setText(String.valueOf(notaExistente.getNota1()));
        txtNota2.setText(String.valueOf(notaExistente.getNota2()));
        txtNota3.setText(String.valueOf(notaExistente.getNota3()));
        txtNota4.setText(String.valueOf(notaExistente.getNota4()));
        atualizarMedia();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void atualizarMedia() {
    if (notaExistente != null) {
      // Recalculate based on fields if we want dynamic update, but here we show saved
      // media or calc from fields
      // Let's calc from fields since we just loaded them or user edited
      try {
        double n1 = Double.parseDouble(txtNota1.getText().replace(",", "."));
        double n2 = Double.parseDouble(txtNota2.getText().replace(",", "."));
        double n3 = Double.parseDouble(txtNota3.getText().replace(",", "."));
        double n4 = Double.parseDouble(txtNota4.getText().replace(",", "."));
        double media = (n1 + n2 + n3 + n4) / 4.0;
        lblMedia.setText(String.format("%.2f", media));
      } catch (Exception e) {
        lblMedia.setText("-");
      }
    }
  }

  private void salvarNotas() {
    try {
      double n1 = Double.parseDouble(txtNota1.getText().replace(",", "."));
      double n2 = Double.parseDouble(txtNota2.getText().replace(",", "."));
      double n3 = Double.parseDouble(txtNota3.getText().replace(",", "."));
      double n4 = Double.parseDouble(txtNota4.getText().replace(",", "."));

      if (n1 < 0 || n1 > 10 || n2 < 0 || n2 > 10 || n3 < 0 || n3 > 10 || n4 < 0 || n4 > 10) {
        JOptionPane.showMessageDialog(this, "As notas devem ser entre 0 e 10.", "Erro", JOptionPane.ERROR_MESSAGE);
        return;
      }

      if (notaExistente == null) {
        Nota nova = new Nota(aluno.getId(), turma.getId(), n1, n2, n3, n4);
        notaDAO.salvar(nova);
        // Reload to get properly initialized object with ID if needed logic depends on
        // it
        notaExistente = notaDAO.buscarPorAlunoETurma(aluno.getId(), turma.getId());
      } else {
        notaExistente.setNota1(n1);
        notaExistente.setNota2(n2);
        notaExistente.setNota3(n3);
        notaExistente.setNota4(n4);
        notaDAO.atualizar(notaExistente);
      }

      atualizarMedia();
      JOptionPane.showMessageDialog(this, "Notas salvas com sucesso!");

    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(this, "Digite apenas números válidos.", "Formato Inválido",
          JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, "Erro ao salvar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }
  }
}
