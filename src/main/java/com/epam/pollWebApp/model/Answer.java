package com.epam.pollWebApp.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Answer {
    private long id;
    private String text;
    private long question_id;
    private long weight;
}
