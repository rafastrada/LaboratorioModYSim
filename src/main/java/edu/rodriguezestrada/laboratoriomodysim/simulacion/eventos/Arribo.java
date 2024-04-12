package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Fel;

/**
 *
 * @author gestrada
 */
public class Arribo extends Evento {

    public Arribo(int tiempo, Avion entidad) {
        super(tiempo, entidad);
    }

    public void procesarEvento(Fel eventosFuturos, boolean servidorLibre) {
        // genera proximo evento y se introduce en la FEL
        eventosFuturos.add(new Arribo(
                this.tiempo + Arribo.calcularDuracion(),
                new Avion()));
        
        // si el servidor esta libre, se crea la salida para esta instancia
        if (servidorLibre) eventosFuturos.add(
                    new Salida(this.tiempo + Salida.calcularDuracion(), this.entidad));
        
        eventosFuturos.ordenarFEL();
    }
    
    @Override
    public String toString(){
        return "(A, " + this.entidad + ", " + this.tiempo + ")";
    }
}
