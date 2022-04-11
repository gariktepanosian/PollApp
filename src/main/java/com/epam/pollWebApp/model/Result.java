package com.epam.pollWebApp.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Result {
    private long id;
    private String explanation;
    private long minScore;
    private long maxScore;
}
