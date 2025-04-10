package com.carpem.parser.mapper;

import com.carpem.parser.Utils.tools.GenericMapper;
import com.carpem.parser.model.TSPatient;
import com.carpem.parser.model.TSPatientDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface TSPatientMapper extends GenericMapper<TSPatient, TSPatientDTO> {
    TSPatientDTO toDto(TSPatient patient);

    TSPatient toModel(TSPatientDTO patientDto);

}
