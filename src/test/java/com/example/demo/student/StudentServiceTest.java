package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock private StudentRepository studentRepository;

    private StudentService underTest;
//    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest =new StudentService(studentRepository);
    }

//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }

    @Test
    void canGetAllStudents() {
        // when
        underTest.getAllStudents();
        // then
        verify(studentRepository).findAll();

    }

    @Test
    void canAddStudent() {
        // Given
        String email = "Mamo@gamil.com";
        Student student = new Student(
                "Mamo",
                email,
                Gender.MALE
        );

        // When
        underTest.addStudent(student);

        // Then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void whenAddStudentThrowErrorIfEmailExist() {
        // Given
        String email = "Mamo@gamil.com";
        Student student = new Student(
                "Mamo",
                email,
                Gender.MALE
        );

        given(studentRepository.selectExistsEmail(anyString()))
                .willReturn(true);

        // When
        // Then
        assertThatThrownBy(() -> underTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + student.getEmail() + " taken");
        verify(studentRepository, never()).save(any());
    }

    @Test
    void canDeleteWhenTheStudentExist() {
        // Given
        given(studentRepository.existsById(anyLong()))
                .willReturn(true);
        // When
        underTest.deleteStudent(1L);
        // Then
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void willThrowErrorWhenDeletingStudentIfIdDoseNotExist() {
        // Given
        given(studentRepository.existsById(anyLong()))
                .willReturn(false);
        // When
        // Then
        assertThatThrownBy(() -> underTest.deleteStudent(1L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id 1 does not exists");
        verify(studentRepository, never()).deleteById(any());
    }
}
