package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // використання методу підключення до бази даних
        try (Connection connection = DataBaseConnection.getConnection()) {
            System.out.println("Підключення до бази даних успішне!");

            // створення об'єкта Homework
            Homework homework = new Homework();
            homework.setId(1);
            homework.setName("Практичне завдання");
            homework.setDescription("Розв'язати практичне завдання з програмування");

            // створення об'єкта Lesson
            Lesson lesson = new Lesson();
            lesson.setId(1);
            lesson.setName("Урок 1");
            lesson.setUpdatedAt("2024-02-15");
            lesson.setHomework(homework);

            // використання класу LessonDao для додавання уроку
            LessonDao lessonDao = new LessonDao();
            lessonDao.addLesson(lesson);

            // використання класу LessonDao для отримання всіх уроків
            List<Lesson> lessons = lessonDao.getAllLessons();
            for (Lesson l : lessons) {
                System.out.println("ID: " + l.getId() + ", Name: " + l.getName() + ", UpdatedAt: " + l.getUpdatedAt() + ", Homework: " + l.getHomework().getName());
            }

            // використання класу LessonDao для видалення уроку
            lessonDao.deleteLesson(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
