from dataclasses import dataclass


def ask_float(prompt):
    while True:
        try:
            return float(input(prompt))
        except ValueError:
            print("Please enter a valid number.")

def ask_int(prompt):
    while True:
        try:
            return int(input(prompt))
        except ValueError:
            print("Please enter a whole number.")

def program1():
    print("Welcome!")
    balance = ask_float("Enter your balance: ")
    while True:
        print("-" * 55)
        print("Select an option:\n1. Withdraw Amount\n2. Deposit Amount\n3. Exit")
        print("-" * 55)
        choice = ask_int("Option: ")
        if choice == 1:
            print("How much amount you want to withdraw from your account?")
            amount = ask_float("Amount: ")
            if amount <= 0:
                print("Amount must be greater than zero.")
            elif amount > balance:
                print(f"Insufficient balance. Your balance is: {balance:g}")
            else:
                balance -= amount
                print(f"Your new balance is: {balance:g}")
        elif choice == 2:
            print("How much amount you want to deposit in your account?")
            amount = ask_float("Amount: ")
            if amount <= 0:
                print("Amount must be greater than zero.")
            else:
                balance += amount
                print(f"Your new balance is: {balance:g}")
        elif choice == 3:
            print("Thank you for using the system!")
            return
        else:
            print("Invalid option. Choose 1, 2, or 3.")



@dataclass
class Date:
    """The 'structure' for a date: year, month and day members."""
    year: int
    month: int
    day: int

    def __str__(self):
        return f"{self.year:04d}-{self.month:02d}-{self.day:02d}"


def enter_date(label):
    while True:
        parts = input(f"{label} (yyyy mm dd): ").split()
        try:
            y, m, d = (int(p) for p in parts)
            if len(parts) == 3 and 1 <= m <= 12 and 1 <= d <= 31 and y >= 1:
                return Date(y, m, d)
        except ValueError:
            pass
        print("Invalid date. Example: 2026 10 27")


class Patient:
    """Base class: name, admission date, disease, discharge date."""

    def enter(self):
        self.name = input("1. Name of the patient: ").strip()
        self.admission = enter_date("2. Date of admission")
        self.disease = input("3. Disease: ").strip()
        self.discharge = enter_date("4. Date of discharge")

    def display(self):
        print(f"Name: {self.name} | Admitted: {self.admission} | Disease: {self.disease} | Discharged: {self.discharge}")


class AgedPatient(Patient):
    """Derived class: adds age."""

    def enter(self):
        super().enter()
        self.age = ask_int("5. Age: ")

    def display(self):
        print(f"Name: {self.name} | Age: {self.age} | Admitted: {self.admission} | "
              f"Disease: {self.disease} | Discharged: {self.discharge}")


def program2():
    db = []
    while True:
        print("\nOUTPUT IN ACTIVITY 2\n1. Insert New Patient\n2. Display all Patient\n"
              "3. Display pediatric patient\n4. Exit")
        choice = ask_int("Choice: ")
        if choice == 1:
            p = AgedPatient()
            p.enter()
            db.append(p)
        elif choice == 2:
            if not db:
                print("No patients in the database.")
            for p in db:
                p.display()
        elif choice == 3:
            kids = [p for p in db if p.age < 12]
            if not kids:
                print("No pediatric patients (under 12) found.")
            for p in kids:
                p.display()
        elif choice == 4:
            return
        else:
            print("Invalid choice. Pick 1 to 4.")

class Professor:
    def __init__(self, name, salary):
        self.name, self.salary = name, salary


class Dean(Professor):
    def __init__(self, name, salary, department):
        super().__init__(name, salary)
        self.department = department

    rank = "Dean"

    def __str__(self):
        return f"Status\nRank: {self.rank}\nName: {self.name}\nDepartment: {self.department}\nSalary: {self.salary}"


class Executive(Dean):
    rank = "Executive"        


def program3():
    print(Dean("John Cena", 32000, "English"))
    print("-" * 50)
    print(Executive("Ronald McDonald", 42000, "Computer Science"))


if __name__ == "__main__":
    while True:
        print("\nChoose a program: 1 Bank  2 Hospital  3 Professor/Dean/Executive  4 Exit")
        choice = ask_int("Choice: ")
        if choice == 1:
            program1()
        elif choice == 2:
            program2()
        elif choice == 3:
            program3()
        elif choice == 4:
            print("Goodbye!")
            break
        else:
            print("Invalid choice. Pick 1 to 4.")