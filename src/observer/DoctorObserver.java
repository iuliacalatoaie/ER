package observer;

import comparators.DoctorComparator;
import entities.Doctor;
import entities.Patient;
import simulation.ERSimulator;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Doctorii observa starea pacientilor internati.
 * Iau decizii cu privire la starea lor in runda actuala.
 *
 */
public final class DoctorObserver implements Observer {

@Override
public void update(Observable o, Object arg) {
    ERSimulator erSimulator = ERSimulator.getInstance();

    LinkedList<Patient> patientsTreated = new LinkedList<>(erSimulator.getTreatmentQueue());
    LinkedList<Patient> toRemove = new LinkedList<>();
    Doctor doctor = new Doctor();
    patientsTreated.sort(new DoctorComparator());

    System.out.println("~~~~ Doctors check their hospitalized patients and give verdicts ~~~~");
    for (Patient patient:patientsTreated) {
        doctor.setType(patient.getDoctor().getType());
        doctor.setId(patient.getDoctor().getId());
        // daca inca mai sunt runde de simulat, pacientul inca nu a terminat rundele
        // si rezultatul investigatiilor nu certifica faptul ca a terminat tratamentul
        if (patient.getRoundsIfTreated() > 0
            && !patient.getInvestigationResult().equals("done treatment")) {
            // decizia doctorului este ca pacientul sa ramana in spital
            System.out.println(doctor.getType().getValue()
            + " says that " + patient.getName() + " should remain in hospital ");
        } else if (patient.getState().getSeverity() <= 0 || patient.getRoundsIfTreated() <= 0) {
            // daca una dintre aceste conditii nu se indeplineste, e trimis acasa
            System.out.println(doctor.getType().getValue() + " sent "
            + patient.getName() + " home");
            toRemove.add(patient);
        }
    }
    erSimulator.getTreatmentQueue().removeAll(toRemove);
    System.out.println();
}
}
