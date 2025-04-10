package com.carpem.parser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "hl7_upload_trace")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Hl7UploadTrace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String ipp; // from HL7

    private LocalDateTime uploadDate;

    private String status; //"SUCCESS", "FAILED"

    private String sourceSystem; // optional

    private String message; // optional error or processing notes

}
