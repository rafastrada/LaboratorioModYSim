package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;

/**
 *
 * @author gestrada
 */
public class Fin extends Evento {

    public Fin(Double tiempoFinalizacion) {
        super(tiempoFinalizacion, null);
    }
    
    @Override
    public String toString(){
        return "(F, -1" + ", " + tiempo + ")";
    }
}
