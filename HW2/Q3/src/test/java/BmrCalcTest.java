import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class BmrCalcTest {
    private static final Path RESULTS = Path.of("results", "bmr_dt_junit_results.txt");

    @BeforeAll
    static void startResults() throws IOException {
        Files.createDirectories(Path.of("results"));
        Files.writeString(RESULTS, "BMR decision-table results" + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    @ParameterizedTest(name = "{0}: age={1}, weight={2}, height={3}, gender=[{4}]")
    @CsvFileSource(resources = "/bmr_dt_test_data.csv", numLinesToSkip = 1)
    void decisionTableCases(String id, int age, double weight, String height, String gender,
                            String kind, String expectedValue, String category) throws IOException {
        if (gender == null) {
            gender = "";
        }
        String input = age + "," + weight + "," + height + ",[" + gender + "]";
        String details = id + " | input=" + input + " | expected=" + expectedValue;
        if (category != null) {
            details = details + " " + category;
        }

        try {
            String output = runProgram(age, weight, height, gender);
            if ("ERROR".equals(kind)) {
                assertTrue(output.contains("Error: " + expectedValue), output);
                assertFalse(output.contains("Your BMR is:"), output);
            } else {
                assertEquals("VALUE", kind);
                assertTrue(output.contains("Your BMR is: " + expectedValue + " kcal/day"), output);
                assertTrue(output.contains("Metabolism Category: " + category), output);
            }
            writeResult("PASS | " + details + " | output=" + output);
        } catch (AssertionError error) {
            writeResult("FAIL | " + details + " | " + error.getMessage());
            throw error;
        } catch (RuntimeException error) {
            writeResult("FAIL | " + details + " | " + error);
            throw error;
        }
    }

    private String runProgram(int age, double weight, String height, String gender) {
        InputStream savedIn = System.in;
        PrintStream savedOut = System.out;
        Locale savedLocale = Locale.getDefault();
        ByteArrayOutputStream capture = new ByteArrayOutputStream();
        String input = age + "\n" + weight + "\n" + height + "\n" + gender + "\n";
        try (PrintStream output = new PrintStream(capture, true, StandardCharsets.UTF_8)) {
            Locale.setDefault(Locale.US);
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(output);
            BmrCalc.main(new String[0]);
        } finally {
            System.setIn(savedIn);
            System.setOut(savedOut);
            Locale.setDefault(savedLocale);
        }
        return capture.toString(StandardCharsets.UTF_8);
    }

    private void writeResult(String message) throws IOException {
        Files.writeString(RESULTS, message.replace("\r", " ").replace("\n", " ")
                + System.lineSeparator(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
