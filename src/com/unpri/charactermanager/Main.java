package com.unpri.charactermanager;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.view.MainWindow;
import java.util.List;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CharacterType[] roles = CharacterType.values();

            CharacterType role0 = roles[0 % roles.length];
            CharacterType role1 = roles[1 % roles.length];
            CharacterType role2 = roles[2 % roles.length];
            CharacterType role3 = roles[3 % roles.length];

            // 9 karakter dengan format (nama, role, level, mana)
            List<Character> characters = List.of(
                new Character("Aldric Stormshield", role0, 38, 80),
                new Character("Lyra Dawnwhisper", role1, 29, 150),
                new Character("Kaelen Swiftbow", role2, 17, 90),
                new Character("Seraphina Lightbringer", role3, 44, 180),
                new Character("Thorin Ironfoot", role0, 12, 60),
                new Character("Vespera Shadowveil", role1, 33, 140),
                new Character("Rowan Oakenshield", role2, 8, 55),
                new Character("Isolde Frostweaver", role3, 26, 130),
                new Character("Garrick Flameblade", role0, 47, 95)
            );

            MainWindow mainWindow = new MainWindow();
            mainWindow.setCharacters(characters);
            mainWindow.setVisible(true);
        });
    }
}