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
        //  patient.setIdGlobal(computeNextId());
        patientRepo.save(patient);
    }

    public List<TSPatientDTO> getListPatients() {
        List<TSPatient> patients = patientRepo.findAll();
        return patients.stream()
                .map(patientMapper::toDto)
                .collect(Collectors.toList());
    }
 /*   public boolean createPatient(I record, Map<String, TSPatient> idCenterPatientMap, TSMedicalSpecialty specialty, Long centerId, Boolean fileVerified, Boolean createPatientIfNeeded, PatientIdentifierMethod idMethod, EntityManager em){
        ZonedDateTime birthDate=null;
        TSCivilStatus civilStatus=null;
        TSPatient patient = null;
        String id=null;
        List<TSMedicalSpecialty> sp=null;


        try{
            //TODO Check if the patient already exists By Combining some Attributes ( birthdate , name , cin ..) Create our Own Search By Id Logic
            id = PatientIdentifier.getInstance().getID(idMethod, record);
            id = TSPatientService.checkPatient()
            //IF Patient DON T EXIST
            if(!idCenterPatientMap.containsKey(id) ){
                patient = new TSPatient();
                sp = new ArrayList<TSMedicalSpecialty>(); sp.add(specialty);
                patient.setSpecialties(sp);
                //TODO create Identifier using
                PatientIdentifier.getInstance().createIdentifier(idMethod, patient, record, centerId);
                em.persist(patient);

                civilStatus = new TSCivilStatus();
                civilStatus.setPatient(patient);
                civilStatus.setFirstName(record.getPatientFirstName());
                civilStatus.setLastName(record.getPatientLastName());
                civilStatus.setBirthDate(record.getPatientBirthDate());
                civilStatus.setSex(record.getPatientSex());
                if(record.getPatientBirthDate() != null){
                    birthDate = record.getPatientBirthDate().toInstant().atZone(ZoneId.systemDefault());
                    civilStatus.setBirthDay((birthDate.getDayOfMonth()));
                    civilStatus.setBirthMonth(birthDate.getMonthValue());
                    civilStatus.setBirthYear(birthDate.getYear());
                }

                em.persist(civilStatus);
                patient.setCivilStatus(civilStatus);
                idCenterPatientMap.put(id, patient);
            }

            record.setPatient(idCenterPatientMap.get(id));

            return true;
        }catch(Exception e){
            record.addReport(AbstractDataImportRow.class, "PATIENT.CREATION.ERROR", e.getMessage());
            return false;
        }
    }*/


}
