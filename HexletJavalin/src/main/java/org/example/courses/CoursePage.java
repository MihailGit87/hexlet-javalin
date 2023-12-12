package org.example.courses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.dto.BasePage;

@AllArgsConstructor
@Getter

public class CoursePage {
    private Course course;
    private BasePage page;

    public CoursePage(Course course) {
        this.course = course;
    }
}