package com.carpem.parser.controller;

import com.carpem.parser.model.TSPatientDTO;
import com.carpem.parser.service.TSPatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "TsPatient", description = "Patient API")
@RestController
@RequestMapping("/api/v1/TsPatient")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TSPatientController {

    private final TSPatientService patientService;

    @Operation(description = "Retrieve all patients")
    @GetMapping
    public ResponseEntity<List<TSPatientDTO>> getPatients() {
        return ResponseEntity.ok(patientService.getListPatients());
    }

}
