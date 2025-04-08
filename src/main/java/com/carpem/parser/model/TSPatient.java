package com.carpem.parser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "TS_PATIENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TSPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GLOBAL")
    private Long idGlobal;

    @Column(name = "IPP")
    private String ipp;

    @Column(name = "SEXE")
    private String sexe;

    @Column(name = "DATE_NAISSANCE")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column(name = "NOM_NAISSANCE")
    private String nomNaissance;

    @Column(name = "NOM_ACTUEL")
    private String nomActuel;

    @Column(name = "PRENOM")
    private String prenom;

    @Column(name = "ID_ASSURE")
    private String idAssure;

    @Column(name = "ID_ASSURE_CLE")
    private String idAssureCle;

    @Column(name = "ID_PERS_SOCIETE")
    private String idPersSociete;

    @Column(name = "RAISON_SOCIALE")
    private String raisonSociale;

    @Column(name = "NATIONALITE")
    private String nationalite;

    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "VILLE")
    private String ville;

    @Column(name = "CODE_POSTAL")
    private String codePostal;

    @Column(name = "PAYS")
    private String pays;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEPHONE")
    private String telephone;

    @Column(name = "TELEPHONE_FIXE")
    private String telephoneFixe;

    @Column(name = "TELEPHONE_MOBILE")
    private String telephoneMobile;

    @Column(name = "TELEPHONE_TRAVAIL")
    private String telephoneTravail;

    @Column(name = "FAX")
    private String fax;

    @Column(name = "LIBELLE_SPECIALITE")
    private String libelleSpecialite;

    @Column(name = "ID_SPECIALITE")
    private String idSpecialite;

    @Column(name = "LIBELLE_HOPITAL")
    private String libelleHopital;

    @Column(name = "ID_HOPITAL")
    private String idHopital;

    @Column(name = "LIBELLE_SERVICE")
    private String libelleService;

    @Column(name = "ID_SERVICE")
    private String idService;

    @Column(name = "PROFESSION")
    private String profession;

    @Column(name = "MEDECIN_TRAITANT")
    private String medecinTraitant;

    @Column(name = "LIEU_NAISSANCE")
    private String lieuNaissance;

    @Column(name = "CODE_INSEE_NAISSANCE")
    private String codeInseeNaissance;

    @Column(name = "PAYS_NAISSANCE")
    private String paysNaissance;

    @Column(name = "CIVILITE")
    private String civilite;

    @Column(name = "COMMENTAIRE")
    private String commentaire;

    @Column(name = "NUM_CAF")
    private String numCaf;

    @Column(name = "ORGANISME_CAF")
    private String organismeCaf;

    @Column(name = "ID_EXT")
    private String idExt;

    @Column(name = "CLE_EXT")
    private String cleExt;

    @Column(name = "DATE_DECES")
    @Temporal(TemporalType.DATE)
    private Date dateDeces;

    @Column(name = "NUMERO_DOSSIER")
    private String numeroDossier;

    @Column(name = "CODE")
    private String code;
}
