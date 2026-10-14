from datetime import datetime, date
import math
import re

def parse_flexible_date(user_input: str) -> date:
    parts = re.sub(r'[^0-9]+', ' ', user_input).strip().split()
    
    if len(parts) != 3 or not parts:
        raise ValueError("Date format must include 3 groups of numbers (Month, Day, Year).")
    
    try:
        month = int(parts[0])
        day = int(parts[1])
        year = int(parts[2])

        if year < 100:
            year += 2100

        return date(year, month, day)
    except Exception:
        raise ValueError("Please enter a valid structural calendar date sequence.")

def main():
    name = ""

    while True:
        try:
            name = input("Please enter your name: ").strip()
            if not name:
                raise ValueError("Name cannot be left empty.")
            if not re.match(r'^[a-zA-Z\s]+$', name):
                raise ValueError("Name must only contain letters and spaces (no numbers or symbols).")
            break
        except Exception as e:
            print(f"[Error] {e}\n")

    keep_running = True
    while keep_running:
        print(f"\n=== Welcome {name}! ===\n")
        
        total = 0
        read = 0
        issue = None
        due = None
        raw_issue_input = ""

        while True:
            try:
                total_str = input("How many pages does the book have? ").strip()
                if not re.match(r'^\d+$', total_str):
                    raise ValueError("Total pages must be a positive whole number (no letters or decimals).")
                total = int(total_str)
                if total <= 0:
                    raise ValueError("Total pages must be greater than 0.")
                break
            except Exception as e:
                print(f"[Error] {e}\n")

        while True:
            try:
                read_str = input("How many pages have you read? ").strip()
                if not re.match(r'^\d+$', read_str):
                    raise ValueError("Pages read must be a non-negative whole number (no letters or decimals).")
                read = int(read_str)
                if read > total:
                    raise ValueError(f"Pages read cannot be greater than the total book pages ({total}).")
                break
            except Exception as e:
                print(f"[Error] {e}\n")

        while True:
            try:
                raw_issue_input = input("Enter Issue Date (e.g., 10, 08, 26 or 10/08/26): ").strip()
                issue = parse_flexible_date(raw_issue_input)
                break
            except Exception as e:
                print(f"[Error] {e}\n")

        while True:
            try:
                due_input = input(f"Enter Due Date (e.g., must be after {raw_issue_input}): ").strip()
                due = parse_flexible_date(due_input)
                if due < issue:
                    raise ValueError(f"Due date cannot be before the issue date ({raw_issue_input}).")
                break
            except Exception as e:
                print(f"[Error] {e}\n")

        days_remaining = max(0, (due - issue).days)
        
        if days_remaining > 0:
            per_day = math.ceil((total - read) / days_remaining)
        else:
            per_day = total - read

        print("\n--- RESULTS ---")
        print(f"You have read {(read / total) * 100:.0f}% of the book.")
        print(f"You have {days_remaining} days remaining to return the book.")
        print(f"You should read {per_day} pages per day.")

        choice = input("\nDo you want to calculate another book? (y/n): ").strip().lower()
        if choice not in ['y', 'yes']:
            keep_running = False
            print(f"\nThank you for using the Library System, {name}! Goodbye.")
        else:
            print("\n--------------------------------------------------\n")

if __name__ == "__main__":
    main()
