package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainWindow extends JFrame {

    private final CharacterManager manager;
    private final JPanel cardGridPanel;
    private final JLabel statusLeftLabel;
    private final JLabel totalCharValueLabel;
    private final JLabel avgLevelValueLabel;
    private final JLabel maxLevelValueLabel;
    private final JLabel totalRoleValueLabel;

    public MainWindow() {
        this(new CharacterManager());
    }

    public MainWindow(CharacterManager manager) {
        super("Character Manager — MainWindow v0.6");
        this.manager = manager;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(AppTheme.PADDING_MD, AppTheme.PADDING_MD));
        getContentPane().setBackground(AppTheme.BG_WINDOW);

        // Label nilai untuk panel tambahan statistik (GridLayout 2x2)
        totalCharValueLabel = createStatValueLabel("0");
        avgLevelValueLabel = createStatValueLabel("0.0");
        maxLevelValueLabel = createStatValueLabel("0");
        totalRoleValueLabel = createStatValueLabel(String.valueOf(CharacterType.values().length));

        // 1. NORTH: Header + Panel Statistik Tambahan (GridLayout 2x2)
        JPanel northSection = new JPanel(new BorderLayout());
        northSection.add(createHeaderPanel(), BorderLayout.NORTH);
        northSection.add(createStatsPanel(), BorderLayout.CENTER);
        add(northSection, BorderLayout.NORTH);

        // 2. WEST: Form Input Karakter Baru (Bagian A)
        JPanel westWrapper = new JPanel(new BorderLayout());
        westWrapper.setBackground(AppTheme.BG_WINDOW);
        westWrapper.setBorder(BorderFactory.createEmptyBorder(
            AppTheme.PADDING_SM, AppTheme.PADDING_MD,
            AppTheme.PADDING_SM, 0
        ));
        westWrapper.add(new CharacterFormPanel(this.manager, this::refreshList), BorderLayout.NORTH);
        add(westWrapper, BorderLayout.WEST);

        // 3. CENTER: Daftar Kartu Berscroll (GridLayout di NORTH wrapper -> JScrollPane)
        cardGridPanel = new JPanel(new GridLayout(0, 2, AppTheme.CARD_GRID_GAP, AppTheme.CARD_GRID_GAP));
        cardGridPanel.setBackground(AppTheme.BG_WINDOW);

        JPanel cardWrapperPanel = new JPanel(new BorderLayout());
        cardWrapperPanel.setBackground(AppTheme.BG_WINDOW);
        cardWrapperPanel.setBorder(BorderFactory.createEmptyBorder(
            AppTheme.PADDING_SM, AppTheme.PADDING_SM,
            AppTheme.PADDING_SM, AppTheme.PADDING_SM
        ));
        cardWrapperPanel.add(cardGridPanel, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(cardWrapperPanel);
        scroll.setPreferredSize(AppTheme.SCROLL_PREFERRED_SIZE);
        scroll.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER_DEFAULT, 1));
        scroll.getVerticalScrollBar().setUnitIncrement(AppTheme.SCROLL_UNIT_INCREMENT);
        add(scroll, BorderLayout.CENTER);

        // 4. EAST: Legenda Role dengan BoxLayout Y_AXIS, strut, dan glue
        add(createLegendPanel(), BorderLayout.EAST);

        // 5. SOUTH: Bar Status (Teks kiri & "v0.6" di kanan)
        statusLeftLabel = new JLabel("Siap memuat data karakter...");
        add(createStatusBarPanel(), BorderLayout.SOUTH);

        // Muat data awal jika manager sudah terisi
        refreshList();

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppTheme.BG_HEADER);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(
            AppTheme.PADDING_MD, AppTheme.PADDING_LG,
            AppTheme.PADDING_MD, AppTheme.PADDING_LG
        ));

        JPanel titleContainer = new JPanel();
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.setOpaque(false);

        JLabel titleLabel = new JLabel("Daftar Karakter Party");
        titleLabel.setFont(AppTheme.FONT_HEADER_TITLE);
        titleLabel.setForeground(AppTheme.TEXT_ON_HEADER);

        JLabel subtitleLabel = new JLabel("Manajemen Roster, Level, dan Distribusi Role");
        subtitleLabel.setFont(AppTheme.FONT_HEADER_SUBTITLE);
        subtitleLabel.setForeground(AppTheme.TEXT_HEADER_MUTED);

        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(4));
        titleContainer.add(subtitleLabel);

        headerPanel.add(titleContainer, BorderLayout.WEST);
        return headerPanel;
    }

    private JPanel createStatsPanel() {
        JPanel statsGrid = new JPanel(new GridLayout(2, 2, AppTheme.PADDING_SM, AppTheme.PADDING_SM));
        statsGrid.setBackground(AppTheme.BG_WINDOW);
        statsGrid.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, AppTheme.BORDER_DEFAULT),
            BorderFactory.createEmptyBorder(
                AppTheme.PADDING_SM, AppTheme.PADDING_MD,
                AppTheme.PADDING_SM, AppTheme.PADDING_MD
            )
        ));

        statsGrid.add(createStatCard("Total Karakter", totalCharValueLabel));
        statsGrid.add(createStatCard("Rata-rata Level", avgLevelValueLabel));
        statsGrid.add(createStatCard("Level Tertinggi", maxLevelValueLabel));
        statsGrid.add(createStatCard("Jumlah Role", totalRoleValueLabel));

        return statsGrid;
    }

    private JPanel createStatCard(String caption, JLabel valueLabel) {
        JPanel box = new JPanel(new BorderLayout(AppTheme.PADDING_SM, 0));
        box.setBackground(AppTheme.BG_STATS_BOX);
        box.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.BORDER_DEFAULT, 1),
            BorderFactory.createEmptyBorder(
                AppTheme.PADDING_SM, AppTheme.PADDING_MD,
                AppTheme.PADDING_SM, AppTheme.PADDING_MD
            )
        ));

        JLabel captionLabel = new JLabel(caption);
        captionLabel.setFont(AppTheme.FONT_BODY);
        captionLabel.setForeground(AppTheme.TEXT_SECONDARY);

        box.add(captionLabel, BorderLayout.WEST);
        box.add(valueLabel, BorderLayout.EAST);
        return box;
    }

    private JLabel createStatValueLabel(String initialValue) {
        JLabel label = new JLabel(initialValue);
        label.setFont(AppTheme.FONT_STAT_VALUE);
        label.setForeground(AppTheme.TEXT_PRIMARY);
        return label;
    }

    private JPanel createLegendPanel() {
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBackground(AppTheme.BG_SURFACE);
        legendPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, AppTheme.BORDER_DEFAULT),
            BorderFactory.createEmptyBorder(
                AppTheme.PADDING_MD, AppTheme.PADDING_MD,
                AppTheme.PADDING_MD, AppTheme.PADDING_LG
            )
        ));

        JLabel legendHeader = new JLabel("Legenda Role");
        legendHeader.setFont(AppTheme.FONT_SECTION_TITLE);
        legendHeader.setForeground(AppTheme.TEXT_PRIMARY);
        legendHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

        legendPanel.add(legendHeader);
        legendPanel.add(Box.createVerticalStrut(AppTheme.PADDING_MD));

        for (CharacterType type : CharacterType.values()) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, AppTheme.PADDING_SM, 2));
            rowPanel.setOpaque(false);
            rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            rowPanel.setMaximumSize(rowPanel.getPreferredSize());

            JPanel colorBox = new JPanel();
            colorBox.setBackground(AppTheme.getRoleColor(type));
            colorBox.setPreferredSize(AppTheme.LEGEND_SWATCH_SIZE);
            colorBox.setBorder(BorderFactory.createLineBorder(AppTheme.BORDER_DEFAULT, 1));

            JLabel typeLabel = new JLabel(type.name());
            typeLabel.setFont(AppTheme.FONT_BODY);
            typeLabel.setForeground(AppTheme.TEXT_PRIMARY);

            rowPanel.add(colorBox);
            rowPanel.add(typeLabel);

            legendPanel.add(rowPanel);
            legendPanel.add(Box.createVerticalStrut(AppTheme.LEGEND_STRUT_HEIGHT));
        }

        legendPanel.add(Box.createVerticalGlue());
        return legendPanel;
    }

    private JPanel createStatusBarPanel() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(AppTheme.BG_STATUS_BAR);
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, AppTheme.BORDER_DEFAULT),
            BorderFactory.createEmptyBorder(
                AppTheme.PADDING_SM, AppTheme.PADDING_MD,
                AppTheme.PADDING_SM, AppTheme.PADDING_MD
            )
        ));

        statusLeftLabel.setFont(AppTheme.FONT_STATUS);
        statusLeftLabel.setForeground(AppTheme.TEXT_PRIMARY);

        JLabel versionLabel = new JLabel("v0.6");
        versionLabel.setFont(AppTheme.FONT_STATUS);
        versionLabel.setForeground(AppTheme.TEXT_SECONDARY);

        statusBar.add(statusLeftLabel, BorderLayout.WEST);
        statusBar.add(versionLabel, BorderLayout.EAST);
        return statusBar;
    }

    public final void refreshList() {
        setCharacters(manager.getAllCharacters());
    }

    public void setCharacters(List<Character> characters) {
        cardGridPanel.removeAll();

        int sumLevel = 0;
        int highestLevel = 0;

        for (Character character : characters) {
            cardGridPanel.add(new CharacterCard(character));
            int lvl = character.getLevel();
            sumLevel += lvl;
            if (lvl > highestLevel) {
                highestLevel = lvl;
            }
        }

        int total = characters.size();
        double average = total == 0 ? 0.0 : (double) sumLevel / total;

        totalCharValueLabel.setText(String.valueOf(total));
        avgLevelValueLabel.setText(String.format("%.1f", average));
        maxLevelValueLabel.setText(String.valueOf(highestLevel));
        statusLeftLabel.setText("Menampilkan " + total + " karakter aktif");

        cardGridPanel.revalidate();
        cardGridPanel.repaint();
    }
}