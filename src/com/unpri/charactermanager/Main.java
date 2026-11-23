package com.unpri.charactermanager;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.view.MainWindow;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CharacterManager manager = new CharacterManager();
            CharacterType[] roles = CharacterType.values();

            CharacterType role0 = roles[0 % roles.length];
            CharacterType role1 = roles[1 % roles.length];
            CharacterType role2 = roles[2 % roles.length];
            CharacterType role3 = roles[3 % roles.length];

            // 9 karakter awal dimasukkan ke CharacterManager agar terdeteksi oleh form & validasi
            manager.addCharacter(new Character("Aldric Stormshield", role0, 38, 80));
            manager.addCharacter(new Character("Lyra Dawnwhisper", role1, 29, 150));
            manager.addCharacter(new Character("Kaelen Swiftbow", role2, 17, 90));
            manager.addCharacter(new Character("Seraphina Lightbringer", role3, 44, 180));
            manager.addCharacter(new Character("Thorin Ironfoot", role0, 12, 60));
            manager.addCharacter(new Character("Vespera Shadowveil", role1, 33, 140));
            manager.addCharacter(new Character("Rowan Oakenshield", role2, 8, 55));
            manager.addCharacter(new Character("Isolde Frostweaver", role3, 26, 130));
            manager.addCharacter(new Character("Garrick Flameblade", role0, 47, 95));

            MainWindow mainWindow = new MainWindow(manager);
            mainWindow.setVisible(true);
        });
    }
}