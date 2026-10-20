import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = input.nextLine();

        System.out.println("\nChoose a Loop:");
        System.out.println("1. While Loop");
        System.out.println("2. Do-While Loop");
        System.out.println("3. For Loop");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();

        System.out.println();

        int i = 1;

        if (choice == 1) {
            while (i <= 5) {
                System.out.println(name);
                i++;
            }
        }
        else if (choice == 2) {
            do {
                System.out.println(name);
                i++;
            } while (i <= 5);
        }
        else if (choice == 3) {
            for (i = 1; i <= 5; i++) {
                System.out.println(name);
            }
        }
        else {
            System.out.println("Invalid choice!");
        }

        input.close();
    }
}