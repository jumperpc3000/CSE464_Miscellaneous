import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreditCardSumSliceTest {
    @Test
    void s0InvalidLengthDoesNotReachCriterion() {
        assertThrows(IllegalArgumentException.class,
                () -> CreditCardSumSlice.finalSumSlice(12345678L));
    }

    @Test
    void s1ZerosKeepTheirPositions() {
        assertEquals(10, CreditCardSumSlice.finalSumSlice(1000000000000008L));
    }

    @Test
    void s2DifferentCheckDigitChangesSum() {
        assertEquals(3, CreditCardSumSlice.finalSumSlice(1000000000000001L));
    }

    @Test
    void s3OriginalSlicePreservesSkippedDigitBug() {
        assertAll(
                () -> assertEquals(9,
                        CreditCardSumSlice.finalSumSlice(1100000000000007L)),
                () -> assertEquals(10,
                        CreditCardSumSlice.finalSumSlice(1100000000000008L)));
    }

    @Test
    void s4OriginalSlicePreservesRawDoublingBug() {
        assertAll(
                () -> assertEquals(19,
                        CreditCardSumSlice.finalSumSlice(6000000000000007L)),
                () -> assertEquals(20,
                        CreditCardSumSlice.finalSumSlice(6000000000000008L)));
    }

    @Test
    void s5AssignmentExampleHasOriginalSum114() {
        assertEquals(114, CreditCardSumSlice.finalSumSlice(3125145643589795L));
    }

    @Test
    void s6OtherInvalidInputsDoNotReachCriterion() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> CreditCardSumSlice.finalSumSlice(0L)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> CreditCardSumSlice.finalSumSlice(-1L)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> CreditCardSumSlice.finalSumSlice(100000000000000L)),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> CreditCardSumSlice.finalSumSlice(10000000000000000L)));
    }
}