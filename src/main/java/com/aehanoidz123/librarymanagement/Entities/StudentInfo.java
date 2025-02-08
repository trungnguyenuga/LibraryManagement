package com.aehanoidz123.librarymanagement.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class StudentInfo {
    private String studentId;
    private String studentEmail;
    private String studentName;
    private String studentPhone;
}
