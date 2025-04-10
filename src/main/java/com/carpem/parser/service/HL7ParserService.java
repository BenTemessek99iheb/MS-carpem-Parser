package com.carpem.parser.service;

import com.carpem.parser.model.HL7PatientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class HL7ParserService {
    public HL7PatientDTO parse(String hl7Raw) {
        HL7PatientDTO dto = new HL7PatientDTO();

        String[] lines = hl7Raw.split("\\r|\\n");
        for (String line : lines) {
            if (line.startsWith("PID|")) {
                String[] fields = line.split("\\|");

                // IPP: PID-3
                String[] pid3Fields = fields[3].split("\\^");
                dto.setIpp(pid3Fields[0]);
                dto.setClinicalCenter(pid3Fields.length > 2 ? pid3Fields[2] : "N/A");

                // Nom complet: PID-5
                String[] nameFields = fields[5].split("\\^");
                dto.setLastName(nameFields[0]);
                dto.setFirstName(nameFields.length > 1 ? nameFields[1] : "");

                // Date de naissance: PID-7
                dto.setBirthDate(LocalDate.parse(fields[7], DateTimeFormatter.ofPattern("yyyyMMdd")));

                // Sexe: PID-8
                String sex = fields[8];
                dto.setGender(sex.equalsIgnoreCase("M") ? "Male" : "Female");
            }
        }

        return dto;
    }


}


