package co.schrom.card;

import co.schrom.database.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageService implements PackageServiceInterface {
    private static PackageService instance;

    private PackageService() {

    }

    public static PackageService getInstance() {
        if (PackageService.instance == null) {
            PackageService.instance = new PackageService();
        }
        return PackageService.instance;
    }

    @Override
    public PackageInterface getPackage(int id) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, price FROM packages WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PackageInterface cardPackage = Package.builder()
                        .id(rs.getInt(1))
                        .name(rs.getString(2))
                        .price(rs.getInt(3))
                        .build();
                rs.close();
                ps.close();
                conn.close();

                return cardPackage;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PackageInterface> getPackages() {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id, name, price FROM packages;");

            List<PackageInterface> packages = new ArrayList<>();
            while (rs.next()) {
                packages.add(Package.builder()
                        .id(rs.getInt(1))
                        .name(rs.getString(2))
                        .price(rs.getInt(3))
                        .build());
            }

            rs.close();
            sm.close();
            conn.close();

            return packages;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PackageInterface addPackage(PackageInterface cardPackage) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO packages(name, price) VALUES(?,?);", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cardPackage.getName());
            ps.setInt(2, cardPackage.getPrice());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return this.getPackage(generatedKeys.getInt(1));
                }
            }
            ps.close();
            conn.close();
        } catch (SQLException ignored) {

        }
        return null;
    }

    @Override
    public boolean deletePackage(int id) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM packages WHERE id = ?;");
            ps.setInt(1, id);

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows == 0) {
                return false;
            }

            ps.close();
            conn.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
