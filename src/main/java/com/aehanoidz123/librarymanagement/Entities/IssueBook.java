package com.aehanoidz123.librarymanagement.Entities;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class IssueBook {
    private int id;
    private String isbn;
    private String title;
    private String studentId;
    private String studentName;
    private LocalDate dateBorrowed;
    private LocalDate dateReturned;
    private double lateFee;
}
