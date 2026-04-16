package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Reservation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }

    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservation (ønske_id, bruger_id, dato) VALUES (?, ?, NOW())";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, reservation.getOnskeId());
            stmt.setInt(2, reservation.getBrugerId());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                reservation.setReservationId(keys.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reservation findByOnskeId(int onskeId) {
        String sql = "SELECT * FROM reservation WHERE ønske_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, onskeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("dato");

                return new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getInt("ønske_id"),
                        rs.getInt("bruger_id"),
                        ts != null ? ts.toLocalDateTime() : null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Reservation> findByBrugerId(int brugerId) {
        List<Reservation> liste = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE bruger_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, brugerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("dato");

                liste.add(new Reservation(
                        rs.getInt("reservation_id"),
                        rs.getInt("ønske_id"),
                        rs.getInt("bruger_id"),
                        ts != null ? ts.toLocalDateTime() : null
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    public boolean erReserveret(int onskeId) {
        String sql = "SELECT COUNT(*) FROM reservation WHERE ønske_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, onskeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteByOnskeId(int onskeId) {
        String sql = "DELETE FROM reservation WHERE ønske_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, onskeId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteByOnskeIdAndBrugerId(int onskeId, int brugerId) {
        String sql = "DELETE FROM reservation WHERE ønske_id = ? AND bruger_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, onskeId);
            stmt.setInt(2, brugerId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}