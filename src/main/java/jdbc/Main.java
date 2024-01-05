package jdbc;

import jdbc.dao.DaoPatient;
import jdbc.dao.DaoPatientImpl;
import jdbc.entity.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/pats";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) {
        DaoPatient daoPatient = new DaoPatientImpl(DB_URL, USERNAME, PASSWORD);

        // Получить всех пациентов
        List<Patient> allPatients = daoPatient.getAll();
        allPatients.forEach(System.out::print);
        System.out.println();

        // Получить пациентов по введенному запросу
        List<Patient> patientsOnRequest = daoPatient.queryByString("SELECT * FROM patients WHERE sex = 2");
        patientsOnRequest.forEach(System.out::print);
        System.out.println();

        // Получить соответствия дат рождения и имён пациентов мужчин с источником финансирования 3
        Map<LocalDate, String> patientsMen = daoPatient.getMenFinSource3BirthdayFio();
        patientsMen.entrySet().forEach(System.out::println);
        System.out.println();

        // Получить пациентов женщин, относящихся к заданной страховой компании
        List<Patient> patientsWomen = daoPatient.getWomenSmo("Согаз");
        patientsWomen.forEach(System.out::print);
        System.out.println();

        // Получить пациентов по введенной фамилии
        List<Patient> patientsByLastName = daoPatient.getPatientsByLastName("Петрова");
        patientsByLastName.forEach(System.out::print);
        System.out.println();

        // Получить пациентов по введенному году рождения
        List<Patient> patientsByYear = daoPatient.getPatientsByYearOfBirth(2000);
        patientsByYear.forEach(System.out::print);
        System.out.println();

        // Получить количество пациентов для источников финансирования
        Map<String, String> countPatients = daoPatient.getNumberOfPatientsForSources();
        countPatients.entrySet().forEach(System.out::println);

        daoPatient.closeConnection();
    }
}
