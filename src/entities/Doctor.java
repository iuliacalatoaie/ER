package entities;

import attributes.DoctorType;
import attributes.IllnessType;
import attributes.State;

import java.util.LinkedList;

/**
 * Clasa ce contine atributele unui doctor.
 *
 */
public final class Doctor {

private static final double C1_ER_TECHNICIAN = 75;
private static final double C2_ER_TECHNICIAN = 40;
private static final double C1_CARDIOLOGIST = 0.4;
private static final double C2_CARDIOLOGIST = 0.1;
private static final double C1_ER_PHYSICIAN = 0.1;
private static final double C2_ER_PHYSICIAN = 0.3;
private static final double C1_GASTROENTEROLOGIST = 0.5;
private static final double C2_GASTROENTEROLOGIST = 0.0;
private static final double C1_GENERAL_SURGEON = 0.2;
private static final double C2_GENERAL_SURGEON = 0.2;
private static final double C1_INTERNIST = 0.01;
private static final double C2_INTERNIST = 0.0;
private static final double C1_NEUROLOGIST = 0.5;
private static final double C2_NEUROLOGIST = 0.1;
private static final int T = 22;

private DoctorType type;
private boolean isSurgeon;
private int maxForTreatment;
private LinkedList<IllnessType> illToCure;
private int id;

public int getMaxForTreatment() {
    return maxForTreatment;
}

public void setMaxForTreatment(final int maxForTreatment) {
    this.maxForTreatment = maxForTreatment;
}


public DoctorType getType() {
    return type;
}

public void setType(final DoctorType type) {
    this.type = type;
}

public boolean getIsSurgeon() {
    return isSurgeon;
}

public void setIsSurgeon(boolean isSurgeon) {
    this.isSurgeon = isSurgeon;
}

@Override
public String toString() {
    return this.type + " " + this.maxForTreatment + " " + this.isSurgeon;
}

public LinkedList<IllnessType> getIllToCure() {
    return illToCure;
}

public void setIllToCure(LinkedList<IllnessType> illToCure) {
    this.illToCure = illToCure;
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

/**
 * Functie ce construieste lista cu afectiunile ce pot fi
 * tratate de doctorul curent.
 */
public void makeListOfIllnesses() {
    illToCure = new LinkedList<>();
    if (this.type.equals(DoctorType.NEUROLOGIST)) {
        illToCure.add(IllnessType.STROKE);
    }
    if (this.type.equals(DoctorType.CARDIOLOGIST)) {
        illToCure.add(IllnessType.HEART_DISEASE);
        illToCure.add(IllnessType.HEART_ATTACK);
    }
    if (this.type.equals(DoctorType.GASTROENTEROLOGIST)) {
        illToCure.add(IllnessType.ABDOMINAL_PAIN);
        illToCure.add(IllnessType.ALLERGIC_REACTION);
        illToCure.add(IllnessType.FOOD_POISONING);
    }
    if (this.type.equals(DoctorType.GENERAL_SURGEON)) {
        illToCure.add(IllnessType.ABDOMINAL_PAIN);
        illToCure.add(IllnessType.BURNS);
        illToCure.add(IllnessType.CAR_ACCIDENT);
        illToCure.add(IllnessType.CUTS);
        illToCure.add(IllnessType.SPORT_INJURIES);
    }
    if (this.type.equals(DoctorType.INTERNIST)) {
        illToCure.add(IllnessType.ABDOMINAL_PAIN);
        illToCure.add(IllnessType.ALLERGIC_REACTION);
        illToCure.add(IllnessType.FOOD_POISONING);
        illToCure.add(IllnessType.HEART_DISEASE);
        illToCure.add(IllnessType.HIGH_FEVER);
        illToCure.add(IllnessType.PNEUMONIA);
    }
    if (this.type.equals(DoctorType.ER_PHYSICIAN)) {
        illToCure.add(IllnessType.ALLERGIC_REACTION);
        illToCure.add(IllnessType.BROKEN_BONES);
        illToCure.add(IllnessType.BURNS);
        illToCure.add(IllnessType.CAR_ACCIDENT);
        illToCure.add(IllnessType.CUTS);
        illToCure.add(IllnessType.HIGH_FEVER);
        illToCure.add(IllnessType.SPORT_INJURIES);
    }
}

/**
 * @return
 * Functie ce intoarce factorul C1 al doctorului curent
 */
public double getC1() {
    if (this.type.equals(DoctorType.GASTROENTEROLOGIST)) {
        return C1_GASTROENTEROLOGIST;
    }
    if (this.type.equals(DoctorType.GENERAL_SURGEON)) {
        return C1_GENERAL_SURGEON;
    }
    if (this.type.equals(DoctorType.CARDIOLOGIST)) {
        return C1_CARDIOLOGIST;
    }
    if (this.type.equals(DoctorType.INTERNIST)) {
        return  C1_INTERNIST;
    }
    if (this.type.equals(DoctorType.ER_PHYSICIAN)) {
        return C1_ER_PHYSICIAN;
    }
    if (this.type.equals(DoctorType.NEUROLOGIST)) {
        return C1_NEUROLOGIST;
    }
    return C1_ER_TECHNICIAN;
}

/**
 * @return
 * Functie ce retureneaza factorul C2 al doctorului curent
 */
public double getC2() {
    if (this.type.equals(DoctorType.GASTROENTEROLOGIST)) {
        return C2_GASTROENTEROLOGIST;
    }
    if (this.type.equals(DoctorType.GENERAL_SURGEON)) {
        return C2_GENERAL_SURGEON;
    }
    if (this.type.equals(DoctorType.CARDIOLOGIST)) {
        return C2_CARDIOLOGIST;
    }
    if (this.type.equals(DoctorType.INTERNIST)) {
        return  C2_INTERNIST;
    }
    if (this.type.equals(DoctorType.ER_PHYSICIAN)) {
        return C2_ER_PHYSICIAN;
    }
    if (this.type.equals(DoctorType.NEUROLOGIST)) {
        return C2_NEUROLOGIST;
    }
    return C2_ER_TECHNICIAN;
}

/**
 * @return
 * Functie ce retuneaza factorul T al doctorului curent
 */
public int getT() {
    return T;
}

/**
 * @return
 * Functie ce returneaza mesajul de afisare ce atesta faptul ca un
 * pacient a fost trimis acasa de doctorul curent
 */
public String sentHomeBy() {
    if (this.type.equals(DoctorType.ER_PHYSICIAN)) {
        return State.HOME_ERPHYSICIAN.getValue();
    }
    if (this.type.equals(DoctorType.NEUROLOGIST)) {
        return State.HOME_NEURO.getValue();
    }
    if (this.type.equals(DoctorType.INTERNIST)) {
        return State.HOME_INTERNIST.getValue();
    }
    if (this.type.equals(DoctorType.GENERAL_SURGEON)) {
        return State.HOME_SURGEON.getValue();
    }
    if (this.type.equals(DoctorType.GASTROENTEROLOGIST)) {
        return State.HOME_GASTRO.getValue();
    }
    return State.HOME_CARDIO.getValue();
}

/**
 * @return
 * Functie ce returneaza mesajul de afisare ce atesta faptul ca un
 * pacient a fost operat de doctorul curent
 */
public String operatedBy() {
    if (this.type.equals(DoctorType.ER_PHYSICIAN)) {
        return State.OPERATED_ERPHYSICIAN.getValue();
    }
    if (this.type.equals(DoctorType.NEUROLOGIST)) {
        return State.OPERATED_NEURO.getValue();
    }
    if (this.type.equals(DoctorType.GENERAL_SURGEON)) {
        return State.OPERATED_SURGEON.getValue();
    }
    return State.OPERATED_CARDIO.getValue();
}

/**
 * @return
 * Functie ce returneaza mesajul de afisare ce atesta faptul ca un
 * pacient a fost internat de doctorul curent
 */
public String hospitalizedBy() {
    if (this.type.equals(DoctorType.ER_PHYSICIAN)) {
        return State.HOSPITALIZED_ERPHYSICIAN.getValue();
    }
    if (this.type.equals(DoctorType.NEUROLOGIST)) {
        return State.HOSPITALIZED_NEURO.getValue();
    }
    if (this.type.equals(DoctorType.INTERNIST)) {
        return State.HOSPITALIZED_INTERNIST.getValue();
    }
    if (this.type.equals(DoctorType.GENERAL_SURGEON)) {
        return State.HOSPITALIZED_SURGEON.getValue();
    }
    if (this.type.equals(DoctorType.GASTROENTEROLOGIST)) {
        return State.HOSPITALIZED_GASTRO.getValue();
    }
    return State.HOSPITALIZED_CARDIO.getValue();
}
}
