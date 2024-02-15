package org.example;
import org.example.DataBaseConnection;
import org.example.Homework;
import org.example.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonDao {
        private static final String INSERT_LESSON_SQL = "INSERT INTO Lesson (name, updatedAt, homework_id) VALUES (?, ?, ?)";
        private static final String DELETE_LESSON_SQL = "DELETE FROM Lesson WHERE id = ?";
        private static final String SELECT_ALL_LESSONS_SQL = "SELECT * FROM Lesson";
        private static final String SELECT_LESSON_BY_ID_SQL = "SELECT * FROM Lesson WHERE id = ?";

        public void addLesson(Lesson lesson) {

            try (Connection connection = DataBaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LESSON_SQL)) {
                preparedStatement.setString(1, lesson.getName());
                preparedStatement.setString(2, lesson.getUpdatedAt());
                preparedStatement.setInt(3, lesson.getHomework().getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void deleteLesson(int lessonId) {
            try (Connection connection = DataBaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LESSON_SQL)) {
                preparedStatement.setInt(1, lessonId);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = new ArrayList<>();
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_LESSONS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Lesson lesson = new Lesson();
                lesson.setId(resultSet.getInt("id"));
                lesson.setName(resultSet.getString("name"));
                lesson.setUpdatedAt(resultSet.getString("updatedAt"));

                Homework homework = getHomeworkById(resultSet.getInt("homework_id"));
                if (homework != null) {
                    lesson.setHomework(homework);
                    System.out.println("ID: " + lesson.getId() + ", Name: " + lesson.getName() + ", UpdatedAt: " + lesson.getUpdatedAt() + ", Homework: " + homework.getName());
                } else {
                    lesson.setHomework(null);
                    System.out.println("ID: " + lesson.getId() + ", Name: " + lesson.getName() + ", UpdatedAt: " + lesson.getUpdatedAt() + ", Homework: No homework assigned");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lessons;
    }

        public Lesson getLessonById(int lessonId) {
            Lesson lesson = null;
            try (Connection connection = DataBaseConnection.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LESSON_BY_ID_SQL)) {
                preparedStatement.setInt(1, lessonId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        lesson = new Lesson();
                        lesson.setId(resultSet.getInt("id"));
                        lesson.setName(resultSet.getString("name"));
                         lesson.setUpdatedAt(resultSet.getString("updatedAt"));

                        lesson.setHomework(getHomeworkById(resultSet.getInt("homework_id")));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return lesson;
        }

    private Homework getHomeworkById(int homeworkId) {
        Homework homework = null;
        String SELECT_HOMEWORK_BY_ID_SQL = "SELECT * FROM Homework WHERE id = ?";
        try (Connection connection = DataBaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_HOMEWORK_BY_ID_SQL)) {
            preparedStatement.setInt(1, homeworkId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    homework = new Homework();
                    homework.setId(resultSet.getInt("id"));
                    homework.setName(resultSet.getString("name"));
                    homework.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homework;
    }

}


