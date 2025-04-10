package com.carpem.parser.service;

import com.carpem.parser.mapper.TSPatientMapper;
import com.carpem.parser.model.HL7PatientDTO;
import com.carpem.parser.model.Hl7UploadTrace;
import com.carpem.parser.model.TSPatient;
import com.carpem.parser.repository.Hl7UploadTraceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling

/**
 * Service for processing HL7 messages and importing patient data.
 */
public class PatientImportService {

    private final HL7ParserService hl7ParserService;
    private final PatientJsonBuilder jsonBuilder;
    private final TSPatientService patientService;
    private final TSPatientMapper patientMapper;
    private final Hl7UploadTraceRepository traceRepository;
    private final Path inputFolder = Paths.get("E:/EBCI_Project/EBCI3/CARPEM_DATA/DATA_dpi_orbis/import_files");

    //TODO CHANGE fILE PATHS

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
    public void processHl7Message(String hl7Raw, String originalFilename) throws IOException {

        log.info("Starting HL7 message processing...");

        try {
            if (traceRepository.existsByFileName(originalFilename)) {
                log.warn("File {} already processed, skipping.", originalFilename);
                return;
            }

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
            Path folderPath = Paths.get("E:/EBCI_Project/EBCI3/CARPEM_DATA/DATA_dpi_orbis/create_patient_messages");
            Files.createDirectories(folderPath);
            // Build filename using original filename with ".ok" extension
            String baseName = originalFilename != null ? originalFilename.replaceAll("\\.[^.]+$", "") : "message";
            Path outputPath = folderPath.resolve(baseName + ".ok");

            //  Path outputPath = Paths.get("E:/EBCI_Project/EBCI3/CARPEM_DATA/DATA_dpi_orbis/create_patient_messages/message.ok");
            Files.writeString(outputPath, json, StandardOpenOption.CREATE);
            //  Sabe trace
            Hl7UploadTrace trace = Hl7UploadTrace.builder()
                    .fileName(originalFilename)
                    .ipp(dto.getIpp())
                    .uploadDate(LocalDateTime.now())
                    .status("SUCCESS")
                    .sourceSystem("HL7_ORBIS") //TODO customize
                    .message("Processed successfully")
                    .build();

            traceRepository.save(trace);

            log.info("Processed HL7 message and wrote output to {}", outputPath);


        } catch (Exception e) {
            log.error("Error processing HL7 message", e);

            // SavInf failed trace
            Hl7UploadTrace trace = Hl7UploadTrace.builder()
                    .fileName(originalFilename)
                    .uploadDate(LocalDateTime.now())
                    .status("FAILED")
                    .message(e.getMessage())
                    .build();

            traceRepository.save(trace);
            throw e;
        }
    }

    @Scheduled(fixedDelay = 20000) // Every 60 seconds
    public void scanAndProcessHL7Files() {
        log.info("Scanning folder for new HL7 files...");

        try {
            if (!Files.exists(inputFolder)) {
                log.warn("Input directory does not exist: {}", inputFolder);
                return;
            }

            Files.list(inputFolder)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".hl7")) // Optional filter
                    .forEach(file -> {
                        String fileName = file.getFileName().toString();
                        try {
                            if (traceRepository.existsByFileName(fileName)) {
                                log.info("File already processed: {}", fileName);
                                return;
                            }

                            String hl7Raw = Files.readString(file);
                            processHl7Message(hl7Raw, fileName);

                            // Optionally move or delete the processed file
                            Files.delete(file); // or move to archive folder
                            // Path archivePath = inputFolder.resolve("archive").resolve(file.getFileName());
                            //Files.createDirectories(archivePath.getParent());
                            //Files.move(file, archivePath, StandardCopyOption.REPLACE_EXISTING);


                        } catch (Exception e) {
                            log.error("Error processing file {}: {}", fileName, e.getMessage());
                        }
                    });

        } catch (IOException e) {
            log.error("Error scanning HL7 files folder", e);
        }
    }

}
