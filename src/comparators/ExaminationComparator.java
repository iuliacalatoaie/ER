package comparators;

import entities.Patient;
import investigation.Urgency;

import java.util.Comparator;

/**
 * Comparator pentru pacientii aflati in coada de examinare.
 *
 * [Calatoaie Iulia-Adriana, Grupa 325CA]
 */
public final class ExaminationComparator implements Comparator<Patient> {
@Override
public int compare(Patient o1, Patient o2) {
    // daca doi pacienti au acelasi grad de urgenta si aceeasi severitate
    // se ordoneaza descrescator dupa nume
    if (o1.getUrgency().equals(o2.getUrgency())) {
        if (o1.getState().getSeverity() == o2.getState().getSeverity()) {
            return o2.getName().compareTo(o1.getName());
        } else {
            // altfel se ordineaza descrescator dupa severitate
            return (o2.getState().getSeverity() - o1.getState().getSeverity());
        }
    } else {
        // IMMEDIATE este cel mai ridicat grad de urgenta
        if (o1.getUrgency().equals(Urgency.IMMEDIATE)) {
            return -1;
        }
        // NON_URGENT este cel mai scazut grad de urgenta
        if (o1.getUrgency().equals(Urgency.NON_URGENT)) {
            return 1;
        }
        if (o2.getUrgency().equals(Urgency.IMMEDIATE)) {
            return 1;
        }
        if (o2.getUrgency().equals(Urgency.NON_URGENT)) {
            return -1;
        }
        // URGENT este mai important ca LESS_URGENT
        if (o1.getUrgency().equals(Urgency.LESS_URGENT)
            && o2.getUrgency().equals(Urgency.URGENT)) {
            return 1;
        }
        if (o2.getUrgency().equals(Urgency.LESS_URGENT)
            && o1.getUrgency().equals(Urgency.URGENT)) {
            return -1;
        }
    }
    return 0;
}
}
