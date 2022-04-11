package com.epam.pollWebApp.model;


import lombok.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Poll {
    private long id;
    private String name;
    private String description;
    private List<Question> questions;
    private List<Result> results;
}
