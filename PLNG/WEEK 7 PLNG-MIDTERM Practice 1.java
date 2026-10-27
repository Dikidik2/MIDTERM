import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PlngMidtermPractice1 {

    static final Scanner in = new Scanner(System.in);

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(in.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a valid number."); }
        }
    }

    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(in.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a whole number."); }
        }
    }

    static int[] readIntLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            if (line.isEmpty()) return new int[0];
            try {
                return Arrays.stream(line.split("\\s+")).mapToInt(Integer::parseInt).toArray();
            } catch (NumberFormatException e) {
                System.out.println("Please enter whole numbers separated by spaces.");
            }
        }
    }

    static void program1() {
        double[] a = new double[10];
        for (int i = 0; i < a.length; i++) a[i] = readDouble("Enter number " + (i + 1) + ": ");

        double sum = 0; int positives = 0;
        for (double v : a) if (v > 0) { sum += v; positives++; }
        if (positives > 0) {
            System.out.println("Sum of positive numbers: " + sum);
            System.out.println("Average of positive numbers: " + (sum / positives));
        } else {
            System.out.println("There are no positive numbers.");
        }

        int negatives = 0;
        for (double v : a) if (v < 0) negatives++;
        System.out.println("Count of negative numbers: " + negatives);

        // Loop 3: minimum
        double min = a[0];
        for (double v : a) if (v < min) min = v;
        System.out.println("Minimum value: " + min);
    }

    static void program2() {
        int[] a = new int[8];
        for (int i = 0; i < a.length; i++) a[i] = readInt("Enter integer " + (i + 1) + ": ");

        List<Integer> unique = new ArrayList<>();
        for (int v : a) if (!unique.contains(v)) unique.add(v);
        System.out.println("Array without duplicates: " + unique);

        if (unique.size() < 2) {
            System.out.println("Not enough distinct values for a second largest/smallest.");
            return;
        }
        int largest = Integer.MIN_VALUE, second = Integer.MIN_VALUE;
        int smallest = Integer.MAX_VALUE, secondSmall = Integer.MAX_VALUE;
        for (int v : unique) {
            if (v > largest) { second = largest; largest = v; }
            else if (v > second) second = v;
            if (v < smallest) { secondSmall = smallest; smallest = v; }
            else if (v < secondSmall) secondSmall = v;
        }
        System.out.println("Second largest element: " + second);
        System.out.println("Second smallest element: " + secondSmall);
    }

    static void program3() {
        int[] data = readIntLine("Enter Data in Array: ");
        System.out.println("Stored Data in Array: " + join(data));
        int pos = readInt("Enter poss. of Element to Delete: ");
        if (pos < 0 || pos >= data.length) {
            System.out.println("Invalid position. Valid positions are 0 to " + (data.length - 1) + ".");
            return;
        }
        int[] result = new int[data.length - 1];
        for (int i = 0, j = 0; i < data.length; i++) if (i != pos) result[j++] = data[i];
        System.out.println("New data in Array: " + join(result));
    }

    static void program4() {
        int size = readInt("Enter Size of Array : ");
        int[] a = new int[size];
        System.out.println("Enter any " + size + " elements in Array: ");
        for (int i = 0; i < size; i++) a[i] = readInt("");
        StringBuilder even = new StringBuilder(), odd = new StringBuilder();
        for (int v : a) {
            if (v % 2 == 0) even.append(v).append(' ');
            else odd.append(v).append(' ');
        }
        System.out.println("Even Elements: " + even.toString().trim());
        System.out.println("Odd Elements: " + odd.toString().trim());
    }

    static void program5() {
        for (int row = 0; row < 4; row++) {
            StringBuilder sb = new StringBuilder("*");
            for (int k = 0; k < row; k++) sb.append("A*");
            System.out.println(sb);
        }
    }

    static void program6() {
        double basic = 12000;
        double da = 0.12 * basic;
        double hra = 150, ta = 120, others = 450;
        double pf = 0.14 * basic;
        double it = 0.15 * basic;
        double net = basic + da + hra + ta + others - (pf + it);
        System.out.printf("Basic: $%.2f  DA: $%.2f  HRA: $%.2f  TA: $%.2f  Others: $%.2f%n",
                basic, da, hra, ta, others);
        System.out.printf("PF: $%.2f  IT: $%.2f%n", pf, it);
        System.out.printf("Net Salary: $%.2f%n", net);
    }

    static String join(int[] a) {
        StringBuilder sb = new StringBuilder();
        for (int v : a) sb.append(v).append(' ');
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        String again;
        do {
            System.out.println("\nChoose the program you want to run");
            for (int i = 1; i <= 6; i++) System.out.println("Program #" + i);
            int choice = readInt("Your choice (1-6): ");
            switch (choice) {
                case 1 -> program1();
                case 2 -> program2();
                case 3 -> program3();
                case 4 -> program4();
                case 5 -> program5();
                case 6 -> program6();
                default -> System.out.println("Invalid choice. Please pick 1 to 6.");
            }
            System.out.print("Do you want to continue ? Y/N ");
            again = in.nextLine().trim();
        } while (again.equalsIgnoreCase("Y"));
        System.out.println("Goodbye!");
    }
}