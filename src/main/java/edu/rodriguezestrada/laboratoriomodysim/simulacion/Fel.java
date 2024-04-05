package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Evento;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Fin;
import java.util.ArrayList;

/**
 *
 * @author gestrada
 */
public class Fel extends ArrayList<Evento> {
    public Fel(Integer tiempoFinalizacion) {
        super();
        
        // se inicia la fel con el evento 'Fin' incluido
        this.add(new Fin(tiempoFinalizacion));
    }
}
