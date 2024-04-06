package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;

/**
 *
 * @author gestrada
 */
public class SimulacionParteUno {
    private int reloj = 0;
    private Pista servidor;
    private Fel eventosFuturos;
    
    // tiempo que termina la simulacion
    public final int tiempoFinalizacion = 100;

    public SimulacionParteUno() {
        this.servidor = new Pista();
        
        this.eventosFuturos = new Fel(this.tiempoFinalizacion);
        
        // se inserta entidad cero
        this.eventosFuturos.add(Arribo(0,new Avion()));
    }
    
    public void iniciarSimulacion() {
        
    }
}
