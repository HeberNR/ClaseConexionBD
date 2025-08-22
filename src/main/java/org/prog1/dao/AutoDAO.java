package org.prog1.dao;

import org.prog1.configuracion.AdministradorDeConexion;
import org.prog1.entities.Auto;
import org.prog1.entities.Marca;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AutoDAO {

  private static Connection conn;

  public void update(Auto auto) {
    // establecer la conexion
    conn = AdministradorDeConexion.obtenerConexion();
    // solo si el auto existe lo modifico
    if (this.existById(auto.getIdAuto())) {
      //
      String sql = "UPDATE autos SET " +
          "patente = '" + auto.getPatente() + "', " +
          "color = '" + auto.getColor() + "', " +
          "anio = " + auto.getAnio() + ", " +
          "kilometraje = " + auto.getKilometraje() + ", " +
          "marca = '" + auto.getMarca() + "', " +
          "modelo = '" + auto.getModelo() + "' " +
          "WHERE idAuto = " + auto.getIdAuto();
      conn = AdministradorDeConexion.obtenerConexion();
      // se crea un statement
      Statement st = null;

      try {
        st = conn.createStatement();
        st.execute(sql);

        // cierro
        st.close();
        conn.close();
      } catch (SQLException e) {
        System.out.println("Error al crear el statement");
        throw new RuntimeException(e);
      }
    }
  }

  public boolean existById(int id) {
    conn = AdministradorDeConexion.obtenerConexion();
    String sql = "SELECT * FROM autos WHERE idAuto = " + id;
    // se crea un statement
    Statement st = null;
    ResultSet rs = null;
    boolean existe = false;

    try {
      st = conn.createStatement(); // creo statement
      rs = st.executeQuery(sql); // ejecuto consulta
      // Si la consuta devuelve al menos un registro, existe
      if (rs.next()) {
        existe = true;
      }

      // CIERRO TODO SIEMPRE
      rs.close();
      st.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }

  public void instertarAuto(Auto auto) {

    // 1 establecer conexion a la base de datos
    conn = AdministradorDeConexion.obtenerConexion();

    // 2 Crear string de consulta SQL
    String sql =
        "INSERT INTO autos (idAuto, patente, color, anio, kilometraje, marca, modelo)" +
            "VALUES (" + auto.getIdAuto() + "," +
            "'" + auto.getPatente() + "'," +
            "'" + auto.getColor() + "'," +
            auto.getAnio() + "," +
            auto.getKilometraje() + "," +
            "'" + auto.getMarca() + "'," +
            "'" + auto.getModelo() + "')";

    // 3 crear instruccion
    Statement st = null;
    try {
      st = conn.createStatement();

      // 4 ejecutar instruccion
      st.execute(sql);

      // 5 cerrar conexion
      st.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public List<Auto> findAll() {

    conn = AdministradorDeConexion.obtenerConexion();

    String sql = "SELECT * FROM autos order by patente";

    Statement st = null;
    ResultSet rs = null;

    List<Auto> listaAutos = new java.util.ArrayList<>();

    try {
      st = conn.createStatement();
      rs = st.executeQuery(sql);

      while (rs.next()) {
        Auto auto = new Auto();
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setAnio(rs.getInt("anio"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setMarca(Marca.valueOf(rs.getString("marca")));
        auto.setModelo(rs.getString("modelo"));

        listaAutos.add(auto);
      }

      rs.close();
      st.close();
      conn.close();


    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return listaAutos;

  }

  public void delete(int idAuto) {
    conn = AdministradorDeConexion.obtenerConexion();
    String sql = "DELETE FROM autos WHERE idAuto = " + idAuto;
    Statement st = null;
    try {
      st = conn.createStatement(); // creo el statement
      st.execute(sql); // ejecuto la consulta
      st.close(); // cierro statement
      conn.close(); // cierro conexion
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public Auto getById(int id) {
    conn = AdministradorDeConexion.obtenerConexion();
    String sql = "SELECT * FROM autos WHERE idAuto = " + id;
    // se crea un statement
    Statement st = null;
    ResultSet rs = null;
    Auto auto = new Auto();

    try {
      st = conn.createStatement(); // creo statement
      rs = st.executeQuery(sql); // ejecuto consulta
      // Si la consuta devuelve al menos un registro, existe
      if (rs.next()) {
        auto.setIdAuto(rs.getInt("idAuto"));
        auto.setPatente(rs.getString("patente"));
        auto.setColor(rs.getString("color"));
        auto.setMarca(Marca.valueOf(rs.getString("marca")));
        auto.setAnio(rs.getInt("anio"));
        auto.setKilometraje(rs.getInt("kilometraje"));
        auto.setModelo(rs.getString("modelo"));
      }

      // CIERRO TODO SIEMPRE
      rs.close();
      st.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return auto;
  }

}
