package com.unpri.charactermanager.model;

import java.util.ArrayList;
import java.util.List;

public class CharacterManager {
    public static final int MAX_CHARACTERS = 20;
    private final List<Character> characters = new ArrayList<>();

    // Mengecek keunikan nama untuk validasi di Form & Model (Bagian A)
    public boolean isNameTaken(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return findByName(name.trim()) != null;
    }

    // Menambah karakter dengan validasi kapasitas & duplikat di lapisan Model
    public boolean addCharacter(Character c) {
        if (c == null) {
            throw new IllegalArgumentException("Data karakter tidak boleh null.");
        }
        // Aturan Validasi Baru #2 (Model): Batas maksimal 20 karakter
        if (characters.size() >= MAX_CHARACTERS) {
            throw new IllegalArgumentException("Daftar penuh: maksimal " + MAX_CHARACTERS + " karakter diperbolehkan.");
        }
        if (isNameTaken(c.getName())) {
            throw new IllegalArgumentException("Karakter dengan nama '" + c.getName() + "' sudah ada!");
        }
        characters.add(c);
        return true;
    }

    public Character findByName(String name) {
        if (name == null) {
            return null;
        }
        String target = name.trim();
        for (Character c : characters) {
            if (c.getName().equalsIgnoreCase(target)) {
                return c;
            }
        }
        return null;
    }

    public boolean removeCharacter(String name) {
        Character c = findByName(name);
        if (c != null) {
            characters.remove(c);
            return true;
        }
        return false;
    }

    public int getTotalCharacters() {
        return characters.size();
    }

    // Mengembalikan salinan (defensive copy) untuk menjaga enkapsulasi
    public List<Character> getAllCharacters() {
        return new ArrayList<>(characters);
    }

    // --- MINI-CHALLENGE SEBELUMNYA ---

    public double getAverageLevel() {
        if (characters.isEmpty()) {
            return 0.0; // Mencegah pembagian dengan nol
        }
        int totalLevel = 0;
        for (Character c : characters) {
            totalLevel += c.getLevel();
        }
        return (double) totalLevel / characters.size();
    }

    public List<Character> findByType(CharacterType type) {
        List<Character> result = new ArrayList<>();
        for (Character c : characters) {
            if (c.getType() == type) {
                result.add(c);
            }
        }
        return result;
    }

    // --- FITUR ANALITIK RANCANGAN SENDIRI ---

    /**
     * Mencari karakter dengan nilai Attack paling tinggi di dalam manager.
     * @return Character terkuat, atau null jika list kosong.
     */
    public Character findStrongestCharacter() {
        if (characters.isEmpty()) {
            return null;
        }

        Character strongest = characters.get(0);
        for (Character c : characters) {
            if (c.getAttack() > strongest.getAttack()) {
                strongest = c;
            }
        }
        return strongest;
    }
}