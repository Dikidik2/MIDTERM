import csv
from random import randint


def ask_int(prompt):
    while True:
        try:
            return int(input(prompt))
        except ValueError:
            print("Please enter a whole number.")


def ask_float(prompt):
    while True:
        try:
            return float(input(prompt))
        except ValueError:
            print("Please enter a number.")


def p31():
    try:
        import pymysql.cursors
    except ImportError:
        print("pymysql is not installed. Run: pip install pymysql")
        return
    try:
        connection = pymysql.connect(host="localhost", user="root", password="", db="students",
                                     charset="utf8mb4", cursorclass=pymysql.cursors.DictCursor)
    except pymysql.MySQLError as e:
        print("Database error:", e)
        return
    try:
        with connection.cursor() as cursor:
            cursor.execute("SELECT `id`, `firstname`, `lastname` FROM `users`")
            for row in cursor.fetchall():
                print("firstname : " + str(row["firstname"]))
                print("lastname : " + str(row["lastname"]))
    finally:
        connection.close()


def p32():
    from tkinter import Tk
    window = Tk()
    window.title("Hello Python")
    window.geometry("300x200+10+20")
    window.mainloop()


def p33():
    from tkinter import Tk, Frame, Label, Entry
    window = Tk()
    window.title("Entry form")
    window.geometry("400x300")
    application = Frame(window)                       
    application.grid()
    Label(application, text="Enter your name").grid(padx=110, pady=10)
    Entry(application, bd=2).grid(padx=110, pady=3)
    window.mainloop()


def p34():
    from tkinter import Tk, Frame, Listbox
    window = Tk()
    window.title("List box")
    window.geometry("400x300")
    application = Frame(window)
    application.grid()
    box = Listbox(application)
    for i, item in enumerate(["Python", "C#", "JAVA", "JAVASCRIPT"], start=1):
        box.insert(i, item)
    box.grid(padx=110, pady=10)
    window.mainloop()                            


def p35():
    from tkinter import Tk, Label, Entry, Button, END
    labels = ["Name", "Course", "Semester", "Form No.", "Contact No.", "Email id", "Address"]
    root = Tk()
    root.configure(background="light green")
    root.title("registration form")
    root.geometry("500x300")
    Label(root, text="Form", bg="light green").grid(row=0, column=1)
    fields = []
    for i, text in enumerate(labels, start=1):
        Label(root, text=text, bg="light green").grid(row=i, column=0)
        entry = Entry(root)
        entry.grid(row=i, column=1, ipadx=100)
        fields.append(entry)
    for current, nxt in zip(fields, fields[1:]):   
        current.bind("<Return>", lambda event, n=nxt: n.focus_set())

    def insert():
        values = [f.get() for f in fields]
        if not any(v.strip() for v in values):
            print("empty input")
            return
        with open("registrations.csv", "a", newline="", encoding="utf-8") as fh:
            writer = csv.writer(fh)
            if fh.tell() == 0:
                writer.writerow(labels)
            writer.writerow(values)
        for f in fields:                           
            f.delete(0, END)
        fields[0].focus_set()

    Button(root, text="Submit", fg="Black", bg="Red", command=insert).grid(row=8, column=1)
    root.mainloop()


def p36():
    rand = randint(1, 100)
    counter = 0
    while True:
        counter += 1
        num = ask_int("Enter values between 1 and 100 (0 exit):")
        if num == 0:
            print("You canceled the game.")
            break
        elif num < rand:
            print("Enter a Higher Number.")
        elif num > rand:
            print("Enter a Lower Number.")
        else:
            print("Randomly selected number {0}!".format(rand))
            print("Your guess number {0}".format(counter))
            break


def p37():
    print("My favourite fruits {}".format(["apples", "erics", "oranges"]))


def p38():
    print([3.14, 2.54, "CPU", "WINDOWS 10"])


def p39():
    weekdays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
    print(weekdays[4])                                


def p40():
    values = [3, 1, 2]
    values[0], values[1], values[2] = 1, 1, 2
    print(values)


def p41():
    weekdays = ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday"]
    print("Friday" in weekdays)
    print("Saturday" in weekdays)


def p42():
    for num in [10, 21, 4, 45, 66, 93]:
        if num % 2 == 0:
            print(num, end=" ")
    print()


def p43():
    combine = list(range(3, 19)) + [21, 22, 23, 24, 25]
    for number in combine:                            
        if number % 4 == 0:
            print(number)


def p44():
    i = 1
    while i <= 12:
        print(" {}. class".format(i))
        i += 1


def p45():
    number = input("enter a number: ").strip()
    if not number.isdigit():
        print("Digits only, please.")
        return
    print("the sum of the digits of the number:", sum(int(d) for d in number))


def p46():
    number = input("enter a number: ").strip()
    if not number.isdigit():
        print("Digits only, please.")
        return
    product = 1
    for digit in number:
        if int(digit) != 0:
            product *= int(digit)
    print("product of the digits of the number:", product)


def p47():
    text = input("enter a name: ")
    for _ in range(10):
        print(text)


def p48():
    text = input("enter a name: ")
    i = 0
    while i < 10:
        i += 1
        print(text)


def p49():
    values = [35, 48, 52, 57, 68, 84]
    print(sum(values) / len(values))


def p50():
    total = 0
    while True:
        num = ask_float("enter a number: ")
        if num == 0:
            break
        total += num
    print("The sum of the numbers: ", total)


PROGRAMS = {n: f for n, f in zip(range(31, 51), [
    p31, p32, p33, p34, p35, p36, p37, p38, p39, p40, p41, p42, p43, p44, p45, p46, p47, p48, p49, p50])}

if __name__ == "__main__":
    while True:
        choice = ask_int("\nChoose a program (31-50, 0 to exit): ")
        if choice == 0:
            break
        PROGRAMS.get(choice, lambda: print("Pick 31 to 50."))()
    print("Goodbye!")