package observer;

import comparators.NameComparator;
import entities.Doctor;
import simulation.ERSimulator;
import entities.Patient;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * Observator pentru etapa de tratare a pacientilor intenati.
 *
 * Asistentele vor trata si stabili cate runde mai au pacientii de stat internati.
 *
 * [Calatoaie Iulia-Adriana, Grupa 325CA]
 */
public final class NurseObserver implements Observer {
@Override
public void update(Observable o, Object arg) {
    ERSimulator erSimulator = ERSimulator.getInstance();
    LinkedList<Patient> patients = new LinkedList<>(erSimulator.getTreatmentQueue());
    int nurses = erSimulator.getInputReader().getNurses();
    Doctor doctor = new Doctor();
    patients.sort(new NameComparator());

    System.out.println("~~~~ Nurses treat patients ~~~~");
    for (int i = 0; i < patients.size(); ++i) {
        patients.get(i).setRoundsIfTreated(patients.get(i).getRoundsIfTreated() - 1);
        System.out.print("Nurse " + i % nurses + " treated " + patients.get(i).getName()
                          + " and patient has ");
        if (patients.get(i).getRoundsIfTreated() == 1) {
            System.out.println(patients.get(i).getRoundsIfTreated() + " more round");
        } else if (patients.get(i).getRoundsIfTreated() < 0) {
            System.out.println(" 0 more rounds");
        } else {
            System.out.println(patients.get(i).getRoundsIfTreated() + " more rounds");
        }
        // setez severitatea pacientilor scazand factorul T
        patients.get(i).getState().setSeverity(patients.get(i).getState().getSeverity()
                                                 - doctor.getT());
        // caut in coada de incomeingPatients pentru a seta si acolo severitatea
        for (int j = 0; j < patients.size(); ++j) {
            if (erSimulator.getTreatmentQueue().get(j).getName().equals(
                patients.get(i).getName())) {
                erSimulator.getTreatmentQueue().get(j).getState().
                 setSeverity(patients.get(i).getState().getSeverity());
                }
            }
        if (patients.get(i).getState().getSeverity() <= 0
            || patients.get(i).getRoundsIfTreated() == 0) {
            // setez in coada incomingPatients faptul ca pacientul si-a terminat tratamentul
            erSimulator.getInputReader().getIncomingPatients().get(
            patients.get(i).getId()).setInvestigationResult("done treatment");
            // caut in coada de tratament pacientul si cand il gasesc setez faptul ca
            // a terminat tratamentul
            for (int j = 0; j < erSimulator.getTreatmentQueue().size(); ++j) {
                if (erSimulator.getTreatmentQueue().get(j).getName().equals(
                    patients.get(i).getName())) {
                    erSimulator.getTreatmentQueue().get(j).setInvestigationResult(
                    "done treatment");
                    break;
                }
            }
        }
    }
    System.out.println();
}
}
