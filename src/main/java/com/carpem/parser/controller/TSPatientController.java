package com.carpem.parser.controller;

import com.carpem.parser.model.TSPatientDTO;
import com.carpem.parser.service.PatientImportService;
import com.carpem.parser.service.TSPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "TsPatient", description = "Patient API")
@RestController
@RequestMapping("/api/v1/hl7/patient")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j

public class TSPatientController {

    private final TSPatientService patientService;
    private final PatientImportService patientImportService;


    @Operation(description = "Retrieve all patients")
    @GetMapping
    public ResponseEntity<List<TSPatientDTO>> getPatients() {
        return ResponseEntity.ok(patientService.getListPatients());
    }

    @Operation(summary = "Upload an HL7 file to process and import patient data")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadHL7File(
            @Parameter(description = "HL7 file to upload", required = true)
            @RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty.");
            }

            String hl7Raw = new String(file.getBytes(), StandardCharsets.UTF_8);
            log.info("Received HL7 file: {}", file.getOriginalFilename());

            patientImportService.processHl7Message(hl7Raw, file.getOriginalFilename());

            return ResponseEntity.ok("HL7 message processed successfully.");
        } catch (IOException e) {
            log.error("Failed to read uploaded file", e);
            return ResponseEntity.internalServerError().body("Failed to read file.");
        } catch (Exception e) {
            log.error("Error processing HL7 message", e);
            return ResponseEntity.internalServerError().body("Error processing HL7 message.");
        }
    }


}
