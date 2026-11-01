public class CreditCardSumSlice {
    public static int finalSumSlice(long cardNumber) {
        final int CARD_LENGHT = 16;
        int length, sum1 = 0, sum2 = 0;
        int[] digitArray = new int[CARD_LENGHT];

        length = (int) (Math.log10(cardNumber) + 1);
        if (length != CARD_LENGHT) {
            throw new IllegalArgumentException("Slice criterion is not reached");
        }

        int i = CARD_LENGHT - 1;
        while (cardNumber > 0) {
            digitArray[i] = (int) (cardNumber % 10);
            cardNumber = cardNumber / 10;
            i = i - 1;
        }

        for (i = CARD_LENGHT - 1; i >= 2; i = i - 2) {
            sum1 = sum1 + digitArray[i];
        }

        for (i = CARD_LENGHT - 2; i >= 0; i = i - 2) {
            int num = digitArray[i] * 2;
            sum2 = sum2 + num;
        }

        int finalSum = sum1 + sum2;
        return finalSum;
    }
}