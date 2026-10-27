"""PLNG - MIDTERM - Practice #1 (Programs 1-6). Run: python plng_midterm_practice1.py"""


def read_float(prompt):
    while True:
        try:
            return float(input(prompt))
        except ValueError:
            print("Please enter a valid number.")


def read_int(prompt):
    while True:
        try:
            return int(input(prompt))
        except ValueError:
            print("Please enter a whole number.")


def program1():
    a = [read_float(f"Enter number {i + 1}: ") for i in range(10)]
    positives = [v for v in a if v > 0]                    
    if positives:
        print("Sum of positive numbers:", sum(positives))
        print("Average of positive numbers:", sum(positives) / len(positives))
    else:
        print("There are no positive numbers.")
    negatives = 0                                           
    for v in a:
        if v < 0:
            negatives += 1
    print("Count of negative numbers:", negatives)
    smallest = a[0]                                         
    for v in a:
        if v < smallest:
            smallest = v
    print("Minimum value:", smallest)


def program2():
    a = [read_int(f"Enter integer {i + 1}: ") for i in range(8)]
    unique = []
    for v in a:                                             
        if v not in unique:
            unique.append(v)
    print("Array without duplicates:", unique)
    if len(unique) < 2:
        print("Not enough distinct values for a second largest/smallest.")
        return
    ordered = sorted(unique)
    print("Second largest element:", ordered[-2])
    print("Second smallest element:", ordered[1])


def program3():
    try:
        data = [int(x) for x in input("Enter Data in Array: ").split()]
    except ValueError:
        print("Please enter whole numbers separated by spaces.")
        return
    print("Stored Data in Array:", *data)
    pos = read_int("Enter poss. of Element to Delete: ")
    if not 0 <= pos < len(data):
        print(f"Invalid position. Valid positions are 0 to {len(data) - 1}.")
        return
    del data[pos]
    print("New data in Array:", *data)


def program4():
    size = read_int("Enter Size of Array : ")
    print(f"Enter any {size} elements in Array: ")
    a = [read_int("") for _ in range(size)]
    print("Even Elements:", *[v for v in a if v % 2 == 0])
    print("Odd Elements:", *[v for v in a if v % 2 != 0])


def program5():
    for row in range(4):
        print("*" + "A*" * row)


def program6():
    basic = 12000
    da, hra, ta, others = 0.12 * basic, 150, 120, 450
    pf, it = 0.14 * basic, 0.15 * basic
    net = basic + da + hra + ta + others - (pf + it)
    print(f"Basic: ${basic:.2f}  DA: ${da:.2f}  HRA: ${hra:.2f}  TA: ${ta:.2f}  Others: ${others:.2f}")
    print(f"PF: ${pf:.2f}  IT: ${it:.2f}")
    print(f"Net Salary: ${net:.2f}")


PROGRAMS = {1: program1, 2: program2, 3: program3, 4: program4, 5: program5, 6: program6}

if __name__ == "__main__":
    while True:
        print("\nChoose the program you want to run")
        for n in PROGRAMS:
            print(f"Program #{n}")
        choice = read_int("Your choice (1-6): ")
        if choice in PROGRAMS:
            PROGRAMS[choice]()
        else:
            print("Invalid choice. Please pick 1 to 6.")
        if input("Do you want to continue ? Y/N ").strip().upper() != "Y":
            break
    print("Goodbye!")