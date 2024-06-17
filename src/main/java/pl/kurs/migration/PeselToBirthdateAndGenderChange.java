package pl.kurs.migration;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
public class PeselToBirthdateAndGenderChange implements CustomTaskChange {

    @Override
    public void execute(Database database) throws CustomChangeException {
        try {
            JdbcConnection jdbcConnection = (JdbcConnection) database.getConnection();
            String selectSql = "SELECT id, pesel FROM person WHERE date_of_birth IS NULL";
            try (PreparedStatement selectStatement = jdbcConnection.prepareStatement(selectSql)) {
                ResultSet resultSet = selectStatement.executeQuery();
                while (resultSet.next()) {
                    int personId = resultSet.getInt("id");
                    String pesel = resultSet.getString("pesel");
                    LocalDate birthdate = parsePeselToBirthdate(pesel);
                    String gender = extractGenderFromPesel(pesel);

                    String updateSql = "UPDATE person SET date_of_birth = ?, gender = ? WHERE id = ?";
                    try (PreparedStatement updateStatement = jdbcConnection.prepareStatement(updateSql)) {
                        updateStatement.setDate(1, java.sql.Date.valueOf(String.valueOf(birthdate)));
                        updateStatement.setString(2, gender);
                        updateStatement.setInt(3, personId);
                        updateStatement.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                throw new CustomChangeException("Error processing PESEL numbers.", e);
            }
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    private LocalDate parsePeselToBirthdate(String pesel) {
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));
        if (month >= 81 && month <= 92) {
            year += 1800;
            month -= 80;
        } else if (month >= 1 && month <= 12) {
            year += 1900;
        } else if (month >= 21 && month <= 32) {
            year += 2000;
            month -= 20;
        } else if (month >= 41 && month <= 52) {
            year += 2100;
            month -= 40;
        } else if (month >= 61 && month <= 72) {
            year += 2200;
            month -= 60;
        }
        return LocalDate.of(year, month, day);
    }

    private String extractGenderFromPesel(String pesel) {
        int genderDigit = Integer.parseInt(pesel.substring(9, 10));
        return (genderDigit % 2 == 0) ? "Female" : "Male";
    }


    @Override
    public String getConfirmationMessage() {
        return "PESEL to dateOfBirth and gender migration completed.";
    }

    @Override
    public void setUp() throws SetupException {

    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {

    }

    @Override
    public ValidationErrors validate(Database database) {
        return null;
    }

}
