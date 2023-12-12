package org.example.courses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.dto.BasePage;

import java.util.List;


@AllArgsConstructor
@Getter
public class CoursesPage extends BasePage{
    private List<Course> courses;
    private String header;
    private String term;

    public CoursesPage(List<Course> courses, String header) {
        this.courses = courses;
        this.header = header;
    }

    public CoursesPage(List<Course> courses) {
        this.courses = courses;
    }
}