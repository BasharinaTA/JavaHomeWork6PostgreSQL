package jdbc.dao;

import jdbc.entity.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DaoPatient {
    List<Patient> getAll();

    List<Patient> queryByString(String query);

    Map<LocalDate, String> getMenFinSource3BirthdayFio();

    List<Patient> getWomenSmo(String smo);

    List<Patient> getPatientsByLastName(String lastName);

    List<Patient> getPatientsByYearOfBirth(int year);

    Map<String, String> getNumberOfPatientsForSources();

    void closeConnection();
}
