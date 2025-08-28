package org.prog1.dao;

import org.prog1.configuracion.AdministradorDeConexion;
import org.prog1.entities.Auto;
import org.prog1.entities.Marca;
import org.prog1.interfaces.AdmConexion;
import org.prog1.interfaces.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutoImpl implements AdmConexion, DAO<Auto, Integer> {
  private Connection conn = null;

  private static final String SQL_INSERT =
      "INSERT INTO autos (patente, color, anio, kilometraje, marca, modelo)" +
          "VALUES (?, ?, ?, ?, ?, ?)";

  private static final String SQL_UPDATE =
      "UPDATE autos SET " +
          "patente = ?" +
          "color = ?" +
          "anio = ?" +
          "kilometraje = ?" +
          "marca = ?" +
          "modelo = ?" +
          "WHERE idAuto = ?";

  private static final String SQL_DELETE = "DELETE FROM autos WHERE idAuto = ?";
  private static final String SQL_GETALL = "SELECT * FROM autos ORDER BY patente";
  private static final String SQL_GETBYID = "SELECT * FROM autos WHERE idAuto = ?";


  @Override
  public List<Auto> getAll() {
    List<Auto> lista = new ArrayList<>();
    return lista;
  }

  @Override
  public void insert(Auto objeto) {

    // 1 establecer conexion a la base de datos
    conn = obtenerConexion();

    // 2 Crear string de consulta SQL
    PreparedStatement pst = null;
    try {
      pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);

      pst.setString(1, objeto.getPatente());
      pst.setString(2, objeto.getColor());
      pst.setInt(3, objeto.getAnio());
      pst.setInt(4, objeto.getKilometraje());
      pst.setString(5, objeto.getMarca().toString());
      pst.setString(6, objeto.getModelo());

      // 3 Ejecutar la instruccion
      int resultado = pst.executeUpdate();
      if (resultado == 1) {
        System.out.println("Auto insertado correctamente");
      } else {
        System.out.println("No se pudo insertar el auto");
      }

      ResultSet rs = pst.getGeneratedKeys();
      if (rs.next()) {
        objeto.setIdAuto(rs.getInt(1));
        System.out.println("El id asignado es el: " + objeto.getIdAuto());
      }

      // 4 Cerrar conexiones
      pst.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public void update(Auto objeto) {
    //establecer la conexion
    conn = obtenerConexion();
    // solo si el auto existe lo modifico
    if (this.existsById(objeto.getIdAuto())) {
      String sql = "UPDATE autos SET " +
          "patente = '" + objeto.getPatente() + "', " +
          "color = '" + objeto.getColor() + "', " +
          "anio = " + objeto.getAnio() + ", " +
          "kilometraje = " + objeto.getKilometraje() + ", " +
          "marca = '" + objeto.getMarca() + "', " +
          "modelo = '" + objeto.getModelo() + "' " +
          "WHERE idAuto = " + objeto.getIdAuto();
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

  @Override
  public void delete(Integer id) {
    conn = AdministradorDeConexion.obtenerConexion();
    String sql = "DELETE FROM autos WHERE idAuto = " + id;
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


  @Override
  public Auto getById(Integer id) {
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

  @Override
  public boolean existsById(Integer id) {
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

      //CIERROTODO LO CREADO
      rs.close();
      st.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }
}
