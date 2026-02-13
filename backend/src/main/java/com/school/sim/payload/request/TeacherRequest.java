package com.school.sim.payload.request;

import lombok.Data;

@Data
public class TeacherRequest {
    private String username;
    private String email;
    private String password;
    private String bio;
    private String subjects;
}
