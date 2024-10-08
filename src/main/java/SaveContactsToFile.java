package main.java;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class SaveContactsToFile {
    SaveContactsToFile(){}

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
}
