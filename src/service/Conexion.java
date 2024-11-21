/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Vehiculo;
/**
 *
 * @author estudiante
 */
public class Conexion {
   private static Connection cnx = null;
   public static Connection obtener() throws SQLException, ClassNotFoundException {
       if(cnx == null){
           try{
               Class.forName("com.mysql.jdbc.Driver");
               cnx = DriverManager.getConnection("jdbc:mysql://localhost/parqueadero", "root", "");
            }catch (SQLException ex){
                throw new SQLException(ex);
            }catch (ClassNotFoundException ex){
                throw new ClassCastException(ex.getMessage());
            }
       }
       return cnx;
   }
   
   public static void cerrar() throws SQLException {
       if (cnx != null){
           cnx.close();
       }
   }
   
   public boolean placaRegistrada(String placa) {
        try {
            String query = "SELECT COUNT(*) FROM registro WHERE placa = ?";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, placa);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
   
   public void agregarVehiculo(Vehiculo vehiculo) {
        try {
            String query = "INSERT INTO registro (placa, tipo, modelo, valor) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setString(1, vehiculo.getPlaca());
            ps.setString(2, vehiculo.getTipo());
            ps.setInt(3, vehiculo.getModelo());
            ps.setDouble(4, vehiculo.getPrecio());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
   public List<Vehiculo> totalizar() {
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        try {
            String query = "SELECT SUM(valor) AS total, COUNT(placa) AS cantidad, tipo FROM registro GROUP BY tipo";
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int cantidad = rs.getInt("cantidad");
                double total = rs.getDouble("total");

                System.out.println("Tipo: " + tipo + " | Cantidad: " + cantidad + " | Total: " + total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaVehiculos;
    }
   public void reiniciar() {
        try {
            String query = "DELETE FROM registro";
            Statement stmt = cnx.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
   }
}

