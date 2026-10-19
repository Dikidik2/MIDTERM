import java.math.BigInteger;
import java.util.Scanner;

/**
 * OOPR Midterm Lab Activity 2 (Programs 1 - 31) in ONE program.
 * Run it, then type the activity number (1-31) from the menu. 0 exits.
 * Activities with several required solutions print every solution, labeled.
 */
public class MidtermActivities {

    static final Scanner sc = new Scanner(System.in);

    static final String[] TITLES = {
        "Vowel or Consonant",
        "Date in Complete Form",
        "Temperature (ICE / WATER / STEAM)",
        "Aircraft Classifier",
        "Ship Class",
        "Earthquake Damage (Richter)",
        "Number to Words (max 3000)",
        "Number to Roman Numerals (max 3000)",
        "Tuition Fee Payment Plan",
        "Percentile to Grade",
        "Numbers and Squares",
        "Alternate Sequence 1, 5, 2, 4 ...",
        "Alternate Sequence 5, 1, 4, 2 ...",
        "Factorial",
        "Fibonacci",
        "Power",
        "Sum of Squares",
        "Sum of 1 to n",
        "Reverse Number",
        "Sum of Powers",
        "Right-Angle Triangle (*)",
        "Floyd's Triangle (1 and 0)",
        "Pyramid, Odd Asterisks",
        "Pyramid, Repeated Number",
        "Pyramid (*)",
        "Pyramid, Numbers Increased by 1",
        "Right Triangle, Numbers Increased by 1",
        "Right Triangle, Repeated Number",
        "Right Triangle, 1 to Row Number",
        "Diamond",
        "Number Pyramid (1 2 3 2 1)"
    };

    // ======================= MAIN MENU =======================
    public static void main(String[] args) {
        System.out.println("=== OOPR MIDTERM LAB ACTIVITY 2 (1-31) ===");
        while (true) {
            System.out.println();
            for (int i = 0; i < TITLES.length; i++) {
                System.out.printf("%2d. %s%n", i + 1, TITLES[i]);
            }
            System.out.println(" 0. Exit");
            int choice = readInt("Enter activity number: ", 0, 31);
            if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }
            System.out.println();
            System.out.println("--- Activity " + choice + ": " + TITLES[choice - 1] + " ---");
            switch (choice) {
                case 1: activity1(); break;
                case 2: activity2(); break;
                case 3: activity3(); break;
                case 4: activity4(); break;
                case 5: activity5(); break;
                case 6: activity6(); break;
                case 7: activity7(); break;
                case 8: activity8(); break;
                case 9: activity9(); break;
                case 10: activity10(); break;
                case 11: activity11(); break;
                case 12: sequence(false); break;
                case 13: sequence(true); break;
                case 14: activity14(); break;
                case 15: activity15(); break;
                case 16: activity16(); break;
                case 17: activity17(); break;
                case 18: activity18(); break;
                case 19: activity19(); break;
                case 20: activity20(); break;
                case 21: activity21(); break;
                case 22: activity22(); break;
                case 23: activity23(); break;
                case 24: activity24(); break;
                case 25: activity25(); break;
                case 26: activity26(); break;
                case 27: activity27(); break;
                case 28: activity28(); break;
                case 29: activity29(); break;
                case 30: activity30(); break;
                case 31: activity31(); break;
            }
        }
        sc.close();
    }

    // ======================= INPUT HELPERS =======================
    static String readLine(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    static int readInt(String prompt, int min, int max) {
        while (true) {
            String s = readLine(prompt);
            try {
                int v = Integer.parseInt(s);
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException e) { }
            System.out.println("Invalid input. Enter a whole number from " + min + " to " + max + ".");
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            String s = readLine(prompt).replace(",", "");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }

    static char readChar(String prompt) {
        String s = readLine(prompt);
        return s.isEmpty() ? ' ' : s.charAt(0);
    }

    static void spaces(int k) {
        for (int i = 0; i < k; i++) System.out.print(' ');
    }

    // ======================= 1. VOWEL OR CONSONANT =======================
    static void activity1() {
        char ch = readChar("Enter a letter: ");
        if (!Character.isLetter(ch)) {
            System.out.println("Invalid input. Please enter a letter.");
            return;
        }
        char u = Character.toUpperCase(ch);

        System.out.println("1st Solution (if / else if / else):");
        String result;
        if (u == 'A') result = "VOWEL";
        else if (u == 'E') result = "VOWEL";
        else if (u == 'I') result = "VOWEL";
        else if (u == 'O') result = "VOWEL";
        else if (u == 'U') result = "VOWEL";
        else result = "CONSONANT";
        System.out.println(ch + " is a " + result);

        System.out.println("2nd Solution (switch/case):");
        switch (u) {
            case 'A': case 'E': case 'I': case 'O': case 'U':
                result = "VOWEL";
                break;
            default:
                result = "CONSONANT";
        }
        System.out.println(ch + " is a " + result);
    }

    // ======================= 2. DATE IN COMPLETE FORM =======================
    static String monthIf(int m) {
        if (m == 1) return "January";
        else if (m == 2) return "February";
        else if (m == 3) return "March";
        else if (m == 4) return "April";
        else if (m == 5) return "May";
        else if (m == 6) return "June";
        else if (m == 7) return "July";
        else if (m == 8) return "August";
        else if (m == 9) return "September";
        else if (m == 10) return "October";
        else if (m == 11) return "November";
        else if (m == 12) return "December";
        else return null;
    }

    static String monthSwitch(int m) {
        switch (m) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return null;
        }
    }

    static int daysInMonth(int m, int y) {
        if (m == 2) return ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) ? 29 : 28;
        if (m == 4 || m == 6 || m == 9 || m == 11) return 30;
        return 31;
    }

    static void activity2() {
        int month, day, year;
        while (true) {
            String line = readLine("Enter date as month day year (e.g. 2 26 1986): ");
            String[] p = line.split("\\s+");
            try {
                if (p.length == 3) {
                    month = Integer.parseInt(p[0]);
                    day = Integer.parseInt(p[1]);
                    year = Integer.parseInt(p[2]);
                    if (month >= 1 && month <= 12 && year > 0
                            && day >= 1 && day <= daysInMonth(month, year)) break;
                }
            } catch (NumberFormatException e) { }
            System.out.println("Invalid date. Use three numbers: month day year.");
        }
        System.out.println("1st Solution (if / else if / else):");
        System.out.println(monthIf(month) + " " + day + ", " + year);
        System.out.println("2nd Solution (switch/case):");
        System.out.println(monthSwitch(month) + " " + day + ", " + year);
    }

    // ======================= 3. TEMPERATURE =======================
    static void activity3() {
        double temp = readDouble("Enter temp: ");
        if (temp < 0) System.out.println("ICE");
        else if (temp <= 100) System.out.println("WATER");
        else System.out.println("STEAM");
    }

    // ======================= 4. AIRCRAFT =======================
    // Assumption: civilian = faster than 1100 km/h AND longer than 52 m;
    // military = at least 500 km/h AND at least 20 m (and not civilian);
    // anything slower/smaller = "It's a bird".
    static void activity4() {
        double speed = readDouble("Enter observed speed (km/h): ");
        double length = readDouble("Enter estimated length (meters): ");
        if (speed > 1100 && length > 52) System.out.println("Aircraft label: civilian");
        else if (speed >= 500 && length >= 20) System.out.println("Aircraft label: military");
        else System.out.println("It's a bird");
    }

    // ======================= 5. SHIP CLASS =======================
    static void activity5() {
        char id = readChar("Enter class ID (B, C, D, F): ");

        System.out.println("1st Solution (if / else if / else):");
        if (id == 'B' || id == 'b') System.out.println("Ship class: Battleship");
        else if (id == 'C' || id == 'c') System.out.println("Ship class: Cruiser");
        else if (id == 'D' || id == 'd') System.out.println("Ship class: Destroyer");
        else if (id == 'F' || id == 'f') System.out.println("Ship class: Frigate");
        else System.out.println("Unknown class ID");

        System.out.println("2nd Solution (switch/case):");
        switch (id) {
            case 'B': case 'b': System.out.println("Ship class: Battleship"); break;
            case 'C': case 'c': System.out.println("Ship class: Cruiser"); break;
            case 'D': case 'd': System.out.println("Ship class: Destroyer"); break;
            case 'F': case 'f': System.out.println("Ship class: Frigate"); break;
            default: System.out.println("Unknown class ID");
        }
    }

    // ======================= 6. EARTHQUAKE =======================
    // The sheet's ranges are garbled; used: n<5.0 little/no damage, 5.0-5.4 some,
    // 5.5-6.4 serious, 6.5-7.4 disaster, 7.5 and higher catastrophe.
    static void activity6() {
        double n = readDouble("Enter Richter scale value: ");
        if (n < 5.0) System.out.println("Little or no damage");
        else if (n < 5.5) System.out.println("Some damage");
        else if (n < 6.5) System.out.println("Serious damage");
        else if (n < 7.5) System.out.println("Disaster");
        else System.out.println("Catastrophe");
    }

    // ======================= 7. NUMBER TO WORDS =======================
    static final String[] ONES = {"zero", "one", "two", "three", "four", "five", "six", "seven",
        "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
        "sixteen", "seventeen", "eighteen", "nineteen"};
    static final String[] TENS = {"", "", "twenty", "thirty", "forty", "fifty", "sixty",
        "seventy", "eighty", "ninety"};

    static String toWords(int n) {
        if (n == 0) return "zero";
        StringBuilder sb = new StringBuilder();
        int th = n / 1000, h = (n % 1000) / 100, r = n % 100;
        if (th > 0) sb.append(ONES[th]).append(" thousand ");
        if (h > 0) sb.append(ONES[h]).append(" hundred ");
        if (r > 0) {
            if (r < 20) {
                sb.append(ONES[r]);
            } else {
                sb.append(TENS[r / 10]);
                if (r % 10 > 0) sb.append(" ").append(ONES[r % 10]);
            }
        }
        return sb.toString().trim();
    }

    static void activity7() {
        int n = readInt("Enter a number: ", 0, 3000);
        System.out.println(toWords(n));
    }

    // ======================= 8. ROMAN NUMERALS =======================
    static void activity8() {
        int n = readInt("Enter a number: ", 1, 3000);
        int[] v = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] s = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < v.length; i++) {
            while (n >= v[i]) {
                sb.append(s[i]);
                n -= v[i];
            }
        }
        System.out.println(sb);
    }

    // ======================= 9. TUITION FEE =======================
    // Follows the stated rule (Two-Installment = 5% discount). The sample's 21,000
    // for plan 2 does not match that rule.
    static void activity9() {
        double fee = readDouble("Enter tuition fee: ");
        System.out.println("(Press 1 for Cash, 2 for Two-Installment, 3 for Three-Installment)");
        int mode = readInt("Enter mode of payment: ", 1, 3);
        double total;
        switch (mode) {
            case 1: total = fee - fee * 0.10; break;
            case 2: total = fee - fee * 0.05; break;
            default: total = fee + fee * 0.10;
        }
        System.out.printf("%nYour total tuition fee is: %,.2f%n", total);
    }

    // ======================= 10. PERCENTILE TO GRADE =======================
    static void activity10() {
        int p = readInt("Enter grade (percentile): ", -1000000, 1000000);
        if (p >= 98 && p <= 100) System.out.println("Grade equivalent: 1.00");
        else if (p >= 95 && p <= 97) System.out.println("Grade equivalent: 1.25");
        else if (p >= 92 && p <= 94) System.out.println("Grade equivalent: 1.50");
        else if (p >= 89 && p <= 91) System.out.println("Grade equivalent: 1.75");
        else if (p >= 85 && p <= 88) System.out.println("Grade equivalent: 2.00");
        else if (p >= 82 && p <= 84) System.out.println("Grade equivalent: 2.25");
        else if (p >= 80 && p <= 81) System.out.println("Grade equivalent: 2.50");
        else if (p >= 77 && p <= 79) System.out.println("Grade equivalent: 2.75");
        else if (p >= 75 && p <= 76) System.out.println("Grade equivalent: 3.00");
        else System.out.println("Out-of-Range");
    }

    // ======================= 11. NUMBERS AND SQUARES =======================
    static void squareRow(int i) {
        System.out.printf("%-15d%d%n", i, i * i);
    }

    static void activity11() {
        System.out.println("1st Solution - for loop");
        System.out.println("Sequence nos.  Squared");
        for (int i = 1; i <= 5; i++) squareRow(i);

        System.out.println("\n2nd Solution - while loop");
        System.out.println("Sequence nos.  Squared");
        int w = 1;
        while (w <= 5) {
            squareRow(w);
            w++;
        }

        System.out.println("\n3rd Solution - do while loop");
        System.out.println("Sequence nos.  Squared");
        int d = 1;
        do {
            squareRow(d);
            d++;
        } while (d <= 5);
    }

    // ======================= 12 & 13. ALTERNATE SEQUENCES =======================
    static String pair(int i, boolean reverse) {
        int a = reverse ? 6 - i : i;
        int b = reverse ? i : 6 - i;
        return a + ", " + b + ", ";
    }

    static void sequence(boolean reverse) {
        StringBuilder sb = new StringBuilder();
        System.out.println("1st Solution - for loop");
        for (int i = 1; i <= 5; i++) sb.append(pair(i, reverse));
        System.out.println(sb.toString().trim());

        sb = new StringBuilder();
        System.out.println("2nd Solution - while loop");
        int w = 1;
        while (w <= 5) {
            sb.append(pair(w, reverse));
            w++;
        }
        System.out.println(sb.toString().trim());

        sb = new StringBuilder();
        System.out.println("3rd Solution - do while loop");
        int d = 1;
        do {
            sb.append(pair(d, reverse));
            d++;
        } while (d <= 5);
        System.out.println(sb.toString().trim());
    }

    // ======================= 14. FACTORIAL =======================
    static void showFactorial(int n, long f) {
        StringBuilder comp = new StringBuilder();
        if (n == 0) comp.append("1");
        for (int i = 1; i <= n; i++) {
            if (i > 1) comp.append("*");
            comp.append(i);
        }
        System.out.println("Factorial value : " + f);
        System.out.println("( The computation is : " + comp + "=" + f + ")");
    }

    static void activity14() {
        int n = readInt("Enter a no. ", 0, 20);
        long f;

        System.out.println("1st Solution - for loop");
        f = 1;
        for (int i = 1; i <= n; i++) f *= i;
        showFactorial(n, f);

        System.out.println("2nd Solution - while loop");
        f = 1;
        int w = 1;
        while (w <= n) {
            f *= w;
            w++;
        }
        showFactorial(n, f);

        System.out.println("3rd Solution - do while loop");
        f = 1;
        int d = 1;
        if (n > 0) {
            do {
                f *= d;
                d++;
            } while (d <= n);
        }
        showFactorial(n, f);
    }

    // ======================= 15. FIBONACCI =======================
    static void activity15() {
        int n = readInt("Enter a no. ", 1, 90);
        StringBuilder sb;
        long a, b, t;

        System.out.println("1st Solution - for loop");
        sb = new StringBuilder();
        a = 0; b = 1;
        for (int i = 0; i < n; i++) {
            sb.append(b).append(' ');
            t = a + b; a = b; b = t;
        }
        System.out.println("Fibonacci series: " + sb.toString().trim());

        System.out.println("2nd Solution - while loop");
        sb = new StringBuilder();
        a = 0; b = 1;
        int w = 0;
        while (w < n) {
            sb.append(b).append(' ');
            t = a + b; a = b; b = t;
            w++;
        }
        System.out.println("Fibonacci series: " + sb.toString().trim());

        System.out.println("3rd Solution - do while loop");
        sb = new StringBuilder();
        a = 0; b = 1;
        int d = 0;
        do {
            sb.append(b).append(' ');
            t = a + b; a = b; b = t;
            d++;
        } while (d < n);
        System.out.println("Fibonacci series: " + sb.toString().trim());
    }

    // ======================= 16. POWER =======================
    static void showPower(int base, int exp, BigInteger r) {
        StringBuilder comp = new StringBuilder();
        if (exp == 0) comp.append("1");
        for (int i = 1; i <= exp; i++) {
            if (i > 1) comp.append(" * ");
            comp.append(base);
        }
        System.out.println("Power value: " + r);
        System.out.println("( The computation is : " + base + "^" + exp + " = " + comp + " = " + r + ")");
    }

    static void activity16() {
        int base = readInt("Enter base no. ", -1000, 1000);
        int exp = readInt("Enter exponent no. ", 0, 100);
        BigInteger b = BigInteger.valueOf(base);
        BigInteger r;

        System.out.println("1st Solution - for loop");
        r = BigInteger.ONE;
        for (int i = 1; i <= exp; i++) r = r.multiply(b);
        showPower(base, exp, r);

        System.out.println("2nd Solution - while loop");
        r = BigInteger.ONE;
        int w = 1;
        while (w <= exp) {
            r = r.multiply(b);
            w++;
        }
        showPower(base, exp, r);

        System.out.println("3rd Solution - do while loop");
        r = BigInteger.ONE;
        int d = 1;
        if (exp > 0) {
            do {
                r = r.multiply(b);
                d++;
            } while (d <= exp);
        }
        showPower(base, exp, r);
    }

    // ======================= 17. SUM OF SQUARES =======================
    static void activity17() {
        int n = readInt("Enter a number: ", 1, 100000);
        long sum;

        System.out.println("1st Solution - for loop");
        sum = 0;
        for (int i = 1; i <= n; i++) sum += (long) i * i;
        showSumSquares(n, sum);

        System.out.println("2nd Solution - while loop");
        sum = 0;
        int w = 1;
        while (w <= n) {
            sum += (long) w * w;
            w++;
        }
        showSumSquares(n, sum);

        System.out.println("3rd Solution - do while loop");
        sum = 0;
        int d = 1;
        do {
            sum += (long) d * d;
            d++;
        } while (d <= n);
        showSumSquares(n, sum);
    }

    static void showSumSquares(int n, long sum) {
        System.out.println("Sum of squares: " + sum);
        if (n <= 15) {
            StringBuilder a = new StringBuilder(), b = new StringBuilder();
            for (int i = 1; i <= n; i++) {
                if (i > 1) { a.append(" + "); b.append(" + "); }
                a.append(i).append("^2");
                b.append(i * i);
            }
            System.out.println("( " + a + " = " + b + " = " + sum + " )");
        }
    }

    // ======================= 18. SUM OF 1 TO n =======================
    static void showSum(int n, long sum) {
        System.out.println("Sum: " + sum);
        if (n <= 20) {
            StringBuilder a = new StringBuilder();
            for (int i = 1; i <= n; i++) {
                if (i > 1) a.append(" + ");
                a.append(i);
            }
            System.out.println("( " + a + " = " + sum + " )");
        }
    }

    static void activity18() {
        int n = readInt("Enter a number: ", 1, 1000000);
        long sum;

        System.out.println("1st Solution - for loop");
        sum = 0;
        for (int i = 1; i <= n; i++) sum += i;
        showSum(n, sum);

        System.out.println("2nd Solution - while loop");
        sum = 0;
        int w = 1;
        while (w <= n) {
            sum += w;
            w++;
        }
        showSum(n, sum);

        System.out.println("3rd Solution - do while loop");
        sum = 0;
        int d = 1;
        do {
            sum += d;
            d++;
        } while (d <= n);
        showSum(n, sum);
    }

    // ======================= 19. REVERSE NUMBER =======================
    static void activity19() {
        int n = readInt("Enter a number: ", 0, Integer.MAX_VALUE);
        long rev;

        System.out.println("1st Solution - for loop");
        rev = 0;
        for (int t = n; t > 0; t /= 10) rev = rev * 10 + t % 10;
        System.out.println("Reverse number: " + rev);

        System.out.println("2nd Solution - while loop");
        rev = 0;
        int w = n;
        while (w > 0) {
            rev = rev * 10 + w % 10;
            w /= 10;
        }
        System.out.println("Reverse number: " + rev);

        System.out.println("3rd Solution - do while loop");
        rev = 0;
        int d = n;
        do {
            rev = rev * 10 + d % 10;
            d /= 10;
        } while (d > 0);
        System.out.println("Reverse number: " + rev);
    }

    // ======================= 20. SUM OF POWERS =======================
    static long pow(int base, int exp) {
        long r = 1;
        for (int i = 1; i <= exp; i++) r *= base;
        return r;
    }

    static void showSumPowers(int n, long sum) {
        StringBuilder a = new StringBuilder(), b = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (i > 1) { a.append(" + "); b.append(" + "); }
            a.append(i).append("^").append(i);
            b.append(pow(i, i));
        }
        System.out.println("Sum of powers: " + sum);
        System.out.println("( " + a + " = " + b + " = " + sum + " )");
    }

    static void activity20() {
        int n = readInt("Enter a number (1-15): ", 1, 15);
        long sum;

        System.out.println("1st Solution - for loop");
        sum = 0;
        for (int i = 1; i <= n; i++) sum += pow(i, i);
        showSumPowers(n, sum);

        System.out.println("2nd Solution - while loop");
        sum = 0;
        int w = 1;
        while (w <= n) {
            sum += pow(w, w);
            w++;
        }
        showSumPowers(n, sum);

        System.out.println("3rd Solution - do while loop");
        sum = 0;
        int d = 1;
        do {
            sum += pow(d, d);
            d++;
        } while (d <= n);
        showSumPowers(n, sum);
    }

    // ======================= PATTERNS (21 - 31) =======================
    // 21. Right-angle triangle of asterisks
    static void activity21() {
        int n = readInt("Number of rows : ", 1, 50);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) System.out.print(j > 1 ? " *" : "*");
            System.out.println();
        }
    }

    // 22. Floyd's triangle of 1s and 0s (matches the sample picture)
    static void activity22() {
        int n = readInt("Number of rows : ", 1, 50);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                if (j > 1) System.out.print(" ");
                System.out.print((i + j) % 2 == 0 ? 1 : 0);
            }
            System.out.println();
        }
    }

    // one centered row of 2i-1 asterisks (used by 23 and 30)
    static void starRow(int n, int i) {
        spaces(2 * (n - i));
        int k = 2 * i - 1;
        for (int j = 1; j <= k; j++) System.out.print(j > 1 ? " *" : "*");
        System.out.println();
    }

    // 23. Pyramid, each row an odd number of asterisks
    static void activity23() {
        int n = readInt("Number of rows : ", 1, 30);
        for (int i = 1; i <= n; i++) starRow(n, i);
    }

    // 24. Pyramid, row number repeated in each row
    static void activity24() {
        int n = readInt("Number of rows : ", 1, 30);
        for (int i = 1; i <= n; i++) {
            spaces(n - i);
            for (int j = 1; j <= i; j++) System.out.print(j > 1 ? " " + i : String.valueOf(i));
            System.out.println();
        }
    }

    // 25. Pyramid of asterisks
    static void activity25() {
        int n = readInt("Number of rows : ", 1, 30);
        for (int i = 1; i <= n; i++) {
            spaces(n - i);
            for (int j = 1; j <= i; j++) System.out.print(j > 1 ? " *" : "*");
            System.out.println();
        }
    }

    // 26. Pyramid, numbers increased by 1
    static void activity26() {
        int n = readInt("Number of rows : ", 1, 30);
        int num = 1;
        for (int i = 1; i <= n; i++) {
            spaces(n - i);
            for (int j = 1; j <= i; j++) {
                if (j > 1) System.out.print(" ");
                System.out.print(num++);
            }
            System.out.println();
        }
    }

    // 27. Right triangle, numbers increased by 1
    static void activity27() {
        int n = readInt("Number of rows : ", 1, 30);
        int num = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                if (j > 1) System.out.print(" ");
                System.out.print(num++);
            }
            System.out.println();
        }
    }

    // 28. Right triangle, row number repeated
    static void activity28() {
        int n = readInt("Number of rows : ", 1, 50);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) System.out.print(j > 1 ? " " + i : String.valueOf(i));
            System.out.println();
        }
    }

    // 29. Right triangle, 1 to row number
    static void activity29() {
        int n = readInt("Number of rows : ", 1, 50);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) System.out.print(j > 1 ? " " + j : "1");
            System.out.println();
        }
    }

    // 30. Diamond (n rows = upper half including the widest row)
    static void activity30() {
        int n = readInt("Number of rows (half of the diamond) : ", 1, 30);
        for (int i = 1; i <= n; i++) starRow(n, i);
        for (int i = n - 1; i >= 1; i--) starRow(n, i);
    }

    // 31. Number pyramid 1 .. i .. 1
    static void activity31() {
        int n = readInt("Number of rows : ", 1, 30);
        for (int i = 1; i <= n; i++) {
            spaces(2 * (n - i));
            for (int j = 1; j <= i; j++) System.out.print(j > 1 ? " " + j : "1");
            for (int j = i - 1; j >= 1; j--) System.out.print(" " + j);
            System.out.println();
        }
    }
}