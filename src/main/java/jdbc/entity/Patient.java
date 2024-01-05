package jdbc.entity;

import java.time.LocalDate;

import static jdbc.utility.PatientAdapter.*;

public class Patient {
    private final Integer num;
    private final String snils;
    private final String sex;
    private final String fio;
    private final LocalDate birthDate;
    private final String policy;
    private final int finSource;

    public String getFio() {
        return fio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getSex() {
        return sex;
    }

    public Integer getNum() {
        return num;
    }

    public String getSnils() {
        return snils;
    }

    public String getPolicy() {
        return policy;
    }

    public int getFinSource() {
        return finSource;
    }

    public Patient(String fio, LocalDate birthDate, Integer sex,
                   Integer num, String smo, String snils, String policy, Integer finSource) {
        this.fio = fio;
        this.birthDate = birthDate;
        this.sex = getSexStr(sex);
        this.num = num;
        this.snils = getSnilsStr(snils);
        this.policy = getPolicyStr(smo, policy);
        this.finSource = finSource;
    }

    @Override
    public String toString() {
        return "Patient{" +
                ", fio='" + fio + '\'' +
                ", birthDate=" + birthDate +
                ", sex='" + sex + '\'' +
                ", num=" + num +
                ", snils='" + snils + '\'' +
                ", policy='" + policy + '\'' +
                ", finSource='" + finSource + '\'' +
                "}\n";
    }
}

