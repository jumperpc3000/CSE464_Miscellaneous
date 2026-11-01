import java.util.Scanner;

public class CreditCardValidationCorrected {
    private static int[] digitArrayTest;
    private static int finalSumTest;

    public static void main(String[] args) {
        System.out.println("Enter the 16 digit card number: ");
        Scanner scan = new Scanner(System.in);
        long cardNumber = scan.nextLong();

        String valid_invlaid = Validate(cardNumber);
        System.out.println(valid_invlaid);
    }

    public static String Validate(long cardNumber) {
        // since the card number is 16 digits, we need long integer

        final int CARD_LENGHT = 16;
        int length, sum1 = 0, sum2 = 0, finalSum = 0;
        int[] digitArray = new int[CARD_LENGHT];
        digitArrayTest = digitArray;
        finalSumTest = finalSum;
        //Scanner scan = new Scanner(System.in);

        // get the card length
        length = (int) (Math.log10(cardNumber) + 1);
        if (length != CARD_LENGHT) {
            System.out.println("Invalid card number, need to have 16 digits");
            return "Invalid Card";
        }

        // get each digit from the card number and set the digitArray
        int i = CARD_LENGHT - 1;

        while (cardNumber > 0) {
            digitArray[i] = (int) (cardNumber % 10);
            cardNumber = cardNumber / 10;
            i = i - 1;
        }

        // starting from the right most digit add every other digit to sum 1
        for (i = CARD_LENGHT - 1; i >= 0; i = i - 2) {
            sum1 = sum1 + digitArray[i];
        }

        // get each digit not counted in above, multiply by 2 and add each digit of  multiplied
        // numbers to sum 2
        for (i = CARD_LENGHT - 2; i >= 0; i = i - 2) {
            int num = digitArray[i] * 2;

            if (num > 9) {
                num = num - 9;
            }

            sum2 = sum2 + num;
        }

        // find the final sum
        finalSum = sum1 + sum2;
        finalSumTest = finalSum;

        // check if the last digit of the final sum is 0
        if (finalSum % 10 == 0) {
            //System.out.println("Valid Card");
            return "Valid Card";
        } else {
            // System.out.println("Invalid Card");
            return "Invalid Card";
        }
    }

    public static int[] getDigitArrayForTesting() {
        return digitArrayTest;
    }

    public static int getFinalSumForTesting() {
        return finalSumTest;
    }
}