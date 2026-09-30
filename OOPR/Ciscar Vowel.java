import java.util.Scanner;

public class Valorant {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter a letter: ");
        char letter = input.next().charAt(0);

        char lower = Character.toLowerCase(letter);

        if (lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u') {
            System.out.println("It's a vowel!");
        } else {
            System.out.println("It's a consonant!");
        }

        input.close();
    }
}