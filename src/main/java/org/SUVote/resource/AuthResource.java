package org.SUVote.resource;

import org.SUVote.dto.AuthResponse;
import org.SUVote.entity.Student;
import org.SUVote.service.AuthService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Optional;

@Path("/auth")
public class AuthResource {
    
    @Inject
    AuthService authService;
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        try {
            Optional<Student> studentOpt = authService.authenticate(
                loginRequest.getUsername(), 
                loginRequest.getPassword()
            );
            
            if (studentOpt.isPresent()) {
                Student student = studentOpt.get();
                AuthResponse response = new AuthResponse(
                    true, 
                    "Login successful",
                    student.getStudentNo(),
                    student.getFullName()
                );
                return Response.ok(response).build();
            } else {
                AuthResponse response = new AuthResponse(false, "Invalid student number or password");
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }
        } catch (Exception e) {
            AuthResponse response = new AuthResponse(false, "Login failed: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }
    
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest registerRequest) {
        try {
            Student student = authService.registerStudent(
                registerRequest.getStudentNo(),
                registerRequest.getPassword(),
                registerRequest.getFirstName(),
                registerRequest.getSurname()
            );
            
            AuthResponse response = new AuthResponse(
                true, 
                "Registration successful",
                student.getStudentNo(),
                student.getFullName()
            );
            return Response.status(Response.Status.CREATED).entity(response).build();
            
        } catch (IllegalArgumentException e) {
            AuthResponse response = new AuthResponse(false, e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
        } catch (Exception e) {
            AuthResponse response = new AuthResponse(false, "Registration failed: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }
    
    // Request DTOs
    public static class LoginRequest {
        private String username;
        private String password;
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class RegisterRequest {
        private String studentNo;
        private String password;
        private String firstName;
        private String surname;
        
        public String getStudentNo() { return studentNo; }
        public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        
        public String getSurname() { return surname; }
        public void setSurname(String surname) { this.surname = surname; }
    }
}