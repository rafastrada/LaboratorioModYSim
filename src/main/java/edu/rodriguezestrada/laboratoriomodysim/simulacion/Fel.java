package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Evento;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Fin;
import java.util.ArrayList;

/**
 *
 * @author gestrada
 */
public class Fel extends ArrayList<Evento> {
    public Fel(Double tiempoFinalizacion) {
        super();
        
        // se inicia la fel con el evento 'Fin' incluido
        this.add(new Fin(tiempoFinalizacion));
    }
    
    public void ordenarFEL() {
        this.sort(new Evento.Comparador());
    }
    
    public void verFEL(){
        for (int i = 0; i < this.size(); i++){
            Evento event = this.get(i);
            System.out.println(event);
            if (i < this.size() -1){
                System.out.println(" | ");
            }
        }
    }
}

