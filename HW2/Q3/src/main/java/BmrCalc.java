import java.util.Scanner;

public class BmrCalc {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
		int age = 0 , height = 0;
		double weight = 0, bmr = 0;
		String gender = "";

        System.out.print("Enter your age (years): ");
        age = scanner.nextInt();

        System.out.print("Enter your weight (pounds): ");
        weight = scanner.nextDouble();

        System.out.print("Enter your height (inches): ");
        height = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter your gender (M/F): ");
        gender = scanner.nextLine();

        // Validate inputs
        if (age <= 0 || age >= 120) {
            System.out.println("Error: Age must be between 1 and 119.");
        } else if (weight <= 0 && weight >= 1000) {
            System.out.println("Error: Weight must be between 1 and 999 pounds.");
        } else if (height <= 0 || height >= 100) {
            System.out.println("Error: Height must be between 1 and 99 inches.");
        } else if (!gender.equals("M") && !gender.equals("F")) {
            System.out.println("Error: Invalid gender. Please enter 'M' or 'F'.");
        } else {
            // Calculate BMR based on gender
            double bmr;
            if (gender.equals("F")) {
                bmr = 66 + (6.23 * weight) + (12.7 * height) - (6.8 * age);
            } else {
                bmr = 655 + (4.35 * weight) + (4.7 * height) - (4.7 * age);
            }

            // Categorize metabolism levels
            String category;
            if (bmr < 1200) {
                category = "Low Metabolism";
            } else if (bmr < 1800) {
                category = "Moderate Metabolism";
            } else if (bmr < 2500) {
                category = "High Metabolism";
            } else {
                category = "Very High Metabolism";
            }

            // Display the result
            System.out.printf("Your BMR is: %.2f kcal/day\n", bmr);
            System.out.println("Metabolism Category: " + category);
        }

        scanner.close();
    }
}
