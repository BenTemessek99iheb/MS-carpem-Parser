package com.carpem.parser.repository;

import com.carpem.parser.model.TSPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITSPatientRepo extends JpaRepository<TSPatient, Long> {
    @Query("SELECT MAX(p.idGlobal) FROM TSPatient p")
    Long findMaxIdGlobal();

    Optional<TSPatient> findByIdGlobal(Long idGlobal);

    boolean existsByIdGlobal(Long idGlobal);


}
