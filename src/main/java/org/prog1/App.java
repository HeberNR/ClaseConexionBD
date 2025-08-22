package org.prog1;

import org.prog1.configuracion.AdministradorDeConexion;
import org.prog1.dao.AutoDAO;
import org.prog1.entities.Auto;
import org.prog1.entities.Marca;

import java.sql.Connection;
import java.util.List;

public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    // Connection miConexion = AdministradorDeConexion.obtenerConexion();

    Auto auto =
        new Auto("AB12356", "Gris", 2020, 12000,
            Marca.Honda, "Civic");

    //guardo en la DB
    AutoDAO autoDAO = new AutoDAO();
    autoDAO.instertarAuto(auto);


    List<Auto> miLista = autoDAO.findAll();
    if (!miLista.isEmpty()) {

      for (Auto auto1 : miLista) {
        System.out.println(auto1.toString());
      }
    }

    Auto autoModificar =
        new Auto("Heber", "Gris", 2020, 12000,
            Marca.Honda, "Civic");
    autoModificar.setIdAuto(8);
    autoDAO.update(autoModificar);

    System.out.println("Auto encontrado: " + autoDAO.getById(8).toString());
    System.out.println("Lista de autos despues de la modificacion:");
    miLista = autoDAO.findAll();
    if (!miLista.isEmpty()) {
      for (Auto auto1 : miLista) {
        System.out.println(auto1.toString());
      }
    }

    /*
    autoDAO.delete(16);
    autoDAO.delete(12);
    autoDAO.delete(17);
    autoDAO.delete(15);
    autoDAO.delete(14);
    */

  }
}
