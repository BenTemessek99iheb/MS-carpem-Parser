package com.carpem.parser.model;

import lombok.Data;

import java.util.Date;

@Data
public class TSPatientDTO {
    private Long idGlobal;
    private String ipp;
    private String sexe;
    private Date dateNaissance;
    private String nomNaissance;
    private String nomActuel;
    private String prenom;
    private String idAssure;
    private String idAssureCle;
    private String idPersSociete;
    private String raisonSociale;
    private String nationalite;
    private String adresse;
    private String ville;
    private String codePostal;
    private String pays;
    private String email;
    private String telephone;
    private String telephoneFixe;
    private String telephoneMobile;
    private String telephoneTravail;
    private String fax;
    private String libelleSpecialite;
    private String idSpecialite;
    private String libelleHopital;
    private String idHopital;
    private String libelleService;
    private String idService;
    private String profession;
    private String medecinTraitant;
    private String lieuNaissance;
    private String codeInseeNaissance;
    private String paysNaissance;
    private String civilite;
    private String commentaire;
    private String numCaf;
    private String organismeCaf;
    private String idExt;
    private String cleExt;
    private Date dateDeces;
    private String numeroDossier;
    private String code;

}
