package simulation;

import comparators.ExaminationComparator;
import comparators.SeverityComparator;
import entities.Patient;
import investigation.UrgencyEstimator;
import investigation.InvestigationResult;
import investigation.Urgency;
import attributes.IllnessType;
import entities.Doctor;
import attributes.DoctorType;

import java.util.LinkedList;
import java.util.Observable;

/**
 * Clasa ce contine elementele necesare simularii.
 *
 * [Calatoaie Iulia-Adriana, Grupa 325CA]
 */
public final class ERSimulator extends Observable {

private static ERSimulator instance = null;
private static final int TREE = 3;

private InputReader inputReader;
private int round;
private LinkedList<Patient> triageQueue = new LinkedList<>();
private LinkedList<Patient> examinationQueue = new LinkedList<>();
private LinkedList<Patient> investigationQueue = new LinkedList<>();
private LinkedList<Patient> treatmentQueue = new LinkedList<>();

private ERSimulator() {
}

public static ERSimulator getInstance() {
    if (instance == null) {
        instance = new ERSimulator();
    }
    return instance;
}

public void update() {
    this.setChanged();
    this.notifyObservers();
}

public InputReader getInputReader() {
    return inputReader;
}

public void setInputReader(InputReader inputReader) {
    this.inputReader = inputReader;
}

public LinkedList<Patient> getTriageQueue() {
    return triageQueue;
}

public void setTriageQueue(LinkedList<Patient> triageQueue) {
    this.triageQueue = triageQueue;
}

public LinkedList<Patient> getExaminationQueue() {
    return examinationQueue;
}

public void setExaminationQueue(LinkedList<Patient> examinationQueue) {
    this.examinationQueue = examinationQueue;
}

public LinkedList<Patient> getInvestigationQueue() {
    return investigationQueue;
}

public void setInvestigationQueue(LinkedList<Patient> investigationsQueue) {
    this.investigationQueue = investigationsQueue;
}

public int getRound() {
    return round;
}

public void setRound(int round) {
    this.round = round;
}

public LinkedList<Patient> getTreatmentQueue() {
    return treatmentQueue;
}

public void setTreatmentQueue(LinkedList<Patient> treatmentQueue) {
    this.treatmentQueue = treatmentQueue;
}

/**
 * @param actualRound
 * Functie ce muta pacientii in triageQueue in functie de runda actuala.
 */
public void moveInTriageQueue(int actualRound) {
    // din toti pacientii ii scot pe cei ce apar la rundele viitoare
    // si pe cei care au fost diagnosticati
    int time = actualRound - 1;
    for (int i = 0; i < inputReader.getIncomingPatients().size(); ++i) {
        if (inputReader.getIncomingPatients().get(i).getTime() == time
            || inputReader.getIncomingPatients().get(i).getInQueue().equals("triage")) {
            triageQueue.add(inputReader.getIncomingPatients().get(i));
            inputReader.getIncomingPatients().get(i).setInQueue("triage");
        }
    }
    // se sorteaza coada de triaj dupa severitate
    triageQueue.sort(new SeverityComparator());
}

/**
 * Functie ce estimeaza urgenta pacientilor si formeaza coada de examinare.
 */
public void moveInExaminationQueue() {
    int nurses = inputReader.getNurses();
    UrgencyEstimator urgencyEstimator = UrgencyEstimator.getInstance();
    for (Patient patient:triageQueue) {
        // ficare asistenta verifica un pacient, stabilindu-i gradul de urgenta
        if (nurses > 0 && patient.getUrgency().equals(Urgency.NOT_DETERMINED)) {
            patient.setUrgency(urgencyEstimator.estimateUrgency(patient.getState().getIllnessName(),
                    patient.getState().getSeverity()));
            inputReader.getIncomingPatients().get(patient.getId()).setUrgency(
            patient.getUrgency());
            inputReader.getIncomingPatients().get(patient.getId()).setInQueue("examination");
            examinationQueue.add(patient);
            --nurses;
        }
    }
    // la final ordonez lista de examinare
    examinationQueue.sort(new ExaminationComparator());
    // golesc lista de triaj
    triageQueue.clear();
}

/**
 * Functie ce simuleaza gasirea unui doctor disponibil
 * pentru fiecare pacient aflat in examinationQueue.
 */
public void examination() {
    IllnessType illnessType;
    boolean foundADoctorToExamine;
    ExaminationComparator comparator = new ExaminationComparator();
    examinationQueue.sort(comparator);
    // pentru fiecare pacient trebuie sa gasesc un doctor care sa il poata consulta
    for (Patient patient:examinationQueue) {
        foundADoctorToExamine = false;
        illnessType = patient.getState().getIllnessName();
        for (int i = 0; i < inputReader.getDoctors().size(); ++i) {
            // daca gasesc un doctor
            // trebuie ca pacientul sa nu aiba nevoie de operatie sau doctorul sa fie chirurg
            if (inputReader.getDoctors().get(i).getIllToCure().contains(illnessType)) {
                if (!patient.getInvestigationResult().equals(InvestigationResult.OPERATE.getValue())
                    || inputReader.getDoctors().get(i).getIsSurgeon()) {
                    // il mut in coada listei si examineaza pacientul daca nu trebuie operat
                    // sau doctorul e si chirurg
                    examinePatient(patient, inputReader.getDoctors().get(i));
                    // mut doctorul la finalul listei
                    inputReader.getDoctors().add(inputReader.getDoctors().get(i));
                    foundADoctorToExamine = true;
                    inputReader.getDoctors().remove(i);
                    break;
                }
            }
        }
        // daca nu s-a gasit un doctor carea sa satisfaca conditiile de mai sus
        // pacientul trebuie mutata in alt spital
        if (!foundADoctorToExamine) {
            patient.setInvestigationResult("another hospital");
            inputReader.getIncomingPatients().get(patient.getId()).setInQueue("none");
        }
    }
    examinationQueue.clear();
}

/**
 * @param patient
 * @param doctor
 * Functie ce simuleaza examinarea unui pacient de catre doctor.
 */
private void examinePatient(Patient patient, Doctor doctor) {
    // daca severity < maxForTreatment pacientul e trimis acasa
    if (patient.getState().getSeverity() <= doctor.getMaxForTreatment()
        && patient.getInvestigationResult().equals(InvestigationResult.NOT_DIAGNOSED.getValue())) {
        // se actualizeaza rezultatul investigatiei
        patient.setInvestigationResult(InvestigationResult.SENT_HOME.getValue());
        inputReader.getIncomingPatients().get(patient.getId()).
        setInvestigationResult(InvestigationResult.SENT_HOME.getValue());
        inputReader.getIncomingPatients().get(patient.getId()).setInQueue("none");
        // se actualizeaza doctorul care a consultat pacientul
        patient.setDoctor(doctor);
        inputReader.getIncomingPatients().get(patient.getId()).setDoctor(doctor);
        return;
    }
    if (patient.getState().getSeverity() > doctor.getMaxForTreatment()
        && patient.getInvestigationResult().equals(InvestigationResult.NOT_DIAGNOSED.getValue())) {
        // se adauga pacientul la coada de investigatie
        investigationQueue.add(patient);
        // si actualizez statusul ca se afla in coada de investigatie
        inputReader.getIncomingPatients().get(patient.getId()).setInQueue("investigation");
        return;
    }
    // daca a ajuns aici, inseamna ca pacientul a fost investigat
    // si doctorul urmeaza sa decida de ce se poate intampla cu el
    if (patient.getInvestigationResult().equals(InvestigationResult.TREATMENT.getValue())) {
        // pacientul e trimis acasa cu tratament
        inputReader.getIncomingPatients().get(patient.getId()).
        setInvestigationResult(InvestigationResult.SENT_HOME.getValue());
        // nu mai e in nicio lista de asteptare
        inputReader.getIncomingPatients().get(patient.getId()).setInQueue("none");
        // se actualizeaza doctorul care a consultat pacientul
        patient.setDoctor(doctor);
        inputReader.getIncomingPatients().get(patient.getId()).setDoctor(doctor);
        return;
    }
    // daca a venit cu sugestie de spitalizare in urma investigatiei
    if (patient.getInvestigationResult().equals(InvestigationResult.HOSPITALIZE.getValue())) {
        // se actualizeaza rundele pentru care va trebui sa ramana in spital
        int severity = patient.getState().getSeverity();
        int rounds = max(Math.toIntExact(Math.round(severity * doctor.getC1())), TREE);
        patient.setRoundsIfTreated(rounds);
        inputReader.getIncomingPatients().get(patient.getId()).setRoundsIfTreated(rounds);
        // nu mai e in nicio lista de asteptare
        inputReader.getIncomingPatients().get(patient.getId()).setInQueue("none");
        // se muta pacientul in coada de tratament
        treatmentQueue.add(patient);
        // se actualizeaza doctorul care a consultat pacientul
        patient.setDoctor(doctor);
        inputReader.getIncomingPatients().get(patient.getId()).setDoctor(doctor);
        return;
    }
    if (patient.getInvestigationResult().equals(InvestigationResult.OPERATE.getValue())) {
        // se recalculeaza severitatea
        int severity = patient.getState().getSeverity();
        severity -= (Math.round(severity * doctor.getC2()));
        // se actualizeaza severitatea
        patient.getState().setSeverity(severity);
        inputReader.getIncomingPatients().get(patient.getId()).getState().setSeverity(severity);
        // se actualizeaza rundele cat va sta in coada de tratare
        int rounds = max(Math.toIntExact(Math.round(severity * doctor.getC1())), TREE);
        patient.setRoundsIfTreated(rounds);
        inputReader.getIncomingPatients().get(patient.getId()).setRoundsIfTreated(rounds);
        // se adauga pacientul in coada de tratare
        treatmentQueue.add(patient);
        // si actualizez faptul ca nu se afla in niciuna dintre cozile de interes
        inputReader.getIncomingPatients().get(patient.getId()).setInQueue("none");
        // se actualizeaza doctorul care a consultat pacientul
        patient.setDoctor(doctor);
        inputReader.getIncomingPatients().get(patient.getId()).setDoctor(doctor);
    }
}

/**
 * @param a
 * @param b
 * @return
 * Functie ce calculeaza maximul dintre doua numere naturale.
 */
private int max(int a, int b) {
    return (a > b) ? a : b;
}

/**
 * Functie ce permite investigarea pacientilor aflati in investigationQueue.
 */
public void investigation() {
    investigationQueue.sort(new ExaminationComparator());
    Doctor doctor = new Doctor();
    doctor.setType(DoctorType.ER_TECHNICIAN);
    int investigators = inputReader.getInvestigators();
    int severity;
    // se investigheaza doar investigators pacienti
    for (Patient patient:investigationQueue) {
        if (investigators > 0) {
            severity = patient.getState().getSeverity();
            // daca severitatea e mai mare ca factorul C1 trebuie operat
            if (severity > doctor.getC1()) {
                patient.setInvestigationResult(InvestigationResult.OPERATE.getValue());
            }
            // daca severitatea e mai mica sau egala ca factorul C2 poate fi trimis acasa
            if (severity <= doctor.getC2()) {
                patient.setInvestigationResult(InvestigationResult.TREATMENT.getValue());
            }
            // daca se afla intre cei doi factori atunci trebuie internat
            if (severity > doctor.getC2() && severity <= doctor.getC1()) {
                patient.setInvestigationResult(
                InvestigationResult.HOSPITALIZE.getValue());
            }
            --investigators;
        } else {
            break;
        }
    }
    investigators = inputReader.getInvestigators();
    // daca s-au investigat toti pacientii
    if (investigationQueue.size() <= investigators) {
        // se adauga toti in coada de examinare pentru a primi verdictul doctorului
        examinationQueue.addAll(investigationQueue);
        // se goleste coada de investigare
        investigationQueue.clear();
    } else {
        // daca nu s-au investigat toti pacientii
        investigators = 0;
        // se elimina din coada primii investigators pacienti,
        // unde investigators e numarul de investigatori
        // si se adauga in coada de examinare pentru a primi verdictul doctorului
        while (investigators < inputReader.getInvestigators()) {
            examinationQueue.add(investigationQueue.get(0));
            // actualizez statusul de a fi prezent in examinationQueue
            investigationQueue.remove(0);
            ++investigators;
        }
    }
    // sortez coada de examinare
    examinationQueue.sort(new ExaminationComparator());
    // setez faptul ca pcaientii sunt in coada de examinare
    for (Patient patient:examinationQueue) {
        inputReader.getIncomingPatients().get(patient.getId()).setInQueue("examination");
    }
}
}
