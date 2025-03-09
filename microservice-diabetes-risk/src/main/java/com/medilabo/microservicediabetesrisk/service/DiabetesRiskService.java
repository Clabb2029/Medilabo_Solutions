package com.medilabo.microservicediabetesrisk.service;

import com.medilabo.microservicediabetesrisk.bean.NoteBean;
import com.medilabo.microservicediabetesrisk.bean.PatientBean;
import com.medilabo.microservicediabetesrisk.bean.ReportBean;
import com.medilabo.microservicediabetesrisk.proxy.MicroserviceNoteProxy;
import com.medilabo.microservicediabetesrisk.proxy.MicroservicePatientProxy;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiabetesRiskService {

    private final MicroserviceNoteProxy noteProxy;
    private final MicroservicePatientProxy patientProxy;

    private static List<String> triggerWords = List.of("Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Fumeuse", "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps");


    public DiabetesRiskService(MicroserviceNoteProxy noteProxy, MicroservicePatientProxy patientProxy) {
        this.noteProxy = noteProxy;
        this.patientProxy = patientProxy;
    }

    public ReportBean getPatientDiabetesReportById(Integer patientId) {
        PatientBean patient = patientProxy.getPatientById(patientId);
        List<NoteBean> patientNoteList = noteProxy.getPatientNotes(patientId);
        return getDiabetesReport(patient, patientNoteList);
    }

    public ReportBean getDiabetesReport(PatientBean patient, List<NoteBean> patientNoteList) {
        int patientAge = calculatePatientAge(patient.getBirthdate());
        List<String> notesTriggerWords = getNotesTriggerWords(patientNoteList);
        int triggerCount = notesTriggerWords.size();
        boolean isFemale = "F".equals(patient.getGender());

        if (triggerCount < 2) {
            return new ReportBean("NONE", "Le dossier du patient ne contient pas ou peu de déclencheurs (moins de 2).", notesTriggerWords);
        }

        if (patientAge < 30) {
            return getReportForYoungPatient(isFemale, triggerCount, notesTriggerWords);
        } else {
            return getReportForAdultPatient(triggerCount, notesTriggerWords);
        }
    }

    private ReportBean getReportForYoungPatient(boolean isFemale, int triggerCount, List<String> notesTriggerWords) {
        if ((isFemale && triggerCount >= 7) || (!isFemale && triggerCount >= 5)) {
            return new ReportBean("Early Onset", "Le patient " + (isFemale ? "est une femme" : "est un homme") + " qui a moins de 30 ans. Son dossier contient " + triggerCount + " termes déclencheurs.", notesTriggerWords);
        }
        if ((isFemale && triggerCount >= 4) || (!isFemale && triggerCount >= 3)) {
            return new ReportBean("In danger", "Le patient " + (isFemale ? "est une femme" : "est un homme") + " qui a moins de 30 ans. Son dossier contient entre " + (isFemale ? "4 et 6" : "3 et 4") + " termes déclencheurs.", notesTriggerWords);
        }
        return new ReportBean();
    }

    private ReportBean getReportForAdultPatient(int triggerCount, List<String> notesTriggerWords) {
        if (triggerCount >= 8) {
            return new ReportBean("Early Onset", "Le patient a 30 ans ou plus. Son dossier contient 8 termes déclencheurs ou plus.", notesTriggerWords);
        }
        if (triggerCount >= 6) {
            return new ReportBean("In danger", "Le patient a 30 ans ou plus. Son dossier contient entre 6 et 7 termes déclencheurs.", notesTriggerWords);
        }
        return new ReportBean("Borderline", "Le patient a 30 ans ou plus. Son dossier contient entre 2 et 5 termes déclencheurs.", notesTriggerWords);
    }

    public List<String> getNotesTriggerWords(List<NoteBean> patientNoteList) {
        List<String> foundKeywords = new ArrayList<>();
        for (NoteBean note : patientNoteList) {
            String description = cleanDescription(note.getDescription().toLowerCase());
            for (String triggerWord : triggerWords) {
                if (description.contains(triggerWord.toLowerCase())) {
                    foundKeywords.add(triggerWord);
                }
            }
        }
        return foundKeywords.stream().distinct().collect(Collectors.toList());
    }

    public int calculatePatientAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public String cleanDescription(String description) {
        String textWithoutHtmlTags = description.replaceAll("<[^>]*>", " ");
        return StringEscapeUtils.unescapeHtml4(textWithoutHtmlTags).trim();
    }
}
