package com.unpri.charactermanager.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class AppTheme {
    public static final Color COLOR_PRIMARY = Color.decode("#0478ed");
    public static final Color COLOR_ACCENT = Color.decode("#571710");
    public static final Color COLOR_GOLD = Color.decode("#625212");
    public static final Color COLOR_BACKGROUND = Color.decode("#ECF0F1");
    
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_REGULAR = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 12);
    
    public static final Border BORDER_CARD = BorderFactory.createLineBorder(Color.GRAY, 1);

    public static Color colorFor(String role) {
        if (role == null) return COLOR_PRIMARY;
        return switch (role.toUpperCase()) {
            case "WARRIOR" -> Color.decode("#3498DB");
            case "MAGE" -> Color.decode("#9B59B6");
            case "ARCHER" -> Color.decode("#E67E22");
            case "ROGUE" -> Color.decode("#1ABC9C");
            default -> COLOR_PRIMARY;
        };
    }
}