package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Onske;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OnskeRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public List<Onske> hentOnsker(int brugerId) {
        List<Onske> onsker = new ArrayList<>();

        String sql = """
            SELECT ø.ønske_id, ø.ønskeliste_id, ø.navn, ø.beskrivelse, ø.produkt_link, ø.pris, ø.billede_link, ø.købt
            FROM ønske ø
            JOIN ønskeliste l ON ø.ønskeliste_id = l.ønskeliste_id
            WHERE l.ejer_bruger_id = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, brugerId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Onske onske = new Onske(
                        rs.getInt("ønske_id"),
                        rs.getInt("ønskeliste_id"),
                        rs.getString("navn"),
                        rs.getString("beskrivelse"),
                        rs.getString("produkt_link"),
                        rs.getDouble("pris"),
                        rs.getString("billede_link"),
                        rs.getBoolean("købt")
                );

                onsker.add(onske);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return onsker;
    }

    public List<Onske> hentOnskerForListe(int onskelisteId) {
        List<Onske> onsker = new ArrayList<>();

        String sql = """
            SELECT ønske_id, ønskeliste_id, navn, beskrivelse, produkt_link, pris, billede_link, købt
            FROM ønske
            WHERE ønskeliste_id = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, onskelisteId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Onske onske = new Onske(
                        rs.getInt("ønske_id"),
                        rs.getInt("ønskeliste_id"),
                        rs.getString("navn"),
                        rs.getString("beskrivelse"),
                        rs.getString("produkt_link"),
                        rs.getDouble("pris"),
                        rs.getString("billede_link"),
                        rs.getBoolean("købt")
                );

                onsker.add(onske);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return onsker;
    }

    public Onske findById(int onskeId) {
        String sql = """
            SELECT ønske_id, ønskeliste_id, navn, beskrivelse, produkt_link, pris, billede_link, købt
            FROM ønske
            WHERE ønske_id = ?
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, onskeId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Onske(
                        rs.getInt("ønske_id"),
                        rs.getInt("ønskeliste_id"),
                        rs.getString("navn"),
                        rs.getString("beskrivelse"),
                        rs.getString("produkt_link"),
                        rs.getDouble("pris"),
                        rs.getString("billede_link"),
                        rs.getBoolean("købt")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void opretOnske(int brugerId, String navn, String beskrivelse, String link, double pris, String billede) {
        String findListeSql = "SELECT ønskeliste_id FROM ønskeliste WHERE ejer_bruger_id = ? LIMIT 1";
        String opretListeSql = "INSERT INTO ønskeliste (ejer_bruger_id, titel, beskrivelse, offentlig, delingslink, oprettet_dato) VALUES (?, ?, ?, ?, ?, NOW())";
        String opretOnskeSql = "INSERT INTO ønske (ønskeliste_id, navn, beskrivelse, produkt_link, pris, billede_link, købt, dato) VALUES (?, ?, ?, ?, ?, ?, false, NOW())";

        try (Connection connection = getConnection()) {

            int ønskelisteId = -1;

            try (PreparedStatement statement = connection.prepareStatement(findListeSql)) {
                statement.setInt(1, brugerId);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    ønskelisteId = rs.getInt("ønskeliste_id");
                }
            }

            if (ønskelisteId == -1) {
                try (PreparedStatement statement = connection.prepareStatement(opretListeSql, Statement.RETURN_GENERATED_KEYS)) {
                    statement.setInt(1, brugerId);
                    statement.setString(2, "Min ønskeliste");
                    statement.setString(3, null);
                    statement.setBoolean(4, true);
                    statement.setString(5, null);
                    statement.executeUpdate();

                    ResultSet keys = statement.getGeneratedKeys();
                    if (keys.next()) {
                        ønskelisteId = keys.getInt(1);
                    }
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(opretOnskeSql)) {
                statement.setInt(1, ønskelisteId);
                statement.setString(2, navn);
                statement.setString(3, beskrivelse);
                statement.setString(4, link);
                statement.setDouble(5, pris);
                statement.setString(6, billede);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void opretOnskeTilListe(int onskelisteId, String navn, String beskrivelse, String link, double pris, String billede) {
        String sql = """
            INSERT INTO ønske (ønskeliste_id, navn, beskrivelse, produkt_link, pris, billede_link, købt, dato)
            VALUES (?, ?, ?, ?, ?, ?, false, NOW())
        """;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, onskelisteId);
            statement.setString(2, navn);
            statement.setString(3, beskrivelse);
            statement.setString(4, link);
            statement.setDouble(5, pris);
            statement.setString(6, billede);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markAsKobt(int onskeId) {
        String sql = "UPDATE ønske SET købt = true WHERE ønske_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, onskeId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markAsNotKobt(int onskeId) {
        String sql = "UPDATE ønske SET købt = false WHERE ønske_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, onskeId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean erKobt(int onskeId) {
        String sql = "SELECT købt FROM ønske WHERE ønske_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, onskeId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("købt");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteOnskeById(int onskeId) {
        String sql = "DELETE FROM ønske WHERE ønske_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, onskeId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}