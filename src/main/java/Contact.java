package main.java;


import java.time.LocalDate;
public class Contact {
  public String name;
  public String phone;
  public String email;
  public LocalDate dateAdded = LocalDate.now();

  public Contact(String name, String phone, String email) {
    this.name = name;
    this.phone = phone;
    this.email = email;

  }

  public String getName() {
    return this.name;
  }

  public String getPhone() {
    return this.phone;
  }

  public String getEmail() {
    return this.email;
  }

  public LocalDate getDateAdded() {
    return this.dateAdded;
  }

  public void setName(String name) {
    if (name.length() > 0) {
      this.name = name;
    }
    System.out.println("Please Enter a valid name");
  }

  public void setPhone(String phone) {
   this.phone = phone;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  

  public String toString() {
    return "Name: " + this.name + "\nPhone: " + this.phone + "\nEmail: " + this.email + "\nDate Added to Address Book: "
        + this.dateAdded;
  }

}
