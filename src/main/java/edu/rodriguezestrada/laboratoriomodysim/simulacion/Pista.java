package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import java.util.ArrayDeque;

/**
 *
 * @author gestrada
 */
public class Pista {
    
    private Avion atendiendo = null;
    protected ArrayDeque<Arribo> cola;

    public ArrayDeque<Arribo> getCola() {
        return cola;
    }


    public Avion getAtendiendo() {
        return atendiendo;
    }

    public Pista() {
        this.cola = new ArrayDeque();
    }

    public void setAtendiendo(Avion atendiendo) {
        this.atendiendo = atendiendo;
    }

}
