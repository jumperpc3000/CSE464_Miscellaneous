import java.util.HashSet;
import java.util.Set;

public class CommonElementsWrapper {

    public static Set<String> CommonElements(String[] array1, String[] array2) {
        Set<String> commonSet = new HashSet<>();
        for (int i = 0; i < array1.length; i++) {
            for (int j = 0; j < array2.length; j++) {
                if (array1[i].equals(array2[j])) {
                    commonSet.add(array1[i]);
                    break;
                }
            }
        }
        return commonSet;
    }
}
