package comparators;

import entities.Patient;

import java.util.Comparator;

/**
 * Comparator pentru ordonarea alfabetica dupa nume a pacientilor pentru afisarea starii.
 *
 */
public final class NameComparator implements Comparator<Patient> {

@Override
public int compare(Patient o1, Patient o2) {
    return o1.getName().compareTo(o2.getName());
}
}
