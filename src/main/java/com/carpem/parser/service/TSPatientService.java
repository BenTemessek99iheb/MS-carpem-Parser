package com.carpem.parser.service;

import com.carpem.parser.mapper.TSPatientMapper;
import com.carpem.parser.model.TSPatient;
import com.carpem.parser.model.TSPatientDTO;
import com.carpem.parser.repository.ITSPatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TSPatientService {
    private final TSPatientMapper patientMapper;
    private final ITSPatientRepo patientRepo;

    public Long computeNextId() {
        Long maxId = patientRepo.findMaxIdGlobal();
        if (maxId == null) {
            return 1L;
        } else {
            return ((Number) maxId).longValue() + 1;
        }
    }

    public Boolean checkPatientExists(Long id) {
        return patientRepo.existsById(id);
    }

    public void savePatient(TSPatientDTO dto) {
        TSPatient patient = patientMapper.toModel(dto);
        patient.setIdGlobal(computeNextId());
        patientRepo.save(patient);
    }

    public List<TSPatientDTO> getListPatients() {
        List<TSPatient> patients = patientRepo.findAll();
        return patients.stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }

}
