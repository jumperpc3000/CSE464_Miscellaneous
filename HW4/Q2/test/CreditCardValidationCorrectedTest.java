import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardValidationCorrectedTest {
    @Test
    void c0RejectWrongLength() {
        CreditCardValidationCorrected.Validate(1000000000000008L);
        String actual = CreditCardValidationCorrected.Validate(12345678L);

        assertAll(
                () -> assertArrayEquals(new int[16],
                        CreditCardValidationCorrected.getDigitArrayForTesting(),
                        "digitArray after length rejection"),
                () -> assertEquals(0,
                        CreditCardValidationCorrected.getFinalSumForTesting(),
                        "finalSum initialization after length rejection"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }

    @Test
    void c1AcceptSimpleValidChecksum() {
        String actual = CreditCardValidationCorrected.Validate(1000000000000008L);
        int[] expectedDigits = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidationCorrected.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(10,
                        CreditCardValidationCorrected.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Valid Card", actual, "verdict"));
    }

    @Test
    void c2RejectSimpleInvalidChecksum() {
        String actual = CreditCardValidationCorrected.Validate(1000000000000001L);
        int[] expectedDigits = {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidationCorrected.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(3,
                        CreditCardValidationCorrected.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }

    @Test
    void c3IncludeDigitAtIndexOne() {
        String actual = CreditCardValidationCorrected.Validate(1100000000000007L);
        int[] expectedDigits = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidationCorrected.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(10,
                        CreditCardValidationCorrected.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Valid Card", actual, "verdict"));
    }

    @Test
    void c4RejectFalseAcceptanceFromSkippedDigit() {
        String actual = CreditCardValidationCorrected.Validate(1100000000000008L);
        int[] expectedDigits = {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidationCorrected.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(11,
                        CreditCardValidationCorrected.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }

    @Test
    void c5ReduceTwoDigitDoubledValue() {
        String actual = CreditCardValidationCorrected.Validate(6000000000000007L);
        int[] expectedDigits = {6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidationCorrected.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(10,
                        CreditCardValidationCorrected.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Valid Card", actual, "verdict"));
    }

    @Test
    void c6RejectFalseAcceptanceFromRawDouble() {
        String actual = CreditCardValidationCorrected.Validate(6000000000000008L);
        int[] expectedDigits = {6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidationCorrected.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(11,
                        CreditCardValidationCorrected.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }

    @Test
    void c7RejectOtherInvalidInputs() {
        assertAll(
                () -> assertEquals("Invalid Card",
                        CreditCardValidationCorrected.Validate(0L)),
                () -> assertEquals("Invalid Card",
                        CreditCardValidationCorrected.Validate(-1L)),
                () -> assertEquals("Invalid Card",
                        CreditCardValidationCorrected.Validate(100000000000000L)),
                () -> assertEquals("Invalid Card",
                        CreditCardValidationCorrected.Validate(10000000000000000L)));
    }

    @Test
    void c8CheckAssignmentExample() {
        String actual = CreditCardValidationCorrected.Validate(3125145643589795L);
        int[] expectedDigits = {3, 1, 2, 5, 1, 4, 5, 6, 4, 3, 5, 8, 9, 7, 9, 5};

        assertAll(
                () -> assertArrayEquals(expectedDigits,
                        CreditCardValidationCorrected.getDigitArrayForTesting(),
                        "digitArray"),
                () -> assertEquals(79,
                        CreditCardValidationCorrected.getFinalSumForTesting(),
                        "finalSum"),
                () -> assertEquals("Invalid Card", actual, "verdict"));
    }
}