package model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.security.crypto.bcrypt.BCrypt;

import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@JsonAutoDetect
public class Person {

  private static final int BCRYPT_ROUNDS = 12;

  public Person() {

  }

  public Person(String firstName, String lastName, String email, String phoneNumber, Role role,
      String password) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.role = role;
    setPassword(password);
    isSubscribed = false;
  }


  public enum Role {
    Admin, Staff, Student
  }

  private Integer id;
  private String firstName;
  private String lastName;
  @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\\b")
  private String email;
  private String phoneNumber;
  private Role role;
  private String passwordHash;
  private boolean isSubscribed;
  private List<PrintRequest> requests;

  @Id
  @JsonProperty
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @JsonProperty
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @JsonProperty
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @JsonProperty
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @JsonProperty
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @JsonProperty
  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  private String getPasswordHash() {
    return passwordHash;
  }

  private void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public boolean checkPassword(String pass) {
    return BCrypt.checkpw(pass, passwordHash);
  }

  public void setPassword(String newPass) {
    setPasswordHash(BCrypt.hashpw(newPass, BCrypt.gensalt(BCRYPT_ROUNDS)));
  }

  @JsonIgnore
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.LAZY)
  public List<PrintRequest> getRequests() {
    return requests;
  }

  public void setRequests(List<PrintRequest> reqs) {
    this.requests = reqs;
  }


  public boolean isSubscribed() {
    return isSubscribed;
  }

  public void setSubscribed(boolean isSubscribed) {
    this.isSubscribed = isSubscribed;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = prime * result + ((role == null) ? 0 : role.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (obj.getClass() != Person.class) {
      return false;
    }
    Person other = (Person) obj;
    if (getEmail() == null) {
      if (other.getEmail() != null) {
        return false;
      }
    } else if (!getEmail().equals(other.getEmail())) {
      return false;
    }
    return true;
  }

}
