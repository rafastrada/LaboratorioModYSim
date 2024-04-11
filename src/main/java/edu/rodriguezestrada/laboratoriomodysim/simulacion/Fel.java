package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Evento;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Fin;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
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
    
    public void ordenarFEL() {
        int n = this.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Evento event1 = this.get(j);
                Evento event2 = this.get(j + 1);
                if (event1.getTiempo() > event2.getTiempo() ||
                        (event1.getTiempo() == event2.getTiempo() &&
                                event1.getClass().equals(Arribo.class) && event2.getClass().equals(Salida.class))) {
                    this.set(j, event2);
                    this.set(j + 1, event1);
                }
                
            }
        }
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
    

