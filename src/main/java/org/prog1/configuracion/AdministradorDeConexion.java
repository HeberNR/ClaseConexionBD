package org.prog1.configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class AdministradorDeConexion {
  public static Connection obtenerConexion() {

    // 4 datos de conexion:
    String dbDriver = "com.mysql.cj.jdbc.Driver";

    // cadena de conexion a mi base de datos
    String dbCadenaDeConexion = "jdbc:mysql://127.0.0.1:3306/progautos";

    // nom usuario
    String dbUsuario = "root";

    // pass bd
    String dbPass = "123456";

    Connection conn = null;

    try {
      Class.forName(dbDriver);

      conn = DriverManager.getConnection(dbCadenaDeConexion, dbUsuario, dbPass);

    } catch (ClassNotFoundException e) {
      System.out.println("No se encontro el driver de la BD");
      throw new RuntimeException(e);
    } catch (SQLException e) {
      System.out.println("No se pudo conectar a la BD");
      throw new RuntimeException(e);
    }

    System.out.println("Conexion exitosa a la BD");
    return conn;
  }
}
