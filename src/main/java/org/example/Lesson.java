package org.example;

import lombok.Data;
import java.time.LocalDateTime;

@Data

public class Lesson {
    private int id;
    private String name;
    private String updatedAt;
    private Homework homework;

}
//Створити таблицю Lesson. Ця таблиця складається з атрибутів: id, name, updatedAt,
// homework_id (пов'язано з таблицею Homework)