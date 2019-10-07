package entities;

import attributes.PatientState;
import investigation.InvestigationResult;
import investigation.Urgency;

/**
 * Clasa ce retine datele unui pacient.
 *
 */
public final class Patient {

private int id;
private String name;
private int age;
private int time;
private PatientState state;
private Urgency urgency = Urgency.NOT_DETERMINED;
private Doctor doctor = new Doctor();
private int roundsIfTreated = -1;
private String investigationResult = InvestigationResult.NOT_DIAGNOSED.getValue();
private String inQueue = "none";

public int getId() {
    return id;
}

public void setId(final int id) {
    this.id = id;
}

public String getName() {
    return name;
}

public void setName(final String name) {
    this.name = name;
}

public int getAge() {
    return age;
}

public void setAge(final int age) {
    this.age = age;
}

public int getTime() {
    return time;
}

public void setTime(final int time) {
    this.time = time;
}

public PatientState getState() {
    return state;
}

public void setState(final PatientState state) {
    this.state = state;
}

@Override
public String toString() {
    return this.name + " " + this.age + " " + this.id + " " + this.state;
}

public Urgency getUrgency() {
    return urgency;
}

public void setUrgency(Urgency urgency) {
    this.urgency = urgency;
}

public Doctor getDoctor() {
    return doctor;
}

public void setDoctor(Doctor doctor) {
    this.doctor = doctor;
}

public int getRoundsIfTreated() {
    return roundsIfTreated;
}

public void setRoundsIfTreated(int roundsIfTreated) {
    this.roundsIfTreated = roundsIfTreated;
}

public String getInvestigationResult() {
    return investigationResult;
}

public void setInvestigationResult(String investigationResult) {
    this.investigationResult = investigationResult;
}

public String getInQueue() {
    return inQueue;
}

public void setInQueue(String inQueue) {
    this.inQueue = inQueue;
}
}
