package org.prog1.dao;

import org.prog1.configuracion.AdministradorDeConexion;
import org.prog1.entities.Auto;
import org.prog1.entities.Marca;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class AutoDAO {

  private static Connection conn;

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
}
