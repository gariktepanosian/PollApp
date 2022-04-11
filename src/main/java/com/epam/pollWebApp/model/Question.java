package com.epam.pollWebApp.model;


import java.util.List;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Question {
    private long id;
    private String text;
    private List<Answer> answers;
    private long pollId;
}


