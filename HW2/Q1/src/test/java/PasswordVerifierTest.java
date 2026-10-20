import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class PasswordVerifierTest {
    private static final Path RESULTS =
            Path.of("results", "password_verifier_junit_results.txt");

    @BeforeAll
    static void startResults() throws IOException {
        Files.createDirectories(Path.of("results"));
        Files.writeString(RESULTS, "Password verifier CSV results" + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @Test
    void pw01() { assertEquals(false, PasswordVerifier.passwordVerification("")); }

    @Test
    void pw02() { assertEquals(false, PasswordVerifier.passwordVerification("!!!!!!!")); }

    @Test
    void pw03() { assertEquals(false, PasswordVerifier.passwordVerification("1111111")); }

    @Test
    void pw04() { assertEquals(false, PasswordVerifier.passwordVerification("1!11111")); }

    @Test
    void pw05() { assertEquals(false, PasswordVerifier.passwordVerification("AAAAAAA")); }

    @Test
    void pw06() { assertEquals(false, PasswordVerifier.passwordVerification("A!AAAAA")); }

    @Test
    void pw07() { assertEquals(false, PasswordVerifier.passwordVerification("A1AAAAA")); }

    @Test
    void pw08() { assertEquals(false, PasswordVerifier.passwordVerification("A1!AAAA")); }

    @Test
    void pw09() { assertEquals(false, PasswordVerifier.passwordVerification("aaaaaaa")); }

    @Test
    void pw10() { assertEquals(false, PasswordVerifier.passwordVerification("a!aaaaa")); }

    @Test
    void pw11() { assertEquals(false, PasswordVerifier.passwordVerification("a1aaaaa")); }

    @Test
    void pw12() { assertEquals(false, PasswordVerifier.passwordVerification("a1!aaaa")); }

    @Test
    void pw13() { assertEquals(false, PasswordVerifier.passwordVerification("aAaaaaa")); }

    @Test
    void pw14() { assertEquals(false, PasswordVerifier.passwordVerification("aA!aaaa")); }

    @Test
    void pw15() { assertEquals(false, PasswordVerifier.passwordVerification("aA1aaaa")); }

    @Test
    void pw16() { assertEquals(false, PasswordVerifier.passwordVerification("aA1!aaa")); }

    @Test
    void pw17() { assertEquals(false, PasswordVerifier.passwordVerification("!!!!!!!!")); }

    @Test
    void pw18() { assertEquals(false, PasswordVerifier.passwordVerification("11111111")); }

    @Test
    void pw19() { assertEquals(false, PasswordVerifier.passwordVerification("1!111111")); }

    @Test
    void pw20() { assertEquals(false, PasswordVerifier.passwordVerification("AAAAAAAA")); }

    @Test
    void pw21() { assertEquals(false, PasswordVerifier.passwordVerification("A!AAAAAA")); }

    @Test
    void pw22() { assertEquals(false, PasswordVerifier.passwordVerification("A1AAAAAA")); }

    @Test
    void pw23() { assertEquals(false, PasswordVerifier.passwordVerification("A1!AAAAA")); }

    @Test
    void pw24() { assertEquals(false, PasswordVerifier.passwordVerification("aaaaaaaa")); }

    @Test
    void pw25() { assertEquals(false, PasswordVerifier.passwordVerification("a!aaaaaa")); }

    @Test
    void pw26() { assertEquals(false, PasswordVerifier.passwordVerification("a1aaaaaa")); }

    @Test
    void pw27() { assertEquals(false, PasswordVerifier.passwordVerification("a1!aaaaa")); }

    @Test
    void pw28() { assertEquals(false, PasswordVerifier.passwordVerification("aAaaaaaa")); }

    @Test
    void pw29() { assertEquals(false, PasswordVerifier.passwordVerification("aA!aaaaa")); }

    @Test
    void pw30() { assertEquals(false, PasswordVerifier.passwordVerification("aA1aaaaa")); }

    @Test
    void pw31() { assertEquals(true, PasswordVerifier.passwordVerification("aA1!aaaa")); }

    @ParameterizedTest(name = "PW{index}: password=[{0}], expected={1}")
    @CsvFileSource(resources = "/password_verifier_test_data.csv", numLinesToSkip = 1)
    void passwordsFromCsv(String password, boolean expected) throws IOException {
        String details = "password=[" + password + "] | expected=" + expected;
        try {
            boolean actual = PasswordVerifier.passwordVerification(password);
            assertEquals(expected, actual);
            writeResult("PASS | " + details + " | actual=" + actual);
        } catch (AssertionError error) {
            writeResult("FAIL | " + details + " | " + error.getMessage());
            throw error;
        } catch (RuntimeException error) {
            writeResult("FAIL | " + details + " | " + error);
            throw error;
        }
    }

    private void writeResult(String message) throws IOException {
        Files.writeString(RESULTS, message + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
