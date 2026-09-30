import java.util.Scanner;

public class OOPR {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        char continueChoice;

        do {
            System.out.println("Choose what program to run");
            System.out.println("1");
            System.out.println("2");
            System.out.println("3");
            System.out.println("4");
            System.out.println("5");
            System.out.println("6");
            System.out.println("7");
            System.out.print("Enter number: ");
            int choice = input.nextInt();

            if (choice == 1) {
                double[] numbers = new double[10];
                System.out.println("Enter 10 real numbers:");
                for (int i = 0; i < 10; i++) {
                    numbers[i] = input.nextDouble();
                }

                double sum = 0;
                int positiveCount = 0;
                for (int i = 0; i < 10; i++) {
                    if (numbers[i] > 0) {
                        sum += numbers[i];
                        positiveCount++;
                    }
                }
                double average = (positiveCount > 0) ? sum / positiveCount : 0;
                System.out.println("Sum of positive numbers: " + sum);
                System.out.println("Average of positive numbers: " + average);

                int negativeCount = 0;
                for (int i = 0; i < 10; i++) {
                    if (numbers[i] < 0) {
                        negativeCount++;
                    }
                }
                System.out.println("Negative numbers count: " + negativeCount);

                double min = numbers[0];
                for (int i = 1; i < 10; i++) {
                    if (numbers[i] < min) {
                        min = numbers[i];
                    }
                }
                System.out.println("Minimum value: " + min);

            } else if (choice == 2) {
                int[] numbers = new int[8];
                System.out.println("Enter 8 integer numbers:");
                for (int i = 0; i < 8; i++) {
                    numbers[i] = input.nextInt();
                }

                int uniqueCount = 0;
                int[] uniqueArr = new int[8];
                for (int i = 0; i < 8; i++) {
                    boolean isDuplicate = false;
                    for (int j = 0; j < uniqueCount; j++) {
                        if (numbers[i] == uniqueArr[j]) {
                            isDuplicate = true;
                            break;
                        }
                    }
                    if (!isDuplicate) {
                        uniqueArr[uniqueCount] = numbers[i];
                        uniqueCount++;
                    }
                }

                System.out.print("Array after removing duplicates: ");
                for (int i = 0; i < uniqueCount; i++) {
                    System.out.print(uniqueArr[i] + " ");
                }
                System.out.println();

                for (int i = 0; i < uniqueCount - 1; i++) {
                    for (int j = 0; j < uniqueCount - i - 1; j++) {
                        if (uniqueArr[j] > uniqueArr[j + 1]) {
                            int temp = uniqueArr[j];
                            uniqueArr[j] = uniqueArr[j + 1];
                            uniqueArr[j + 1] = temp;
                        }
                    }
                }

                if (uniqueCount >= 2) {
                    System.out.println("Second smallest: " + uniqueArr[1]);
                    System.out.println("Second largest: " + uniqueArr[uniqueCount - 2]);
                } else {
                    System.out.println("Not enough unique elements to find second values.");
                }

            } else if (choice == 3) {
                int[] arr = new int[5];
                System.out.print("Enter Data in Array: ");
                for (int i = 0; i < 5; i++) {
                    arr[i] = input.nextInt();
                }

                System.out.print("Stored Data in Array: ");
                for (int i = 0; i < 5; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();

                System.out.print("Enter poss. of Element to Delete: ");
                int pos = input.nextInt();

                System.out.print("New data in Array: ");
                for (int i = 0; i < 5; i++) {
                    if (i != pos) {
                        System.out.print(arr[i] + " ");
                    }
                }
                System.out.println();

            } else if (choice == 4) {
                System.out.print("Enter Size of Array: ");
                int size = input.nextInt();
                int[] arr = new int[size];

                System.out.println("Enter any " + size + " elements in Array:");
                for (int i = 0; i < size; i++) {
                    arr[i] = input.nextInt();
                }

                System.out.print("Even Elements: ");
                for (int i = 0; i < size; i++) {
                    if (arr[i] % 2 == 0) {
                        System.out.print(arr[i] + " ");
                    }
                }
                System.out.println();

                System.out.print("Odd Elements: ");
                for (int i = 0; i < size; i++) {
                    if (arr[i] % 2 != 0) {
                        System.out.print(arr[i] + " ");
                    }
                }
                System.out.println();

            } else if (choice == 5) {
                System.out.println("*");
                System.out.println("A");
                System.out.println("AA*");
                System.out.println("AAA");

            } else if (choice == 6) {
                System.out.println("Creating student 1 (Default Constructor)...");
                Student s1 = new Student();
                System.out.println("Student 1 No: " + s1.getStudentNo());
                System.out.println("Student 1 Name: " + s1.getStudentName());
                System.out.println("Student 1 DOB: " + s1.getDateOfBirth());
                System.out.println("Student 1 Tariff: " + s1.getTariffPoints());

                System.out.println("\nCreating student 2 (Parameterized Constructor)...");
                Student s2 = new Student("S101", "John Doe", "15th August 1996", 150);
                System.out.println("Student 2 No: " + s2.getStudentNo());
                System.out.println("Student 2 Name: " + s2.getStudentName());
                System.out.println("Student 2 DOB: " + s2.getDateOfBirth());
                System.out.println("Student 2 Tariff: " + s2.getTariffPoints());

                System.out.println("\nTotal students created: " + Student.noOfStudents);

            } else if (choice == 7) {
                System.out.println("Simulating DB Record Inserts:");
                System.out.println("Inserted: 101 | RavikumarRanga | 9849211983");
                System.out.println("Inserted: 102 | Gurulingam | 949459306");
                System.out.println("Inserted: 103 | Gsr | 9553122275");
            }

            System.out.print("Do you want to continue ? Y/N: ");
            continueChoice = input.next().charAt(0);

        } while (continueChoice == 'Y' || continueChoice == 'y');
    }
}

class Student {
    private String studentNo;
    private String studentName;
    private String dateOfBirth;
    private int tariffPoints;
    public static int noOfStudents = 0;

    public Student() {
        this.studentNo = "not known";
        this.studentName = "not known";
        this.dateOfBirth = "1st January 1995";
        this.tariffPoints = 20;
        noOfStudents++;
    }

    public Student(String studentNo, String studentName, String dateOfBirth, int tariffPoints) {
        this.studentNo = studentNo;
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
        if (tariffPoints >= 20 && tariffPoints <= 280) {
            this.tariffPoints = tariffPoints;
        } else {
            this.tariffPoints = 20;
        }
        noOfStudents++;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getTariffPoints() {
        return tariffPoints;
    }

    public void setTariffPoints(int tariffPoints) {
        if (tariffPoints >= 20 && tariffPoints <= 280) {
            this.tariffPoints = tariffPoints;
        }
    }
}