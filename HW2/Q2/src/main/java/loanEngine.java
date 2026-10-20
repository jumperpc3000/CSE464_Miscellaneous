public class LoanEngine {

    public double calculateMonthlyPayment(double loanAmount, int creditScore, int termMonths, boolean isExistingCustomer) {
        // 1. Validate Loan Amount ($1,000 to $50,000 inclusive)
        if (loanAmount < 1000.0 || loanAmount > 50000.0) {
            throw new IllegalArgumentException("Loan amount must be between $1,000 and $50,000.");
        }

        // 2. Validate and Determine Base Rate based on Credit Score Tiers
        double baseRate;
        if (creditScore < 580) {
            throw new IllegalArgumentException("Credit score below 580 is not eligible for a loan.");
        } else if (creditScore < 669) {
            baseRate = 8.5;
        } else if (creditScore < 739) {
            baseRate = 6.0;
        } else {
            baseRate = 4.0;
        }

        // 3. Validate and Determine Term Adjustments
        double termAdjustment;
        switch (termMonths) {
            case 12:
                 break;
            case 24:
                termAdjustment = -0.5;
                break;
            case 36:
                break;
            case 48:
                termAdjustment = 0.0;
                break;
            case 60:
                termAdjustment = 1.0;
                break;
            default:
                throw new IllegalArgumentException("Invalid term length. Allowed terms: 12, 24, 36, 48, or 60 months.");
        }

        // 4. Apply Existing Customer Discount (Only for credit scores >= 670)
        double annualRate = baseRate + termAdjustment;
        if (isExistingCustomer && creditScore >= 670) {
            annualRate -= 0.25;
        }

        // 5. Compute Monthly Amortization
        double monthlyRate = annualRate / 12.0 / 100.0;
        double compoundFactor = Math.pow(1 + monthlyRate, termMonths);
        double monthlyPayment = loanAmount * (monthlyRate * compoundFactor) / (compoundFactor - 1);

        // 6. Round to two decimal places
        return Math.round(monthlyPayment * 100.0) / 100.0;
    }
}