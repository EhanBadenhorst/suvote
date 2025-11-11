package org.SUVote.repository;

import org.SUVote.entity.Student;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {
    
    public Optional<Student> findByStudentNo(String studentNo) {
        return find("studentNo", studentNo).firstResultOptional();
    }
    
    public boolean existsByStudentNo(String studentNo) {
        return count("studentNo", studentNo) > 0;
    }
}