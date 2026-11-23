package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.CharacterType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public final class AppTheme {

    private AppTheme() {
    }

    // Warna Latar & Permukaan
    public static final Color BG_WINDOW = new Color(241, 245, 249);
    public static final Color BG_MAIN = BG_WINDOW; // Alias untuk CharacterFormPanel
    public static final Color BG_SURFACE = Color.WHITE;
    public static final Color BG_CARD = BG_SURFACE;
    public static final Color BG_HEADER = new Color(30, 41, 59);
    public static final Color BG_STATS_BOX = new Color(248, 250, 252);
    public static final Color BG_STATUS_BAR = new Color(226, 232, 240);

    // Warna Teks & Garis Batas
    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);
    public static final Color TEXT_ON_HEADER = Color.WHITE;
    public static final Color TEXT_HEADER_MUTED = new Color(203, 213, 225);
    public static final Color BORDER_DEFAULT = new Color(203, 213, 225);

    // Warna Status & Aksi Form (Validasi Bagian A)
    public static final Color ACCENT_PRIMARY = new Color(37, 99, 235);
    public static final Color ERROR_COLOR = new Color(220, 38, 38);
    public static final Color SUCCESS_COLOR = new Color(22, 163, 74);

    // Border Komponen
    public static final Border FORM_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_DEFAULT, 1),
            BorderFactory.createEmptyBorder(12, 16, 12, 16)
    );

    public static final Border CARD_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_DEFAULT, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
    );

    public static final Border ERROR_BORDER = BorderFactory.createLineBorder(ERROR_COLOR, 2);

    // Palet Warna Role (otomatis dipetakan ke CharacterType.values())
    private static final Color[] ROLE_COLORS = {
        new Color(220, 38, 38),   // Merah
        new Color(37, 99, 235),   // Biru
        new Color(22, 163, 74),   // Hijau
        new Color(217, 119, 6),   // Oranye
        new Color(124, 58, 237),  // Ungu
        new Color(13, 148, 136)   // Teal
    };

    // Tipografi
    public static final Font FONT_HEADER_TITLE = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FONT_HEADER_SUBTITLE = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font FONT_SECTION_TITLE = new Font("SansSerif", Font.BOLD, 13);
    public static final Font FONT_CARD_NAME = new Font("SansSerif", Font.BOLD, 14);
    public static final Font FONT_BODY = new Font("SansSerif", Font.PLAIN, 12);
    public static final Font FONT_STAT_VALUE = new Font("SansSerif", Font.BOLD, 15);
    public static final Font FONT_STATUS = new Font("Monospaced", Font.PLAIN, 12);

    // Alias Tipografi untuk Form
    public static final Font FONT_TITLE = FONT_CARD_NAME;
    public static final Font FONT_SUBTITLE = FONT_SECTION_TITLE;
    public static final Font FONT_SMALL = FONT_BODY;

    // Dimensi & Jarak
    public static final Dimension SCROLL_PREFERRED_SIZE = new Dimension(580, 400);
    public static final Dimension LEGEND_SWATCH_SIZE = new Dimension(14, 14);
    public static final int PADDING_LG = 16;
    public static final int PADDING_MD = 12;
    public static final int PADDING_SM = 8;
    public static final int CARD_GRID_GAP = 12;
    public static final int LEGEND_STRUT_HEIGHT = 8;
    public static final int SCROLL_UNIT_INCREMENT = 16;

    public static Color getRoleColor(CharacterType type) {
        if (type == null) {
            return TEXT_SECONDARY;
        }
        return ROLE_COLORS[type.ordinal() % ROLE_COLORS.length];
    }
}