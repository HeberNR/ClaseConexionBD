package org.prog1;

import org.prog1.dao.AutoImpl;
import org.prog1.dao.ClienteDAO;
import org.prog1.dao.SeguroDAO;
import org.prog1.entities.Auto;
import org.prog1.entities.Cliente;
import org.prog1.entities.Marca;
import org.prog1.entities.Seguro;

public class App {
  public static void main(String[] args) {

    AutoImpl autoImpl = new AutoImpl();
    ClienteDAO clienteDAO = new ClienteDAO();
    SeguroDAO seguroDAO = new SeguroDAO();

    Cliente cliente1 = new Cliente("Heber", "Ramirez", "3482605057");
    Seguro seguro1 = new Seguro("Contra todo riesgo", 17.500,
        "Patronal Seguros");

    Auto auto1 = new Auto("AB123CD", "Rojo", 2022,
        35143, Marca.Honda, "Civic", cliente1, seguro1);


    clienteDAO.insert(cliente1);
    seguroDAO.insert(seguro1);
    autoImpl.insert(auto1);
  }
}
