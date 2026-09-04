package com.example.nashik_cityguide;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link PasswordHashingSecurity}.
 *
 * These tests run on the JVM (no Android device or emulator needed) because
 * PasswordHashingSecurity is pure Java backed by the jBCrypt library.
 */
public class PasswordHashingSecurityTest {

    // ------------------------------------------------------------------ //
    //  hashPassword() tests
    // ------------------------------------------------------------------ //

    @Test
    public void hashPassword_returnsNonNull() {
        String hash = PasswordHashingSecurity.hashPassword("TestPass@1");
        assertNotNull("Hash must not be null", hash);
        assertFalse("Hash must not be empty", hash.isEmpty());
    }

    @Test
    public void hashPassword_returnsBcryptFormat() {
        // BCrypt hashes always start with $2a$ (or $2b$)
        String hash = PasswordHashingSecurity.hashPassword("TestPass@1");
        assertTrue(
            "Hash should start with BCrypt prefix $2a$ or $2b$",
            hash.startsWith("$2a$") || hash.startsWith("$2b$")
        );
    }

    @Test
    public void hashPassword_differentSaltsProduceDifferentHashes() {
        // Each call to hashPassword() should generate a unique salt, so two
        // hashes of the same plain-text must not be equal.
        String hash1 = PasswordHashingSecurity.hashPassword("SamePassword1");
        String hash2 = PasswordHashingSecurity.hashPassword("SamePassword1");
        assertNotEquals(
            "Two hashes of the same password must differ (unique salts)",
            hash1, hash2
        );
    }

    @Test
    public void hashPassword_emptyStringIsHandledGracefully() {
        // BCrypt permits hashing an empty string.
        String hash = PasswordHashingSecurity.hashPassword("");
        assertNotNull("Hash of empty string must not be null", hash);
        assertFalse("Hash of empty string must not be empty", hash.isEmpty());
    }

    @Test
    public void hashPassword_longPasswordIsHandledGracefully() {
        // BCrypt truncates at 72 bytes; this test ensures no exception is thrown.
        String longPass = "A@1" + "abcdefghijklmnopqrstuvwxyz".repeat(4);
        String hash = PasswordHashingSecurity.hashPassword(longPass);
        assertNotNull("Hash of long password must not be null", hash);
    }

    // ------------------------------------------------------------------ //
    //  checkPassword() tests
    // ------------------------------------------------------------------ //

    @Test
    public void checkPassword_correctPasswordReturnsTrue() {
        String plain = "MySecure@99";
        String hash  = PasswordHashingSecurity.hashPassword(plain);
        assertTrue(
            "checkPassword must return true for the correct plain-text password",
            PasswordHashingSecurity.checkPassword(plain, hash)
        );
    }

    @Test
    public void checkPassword_wrongPasswordReturnsFalse() {
        String hash = PasswordHashingSecurity.hashPassword("CorrectPass@1");
        assertFalse(
            "checkPassword must return false for a wrong password",
            PasswordHashingSecurity.checkPassword("WrongPass@9", hash)
        );
    }

    @Test
    public void checkPassword_caseSensitivePasswordReturnsFalse() {
        // Passwords are case-sensitive; lowercase version must not match.
        String plain = "CaseSensitive@1";
        String hash  = PasswordHashingSecurity.hashPassword(plain);
        assertFalse(
            "checkPassword must return false when case differs",
            PasswordHashingSecurity.checkPassword("casesensitive@1", hash)
        );
    }

    @Test
    public void checkPassword_emptyVsCorrectPasswordReturnsFalse() {
        String hash = PasswordHashingSecurity.hashPassword("NotEmpty@1");
        assertFalse(
            "checkPassword must return false when an empty string is tested against a real hash",
            PasswordHashingSecurity.checkPassword("", hash)
        );
    }

    @Test
    public void hashAndVerify_roundTripIsConsistent() {
        // Round-trip: hash → verify → re-hash → verify again.
        String password = "RoundTrip@77";
        String hash1 = PasswordHashingSecurity.hashPassword(password);
        assertTrue(PasswordHashingSecurity.checkPassword(password, hash1));

        // A second independent hash must also verify correctly.
        String hash2 = PasswordHashingSecurity.hashPassword(password);
        assertTrue(PasswordHashingSecurity.checkPassword(password, hash2));
    }
}
