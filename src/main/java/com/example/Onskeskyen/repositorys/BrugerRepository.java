package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Bruger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BrugerRepository {

    private final String dbUrl;
    private final String username;
    private final String password;

    public BrugerRepository(
            @Value("${app.database.host}") String host,
            @Value("${app.database.port}") String port,
            @Value("${app.database.name}") String databaseName,
            @Value("${app.database.username}") String username,
            @Value("${app.database.password}") String password
    ) {
        this.dbUrl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName
                + "?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
        this.username = username;
        this.password = password;
    }

    public List<Bruger> findAll() {
        List<Bruger> brugere = new ArrayList<>();
        String sql = "SELECT * FROM bruger";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("dato");
                LocalDateTime dato = null;

                if (ts != null) {
                    dato = ts.toLocalDateTime();
                }

                Bruger bruger = new Bruger(
                        resultSet.getInt("bruger_id"),
                        resultSet.getString("navn"),
                        resultSet.getString("email"),
                        resultSet.getString("kodeord"),
                        dato
                );

                brugere.add(bruger);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Kunne ikke hente brugere fra databasen", e);
        }

        return brugere;
    }

    public Optional<Bruger> findById(int id) {
        String sql = "SELECT * FROM bruger WHERE bruger_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("dato");
                LocalDateTime dato = null;

                if (ts != null) {
                    dato = ts.toLocalDateTime();
                }

                Bruger bruger = new Bruger(
                        resultSet.getInt("bruger_id"),
                        resultSet.getString("navn"),
                        resultSet.getString("email"),
                        resultSet.getString("kodeord"),
                        dato
                );

                return Optional.of(bruger);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Kunne ikke hente bruger med id " + id, e);
        }

        return Optional.empty();
    }

    public void updateById(int id, String navn, String email, String kodeord) {
        String sql = "UPDATE bruger SET navn = ?, email = ?, kodeord = ? WHERE bruger_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, navn);
            statement.setString(2, email);
            statement.setString(3, kodeord);
            statement.setInt(4, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException("Kunne ikke opdatere bruger med id " + id, e);
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM bruger WHERE bruger_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException("Kunne ikke slette bruger med id " + id, e);
        }
    }

    public void save(Bruger bruger) {
        String sql = "INSERT INTO bruger (navn, email, kodeord, dato) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bruger.getNavn());
            statement.setString(2, bruger.getEmail());
            statement.setString(3, bruger.getKodeord());
            statement.setTimestamp(4, Timestamp.valueOf(bruger.getDato()));

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new IllegalStateException("Kunne ikke gemme bruger med email " + bruger.getEmail(), e);
        }
    }

    public Optional<Bruger> findByEmail(String email) {
        String sql = "SELECT * FROM bruger WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("dato");
                LocalDateTime dato = null;

                if (ts != null) {
                    dato = ts.toLocalDateTime();
                }

                Bruger bruger = new Bruger(
                        resultSet.getInt("bruger_id"),
                        resultSet.getString("navn"),
                        resultSet.getString("email"),
                        resultSet.getString("kodeord"),
                        dato
                );

                return Optional.of(bruger);
            }

        } catch (SQLException e) {
            throw new IllegalStateException("Kunne ikke hente bruger med email " + email, e);
        }

        return Optional.empty();
    }
}

