import java.util.Scanner;

public class Ciscar {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = input.nextInt();

        if (number % 2 == 0) {
            System.out.println("It's an even number!");
        } else {
            System.out.println("It's an odd number!");
        }

        input.close();
    }
}