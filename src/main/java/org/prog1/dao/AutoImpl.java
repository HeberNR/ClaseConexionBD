package org.prog1.dao;

import org.prog1.entities.Auto;
import org.prog1.interfaces.AdmConexion;
import org.prog1.interfaces.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AutoImpl implements AdmConexion, DAO<Auto, Integer> {
  private Connection conn = null;

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
    String sql =
        "INSERT INTO autos (idAuto, patente, color, anio, kilometraje, marca, modelo)" +
            "VALUES (" + objeto.getIdAuto() + "," +
            "'" + objeto.getPatente() + "'," +
            "'" + objeto.getColor() + "'," +
            objeto.getAnio() + "," +
            objeto.getKilometraje() + "," +
            "'" + objeto.getMarca() + "'," +
            "'" + objeto.getModelo() + "')";
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

  @Override
  public void update(Auto objeto) {

  }

  @Override
  public void delete(Integer id) {

  }

  @Override
  public Auto getById(Integer id) {
    return null;
  }

  @Override
  public boolean existsById(Integer id) {
    return false;
  }
}
