first_name = input("Enter first name: ")
last_name = input("Enter last name: ")
full_name = first_name + " " + last_name

print("\n" + "="*40 + "\n")
print("CHOOSE A PROGRAM TO RUN:")
print("1. Program 1")
print("2. Program 2")
choice = input("Enter choice (1 or 2): ")

print("\n" + "="*40 + "\n")

if choice == "1":
    print("--- PROGRAM 1 ---")
    bytes_obj = full_name.encode('utf-8')
    hex_str = bytes_obj.hex()
    ba = bytearray(bytes_obj)
    
    print("1. Bytes object:", bytes_obj)
    print("2. Bytes to string:", bytes_obj.decode('utf-8'))
    print("3. Bytes to hex:", hex_str)
    print("4. Hex to bytes:", bytes.fromhex(hex_str))
    print("5. New bytearray:", bytearray(full_name, 'utf-8'))
    print("6. Bytes to bytearray:", ba)
    
    print("7. Sliced bytes:", bytes_obj[:6])
    
    if ba:
        ba[0] = ord(chr(ba[0]).lower())
    print("8. Modified message:", ba.decode('utf-8'))

elif choice == "2":
    print("--- PROGRAM 2 ---")
    print("First name:", first_name)
    print("Last name:", last_name)
    print("Full name:", full_name)
    print("First character:", full_name[0] if full_name else "")
    print("Last character:", full_name[-1] if full_name else "")

    try:
        full_name[0] = "D"
    except TypeError:
        print("Strings are immutable; individual characters")
        print("cannot be changed directly.")

    print("New name:", "D" + first_name[1:])
    print("Original name:", first_name)

    print("Characters in the full name:")
    for char in full_name:
        print(char)

    print("Sliced first name:", full_name[:len(first_name)])
    print("Sliced last name:", full_name[len(first_name)+1:])

    search_char = input("Enter one character to search: ")
    if search_char in full_name:
        print("Character found at index:", full_name.find(search_char))
    else:
        print("Character not found.")

    before_space = first_name[-1] if first_name else ""
    after_space = last_name[0] if last_name else ""
    print(f'The blank line between {before_space} and {after_space} is the space in "{full_name}".')

else:
    print("Invalid choice! Please run the program again and select 1 or 2.")
