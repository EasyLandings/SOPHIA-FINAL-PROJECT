package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ReadAddressBook {
    ReadAddressBook(){

    }
      // reads the csvFile and returns the contacts ArrayList
  public static ArrayList<Contact> readAddressBook(String csvFile) {
  ArrayList<Contact> contactList = new ArrayList<Contact>();

  File inputDataFile = new File(csvFile);
  List<String> lines = new ArrayList<String>();

  try {
    lines = Files.readAllLines(inputDataFile.toPath());
    for (String line : lines) {
      if (line.trim().isEmpty()) {
        continue; // Skip empty lines
      }

      String[] contactData = line.split(",");
      if (contactData.length >= 3) {
        String name = contactData[0].trim();
        String phone = contactData[1].trim();
        String email = contactData[2].trim();

        if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
          Contact contact = new Contact(name, phone, email);
          contactList.add(contact);
        }
      }
    }
  } catch (FileNotFoundException ex) {
    System.out.println("File not found: " + ex.getMessage());
  } catch (IOException ex) {
    System.out.println("I/O error: " + ex.getMessage());
  }
  return contactList;
}
}
