import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class LoanEngineTest {
    private static final Path RESULTS = Path.of("results", "loanTest_ep_junit_results.txt");
    private final LoanEngine engine = new LoanEngine();

    @BeforeAll
    static void startResults() throws IOException {
        Files.createDirectories(Path.of("results"));
        Files.writeString(RESULTS, "Loan EP and BVA results" + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @ParameterizedTest(name = "EP{index}: amount={0}, credit={1}, term={2}, existing={3}")
    @CsvFileSource(resources = "/loan_test_data.csv", numLinesToSkip = 1)
    void epCases(double amount, int credit, int term, boolean existing,
                 String expectedPayment, String expectedException) throws IOException {
        checkLoan("EP", amount, credit, term, existing, expectedPayment, expectedException);
    }

    @ParameterizedTest(name = "BV{index}: amount={0}, credit={1}, term={2}, existing={3}")
    @CsvFileSource(resources = "/loan_bvatest_data.csv", numLinesToSkip = 1)
    void bvaCases(double amount, int credit, int term, boolean existing,
                  String expectedPayment, String expectedException) throws IOException {
        checkLoan("BVA", amount, credit, term, existing, expectedPayment, expectedException);
    }

    private void checkLoan(String group, double amount, int credit, int term,
                           boolean existing, String expectedPayment,
                           String expectedException) throws IOException {
        String input = amount + "," + credit + "," + term + "," + existing;
        String details = group + " | input=" + input;
        try {
            if ("IllegalArgumentException".equals(expectedException)) {
                boolean exceptionThrown = false;
                try {
                    engine.calculateMonthlyPayment(amount, credit, term, existing);
                } catch (IllegalArgumentException error) {
                    exceptionThrown = true;
                }
                assertTrue(exceptionThrown, "Expected IllegalArgumentException: " + input);
                writeResult("PASS | " + details + " | expected=IllegalArgumentException"
                        + " | actual=IllegalArgumentException");
            } else {
                assertEquals("NONE", expectedException);
                double actual = engine.calculateMonthlyPayment(amount, credit, term, existing);
                assertEquals(Double.parseDouble(expectedPayment), actual, 0.01, input);
                writeResult("PASS | " + details + " | expected=" + expectedPayment
                        + " | actual=" + actual);
            }
        } catch (AssertionError error) {
            writeResult("FAIL | " + details + " | expected=" + expectedPayment
                    + "," + expectedException + " | " + error.getMessage());
            throw error;
        } catch (RuntimeException error) {
            writeResult("FAIL | " + details + " | expected=" + expectedPayment
                    + "," + expectedException + " | " + error);
            throw error;
        }
    }

    private void writeResult(String message) throws IOException {
        Files.writeString(RESULTS, message + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
