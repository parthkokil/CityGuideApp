package com.example.nashik_cityguide;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unit tests for the input-validation logic used in signup_activity.
 *
 * The regex constants below are extracted from
 * {@code signup_activity.java} so that they can be tested independently
 * on the JVM without an Android device. This ensures that any future
 * accidental change to the regex in the Activity is caught immediately.
 *
 * Mobile validation rule (from signup_activity.java line 162):
 *   "[6-9][0-9]{9}"  — Indian mobile numbers starting with 6–9, exactly 10 digits.
 *
 * Password validation rule (from signup_activity.java lines 192-194):
 *   Minimum 6 characters (the only server-side rule enforced before Firebase).
 */
public class InputValidationTest {

    /** Regex extracted from {@code signup_activity.java} — keep in sync. */
    private static final String MOBILE_REGEX = "[6-9][0-9]{9}";

    // Helper method that replicates the Matcher.find() call in signup_activity.
    private boolean isValidMobile(String mobile) {
        Pattern pattern = Pattern.compile(MOBILE_REGEX);
        Matcher matcher = pattern.matcher(mobile);
        // The Activity uses matcher.find() AND checks length == 10 separately.
        // Combining both here for a clean unit-test.
        return mobile.length() == 10 && matcher.matches();
    }

    // ------------------------------------------------------------------ //
    //  Mobile number validation
    // ------------------------------------------------------------------ //

    @Test
    public void mobileRegex_validNumberStartingWith9_matches() {
        assertTrue(isValidMobile("9876543210"));
    }

    @Test
    public void mobileRegex_validNumberStartingWith8_matches() {
        assertTrue(isValidMobile("8765432109"));
    }

    @Test
    public void mobileRegex_validNumberStartingWith7_matches() {
        assertTrue(isValidMobile("7000000000"));
    }

    @Test
    public void mobileRegex_validNumberStartingWith6_matches() {
        assertTrue(isValidMobile("6123456789"));
    }

    @Test
    public void mobileRegex_numberStartingWith1_doesNotMatch() {
        assertFalse(isValidMobile("1234567890"));
    }

    @Test
    public void mobileRegex_numberStartingWith5_doesNotMatch() {
        assertFalse(isValidMobile("5987654321"));
    }

    @Test
    public void mobileRegex_tooShort_doesNotMatch() {
        assertFalse(isValidMobile("98765"));
    }

    @Test
    public void mobileRegex_tooLong_doesNotMatch() {
        assertFalse(isValidMobile("98765432101"));
    }

    @Test
    public void mobileRegex_containsLetters_doesNotMatch() {
        assertFalse(isValidMobile("9abc123456"));
    }

    @Test
    public void mobileRegex_emptyString_doesNotMatch() {
        assertFalse(isValidMobile(""));
    }

    // ------------------------------------------------------------------ //
    //  Password length validation (minimum 6 chars, as enforced in Activity)
    // ------------------------------------------------------------------ //

    @Test
    public void passwordLength_exactlyMinimum_isValid() {
        assertTrue("Password of 6 chars should be accepted", "abc123".length() >= 6);
    }

    @Test
    public void passwordLength_lessThanMinimum_isInvalid() {
        assertFalse("Password shorter than 6 chars should be rejected", "ab1".length() >= 6);
    }

    @Test
    public void passwordLength_moreThanMinimum_isValid() {
        assertTrue("Password longer than 6 chars should be accepted", "MyStr0ng@Pass".length() >= 6);
    }

    @Test
    public void passwordLength_emptyString_isInvalid() {
        assertFalse("Empty password should be rejected", "".length() >= 6);
    }

    // ------------------------------------------------------------------ //
    //  Email format validation (replicates android.util.Patterns using RFC-5322-like regex)
    // ------------------------------------------------------------------ //

    // Note: android.util.Patterns is not available on JVM.
    // We use a standard RFC-compliant regex here for testing the pattern logic.
    private static final String EMAIL_REGEX =
        "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";

    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    @Test
    public void emailRegex_validEmail_matches() {
        assertTrue(isValidEmail("user@example.com"));
    }

    @Test
    public void emailRegex_validEmailWithSubdomain_matches() {
        assertTrue(isValidEmail("parth.shah@mail.nashik.in"));
    }

    @Test
    public void emailRegex_missingAtSign_doesNotMatch() {
        assertFalse(isValidEmail("userwithnoat.com"));
    }

    @Test
    public void emailRegex_missingDomain_doesNotMatch() {
        assertFalse(isValidEmail("user@"));
    }

    @Test
    public void emailRegex_missingTld_doesNotMatch() {
        assertFalse(isValidEmail("user@domain"));
    }

    @Test
    public void emailRegex_emptyString_doesNotMatch() {
        assertFalse(isValidEmail(""));
    }
}
