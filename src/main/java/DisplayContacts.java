package main.java;

import java.util.ArrayList;

public class DisplayContacts {
    DisplayContacts() {

    }

    // Displays the contacts ArrayList and cleans up the output.
  public static void displayContacts(ArrayList<Contact> contacts) {
    if (contacts.isEmpty()) {
        System.out.println("\nAddress Book Empty! You Need to add some contacts!");
    } else {
    System.out.println("\nCurrent Contacts:");
    System.out.println("ID | Name | Phone | Email");
    System.out.println("------------------------");
    // loop through the contacts ArrayList and print each contact's details
    for (int i = 0; i < contacts.size(); i++) {
      System.out.println(i + " | " + contacts.get(i).toString());
    }
}
  }
}
