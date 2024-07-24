package com.devruso.windCharge.database;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {

    private Connection connection;
    private String host;
    private String database;
    private String username;
    private String password;
    private int port;

    public DatabaseManager(String host, String database, String username, String password, int port) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
        this.port = port;
    }


    public void connect() {
        try {
            // Registra o driver MySQL. Driver mais recente para conexões MySQL.
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conecta-se ao banco de dados MySQL
            String url = "jdbc:mysql://localhost:3306/minecraft";
            connection = DriverManager.getConnection(url, "root", "12345678");
            System.out.println("Conexão com MySQL estabelecida com sucesso!");
            createTablesIfNotExists();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro de conexão com MySQL: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Driver MySQL não encontrado.");
        }
    }

    private void createTablesIfNotExists() {
        try (Statement stmt = connection.createStatement()) {
            // Cria a tabela homes se não existir
            String createHomesTableSQL = "CREATE TABLE IF NOT EXISTS homes ("
                    + "uuid VARCHAR(36), "
                    + "home VARCHAR(255), "
                    + "world VARCHAR(255), "
                    + "x DOUBLE, "
                    + "y DOUBLE, "
                    + "z DOUBLE, "
                    + "yaw FLOAT, "
                    + "pitch FLOAT, "
                    + "PRIMARY KEY (uuid, home))";
            stmt.executeUpdate(createHomesTableSQL);
            System.out.println("Tabelas criadas ou já existentes.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void saveHome(UUID playerUUID, String homeName, Location location) {
        try {
            PreparedStatement ps = connection.prepareStatement("REPLACE INTO homes (uuid, home, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, playerUUID.toString());
            ps.setString(2, homeName);
            ps.setString(3, location.getWorld().getName());
            ps.setDouble(4, location.getX());
            ps.setDouble(5, location.getY());
            ps.setDouble(6, location.getZ());
            ps.setFloat(7, location.getYaw());
            ps.setFloat(8, location.getPitch());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Location getHome(UUID playerUUID, String homeName) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM homes WHERE uuid = ? AND home = ?");
            ps.setString(1, playerUUID.toString());
            ps.setString(2, homeName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Location(
                        Bukkit.getWorld(rs.getString("world")),
                        rs.getDouble("x"),
                        rs.getDouble("y"),
                        rs.getDouble("z"),
                        rs.getFloat("yaw"),
                        rs.getFloat("pitch")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteHome(UUID playerUUID, String homeName) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM homes WHERE uuid = ? AND home = ?");
            ps.setString(1, playerUUID.toString());
            ps.setString(2, homeName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
