package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {
    @Mock private StudentService studentService;
    @Mock private StudentRepository studentRepository;

    private StudentController underTest;

    @BeforeEach
    void setUp() {
        underTest = new StudentController(studentService);
    }
    @Test
    void getAllStudents() {
        // Given
        List<Student> students = List.of(
                new Student("Mamo", "mamo@gmail.com", Gender.MALE),
                new Student("Almaz", "almaz@gmail.com", Gender.FEMALE)
        );
        given(studentService.getAllStudents())
                .willReturn(students);
        // When
        underTest.getAllStudents();
        // Then
        verify(studentService).getAllStudents();
    }

    @Test
    void addStudent() {
    }

    @Test
    void deleteStudent() {
    }
}
