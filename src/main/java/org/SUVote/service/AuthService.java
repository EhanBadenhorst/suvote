package org.SUVote.service;

import org.SUVote.entity.Student;
import org.SUVote.repository.StudentRepository;
import org.SUVote.util.PasswordUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class AuthService {
    
    @Inject
    StudentRepository studentRepository;
    
    /**
     * Authenticate a student with student number and password
     */
    public Optional<Student> authenticate(String studentNo, String password) {
        try {
            if (studentNo == null || password == null || studentNo.trim().isEmpty()) {
                return Optional.empty();
            }
            
            // Find student by student number
            Optional<Student> studentOpt = studentRepository.findByStudentNo(studentNo.trim());
            
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                
                // Check if password is hashed or plain text
                String storedPassword = student.getPassword();
                
                // If password starts with $2a$ or $2b$ or $2y$, it's bcrypt hashed
                if (storedPassword != null && storedPassword.startsWith("$2")) {
                    // Use bcrypt verification
                    if (PasswordUtil.checkPassword(password, storedPassword)) {
                        return Optional.of(student);
                    }
                } else {
                    // TEMPORARY: Direct password comparison (for legacy/test data)
                    // Remove this in production!
                    if (password.equals(storedPassword)) {
                        return Optional.of(student);
                    }
                }
            }
            
            return Optional.empty();
            
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    /**
     * Register a new student
     */
    @Transactional
    public Student registerStudent(String studentNo, String password, String firstName, String surname) {
        // Validate input
        if (studentNo == null || studentNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Student number is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (surname == null || surname.trim().isEmpty()) {
            throw new IllegalArgumentException("Surname is required");
        }
        
        // Check if student already exists
        if (studentRepository.existsByStudentNo(studentNo.trim())) {
            throw new IllegalArgumentException("Student number already exists");
        }
        
        // Validate password strength (optional - add your own rules)
        if (password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        
        // Create new student
        Student student = new Student();
        student.setStudentNo(studentNo.trim());
        student.setFirstName(firstName.trim());
        student.setSurname(surname.trim());
        
        // Hash the password before storing
        String hashedPassword = PasswordUtil.hashPassword(password);
        student.setPassword(hashedPassword);
        
        // Persist to database
        studentRepository.persist(student);
        
        return student;
    }
    
    /**
     * Change password for a student
     */
    @Transactional
    public boolean changePassword(String studentNo, String oldPassword, String newPassword) {
        // Authenticate with old password first
        Optional<Student> studentOpt = authenticate(studentNo, oldPassword);
        
        if (studentOpt.isEmpty()) {
            return false; // Old password is incorrect
        }
        
        Student student = studentOpt.get();
        
        // Validate new password
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("New password must be at least 6 characters long");
        }
        
        // Hash and update password
        String hashedPassword = PasswordUtil.hashPassword(newPassword);
        student.setPassword(hashedPassword);
        
        // Persist changes
        studentRepository.persist(student);
        
        return true;
    }
    
    /**
     * Check if a student number exists
     */
    public boolean studentExists(String studentNo) {
        if (studentNo == null || studentNo.trim().isEmpty()) {
            return false;
        }
        return studentRepository.existsByStudentNo(studentNo.trim());
    }
}