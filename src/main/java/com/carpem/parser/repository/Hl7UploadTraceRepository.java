package com.carpem.parser.repository;

import com.carpem.parser.model.Hl7UploadTrace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Hl7UploadTraceRepository extends JpaRepository<Hl7UploadTrace, Long> {
    boolean existsByFileName(String fileName);
}
