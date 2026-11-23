package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class CharacterFormPanel extends JPanel {

    private final CharacterManager manager;
    private final Runnable onAdded;

    private final JTextField nameField;
    private final JComboBox<CharacterType> roleCombo;
    private final JSpinner levelSpinner;
    private final JLabel messageLabel;
    private final Border defaultNameBorder;

    public CharacterFormPanel(CharacterManager manager, Runnable onAdded) {
        this.manager = manager;
        this.onAdded = onAdded;

        setLayout(new BorderLayout(0, AppTheme.PADDING_MD));
        setBackground(AppTheme.BG_SURFACE);
        setBorder(AppTheme.FORM_BORDER);
        setPreferredSize(new Dimension(270, 310));

        // Judul Form
        JLabel titleLabel = new JLabel("Tambah Karakter Baru");
        titleLabel.setFont(AppTheme.FONT_SECTION_TITLE);
        titleLabel.setForeground(AppTheme.TEXT_PRIMARY);
        add(titleLabel, BorderLayout.NORTH);

        // Panel Input (GridBagLayout)
        JPanel formGrid = new JPanel(new GridBagLayout());
        formGrid.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;

        // 1. Input Nama (JTextField)
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 4, 0);
        JLabel nameLabel = new JLabel("Nama Karakter:");
        nameLabel.setFont(AppTheme.FONT_BODY);
        nameLabel.setForeground(AppTheme.TEXT_PRIMARY);
        formGrid.add(nameLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        nameField = new JTextField(16);
        nameField.setFont(AppTheme.FONT_BODY);
        defaultNameBorder = nameField.getBorder();
        formGrid.add(nameField, gbc);

        // 2. Input Role (JComboBox<CharacterType>)
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 4, 0);
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(AppTheme.FONT_BODY);
        roleLabel.setForeground(AppTheme.TEXT_PRIMARY);
        formGrid.add(roleLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        roleCombo = new JComboBox<>(CharacterType.values());
        roleCombo.setFont(AppTheme.FONT_BODY);
        formGrid.add(roleCombo, gbc);

        // 3. Input Level (JSpinner 1 - 99)
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 4, 0);
        JLabel levelLabel = new JLabel("Level (1–99):");
        levelLabel.setFont(AppTheme.FONT_BODY);
        levelLabel.setForeground(AppTheme.TEXT_PRIMARY);
        formGrid.add(levelLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 10, 0);
        levelSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
        levelSpinner.setFont(AppTheme.FONT_BODY);
        formGrid.add(levelSpinner, gbc);

        // 4. Label Umpan Balik Inline
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 10, 0);
        messageLabel = new JLabel(" ");
        messageLabel.setFont(AppTheme.FONT_BODY);
        formGrid.add(messageLabel, gbc);

        // 5. Tombol Submit
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        JButton submitButton = new JButton("Tambah Karakter");
        submitButton.setFont(AppTheme.FONT_SECTION_TITLE);
        submitButton.setBackground(AppTheme.ACCENT_PRIMARY);
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> handleSubmit());
        formGrid.add(submitButton, gbc);

        add(formGrid, BorderLayout.CENTER);
    }

    /**
     * Validasi murni pada lapisan View (tidak menyentuh komponen UI).
     * Urutan pemeriksaan: kelengkapan -> panjang -> format -> keunikan.
     */
    private String validateName(String rawName) {
        // 1. Kelengkapan
        if (rawName == null || rawName.trim().isEmpty()) {
            return "Nama karakter wajib diisi.";
        }

        String trimmed = rawName.trim();

        // 2. Panjang
        if (trimmed.length() < 3 || trimmed.length() > 20) {
            return "Nama harus 3–20 karakter.";
        }

        // 3. Format (termasuk Aturan Baru #1: larangan spasi ganda)
        if (trimmed.contains("  ")) {
            return "Nama tidak boleh mengandung spasi ganda.";
        }
        if (!trimmed.matches("[a-zA-Z0-9 ]+")) {
            return "Nama hanya boleh huruf, angka, dan spasi.";
        }

        // 4. Keunikan
        if (manager.isNameTaken(trimmed)) {
            return "Nama '" + trimmed + "' sudah digunakan.";
        }

        return null;
    }

    private void handleSubmit() {
        // Pulihkan border asli setiap percobaan submit
        nameField.setBorder(defaultNameBorder);
        messageLabel.setText(" ");

        String rawName = nameField.getText();
        String validationError = validateName(rawName);
        if (validationError != null) {
            showNameError(validationError);
            return;
        }

        int level;
        try {
            levelSpinner.commitEdit();
            level = (Integer) levelSpinner.getValue();
        } catch (ParseException | ClassCastException ex) {
            showInlineError("Level harus angka bulat 1–99.");
            levelSpinner.requestFocusInWindow();
            return;
        }

        CharacterType selectedRole = (CharacterType) roleCombo.getSelectedItem();
        String cleanName = rawName.trim();

        try {
            // Validasi lapisan Model (termasuk Mini-Challenge MAGE <= 60 & max 20 karakter)
            Character newChar = new Character(cleanName, selectedRole, level);
            manager.addCharacter(newChar);

            // Reset field setelah sukses
            nameField.setText("");
            nameField.setBorder(defaultNameBorder);
            roleCombo.setSelectedIndex(0);
            levelSpinner.setValue(1);

            messageLabel.setForeground(AppTheme.SUCCESS_COLOR);
            messageLabel.setText("<html>Karakter <b>" + cleanName + "</b> ditambahkan.</html>");

            if (onAdded != null) {
                onAdded.run();
            }
        } catch (IllegalArgumentException ex) {
            showInlineError(ex.getMessage());
        }
    }

    private void showNameError(String msg) {
        showInlineError(msg);
        nameField.setBorder(AppTheme.ERROR_BORDER);
        nameField.requestFocusInWindow();
    }

    private void showInlineError(String msg) {
        messageLabel.setForeground(AppTheme.ERROR_COLOR);
        messageLabel.setText("<html>" + msg + "</html>");
    }
}