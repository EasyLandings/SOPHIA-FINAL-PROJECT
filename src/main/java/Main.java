package main.java;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {
    // instantiate scanner object to use scanner
    Scanner scanner = new Scanner(System.in);
    // File object to read from file
    String csvFile = "src/main/java/addressBook.csv";

    // Check if file exists, if not create it
    if (!initializeAddressBook(csvFile)) {
      System.out.println("Error initializing address book. Program will exit.");
      scanner.close();
      return;
    }

    while (true) {
      // Read current contacts
      ArrayList<Contact> contacts = readAddressBook(csvFile);

      // Display menu
      System.out.println("\nAddress Book Menu:");
      System.out.println("1. Display all contacts");
      System.out.println("2. Update a contact");
      System.out.println("3. Delete a contact");
      System.out.println("4. Add a new contact");
      System.out.println("5. Exit");
      System.out.print("Enter your choice: ");
    
      int choice ;
      // Handle invalid input for choice
      try {
          choice = scanner.nextInt();
          scanner.nextLine(); // Clear buffer
          
      } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter an integer.");
          scanner.nextLine(); // Clear invalid input
          continue;
      }
    
     
      // If choice equals one of these cases, run the method corresponding to the case also added if statement to check if contacts is empty first.
      switch (choice) {
        case 1:
        if (contacts.isEmpty()) {
            System.out.println("Address Book Empty! You need to add some contacts!");
            break;
        }
          displayContacts(contacts);
          break;
        case 2:
        if (contacts.isEmpty()) {
            System.out.println("Address Book Empty! You need to add some contacts!");
            break;
        }
          updateContact(contacts, scanner, csvFile);
          break;
        case 3:
        if (contacts.isEmpty()) {
            System.out.println("Address Book Empty! You need to add some contacts!");
            break;
        }
          deleteContact(contacts, scanner, csvFile);
          break;
        case 4:
          addContact(scanner, csvFile);
          break;
        case 5:
          System.out.println("Goodbye!");
          scanner.close();
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }

    }

  }

  // if contacts is empty, print "No contacts to delete"
  public static void deleteContact(ArrayList<Contact> contacts, Scanner scanner, String csvFile) {
    if (contacts.isEmpty()) {
      System.out.println("No contacts to delete!");
      return;
    }
    // otherwise display all contacts
    displayContacts(contacts);

    System.out.print("\nEnter the ID of the contact to delete: ");
    int id = scanner.nextInt();
    scanner.nextLine(); // Clear buffer

    // check to make sure the id is valid by checking the range of the list size
    if (id < 0 || id >= contacts.size()) {
      System.out.println("Invalid contact ID");
      return;
    }

    // Show contact details and confirm deletion
    Contact contactToDelete = contacts.get(id);
    System.out.println("\nYou are about to delete this contact:");
    System.out.println(contactToDelete.toString());
    System.out.print("Are you sure? (y/n): ");

    // get the confirmation from the user, trim it of extra spaces and convert it to
    // lowercase
    String confirmation = scanner.nextLine().trim().toLowerCase();

    if (confirmation.equals("y")) {
      // Remove the contact
      contacts.remove(id);

      // Save updated list to file or display error messages
      try {
        saveContactsToFile(contacts, csvFile);
        System.out.println("Contact deleted successfully!");
      } catch (Exception e) {
        System.out.println("Error saving changes: " + e.getMessage());
        System.out.println("Please try again.");
      }
    } else {
      System.out.println("Deletion cancelled.");
    }
  }

  // Method to add a new contact
  private static void addContact(Scanner scanner, String csvFile) {
    //set intital values for the contact  
      String name, phone, email;

      // Validate the name input forcing the user to enter a valid name while the length is < 0
      do {
          System.out.print("Enter name: ");
          name = scanner.nextLine();
      
          if (name.length() == 0) {
              System.out.println("Invalid input. Name must be at least 1 character long.");
          }
      } while (name.length() == 0);

      // Validate the phone input forcing the user to enter a valid phone length while the length is < 10
      do {
          System.out.print("Enter phone: ");
          phone = scanner.nextLine();
          if (phone.length() < 10) {
              System.out.println("Invalid input. Phone number must be at least 10 character long.");
          }
      } while (phone.length() < 10);

      // Validate the email input
      do {
          System.out.print("Enter email: ");
          email = scanner.nextLine();
          if (email.length() == 0) {
              System.out.println("Invalid input. Email must be at least 1 character long.");
          }
      } while (email.length() == 0);

      // Create and add the new contact if all validations pass
      Contact newContact = new Contact(name, phone, email);
      addContactToFile(newContact, csvFile);
  }

  // Method to add contact to the CSV file
  private static void addContactToFile(Contact contact, String csvFile) {
    // Create a new list to store all contacts
    try {
      FileWriter writer = new FileWriter(csvFile, true);

      // sb is a string builder object that allows us to build strings by appending
      // characters to it
      StringBuilder sb = new StringBuilder();
      sb.append(contact.getName()).append(",");
      sb.append(contact.getPhone()).append(",");
      sb.append(contact.getEmail()).append(",");
      sb.append(contact.getDateAdded()).append("\n");

      writer.write(sb.toString());
      writer.close();
      System.out.println("Contact added successfully!");
    } catch (IOException e) {
      System.out.println("Error writing to file: " + e.getMessage());
    }
  }

  // Displays the contacts ArrayList and cleans up the output.
  public static void displayContacts(ArrayList<Contact> contacts) {
    System.out.println("\nCurrent Contacts:");
    System.out.println("ID | Name | Phone | Email");
    System.out.println("------------------------");
    // loop through the contacts ArrayList and print each contact's details
    for (int i = 0; i < contacts.size(); i++) {
      System.out.println(i + " | " + contacts.get(i).toString());
    }
  }

  // displays contacts, gets the id of the contact to update, and calls the
  // updateContact method.
  private static void updateContact(ArrayList<Contact> contacts, Scanner scanner, String csvFile) {
      displayContacts(contacts);

      System.out.print("\nEnter the ID of the contact to update: ");
      int id = scanner.nextInt();
      scanner.nextLine(); // Clear buffer

      // if the id doesn't exist, print error message
      if (id < 0 || id >= contacts.size()) {
          System.out.println("Invalid contact ID");
          return;
      }

      Contact contactToUpdate = contacts.get(id);

      System.out.println("\nWhat would you like to update? Enter number 1-3");
      System.out.println("1. Name");
      System.out.println("2. Phone");
      System.out.println("3. Email");
      System.out.print("Enter your choice: ");

      try {
          int updateChoice = scanner.nextInt();
          scanner.nextLine(); // Clear buffer

          // Variable to hold the new value
          String newValue;

          //Used a do-while block to keep the program asking which contact value to update if the user left the value blank.
          do {
              System.out.print("Enter new value: ");
              newValue = scanner.nextLine();

              // Validate the input to be a non-empty string
              if (newValue.length() == 0) {
                  System.out.println("Invalid input. Please enter a value with length greater than 0.");
                  newValue = null;
              }
          } while (newValue == null);

          // Update the contact detail based on the user's choice
          switch (updateChoice) {
              case 1:
                  contactToUpdate.setName(newValue);
                  break;
              case 2:
                  contactToUpdate.setPhone(newValue);
                  break;
              case 3:
                  contactToUpdate.setEmail(newValue);
                  break;
              default:
                  System.out.println("Invalid choice");
                  return;
          }
      } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter a valid number.");
          scanner.nextLine(); // Clear invalid input
          return;
      }

      saveContactsToFile(contacts, csvFile);
  }

  // saves the contacts ArrayList to the csvFile
  public static void saveContactsToFile(ArrayList<Contact> contacts, String csvFile) {
    try {
      List<String> lines = new ArrayList<String>();
      // loops through the contacts, selects the contact info to save and adds it to
      // the lines ArrayList
      for (Contact contact : contacts) {
        lines.add(contact.getName() + "," + contact.getPhone() + "," + contact.getEmail());
      }
      // writes the lines ArrayList to the csvFile
      Files.write(new File(csvFile).toPath(), lines, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException ex) {
      System.out.println("Error saving to file: " + ex.getMessage());
      throw new RuntimeException("Failed to save contacts to file", ex);
    }
  }

  // reads the csvFile and returns the contacts ArrayList
  public static ArrayList<Contact> readAddressBook(String csvFile) {
    // Create an empty ArrayList of CompanyEmployee objects
    ArrayList<Contact> contactList = new ArrayList<Contact>();

    // File object for accessing the CSV file
    File inputDataFile = new File(csvFile);
    List<String> lines = new ArrayList<String>();
    // trys to reach all the lines in the csv file and adds them to the lines
    // ArrayList
    try {
      lines = Files.readAllLines(inputDataFile.toPath());
      // loops through the lines ArrayList and adds each line to the contactList
      // ArrayList
      for (String line : lines) {
        // splits the line by the commas and adds the parts to the contactList ArrayList
        String[] contactData = line.split(",");
        String name = contactData[0];
        String phone = contactData[1];
        String email = contactData[2];
        // creates a new Contact object for each lined in the csvFile
        Contact contact = new Contact(name, phone, email);
        // adds the contact to the contactList
        contactList.add(contact);
      }
    } catch (FileNotFoundException ex) {
      System.out.println("File not found: " + ex.getMessage());
    } catch (IOException ex) {
      System.out.println("I/O error: " + ex.getMessage());
    } catch (NumberFormatException ex) {
      System.out.println("Number Format Error: " + ex.getMessage());
    }
    return contactList;
  }

  //method to initialize the address book
  private static boolean initializeAddressBook(String csvFile) {
  File file = new File(csvFile);
  File directory = new File(file.getParent());

  if (!directory.exists()) {
    if (!directory.mkdirs()) {
      System.out.println("Error creating directory structure.");
      return false;
    }
  }

  if (!file.exists()) {
    try {
      System.out.println("Creating new address book: " + file.getName());
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        System.err.format("IOException: %s%n", e);
      }
      if (file.createNewFile()) {
        Files.write(file.toPath(), new ArrayList<String>(), StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("Address book created successfully!");
        return true;
      } else {
        System.out.println("Error creating address book file.");
        return false;
      }
    } catch (IOException e) {
      System.out.println("Error creating file: " + e.getMessage());
      return false;
    }
  }

  // Clean up empty lines if file already exists
  try {
    List<String> lines = Files.readAllLines(file.toPath());
    List<String> cleanedLines = new ArrayList<>();
    for (String line : lines) {
      if (!line.trim().isEmpty()) {
        cleanedLines.add(line);
      }
    }
    Files.write(file.toPath(), cleanedLines, StandardOpenOption.TRUNCATE_EXISTING);
  } catch (IOException e) {
    System.out.println("Error cleaning up file: " + e.getMessage());
    return false;
  }

  return true;
}


}
