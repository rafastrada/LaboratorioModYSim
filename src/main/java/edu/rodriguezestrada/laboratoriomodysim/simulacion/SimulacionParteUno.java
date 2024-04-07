package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Evento;

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
        this.eventosFuturos.add(new Arribo(0,new Avion()));
    }
    
    public void iniciarSimulacion() {
        
        while (reloj != tiempoFinalizacion){
            Evento eventoInminente = eventosFuturos.remove(0);
            String event = eventoInminente.getClass().getSimpleName();
            Avion item = eventoInminente.getEntidad();
            int time = eventoInminente.getTiempo();
            
            
            
            switch (event){
                case "Arribo":
                    if (servidor.getAtendiendo() != null){
                        servidor.setAtendiendo(item);
                        Probabilidad tiempoSalida = probabilidad
                        Salida planSalida = new Salida()
                    }
                    else{}
                     
                    break;
                
                case "Salida":
                    break;
            }
            
        }
    }
}
