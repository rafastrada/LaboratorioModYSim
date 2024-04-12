package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import java.util.ArrayDeque;
import java.util.Objects;

/**
 *
 * @author gestrada
 */
public class Pista {
    
    private Avion atendiendo = null;
    protected ArrayDeque<Avion> cola;

    public ArrayDeque<Avion> getCola() {
        return cola;
    }


    public Avion getAtendiendo() {
        return atendiendo;
    }

    public Pista() {
        this.cola = new ArrayDeque();
    }

    public boolean isOcupado() {
        return Objects.nonNull(this.atendiendo);
    }
    
    public void ingresoDeAvion(Avion ingresante) {
        // si el servidor no esta ocupado pasa a ser atendido
        if (!this.isOcupado()) this.atendiendo = ingresante;
        // si el servidor esta ocupado, la entidad pasa a la cola de espera
        else this.cola.add(ingresante);
    }
    
    public void salidaDeAvion(Avion saliente) throws Exception {
        // si el avion pasado por parametro es el atendido, este sale
        if (this.atendiendo.equals(saliente)) {
            if (!this.cola.isEmpty()) this.atendiendo = this.cola.remove();
            else this.atendiendo = null;
        } else {
            throw new Exception("ERROR: La entidad indicada no está siendo atendida.");
        }
    }
}