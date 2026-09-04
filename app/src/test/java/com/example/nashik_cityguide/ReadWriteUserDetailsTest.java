package com.example.nashik_cityguide;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for {@link ReadWriteUserDetails}.
 *
 * These tests run purely on the JVM — no Android framework is required because
 * ReadWriteUserDetails is a plain Java POJO with no Android dependencies.
 */
public class ReadWriteUserDetailsTest {

    // ------------------------------------------------------------------ //
    //  Default (no-arg) constructor
    // ------------------------------------------------------------------ //

    @Test
    public void defaultConstructor_allFieldsAreNull() {
        ReadWriteUserDetails user = new ReadWriteUserDetails();
        assertNull("username should be null by default", user.username);
        assertNull("email should be null by default",    user.email);
        assertNull("dob should be null by default",      user.dob);
        assertNull("gender should be null by default",   user.gender);
        assertNull("mobile should be null by default",   user.mobile);
        assertNull("pass should be null by default",     user.pass);
    }

    // ------------------------------------------------------------------ //
    //  Parameterised constructor
    // ------------------------------------------------------------------ //

    @Test
    public void constructor_setsAllFieldsCorrectly() {
        ReadWriteUserDetails user = new ReadWriteUserDetails(
            "Parth",
            "parth@example.com",
            "9876543210",
            "01/01/2000",
            "Male",
            "$2a$12$hashedpasswordstring"
        );

        assertEquals("Parth",                  user.username);
        assertEquals("parth@example.com",       user.email);
        assertEquals("9876543210",              user.mobile);
        assertEquals("01/01/2000",              user.dob);
        assertEquals("Male",                    user.gender);
        assertEquals("$2a$12$hashedpasswordstring", user.pass);
    }

    @Test
    public void constructor_acceptsEmptyStrings() {
        ReadWriteUserDetails user = new ReadWriteUserDetails(
            "", "", "", "", "", ""
        );
        // Empty string is a valid (though not desirable) value — fields must not be null.
        assertEquals("", user.username);
        assertEquals("", user.email);
        assertEquals("", user.mobile);
        assertEquals("", user.dob);
        assertEquals("", user.gender);
        assertEquals("", user.pass);
    }

    @Test
    public void constructor_acceptsNullArguments() {
        // Firebase's setValue() calls the POJO constructor via reflection;
        // null arguments should not throw.
        ReadWriteUserDetails user = new ReadWriteUserDetails(
            null, null, null, null, null, null
        );
        assertNull(user.username);
        assertNull(user.email);
        assertNull(user.mobile);
        assertNull(user.dob);
        assertNull(user.gender);
        assertNull(user.pass);
    }

    // ------------------------------------------------------------------ //
    //  Public field mutation (direct access, as the class uses public fields)
    // ------------------------------------------------------------------ //

    @Test
    public void publicFields_canBeReadAndWrittenDirectly() {
        ReadWriteUserDetails user = new ReadWriteUserDetails();

        user.username = "NewUser";
        user.email    = "new@example.com";
        user.mobile   = "9123456789";
        user.dob      = "15/08/1995";
        user.gender   = "Female";
        user.pass     = "hashedValue";

        assertEquals("NewUser",         user.username);
        assertEquals("new@example.com", user.email);
        assertEquals("9123456789",      user.mobile);
        assertEquals("15/08/1995",      user.dob);
        assertEquals("Female",          user.gender);
        assertEquals("hashedValue",     user.pass);
    }

    @Test
    public void constructor_storesExactPassedValues_noTransformation() {
        // Verify the constructor does NOT trim or modify any input values.
        String nameWithSpaces = "  Parth  ";
        ReadWriteUserDetails user = new ReadWriteUserDetails(
            nameWithSpaces, "a@b.com", "9000000000", "01/01/99", "Male", "pass"
        );
        assertEquals("Username with surrounding spaces must be stored as-is",
            nameWithSpaces, user.username);
    }

    @Test
    public void twoInstances_areIndependent() {
        ReadWriteUserDetails user1 = new ReadWriteUserDetails(
            "Alice", "alice@example.com", "9111111111", "01/01/1990", "Female", "hash1"
        );
        ReadWriteUserDetails user2 = new ReadWriteUserDetails(
            "Bob", "bob@example.com", "9222222222", "02/02/1985", "Male", "hash2"
        );

        assertNotEquals(user1.username, user2.username);
        assertNotEquals(user1.email,    user2.email);
        assertNotEquals(user1.mobile,   user2.mobile);
    }
}
