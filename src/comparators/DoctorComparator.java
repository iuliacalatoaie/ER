package comparators;

import entities.Patient;

import java.util.Comparator;

/**
 * Comparator pentru a ordona pacientii in functie de ordinea doctorilor din datele de intrare.
 *
 */
public final class DoctorComparator implements Comparator<Patient> {
@Override
public int compare(Patient o1, Patient o2) {
    // vor fi ordonati crescator dupa id-ul doctorilor
    // daca sunt consultati de acelasi doctor vor fi ordonati alfabetic
    if (o1.getDoctor().getId() == o2.getDoctor().getId()) {
        return o1.getName().compareTo(o2.getName());
    }
    return o1.getDoctor().getId() - o2.getDoctor().getId();
}
}
