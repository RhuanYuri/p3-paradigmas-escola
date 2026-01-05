package view;

import javax.swing.*;
import java.awt.*;

public class TelaHome extends JFrame {

    public TelaHome() {
        setTitle("Sistema Escolar");
        setSize(520, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // PAINEL PRINCIPAL
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(Color.WHITE);
        add(painelPrincipal);

        // TOPO
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(173, 216, 230));
        painelTopo.setPreferredSize(new Dimension(520, 80));
        painelTopo.setLayout(new GridBagLayout());

        JLabel lblTitulo = new JLabel("Sistema Escolar");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(40, 40, 40));

        painelTopo.add(lblTitulo);
        painelPrincipal.add(painelTopo, BorderLayout.NORTH);

        // MEIO
        JPanel painelCentro = new JPanel();
        painelCentro.setBackground(Color.WHITE);
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel lblSubtitulo = new JLabel("Bem-vindo!");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDescricao = new JLabel(
                "<html><div style=text-align: center;>"
                        + " Gerencie alunos, professores e turmas<br>" +
                        "com eficiência, organização e simplicidade." +
                        "</div></html>",
                SwingConstants.CENTER
        );
        lblDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDescricao.setForeground(Color.DARK_GRAY);
        lblDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);

        painelCentro.add(lblSubtitulo);
        painelCentro.add(Box.createVerticalStrut(15));
        painelCentro.add(lblDescricao);
        painelCentro.add(Box.createVerticalStrut(35));

        JButton btnEntrar = new JButton("Entrar no Sistema");
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.setPreferredSize(new Dimension(220, 45));
        btnEntrar.setMaximumSize(new Dimension(220, 45));
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnEntrar.setBackground(new Color(100, 149, 237));
        btnEntrar.setForeground(Color.BLACK);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEntrar.addActionListener(e -> {
            dispose();
            new MenuPrincipal();
        });

        painelCentro.add(btnEntrar);
        painelPrincipal.add(painelCentro, BorderLayout.CENTER);

        // RODAPE
        JPanel painelRodape = new JPanel();
        painelRodape.setBackground(Color.WHITE);

        JLabel lblRodape = new JLabel("Projeto acadêmico • Paradigmas de Programação");
        lblRodape.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblRodape.setForeground(Color.GRAY);

        painelRodape.add(lblRodape);
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

        setVisible(true);
    }
}
