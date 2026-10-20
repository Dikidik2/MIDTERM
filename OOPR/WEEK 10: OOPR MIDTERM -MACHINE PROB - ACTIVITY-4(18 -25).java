// PROGRAMIMING 24 Simple ROS – Restaurant Ordering System
import java.util.Scanner;

public class RestaurantOrderingSystem {

    static final int ITEM_COUNT = 3;
    static final String LINE = "=".repeat(48);
    static final String DASH = "-".repeat(48);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ---- Customer Information ----
        System.out.print("Customer Name: ");
        String name = scanner.nextLine().trim();

        String orderType;
        while (true) {
            System.out.print("Order Type (Dine-In / Takeout): ");
            String in = scanner.nextLine().trim().toLowerCase().replace("-", "").replace(" ", "");
            if (in.equals("dinein")) { orderType = "Dine-In"; break; }
            if (in.equals("takeout")) { orderType = "Takeout"; break; }
            System.out.println("Invalid order type. Please enter Dine-In or Takeout.");
        }

        // ---- Order Details ----
        String[] itemNames = new String[ITEM_COUNT];
        int[] qty = new int[ITEM_COUNT];
        double[] price = new double[ITEM_COUNT];
        double[] itemTotal = new double[ITEM_COUNT];
        double subtotal = 0;

        for (int i = 0; i < ITEM_COUNT; i++) {
            System.out.println("\n--- Item " + (i + 1) + " ---");
            System.out.print("Item Name: ");
            itemNames[i] = scanner.nextLine().trim();
            qty[i] = readInt(scanner, "Quantity: ");
            price[i] = readDouble(scanner, "Price per Item: ");
            itemTotal[i] = qty[i] * price[i];
            subtotal += itemTotal[i];
        }

        // ---- Additional Charges ----
        System.out.println();
        double serviceCharge = readDouble(scanner, "Service Charge (0 if Takeout): ");
        double packagingFee = readDouble(scanner, "Packaging Fee (0 if Dine-In): ");

        // ---- Payment Details ----
        double discountPercent = readDouble(scanner, "Discount (%): ");
        double tendered = readDouble(scanner, "Amount Tendered: ");

        // ---- Computation ----
        boolean dineIn = orderType.equals("Dine-In");
        double applicableCharge = dineIn ? serviceCharge : packagingFee;
        double totalBeforeDiscount = subtotal + applicableCharge;
        double discountAmount = totalBeforeDiscount * (discountPercent / 100);
        double totalDue = totalBeforeDiscount - discountAmount;
        double change = tendered - totalDue;

        // ---- Receipt ----
        System.out.println();
        System.out.println(LINE);
        System.out.println("        ******** RESTAURANT RECEIPT ********");
        System.out.println(LINE);

        System.out.println("CUSTOMER DETAILS");
        System.out.println(DASH);
        System.out.printf("%-20s: %s%n", "Name", name);
        System.out.printf("%-20s: %s%n", "Order Type", orderType);
        System.out.println(DASH);

        System.out.println("ORDER SUMMARY");
        System.out.println(DASH);
        System.out.printf("%-18s %8s %10s %10s%n", "Item Name", "Quantity", "Price", "Total");
        System.out.println(DASH);
        for (int i = 0; i < ITEM_COUNT; i++) {
            System.out.printf("%-18s %8d %10.2f %10.2f%n", itemNames[i], qty[i], price[i], itemTotal[i]);
        }
        System.out.println(DASH);

        System.out.println("BILLING SUMMARY");
        System.out.println(DASH);
        System.out.printf("%-28s: %12.2f%n", "Subtotal", subtotal);
        System.out.printf("%-28s: %12.2f%n", dineIn ? "Service Charge" : "Packaging Fee", applicableCharge);
        System.out.printf("%-28s: %12.2f%n", "Total Before Discount", totalBeforeDiscount);
        System.out.printf("%-28s: %11.2f%%%n", "Discount (%)", discountPercent);
        System.out.printf("%-28s: %12.2f%n", "Discount Amount", discountAmount);
        System.out.printf("%-28s: %12.2f%n", "Total Amount Due", totalDue);
        System.out.printf("%-28s: %12.2f%n", "Amount Tendered", tendered);
        if (change >= 0) {
            System.out.printf("%-28s: %12.2f%n", "Change", change);
        } else {
            System.out.printf("%-28s: %12.2f%n", "Change", 0.00);
            System.out.printf("%-28s: %12.2f%n", "Remaining Balance", -change);
        }
        System.out.println(LINE);
        System.out.println("        Thank you for dining with us!");
        System.out.println(LINE);

        scanner.close();
    }

    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= 0) return v;
            } catch (NumberFormatException e) { }
            System.out.println("Invalid input. Enter a whole number (0 or more).");
        }
    }

    static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v >= 0) return v;
            } catch (NumberFormatException e) { }
            System.out.println("Invalid input. Enter a number (0 or more).");
        }
    }
}

// PROGRAMMING 25 Simple Flower Reservation with Billing System

import java.util.Scanner;

public class FlowerReservationSystem {

    static final String LINE = "=".repeat(52);
    static final String DASH = "-".repeat(52);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ---- Customer Information ----
        System.out.print("Customer Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Customer ID: ");
        String customerId = scanner.nextLine().trim();
        System.out.print("Contact Number: ");
        String contact = scanner.nextLine().trim();

        // ---- Reservation Details ----
        System.out.print("Flower Type (e.g., Roses, Tulips, Sunflowers): ");
        String flowerType = scanner.nextLine().trim();
        System.out.print("Arrangement Type (e.g., Bouquet, Vase, Box): ");
        String arrangement = scanner.nextLine().trim();
        double pricePerArrangement = readDouble(scanner, "Price per Arrangement: ");
        int quantity = readInt(scanner, "Quantity: ");
        System.out.print("Reservation Date (YYYY-MM-DD): ");
        String reservationDate = scanner.nextLine().trim();
        System.out.print("Pickup/Delivery Date (YYYY-MM-DD): ");
        String deliveryDate = scanner.nextLine().trim();

        // ---- Additional Services ----
        double deliveryFee = readDouble(scanner, "Delivery Fee: ");
        double customizationFee = readDouble(scanner, "Customization Fee: ");
        double otherCharges = readDouble(scanner, "Other Charges: ");

        // ---- Payment Details ----
        double discountPercent = readDouble(scanner, "Discount (%): ");
        double downPayment = readDouble(scanner, "Down Payment: ");

        // ---- Computation ----
        double reservationCost = pricePerArrangement * quantity;
        double totalAdditional = deliveryFee + customizationFee + otherCharges;
        double subtotal = reservationCost + totalAdditional;
        double discountAmount = subtotal * (discountPercent / 100);
        double totalBill = subtotal - discountAmount;
        double remainingBalance = totalBill - downPayment;

        // ---- Receipt ----
        System.out.println();
        System.out.println(LINE);
        System.out.println("      ******** FLOWER RESERVATION RECEIPT ********");
        System.out.println(LINE);

        System.out.println("CUSTOMER DETAILS");
        System.out.println(DASH);
        System.out.printf("%-26s: %s%n", "Name", name);
        System.out.printf("%-26s: %s%n", "Customer ID", customerId);
        System.out.printf("%-26s: %s%n", "Contact Number", contact);
        System.out.println(DASH);

        System.out.println("RESERVATION SUMMARY");
        System.out.println(DASH);
        System.out.printf("%-26s: %s%n", "Flower Type", flowerType);
        System.out.printf("%-26s: %s%n", "Arrangement Type", arrangement);
        System.out.printf("%-26s: %d%n", "Quantity", quantity);
        System.out.printf("%-26s: %14.2f%n", "Price per Arrangement", pricePerArrangement);
        System.out.printf("%-26s: %14.2f%n", "Reservation Cost", reservationCost);
        System.out.printf("%-26s: %s%n", "Reservation Date", reservationDate);
        System.out.printf("%-26s: %s%n", "Pickup/Delivery Date", deliveryDate);
        System.out.println(DASH);

        System.out.println("CHARGES BREAKDOWN");
        System.out.println(DASH);
        System.out.printf("%-26s: %14.2f%n", "Delivery Fee", deliveryFee);
        System.out.printf("%-26s: %14.2f%n", "Customization Fee", customizationFee);
        System.out.printf("%-26s: %14.2f%n", "Other Charges", otherCharges);
        System.out.printf("%-26s: %14.2f%n", "Subtotal", subtotal);
        System.out.println(DASH);

        System.out.println("PAYMENT SUMMARY");
        System.out.println(DASH);
        System.out.printf("%-26s: %13.2f%%%n", "Discount (%)", discountPercent);
        System.out.printf("%-26s: %14.2f%n", "Discount Amount", discountAmount);
        System.out.printf("%-26s: %14.2f%n", "Total Bill", totalBill);
        System.out.printf("%-26s: %14.2f%n", "Down Payment", downPayment);
        System.out.printf("%-26s: %14.2f%n", "Remaining Balance", remainingBalance);
        System.out.println(LINE);
        System.out.println("        Thank you for your reservation!");
        System.out.println(LINE);

        scanner.close();
    }

    static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= 0) return v;
            } catch (NumberFormatException e) { }
            System.out.println("Invalid input. Enter a whole number (0 or more).");
        }
    }

    static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (v >= 0) return v;
            } catch (NumberFormatException e) { }
            System.out.println("Invalid input. Enter a number (0 or more).");
        }
    }
}
