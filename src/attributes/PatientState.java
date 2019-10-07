package attributes;

/**
 * Clasa ce contine datele de stare a unui pacient necesare citirii datelor de intrare.
 *
 * [Calatoaie Iulia-Adriana, Grupa 325CA]
 */
public final class PatientState {
private IllnessType illnessName;
private int severity;

public IllnessType getIllnessName() {
    return illnessName;
}

public void setIllnessName(IllnessType illnessName) {
    this.illnessName = illnessName;
}

public int getSeverity() {
    return severity;
}

public void setSeverity(int severity) {
    this.severity = severity;
}
}
