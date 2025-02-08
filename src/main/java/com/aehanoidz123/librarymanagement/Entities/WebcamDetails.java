package com.aehanoidz123.librarymanagement.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class WebcamDetails {
    private String webcamName;
    private int webcamIdx;

    @Override
    public String toString() {
        return webcamName;
    }
}
