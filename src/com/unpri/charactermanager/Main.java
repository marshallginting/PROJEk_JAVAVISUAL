package com.unpri.charactermanager;

import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.view.MainWindow;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            // 1. Inisialisasi CharacterManager
            CharacterManager manager = new CharacterManager();
            
            // 2. Tambahkan data karakter beserta atribut lengkapnya
            manager.addCharacter(new Character("Arthur", CharacterType.WARRIOR, 10, 50));
            manager.addCharacter(new Character("Gandalf", CharacterType.MAGE, 12, 100));
            manager.addCharacter(new Character("Robin", CharacterType.ARCHER, 8, 40));
            manager.addCharacter(new Character("Lancelot", CharacterType.WARRIOR, 15, 60));
            manager.addCharacter(new Character("Merlin", CharacterType.MAGE, 11, 90));

            // 3. Tampilkan Jendela Utama GUI Swing
            MainWindow window = new MainWindow(manager);
            window.setVisible(true);
        });
    }
}