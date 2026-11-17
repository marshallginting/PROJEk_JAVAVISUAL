package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharacterCard extends JPanel {

    public CharacterCard(Character character) {
        setLayout(new BorderLayout(AppTheme.PADDING_SM, AppTheme.PADDING_SM));
        setBackground(AppTheme.BG_SURFACE);

        Color roleColor = AppTheme.getRoleColor(character.getType());

        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, roleColor),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER_DEFAULT, 1),
                BorderFactory.createEmptyBorder(
                    AppTheme.PADDING_MD, AppTheme.PADDING_MD,
                    AppTheme.PADDING_MD, AppTheme.PADDING_MD
                )
            )
        ));

        // Baris Atas: Nama (kiri) & Level (kanan)
        JPanel topRow = new JPanel(new BorderLayout(AppTheme.PADDING_SM, 0));
        topRow.setOpaque(false);

        JLabel nameLabel = new JLabel(character.getName());
        nameLabel.setFont(AppTheme.FONT_CARD_NAME);
        nameLabel.setForeground(AppTheme.TEXT_PRIMARY);

        JLabel levelLabel = new JLabel("Lv. " + character.getLevel());
        levelLabel.setFont(AppTheme.FONT_SECTION_TITLE);
        levelLabel.setForeground(roleColor);

        topRow.add(nameLabel, BorderLayout.WEST);
        topRow.add(levelLabel, BorderLayout.EAST);

        // Baris Bawah: Role & Tier Level (GridLayout 1x2)
        JPanel detailGrid = new JPanel(new GridLayout(1, 2, AppTheme.PADDING_SM, 0));
        detailGrid.setOpaque(false);

        JLabel roleLabel = new JLabel("Role: " + character.getType().name());
        roleLabel.setFont(AppTheme.FONT_BODY);
        roleLabel.setForeground(AppTheme.TEXT_SECONDARY);

        String tierText = character.getLevel() >= 25 ? "Tier: Veteran" : "Tier: Adventurer";
        JLabel tierLabel = new JLabel(tierText);
        tierLabel.setFont(AppTheme.FONT_BODY);
        tierLabel.setForeground(AppTheme.TEXT_SECONDARY);

        detailGrid.add(roleLabel);
        detailGrid.add(tierLabel);

        add(topRow, BorderLayout.NORTH);
        add(detailGrid, BorderLayout.CENTER);
    }
}