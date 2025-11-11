package org.SUVote.resource;

import org.SUVote.entity.Student;
import org.SUVote.repository.StudentRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/students")
public class StudentResource {

    @Inject
    StudentRepository studentRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getAllStudents() {
        return studentRepository.listAll();
    }

    @GET
    @Path("/{studentNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("studentNo") String studentNo) {
        Optional<Student> student = studentRepository.findByStudentNo(studentNo);
        if (student.isPresent()) {
            return Response.ok(student.get()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(Student student) {
        studentRepository.persist(student);
        return Response.status(Response.Status.CREATED).entity(student).build();
    }

    @PUT
    @Path("/{studentNo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("studentNo") String studentNo, Student updatedStudent) {
        Optional<Student> studentOpt = studentRepository.findByStudentNo(studentNo);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setFirstName(updatedStudent.getFirstName());
            student.setMiddleName(updatedStudent.getMiddleName());
            student.setSurname(updatedStudent.getSurname());
            student.setInitials(updatedStudent.getInitials());
            student.setGender(updatedStudent.getGender());
            student.setNationality(updatedStudent.getNationality());
            student.setPassword(updatedStudent.getPassword());
            studentRepository.persist(student);
            return Response.ok(student).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{studentNo}")
    public Response deleteStudent(@PathParam("studentNo") String studentNo) {
        Optional<Student> student = studentRepository.findByStudentNo(studentNo);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}