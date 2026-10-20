public class PasswordVerifier {

    public static boolean passwordVerification(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char ch : password.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasNumber = true;
            } else {
                hasSpecialCharacter = true;
            }
        }

        return hasLowercase && hasUppercase && hasNumber && hasSpecialCharacter;
    }


}