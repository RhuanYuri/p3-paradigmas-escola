import dao.*;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class EscolaGUI extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    // DAOs para persistência
    private ProfessorDAO professorDAO = new ProfessorDAO();
    private TurmaDAO turmaDAO = new TurmaDAO();
    private NotaDAO notaDAO = new NotaDAO();

    // Estado da navegação
    private Professor professorSelecionado;
    private Turma turmaSelecionada;
    private Aluno alunoSelecionado;

    public EscolaGUI() {
        setTitle("Sistema Escolar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Inicializa a tela principal
        mainPanel.add(criarTelaSelecaoProfessor(), "selecaoProfessor");

        add(mainPanel);
        cardLayout.show(mainPanel, "selecaoProfessor");
    }

    // TELA 1: Seleção de Professor (Estilo Netflix)
    private JPanel criarTelaSelecaoProfessor() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(20, 20, 20));

        JLabel titulo = new JLabel("Quem está ensinando?", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel perfisPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        perfisPanel.setBackground(new Color(20, 20, 20));

        try {
            List<Professor> professores = professorDAO.listar();
            for (Professor p : professores) {
                JButton btnProfessor = new JButton(p.getNome());
                btnProfessor.setPreferredSize(new Dimension(150, 150));
                btnProfessor.setBackground(new Color(50, 50, 50));
                btnProfessor.setForeground(Color.WHITE);
                btnProfessor.setFocusPainted(false);
                btnProfessor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

                btnProfessor.addActionListener(e -> {
                    this.professorSelecionado = p;
                    exibirTelaTurmas();
                });
                perfisPanel.add(btnProfessor);
            }
        } catch (Exception e) {
            JLabel erro = new JLabel("Erro ao carregar professores: " + e.getMessage());
            erro.setForeground(Color.RED);
            perfisPanel.add(erro);
        }

        panel.add(perfisPanel, BorderLayout.CENTER);
        return panel;
    }

    // TELA 2: Listagem de Turmas com Destaque
    private void exibirTelaTurmas() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Turmas de: " + professorSelecionado.getNome());
        header.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(header, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        List<Turma> turmas = turmaDAO.listar();
        for (Turma t : turmas) model.addElement(t.getNome());

        JList<String> list = new JList<>(model);

        // AJUSTE DE DESTAQUE
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectionBackground(new Color(0, 120, 215)); // Azul destaque
        list.setSelectionForeground(Color.WHITE);
        list.setFixedCellHeight(30);

        // Atalho: Clique duplo para avançar
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    avancarParaAlunos(list, turmas);
                }
            }
        });

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        // Painel de Navegação
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> cardLayout.show(mainPanel, "selecaoProfessor"));

        JButton btnAvancar = new JButton("Ver Alunos");
        btnAvancar.addActionListener(e -> avancarParaAlunos(list, turmas));

        btnPanel.add(btnVoltar);
        btnPanel.add(btnAvancar);
        panel.add(btnPanel, BorderLayout.SOUTH);

        mainPanel.add(panel, "listagemTurmas");
        cardLayout.show(mainPanel, "listagemTurmas");
    }

    private void avancarParaAlunos(JList<String> list, List<Turma> turmas) {
        int idx = list.getSelectedIndex();
        if (idx != -1) {
            this.turmaSelecionada = turmas.get(idx);
            exibirTelaAlunos();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma turma primeiro.");
        }
    }

    // TELA 3: Listagem de Alunos com Destaque
    private void exibirTelaAlunos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Alunos da Turma: " + turmaSelecionada.getNome());
        header.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(header, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        List<Aluno> alunos = turmaSelecionada.getAlunos();
        if (alunos != null) {
            for (Aluno a : alunos) model.addElement(a.getNome() + " (" + a.getMatricula() + ")");
        }

        JList<String> list = new JList<>(model);

        // AJUSTE DE DESTAQUE
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectionBackground(new Color(0, 120, 215));
        list.setSelectionForeground(Color.WHITE);
        list.setFixedCellHeight(30);

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    avancarParaNotas(list, alunos);
                }
            }
        });

        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> exibirTelaTurmas());

        JButton btnNotas = new JButton("Gerenciar Notas");
        btnNotas.addActionListener(e -> avancarParaNotas(list, alunos));

        btnPanel.add(btnVoltar);
        btnPanel.add(btnNotas);
        panel.add(btnPanel, BorderLayout.SOUTH);

        mainPanel.add(panel, "listagemAlunos");
        cardLayout.show(mainPanel, "listagemAlunos");
    }

    private void avancarParaNotas(JList<String> list, List<Aluno> alunos) {
        int idx = list.getSelectedIndex();
        if (idx != -1) {
            this.alunoSelecionado = alunos.get(idx);
            exibirTelaNotas();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um aluno primeiro.");
        }
    }

    // TELA 4: Gerenciamento de Notas
    private void exibirTelaNotas() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel gridPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        Nota notaObj = notaDAO.buscarPorAlunoETurma(alunoSelecionado.getId(), turmaSelecionada.getId());

        JTextField[] fields = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            gridPanel.add(new JLabel("Nota " + (i + 1) + ":"));
            fields[i] = new JTextField();
            if (notaObj != null) {
                double v = (i==0)? notaObj.getNota1() : (i==1)? notaObj.getNota2() : (i==2)? notaObj.getNota3() : notaObj.getNota4();
                fields[i].setText(String.valueOf(v));
            }
            gridPanel.add(fields[i]);
        }

        gridPanel.add(new JLabel("Média Atual:"));
        JLabel mediaLabel = new JLabel(notaObj != null ? String.format("%.2f", notaObj.calcularMedia()) : "N/A");
        mediaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gridPanel.add(mediaLabel);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> exibirTelaAlunos());

        JButton btnSalvar = new JButton("Salvar Notas");
        btnSalvar.addActionListener(e -> {
            try {
                Nota novaNota = new Nota(
                        alunoSelecionado.getId(),
                        turmaSelecionada.getId(),
                        Double.parseDouble(fields[0].getText().replace(",", ".")),
                        Double.parseDouble(fields[1].getText().replace(",", ".")),
                        Double.parseDouble(fields[2].getText().replace(",", ".")),
                        Double.parseDouble(fields[3].getText().replace(",", "."))
                );

                if (notaObj == null) notaDAO.salvar(novaNota);
                else notaDAO.atualizar(novaNota);

                JOptionPane.showMessageDialog(this, "Notas salvas!");
                exibirTelaAlunos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Digite apenas números.");
            }
        });

        btnPanel.add(btnVoltar);
        btnPanel.add(btnSalvar);

        contentPanel.add(new JLabel("Notas de: " + alunoSelecionado.getNome()), BorderLayout.NORTH);
        contentPanel.add(gridPanel, BorderLayout.CENTER);
        contentPanel.add(btnPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, "gerenciamentoNotas");
        cardLayout.show(mainPanel, "gerenciamentoNotas");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new EscolaGUI().setVisible(true));
    }
}