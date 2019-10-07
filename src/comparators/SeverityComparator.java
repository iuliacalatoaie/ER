package comparators;

import entities.Patient;

import java.util.Comparator;

/**
 * Comparator pentru ordonarea dupa severitate necesara in ordonatrea pacientilor in coada de triaj.
 *
 */
public final class SeverityComparator implements Comparator<Patient> {
@Override
public int compare(Patient o1, Patient o2) {
    return o2.getState().getSeverity() - o1.getState().getSeverity();
}
}
