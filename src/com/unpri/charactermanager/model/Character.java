package com.unpri.charactermanager.model;

import java.util.Objects;

public class Character {
    private String name;
    private CharacterType type;
    private int level;
    private int hitPoints;
    private int attack;
    private int mana;

    // Konstruktor 1: Default level 1, mana 50
    public Character(String name, CharacterType type) {
        this(name, type, 1, 50);
    }

    // Konstruktor 2: Digunakan oleh CharacterFormPanel (nama, role/type, level)
    public Character(String name, CharacterType type, int level) {
        this(name, type, level, 50);
    }

    // Konstruktor 3: Utama dengan validasi penuh
    public Character(String name, CharacterType type, int level, int mana) {
        validateInvariants(name, type, level);
        this.name = name.trim();
        this.type = type;
        this.level = level;
        calculateStats();
        setMana(mana);
    }

    private void validateInvariants(String name, CharacterType type, int level) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama karakter tidak boleh kosong.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Role karakter tidak boleh null.");
        }
        if (level < 1 || level > 99) {
            throw new IllegalArgumentException("Level karakter harus berada di antara 1 sampai 99.");
        }
        // Mini-Challenge Bagian B: Aturan khusus MAGE di lapisan Model
        if (type == CharacterType.MAGE && level > 60) {
            throw new IllegalArgumentException("Karakter dengan role MAGE tidak boleh memiliki level di atas 60.");
        }
    }

    private void calculateStats() {
        switch (this.type) {
            case WARRIOR:
                this.hitPoints = 100 + (this.level - 1) * 20;
                this.attack = 15 + (this.level - 1) * 5;
                break;
            case MAGE:
                this.hitPoints = 70 + (this.level - 1) * 10;
                this.attack = 25 + (this.level - 1) * 8;
                break;
            case ARCHER:
                this.hitPoints = 85 + (this.level - 1) * 15;
                this.attack = 20 + (this.level - 1) * 6;
                break;
            case ROGUE:
                this.hitPoints = 80 + (this.level - 1) * 12;
                this.attack = 22 + (this.level - 1) * 7;
                break;
            default:
                this.hitPoints = 80 + (this.level - 1) * 12;
                this.attack = 15 + (this.level - 1) * 5;
                break;
        }
    }

    // Getter & Setter dengan penjagaan invarian
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama karakter tidak boleh kosong.");
        }
        this.name = name.trim();
    }

    public CharacterType getType() {
        return type;
    }

    // Alias getter agar kompatibel jika dipanggil sebagai getRole()
    public CharacterType getRole() {
        return type;
    }

    public void setType(CharacterType type) {
        validateInvariants(this.name, type, this.level);
        this.type = type;
        calculateStats();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        validateInvariants(this.name, this.type, level);
        this.level = level;
        calculateStats();
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getAttack() {
        return attack;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        if (mana < 0) {
            throw new IllegalArgumentException("Mana tidak boleh negatif!");
        }
        this.mana = mana;
    }

    public boolean isAlive() {
        return this.hitPoints > 0;
    }

    // Override equals() & hashCode() berdasarkan Nama (Case-insensitive)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return name != null && character.name != null
                && name.equalsIgnoreCase(character.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name == null ? null : name.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("Character{name='%s', type=%s, level=%d, HP=%d, Attack=%d, Mana=%d}",
                name, type, level, hitPoints, attack, mana);
    }
}