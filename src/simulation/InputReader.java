package simulation;

import entities.Doctor;
import entities.Patient;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Clasa ce contine instantele necesare citirii din fisier a datelor de intrare.
 *
 * [Calatoaie Iulia-Adriana, Grupa 325CA]
 */
public final class InputReader extends Observable {

private int simulationLength;
private int nurses;
private int investigators;
private LinkedList<Doctor> doctors = new LinkedList<>();
private LinkedList<Patient> incomingPatients = new LinkedList<>();

public int getSimulationLength() {
    return simulationLength;
}

public void setSimulationLength(int simulationLength) {
    this.simulationLength = simulationLength;
}

public int getNurses() {
    return nurses;
}

public void setNurses(int nurses) {
    this.nurses = nurses;
}

public int getInvestigators() {
    return investigators;
}

public void setInvestigators(int investigators) {
    this.investigators = investigators;
}

public LinkedList<Doctor> getDoctors() {
    return doctors;
}

public void setDoctors(LinkedList<Doctor> doctors) {
    this.doctors = doctors;
}

public LinkedList<Patient> getIncomingPatients() {
    return incomingPatients;
}

public void setIncomingPatients(LinkedList<Patient> incomingPatients) {
    this.incomingPatients = incomingPatients;
}
}
