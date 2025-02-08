package com.aehanoidz123.librarymanagement.Entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Student {
    private int id;
    private String studentId;
    private String name;
    private String phone;
    private String email;
}
