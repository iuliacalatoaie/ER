package investigation;

/**
 * Actiuni care trebuie efectuate asupra pacientului dupa o investigatie.
 * Pot fi folosite de catre obiectele reprezentand medici / asistente medicale
 * / tehnicieni sau cozi
 *
 * [Parte din scheletul temei]
 */
public enum InvestigationResult {

    OPERATE("operate"),
    HOSPITALIZE("hospitalize"),
    TREATMENT("treatment"),
    NOT_DIAGNOSED("not diagnosed"),
    SENT_HOME("sent home");
private String value;

InvestigationResult(String value) {
    this.value = value;
}

public String getValue() {
    return value;
}
}
