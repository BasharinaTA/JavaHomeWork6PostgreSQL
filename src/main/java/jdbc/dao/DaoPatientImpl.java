package jdbc.dao;

import jdbc.entity.Patient;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoPatientImpl implements DaoPatient {
    private final String dbUrl;
    private final String username;
    private final String password;
    private final Connection connection;

    public DaoPatientImpl(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
        try {
            this.connection = DriverManager.getConnection(dbUrl, username, password);
        } catch (SQLException e) {
            System.out.println("no such bd");
            throw new RuntimeException(e);
        }
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public List<Patient> getAll() {
        return queryByString("SELECT * FROM patients");
    }

    @Override
    public List<Patient> queryByString(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            List<Patient> patients = new ArrayList<>();
            while (result.next()) {
                patients.add(getFromResultEntry(result));
            }
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Patient getFromResultEntry(ResultSet result) throws SQLException {
        return new Patient(result.getString("fio"),
                getDateFromString(result.getString("birth_date")),
                Integer.parseInt(result.getString("sex")),
                Integer.parseInt(result.getString("num")),
                result.getString("smo"),
                result.getString("snils"),
                result.getString("policy"),
                Integer.parseInt(result.getString("fin_source"))
        );
    }

    private LocalDate getDateFromString(String date) {
        String[] birthday = date.split("-");
        return LocalDate.of(Integer.parseInt(birthday[0]), Integer.parseInt(birthday[1]), Integer.parseInt(birthday[2]));
    }

    @Override
    public Map<LocalDate, String> getMenFinSource3BirthdayFio() {
        String query = "SELECT p.fio, p.birth_date FROM patients p WHERE sex = 1 AND fin_source = 3";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            Map<LocalDate, String> patients = new HashMap<>();

            while (result.next()) {
                patients.put(getDateFromString(result.getString("birth_date")), result.getString("fio"));
            }
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Patient> getWomenSmo(String smo) {
        return queryByString("SELECT * FROM patients p WHERE p.sex = 2 AND p.smo = '" + smo + "'");
    }

    @Override
    public List<Patient> getPatientsByLastName(String lastName) {
        return queryByString("SELECT * FROM patients p WHERE p.fio LIKE '" + lastName + " % %'");
    }

    @Override
    public List<Patient> getPatientsByYearOfBirth(int year) {
        return queryByString("SELECT * FROM patients p WHERE date_part('year', p.birth_date) = " + year );
    }

    @Override
    public Map<String, String> getNumberOfPatientsForSources() {
        String query = "SELECT p.fin_source, COUNT(*) AS count FROM patients p GROUP BY p.fin_source";
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            Map<String, String> map = new HashMap<>();
            while (result.next()) {
                map.put(result.getString("fin_source"),result.getString("count"));
            }
            return map;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
