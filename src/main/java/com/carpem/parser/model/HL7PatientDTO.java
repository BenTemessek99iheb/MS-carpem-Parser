package com.carpem.parser.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HL7PatientDTO {
    private String ipp;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate birthDate;
    private String clinicalCenter; // Ex: "8016888843"

}
