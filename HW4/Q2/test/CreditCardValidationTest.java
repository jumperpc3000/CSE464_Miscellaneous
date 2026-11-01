import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardValidationTest {
    @Test
    void c0RejectWrongLength() {
        CreditCardValidation.Validate(1000000000000008L);
        String actual = CreditCardValidation.Validate(12345678L);

        assertAll(
                () -> assertArrayEquals(new int[16],
                        CreditCardValidation.getDigitArrayForTesting(),
                        "digitArray after length rejection"),
                () -> assertEquals(0,
                        CreditCardValidation.getFinalSumForTesting(),
                        "finalSum initialization after length rejection"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }

    @Test
    void c1AcceptSimpleValidChecksum() {
        String actual = CreditCardValidation.Validate(1000000000000008L);
        int[] expectedDigits = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidation.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(10,
                        CreditCardValidation.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Valid Card", actual, "verdict"));
    }

    @Test
    void c2RejectSimpleInvalidChecksum() {
        String actual = CreditCardValidation.Validate(1000000000000001L);
        int[] expectedDigits = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidation.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(3,
                        CreditCardValidation.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }

    @Test
    void c3IncludeDigitAtIndexOne() {
        String actual = CreditCardValidation.Validate(1100000000000007L);
        int[] expectedDigits = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidation.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(10,
                        CreditCardValidation.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Valid Card", actual, "verdict"));
    }

    @Test
    void c4RejectFalseAcceptanceFromSkippedDigit() {
        String actual = CreditCardValidation.Validate(1100000000000008L);
        int[] expectedDigits = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidation.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(11,
                        CreditCardValidation.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }

    @Test
    void c5ReduceTwoDigitDoubledValue() {
        String actual = CreditCardValidation.Validate(6000000000000007L);
        int[] expectedDigits = {6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidation.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(10,
                        CreditCardValidation.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Valid Card", actual, "verdict"));
    }

    @Test
    void c6RejectFalseAcceptanceFromRawDouble() {
        String actual = CreditCardValidation.Validate(6000000000000008L);
        int[] expectedDigits = {6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidation.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(11,
                        CreditCardValidation.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }

    @Test
    void c7RejectOtherInvalidInputs() {
        assertAll(
                () -> assertEquals("Invalid Card",
                        CreditCardValidation.Validate(0L)),
                () -> assertEquals("Invalid Card",
                        CreditCardValidation.Validate(-1L)),
                () -> assertEquals("Invalid Card",
                        CreditCardValidation.Validate(100000000000000L)),
                () -> assertEquals("Invalid Card",
                        CreditCardValidation.Validate(10000000000000000L)));
    }

    @Test
    void c8CheckAssignmentExample() {
        String actual = CreditCardValidation.Validate(3125145643589795L);
        int[] expectedDigits = {3, 1, 2, 5, 1, 4, 5, 6, 4, 3, 5, 8, 9, 7, 9, 5};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidation.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(79,
                        CreditCardValidation.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }
}