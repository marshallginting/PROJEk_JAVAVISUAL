package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CharacterCard extends JPanel {

    public CharacterCard(Character character) {
        setLayout(new BorderLayout(AppTheme.PADDING_SM, AppTheme.PADDING_SM));
        setBackground(AppTheme.BG_SURFACE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER_DEFAULT, 1),
                BorderFactory.createEmptyBorder(
                        AppTheme.PADDING_MD,
                        AppTheme.PADDING_MD,
                        AppTheme.PADDING_MD,
                        AppTheme.PADDING_MD
                )
        ));

        // Bagian Atas: Nama Karakter + Badge Role & Level
        JPanel topPanel = new JPanel(new BorderLayout(AppTheme.PADDING_SM, 0));
        topPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(character.getName());
        nameLabel.setFont(AppTheme.FONT_CARD_NAME);
        nameLabel.setForeground(AppTheme.TEXT_PRIMARY);

        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        badgePanel.setOpaque(false);

        CharacterType type = character.getType();
        JLabel roleBadge = new JLabel(type.name());
        roleBadge.setFont(AppTheme.FONT_SECTION_TITLE);
        roleBadge.setForeground(Color.WHITE);
        roleBadge.setOpaque(true);
        roleBadge.setBackground(AppTheme.getRoleColor(type));
        roleBadge.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));

        JLabel levelLabel = new JLabel("Lv. " + character.getLevel());
        levelLabel.setFont(AppTheme.FONT_SECTION_TITLE);
        levelLabel.setForeground(AppTheme.TEXT_SECONDARY);

        badgePanel.add(roleBadge);
        badgePanel.add(levelLabel);

        topPanel.add(nameLabel, BorderLayout.WEST);
        topPanel.add(badgePanel, BorderLayout.EAST);

        // Bagian Bawah: Kotak Stats (HP, Attack, Mana)
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, AppTheme.PADDING_SM, 0));
        statsPanel.setBackground(AppTheme.BG_STATS_BOX);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER_DEFAULT, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        statsPanel.add(createStatBox("HP", String.valueOf(character.getHitPoints())));
        statsPanel.add(createStatBox("ATK", String.valueOf(character.getAttack())));
        statsPanel.add(createStatBox("MANA", String.valueOf(character.getMana())));

        add(topPanel, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.CENTER);
    }

    private JPanel createStatBox(String title, String value) {
        JPanel box = new JPanel(new BorderLayout());
        box.setOpaque(false);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(AppTheme.FONT_BODY);
        titleLabel.setForeground(AppTheme.TEXT_SECONDARY);

        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(AppTheme.FONT_STAT_VALUE);
        valueLabel.setForeground(AppTheme.TEXT_PRIMARY);

        box.add(titleLabel, BorderLayout.NORTH);
        box.add(valueLabel, BorderLayout.CENTER);
        return box;
    }
}
