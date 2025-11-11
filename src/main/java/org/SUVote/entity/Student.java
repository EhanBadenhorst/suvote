package org.SUVote.entity;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students")
public class Student {
    
    @Id
    @Column(name = "student_no")
    private String studentNo;  // Changed to match database column name
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "middle_name")
    private String middleName;
    
    @Column(name = "surname")
    private String surname;
    
    @Column(name = "initials")
    private String initials;
    
    @Column(name = "gender")
    private String gender;
    
    @Column(name = "nationality")
    private String nationality;
    
    @Column(name = "id")
    private String id;
    
    @Column(name = "password")
    private String password;
    
    // Constructors
    public Student() {}
    
    public Student(String firstName, String middleName, String surname, 
                   String initials, String gender, String nationality, 
                   String id, String studentNo, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.surname = surname;
        this.initials = initials;
        this.gender = gender;
        this.nationality = nationality;
        this.id = id;
        this.studentNo = studentNo;
        this.password = password;
    }
    
    // Getters and Setters
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    
    public String getInitials() { return initials; }
    public void setInitials(String initials) { this.initials = initials; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getFullName() {
        return firstName + " " + (middleName != null ? middleName + " " : "") + surname;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentNo, student.studentNo);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(studentNo);
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "studentNo='" + studentNo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}