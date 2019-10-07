package attributes;

/**
 * Clasa ce retire tipul unui doctor.
 *
 */
public enum DoctorType {

    CARDIOLOGIST("Cardiologist"),
    ER_PHYSICIAN("ERPhysician"),
    GASTROENTEROLOGIST("Gastroenterologist"),
    GENERAL_SURGEON("General Surgeon"),
    INTERNIST("Internist"),
    NEUROLOGIST("Neurologist"),
    ER_TECHNICIAN("Er Technician");
private String value;

DoctorType(String value) {
    this.value = value;
}

public String getValue() {
    return value;
}
}
