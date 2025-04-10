package com.carpem.parser.service;

import com.carpem.parser.mapper.TSPatientMapper;
import com.carpem.parser.model.HL7PatientDTO;
import com.carpem.parser.model.TSPatient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
/**
 * Service for processing HL7 messages and importing patient data.
 */
public class PatientImportService {

    private final HL7ParserService hl7ParserService;
    private final PatientJsonBuilder jsonBuilder;
    private final TSPatientService patientService;
    private final TSPatientMapper patientMapper;

    /*  public void processHl7Message(String hl7Raw) throws IOException {
          HL7PatientDTO dto = hl7ParserService.parse(hl7Raw);

        //  boolean exists = patientService.checkPatientExistsByIPP(dto.getIpp());
          Long idBase = patientService.computeNextId();
          Long idPoumon = 1L; // ou ID spécifique à la spécialité Poumon

       //   if (!exists) {
              TSPatient patient = new TSPatient();
              patient.setIdGlobal(idBase);
              patient.setIpp(dto.getIpp());
              patient.setNomActuel(dto.getLastName());
              patient.setPrenom(dto.getFirstName());
              patient.setSexe(dto.getGender().substring(0, 1));
              patient.setDateNaissance(Date.from(dto.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

              patientService.savePatient(patientMapper.toDto(patient));
         // }

          String json = jsonBuilder.buildJson(dto, idBase, idPoumon);

          // Écrire dans un fichier .ok
          Path outputPath = Paths.get("E:/EBCI_Project/EBCI3/CARPEM_DATA/DATA_dpi_orbis/output/message.ok");
          Files.writeString(outputPath, json, StandardOpenOption.CREATE);
      }
  */
    public void processHl7Message(String hl7Raw) throws IOException {
        try {
            log.info("Starting HL7 message processing...");

            HL7PatientDTO dto = hl7ParserService.parse(hl7Raw);
            log.debug("Parsed HL7 DTO: {}", dto);

            Long idBase = patientService.computeNextId();
            Long idPoumon = 1L; // Or the correct specialty ID

            log.info("Computed next patient ID: {}", idBase);

            TSPatient patient = new TSPatient();
            //  patient.setIdGlobal(idBase);
            patient.setIpp(dto.getIpp());
            patient.setNomActuel(dto.getLastName());
            patient.setPrenom(dto.getFirstName());
            patient.setSexe(dto.getGender().substring(0, 1));
            patient.setDateNaissance(Date.from(dto.getBirthDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            log.debug("Saving new patient: {}", patient);
            patientService.savePatient(patientMapper.toDto(patient));

            String json = jsonBuilder.buildJson(dto, idBase, idPoumon);
            log.debug("Generated JSON: {}", json);
           //TODO check if the Folder Exists
            Path outputPath = Paths.get("E:/EBCI_Project/EBCI3/CARPEM_DATA/DATA_dpi_orbis/create_patient_messages/message.ok");
            Files.writeString(outputPath, json, StandardOpenOption.CREATE);
            log.info("Message written to output file: {}", outputPath);

        } catch (Exception e) {
            log.error("Error during HL7 message processing", e);
            throw e;
        }
    }

}
