import datetime
import math
import random


def ask_float(prompt):
    while True:
        try:
            return float(input(prompt))
        except ValueError:
            print("Please enter a number.")


def ask_int(prompt):
    while True:
        try:
            return int(input(prompt))
        except ValueError:
            print("Please enter a whole number.")


def p1():
    print("Hello World")


def p2():
    print("Hello", input("What is your name? "))


def p3():
    a, b = ask_float("Enter first number: "), ask_float("Enter second number: ")
    print("The sum of {0} and {1} is {2}".format(a, b, a + b))


def p4():
    a, b = ask_float("Enter first number: "), ask_float("Enter second number: ")
    print("average:", (a + b) / 2)                      


def p5():
    visa, final = ask_float("enter your visa grade : "), ask_float("enter your final grade : ")
    print("average :", visa * 0.3 + final * 0.7)


def p6():
    grades = [ask_float(f"your {n} exam : ") for n in ("first", "second", "third")]
    print("average :", sum(grades) / 3)


def p7():
    print("Passed" if ask_float("enter average : ") >= 50 else "Failed")


def p8():
    n = ask_int("Enter a number: ")
    print("{0} is {1}".format(n, "Even" if n % 2 == 0 else "Odd"))


def p9():
    n = ask_float("Enter a number: ")
    print("Positive number" if n > 0 else "Zero" if n == 0 else "Negative number")


def p10():
    print("body mass index calculation program")
    height, weight = ask_float("enter height (m): "), ask_float("enter weight (kg): ")
    bmi = weight / (height * height)
    label = ("underweight" if bmi < 18.5 else "normal weight" if bmi <= 25
             else "overweight" if bmi <= 30 else "obese")
    print(f"\n {label} BMI: {bmi:.2f}")


def p11():
    print("Your Age Is Not Eligible To Get A Driver's License" if ask_int("enter age : ") < 18
          else "Your Age Is Eligible To Get Your License")


def p12():
    for i in range(1, 101):
        print(i)


def p13():
    for i in range(1, 101):
        if i % 2 == 0:
            print(i)


def p14():
    for i in range(1, 101):
        if i % 2 != 0:
            print(i)


def p15():
    for i in range(1, 101):
        if i % 3 == 0 or i % 5 == 0:                          
            print(i)


def p16():
    for i in range(1, ask_int("enter number : ") + 1):
        print(i)


def p17():
    short, tall = ask_int("Enter short side : "), ask_int("Enter tall side : ")
    print("area:", short * tall)                            
    print("perimeter:", 2 * (short + tall))


def p18():
    for char in input("Enter a word: "):
        print(char)


def p19():
    n1, n2 = ask_int("first number: "), ask_int("second number: ")
    total = sum(range(n1 + 1, n2))                          
    print("Sum of numbers between {0} and {1} : {2}".format(n1, n2, total))


def p20():
    selection = input("Press (1) for Cinema, (2) for Theater : ").strip()
    student = input("Are you student(Y/N) : ").strip()
    price = {"1": 10, "2": 5}.get(selection)
    if price is None:
        print("Invalid selection.")
        return
    if student.upper() == "Y":
        price /= 2
    print(" The fee you have to pay :", price)


def p21():
    num = ask_int("Enter a number: ")
    if num > 1:
        for i in range(2, num):
            if num % i == 0:
                print(num, "is not a prime number")
                print(i, "times", num // i, "is", num)
                break
        else:
            print(num, "is a prime number")
    else:
        print(num, "is not a prime number")


def p22():
    values = [ask_int(f"Please enter the Value of {i} Element : ")
              for i in range(1, ask_int("Please enter the Total Number of List Elements: ") + 1)]
    print("\nThe Sum of Even Numbers in this List =", sum(v for v in values if v % 2 == 0))
    print("The Sum of Odd Numbers in this List =", sum(v for v in values if v % 2 != 0))


def p23():
    salary, rate = ask_float("enter salary : "), ask_float("salary raise rate(%) : ")  
    print("increased salary :", salary + salary * rate / 100)


def find_diameter(radius): return 2 * radius
def find_circumference(radius): return 2 * math.pi * radius
def find_area(radius): return math.pi * radius * radius


def p24():
    r = ask_float(" Please Enter the radius of a circle: ")
    print("\n Diameter Of a Circle = %.2f" % find_diameter(r))
    print(" Circumference Of a Circle = %.2f" % find_circumference(r))
    print(" Area Of a Circle = %.2f" % find_area(r))


def area_rectangle(a, b): return a * b
def perimeter_rectangle(a, b): return 2 * (a + b)


def p25():
    a, b = ask_float("Enter width: "), ask_float("Enter height: ")
    print("Area =", area_rectangle(a, b))
    print("Perimeter =", perimeter_rectangle(a, b))


def p26():
    lower, upper = ask_int("Enter Lower bound:- "), ask_int("Enter Upper bound:- ")
    if upper < lower:
        print("Upper bound must be >= lower bound.")
        return
    x = random.randint(lower, upper)
    chances = max(1, math.ceil(math.log(upper - lower + 1, 2)))
    print(f"\n\tYou've only {chances} chances to guess the integer!\n")
    for count in range(1, chances + 1):
        guess = ask_int("Guess a number:- ")
        if guess == x:
            print("Congratulations you did it in", count, "try")
            return
        print("You guessed too small!" if x > guess else "You Guessed too high!")
    print("\nThe number is %d" % x)
    print("\tBetter Luck Next time!")


def p27():
    text = input("Enter the date(for example:09 02 2019):")
    try:
        day = datetime.datetime.strptime(text, "%d %m %Y").weekday()
    except ValueError:
        print("Invalid date. Use the format dd mm yyyy.")
        return
    print(["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"][day])


def find_missing(lst):
    return [x for x in range(lst[0], lst[-1] + 1) if x not in lst]


def p28():
    print(find_missing([1, 2, 4, 6, 7, 9, 10]))            


def p29():
    char_list = ["a", "b", "c"]
    matched = [ch in char_list for ch in "abcd"]
    print(matched)                                         
    print(all(matched))


def p30():
    total = even_sum = odd_sum = even_n = odd_n = 0
    while True:
        text = input("Give me an integer or type 'done' to be done. ")
        if text.lower() == "done":
            break
        try:
            v = int(text)
        except ValueError:
            print("Not an integer, try again.")
            continue
        total += v
        if v % 2 == 0:
            even_sum, even_n = even_sum + v, even_n + 1
        else:
            odd_sum, odd_n = odd_sum + v, odd_n + 1
    print(total)
    print("Even Average:", even_sum / even_n if even_n else "n/a")
    print("Odd Average:", odd_sum / odd_n if odd_n else "n/a")


PROGRAMS = {i: f for i, f in enumerate(
    [p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15, p16, p17, p18, p19, p20,
     p21, p22, p23, p24, p25, p26, p27, p28, p29, p30], start=1)}

if __name__ == "__main__":
    while True:
        choice = ask_int("\nChoose a program (1-30, 0 to exit): ")
        if choice == 0:
            break
        PROGRAMS.get(choice, lambda: print("Pick 1 to 30."))()
    print("Goodbye!")