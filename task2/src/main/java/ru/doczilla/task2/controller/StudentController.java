package ru.doczilla.task2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import ru.doczilla.task2.model.Student;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public void addStudent(@RequestBody Student student) {
        String sql = "INSERT INTO student (first_name, last_name, middle_name, birth_date, group_name) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, student.getFirstName(), student.getLastName(), student.getMiddleName(), student.getBirthDate(), student.getGroupName());
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable("id") Long id) {
        String sql = "DELETE FROM student WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @GetMapping
    public List<Student> listStudents() {
        return jdbcTemplate.query("SELECT * FROM student", (rs, rowNum) ->
                new Student(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("middle_name"),
                        rs.getDate("birth_date").toLocalDate(),
                        rs.getString("group_name")
                ));
    }
}
