package co.schrom.card;

import co.schrom.database.DatabaseService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PackageServiceTest {

    static PackageService packageService;

    @BeforeAll
    static void beforeAll() {
        packageService = PackageService.getInstance();
    }

    @BeforeEach
    void beforeEach() {
        // Delete package with id <0 before every test case
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("DELETE FROM packages WHERE id < 0;");
            sm.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get a package by id")
    void testGetPackage() {
        try {
            // arrange
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO packages(id, name, price) VALUES(-1, 'DefaultCardPackage', 5)");

            // act
            Package cardPackage = (Package) packageService.getPackage(-1);

            // cleanup
            sm.executeUpdate("DELETE FROM packages WHERE id = -1;");
            sm.close();
            conn.close();

            // cleanup
            assertNotNull(cardPackage);
            assertEquals("DefaultCardPackage", cardPackage.getName());
            assertEquals(5, cardPackage.getPrice());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Get all packages")
    void testGetPackages() {
        try {
            // arrange
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO packages(id, name, price) VALUES(-1, 'DefaultCardPackage', 5)");
            sm.executeUpdate("INSERT INTO packages(id, name, price) VALUES(-2, 'AnotherDefaultCardPackage', 5)");

            // act
            List<PackageInterface> packages = packageService.getPackages();

            // cleanup
            sm.executeUpdate("DELETE FROM packages WHERE id < 0");
            sm.close();
            conn.close();

            // assert
            assertNotNull(packages);
            assertEquals(2, packages.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Add a package")
    void testAddPackage() {
        try {
            // arrange
            Package newCardPackage = Package.builder()
                    .name("CardPackage")
                    .price(5)
                    .build();

            // act
            Package cardPackage = (Package) packageService.addPackage(newCardPackage);

            // assert
            assertNotNull(cardPackage);
            assertNotNull(cardPackage.getName());

            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, price FROM packages WHERE id = ?;");
            ps.setInt(1, cardPackage.getId());
            ResultSet rs = ps.executeQuery();
            assertTrue(rs.next());
            assertEquals("CardPackage", cardPackage.getName());
            ;
            assertEquals(5, cardPackage.getPrice());

            // cleanup
            ps = conn.prepareStatement("DELETE FROM packages WHERE id = ?;");
            ps.setInt(1, cardPackage.getId());
            ps.executeUpdate();
            ps.close();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Delete a package")
    void testDeletePackage() {
        try {
            // arrange
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            sm.executeUpdate("INSERT INTO packages(id, name, price) VALUES(-1, 'DefaultCardPackage', 5)");

            // act
            boolean result = packageService.deletePackage(-1);
            ResultSet rs = sm.executeQuery("SELECT id FROM packages WHERE id = -1;");

            // assert
            assertTrue(result);
            assertFalse(rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
