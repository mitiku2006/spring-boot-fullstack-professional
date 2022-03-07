package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository underTest;

    @Test
    void whenStudentEmailExists() {
        // Given
        String email = "Mamo@gamil.com";
        Student student = new Student(
                "Mamo",
                email,
                Gender.MALE
        );
        underTest.save(student);

        // When
        boolean result = underTest.selectExistsEmail(email);

        // Expected
        assertThat(result).isTrue();
    }

    @Test
    void whenStudentEmailDoesNotExists() {
        // Given
        String email = "Mamo@gamil.com";

        // When
        boolean result = underTest.selectExistsEmail(email);

        // Expected
        assertThat(result).isFalse();
    }
}
