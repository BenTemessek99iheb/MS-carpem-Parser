package com.carpem.parser.service;

import com.carpem.parser.model.HL7PatientDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class PatientJsonBuilder {
    public String buildJson(HL7PatientDTO dto, Long idBase, Long idPoumon) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        ArrayNode array = mapper.createArrayNode();

        ObjectNode patientNode = mapper.createObjectNode();
        ObjectNode references = mapper.createObjectNode();
        ObjectNode card = mapper.createObjectNode();

        references.put("Id Patient Base", idBase);
        references.put("< Id patient name >", idPoumon);
        references.put("<ClinicalCenter>", dto.getIpp());
        references.put("Registre Référence", "AA");

        card.put("name", dto.getLastName());
        card.put("surname", dto.getFirstName());
        card.put("gender", dto.getGender());
        card.put("birth_date", dto.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        patientNode.set("References", references);
        patientNode.set("Patient_Card", card);

        array.add(patientNode);
        root.set("Clinic Patients CARPEM", array);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
    }

}
