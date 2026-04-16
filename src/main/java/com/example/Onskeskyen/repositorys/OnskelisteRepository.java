package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Onskeliste;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OnskelisteRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public List<Onskeliste> findByBrugerId(int brugerId) {
        List<Onskeliste> lister = new ArrayList<>();
        String sql = "SELECT * FROM ønskeliste WHERE ejer_bruger_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, brugerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("oprettet_dato");

                Onskeliste liste = new Onskeliste(
                        resultSet.getInt("ønskeliste_id"),
                        resultSet.getInt("ejer_bruger_id"),
                        resultSet.getString("titel"),
                        resultSet.getString("beskrivelse"),
                        resultSet.getBoolean("offentlig"),
                        resultSet.getString("delingslink"),
                        ts != null ? ts.toLocalDateTime() : null
                );

                lister.add(liste);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lister;
    }

    public List<Onskeliste> findAllOffentlige() {
        List<Onskeliste> lister = new ArrayList<>();
        String sql = "SELECT * FROM ønskeliste WHERE offentlig = true";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("oprettet_dato");

                Onskeliste liste = new Onskeliste(
                        resultSet.getInt("ønskeliste_id"),
                        resultSet.getInt("ejer_bruger_id"),
                        resultSet.getString("titel"),
                        resultSet.getString("beskrivelse"),
                        resultSet.getBoolean("offentlig"),
                        resultSet.getString("delingslink"),
                        ts != null ? ts.toLocalDateTime() : null
                );

                lister.add(liste);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lister;
    }

    public Onskeliste findById(int id) {
        String sql = "SELECT * FROM ønskeliste WHERE ønskeliste_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("oprettet_dato");

                return new Onskeliste(
                        resultSet.getInt("ønskeliste_id"),
                        resultSet.getInt("ejer_bruger_id"),
                        resultSet.getString("titel"),
                        resultSet.getString("beskrivelse"),
                        resultSet.getBoolean("offentlig"),
                        resultSet.getString("delingslink"),
                        ts != null ? ts.toLocalDateTime() : null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Onskeliste findByDelingslink(String delingslink) {
        String sql = "SELECT * FROM ønskeliste WHERE delingslink = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, delingslink);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("oprettet_dato");

                return new Onskeliste(
                        resultSet.getInt("ønskeliste_id"),
                        resultSet.getInt("ejer_bruger_id"),
                        resultSet.getString("titel"),
                        resultSet.getString("beskrivelse"),
                        resultSet.getBoolean("offentlig"),
                        resultSet.getString("delingslink"),
                        ts != null ? ts.toLocalDateTime() : null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void save(Onskeliste liste) {
        String sql = "INSERT INTO ønskeliste (ejer_bruger_id, titel, beskrivelse, offentlig, delingslink, oprettet_dato) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, liste.getBrugerId());
            statement.setString(2, liste.getTitel());
            statement.setString(3, liste.getBeskrivelse());
            statement.setBoolean(4, liste.isOffentlig());
            statement.setString(5, liste.getDelingslink());

            if (liste.getOprettetDato() != null) {
                statement.setTimestamp(6, Timestamp.valueOf(liste.getOprettetDato()));
            } else {
                statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            }

            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                liste.setOnskelisteId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateById(int id, String titel, String beskrivelse, boolean offentlig) {
        String sql = "UPDATE ønskeliste SET titel = ?, beskrivelse = ?, offentlig = ? WHERE ønskeliste_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, titel);
            statement.setString(2, beskrivelse);
            statement.setBoolean(3, offentlig);
            statement.setInt(4, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDelingslinkById(int id, String delingslink) {
        String sql = "UPDATE ønskeliste SET delingslink = ? WHERE ønskeliste_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, delingslink);
            statement.setInt(2, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM ønskeliste WHERE ønskeliste_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}