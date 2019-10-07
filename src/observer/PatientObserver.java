package observer;

import comparators.NameComparator;
import investigation.InvestigationResult;
import attributes.State;
import simulation.ERSimulator;
import entities.Patient;

import java.util.Observer;
import java.util.Observable;
import java.util.LinkedList;

/**
 * Clasa ce observa pacientii le fiecare runda si se afiseaza starea lor.
 *
 * [Calatoaie Iulia-Adriana, Grupa 325CA]
 */
public final class PatientObserver implements Observer {

@Override
public void update(Observable o, Object arg) {
    ERSimulator erSimulator = ERSimulator.getInstance();
    LinkedList<Patient> patients = new LinkedList<>(
            erSimulator.getInputReader().getIncomingPatients());
    // sortez alfabetic pacientii inainte sa ii afisez
    patients.sort(new NameComparator());

    System.out.println("~~~~ Patients in round " + erSimulator.getRound() + " ~~~~");
    // afisez starea fiecarui pacient care se afla in coada de triaj, de examinare,
    // de investigatii sau a fost vazut de un doctor
    for (Patient patient:patients) {
        if (patient.getInQueue().equals("triage")) {
            System.out.println(patient.getName() + " is " + State.TRIAGEQUEUE.getValue());
        }
        if (patient.getInQueue().equals("examination")) {
            System.out.println(patient.getName() + " is " + State.EXAMINATIONSQUEUE.getValue());
        }
        if (patient.getInQueue().equals("investigation")) {
            System.out.println(patient.getName() + " is " + State.INVESTIGATIONSQUEUE.getValue());
        }
        if (patient.getInQueue().equals("none")) {
            if (patient.getInvestigationResult().equals(
                InvestigationResult.HOSPITALIZE.getValue())) {
                System.out.println(patient.getName() + " is "
                + patient.getDoctor().hospitalizedBy());
            }
            if (patient.getInvestigationResult().equals(InvestigationResult.OPERATE.getValue())) {
                System.out.println(patient.getName() + " is " + patient.getDoctor().operatedBy());
            }
            if (patient.getInvestigationResult().equals(InvestigationResult.SENT_HOME.getValue())) {
                System.out.println(patient.getName() + " is " + patient.getDoctor().sentHomeBy());
            }
            if (patient.getInvestigationResult().equals("another hospital")) {
                System.out.println(patient.getName() + " is " + State.OTHERHOSPITAL.getValue());
            }
            if (patient.getInvestigationResult().equals("done treatment")) {
                System.out.println(patient.getName() + " is "
                + State.HOME_DONE_TREATMENT.getValue());
            }
        }
    }
    System.out.println();
}
}
