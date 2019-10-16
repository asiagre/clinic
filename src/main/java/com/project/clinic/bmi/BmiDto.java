package com.project.clinic.bmi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BmiDto {
    private Weight weight;
    private Height height;
    private String sex;
    private String age;
}
