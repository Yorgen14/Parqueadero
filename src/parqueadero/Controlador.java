/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero;
import model.Vehiculo;
import service.Conexion;
/**
 *
 * @author varga
 */
public class Controlador {
    public class ParqueaderoController {
    private Conexion db;

    public ParqueaderoController() {
        db = new Conexion();
    }

    public void agregarVehiculo(String placa, String tipo, int modelo) throws Exception {
        if (db.placaRegistrada(placa)) {
            throw new Exception("La placa ya está registrada.");
        }

      
        if ((tipo.equals("carro") && placa.length() != 6) || (tipo.equals("moto") && placa.length() != 5)) {
            throw new Exception("Formato de placa incorrecto.");
        }

      o
        double precio;
        if (tipo.equals("carro")) {
            if (modelo < 2012) {
                precio = 2000;
            } else if (modelo >= 2012) {
                precio = 2500;
            } else {
                precio = 2500 * 1.20; 
            }
        } else {
            if (modelo < 2012) {
                precio = 1000;
            } else if (modelo >= 2012) {
                precio = 1200;
            } else {
                precio = 1200 * 1.10; 
            }
        }

        Vehiculo vehiculo = new Vehiculo(placa, tipo, modelo, precio);
        db.agregarVehiculo(vehiculo);
    }

    public void totalizar() {
        db.totalizar();
    }

    public void reiniciar() {
        db.reiniciar();
    }
}

}
