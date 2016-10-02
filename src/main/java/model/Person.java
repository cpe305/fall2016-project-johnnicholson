package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {
  
  public enum Role {
    Admin,
    Staff,
    Student
  }
  
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String phoneNumber;
  private Role role;
  
  @Id
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  @Column
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  
  public Role getRole() {
    return role;
  }
  public void setRole(Role role) {
    this.role = role;
  }
  
  
}
