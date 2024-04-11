package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;

/**
 *
 * @author rodri
 */
public class Salida extends Evento {
    
    public Salida(int tiempo, Avion entidad){
        super(tiempo, entidad);
    }
    
    @Override
    public String toString(){
        return "(S, " + this.entidad + ", " + this.tiempo + ")";
    }
}