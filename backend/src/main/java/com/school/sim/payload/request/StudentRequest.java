package com.school.sim.payload.request;

import lombok.Data;

@Data
public class StudentRequest {
    private String username;
    private String email;
    private String password;
    private String grade;
    private String className;
    private Integer enrollmentYear;
}
