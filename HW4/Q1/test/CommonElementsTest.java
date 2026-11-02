import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommonElementsTest {
    @Test
    void p1EmptyFirstArray() {
        assertEquals(Collections.emptySet(),
                CommonElementsWrapper.CommonElements(
                        new String[] {}, new String[] {"A"}));
    }

    @Test
    void p2EmptySecondArray() {
        assertEquals(Collections.emptySet(),
                CommonElementsWrapper.CommonElements(
                        new String[] {"A"}, new String[] {}));
    }

    @Test
    void p3NoMatch() {
        assertEquals(Collections.emptySet(),
                CommonElementsWrapper.CommonElements(
                        new String[] {"A"}, new String[] {"B"}));
    }

    @Test
    void p4MatchAndBreak() {
        assertEquals(Set.of("A"),
                CommonElementsWrapper.CommonElements(
                        new String[] {"A"}, new String[] {"A"}));
    }

    @Test
    void a1RepeatedElementsRemainUnique() {
        assertEquals(Set.of("A", "B"),
                CommonElementsWrapper.CommonElements(
                        new String[] {"A", "A", "B"},
                        new String[] {"X", "A", "B", "A"}));
    }

    @Test
    void a2NullFirstArrayThrows() {
        assertThrows(NullPointerException.class,
                () -> CommonElementsWrapper.CommonElements(
                        null, new String[] {"A"}));
    }

    @Test
    void a3NullSecondArrayThrowsWhenFirstNonempty() {
        assertThrows(NullPointerException.class,
                () -> CommonElementsWrapper.CommonElements(
                        new String[] {"A"}, null));
    }

    @Test
    void a4BothNullArraysThrow() {
        assertThrows(NullPointerException.class,
                () -> CommonElementsWrapper.CommonElements(null, null));
    }

    @Test
    void a5EmptyFirstArraySkipsNullSecond() {
        assertEquals(Collections.emptySet(),
                CommonElementsWrapper.CommonElements(new String[] {}, null));
    }
}