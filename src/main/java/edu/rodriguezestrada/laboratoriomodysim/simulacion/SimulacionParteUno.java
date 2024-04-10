package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Evento;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.ProbabilidadArbitraria;
import java.util.AbstractMap.SimpleEntry;
import java.util.Vector;

/**
 *
 * @author gestrada
 */
public class SimulacionParteUno implements Simulacion {
    private int reloj = 0;
    private boolean enEjecucion;
    private Pista servidor;
    private Fel eventosFuturos;
    
    // tiempo que termina la simulacion
    public final int tiempoFinalizacion = 100;

    public SimulacionParteUno() {
        this.enEjecucion = false;
        this.servidor = new Pista();
        
        this.eventosFuturos = new Fel(this.tiempoFinalizacion);
        
        // se inserta entidad cero
        this.eventosFuturos.add(new Arribo(0,new Avion()));
    }
    
    public void inicializarProbabilidades() {
        
        Vector<SimpleEntry<Integer, Double>> distribucionArribos = new Vector<SimpleEntry<Integer, Double>>();
        distribucionArribos.add(new SimpleEntry<>(10, 0.35));
        distribucionArribos.add(new SimpleEntry<>(15, 0.45));
        distribucionArribos.add(new SimpleEntry<>(17, 0.2));
        
        Arribo.setValoresAzarosos(new ProbabilidadArbitraria(distribucionArribos));
        
        Vector<SimpleEntry<Integer, Double>> distribucionSalidas = new Vector<SimpleEntry<Integer, Double>>();
        distribucionSalidas.add(new SimpleEntry<>(8, 0.38));
        distribucionSalidas.add(new SimpleEntry<>(10, 0.32));
        distribucionSalidas.add(new SimpleEntry<>(13, 0.1));
        distribucionSalidas.add(new SimpleEntry<>(15, 0.2));
        
        Salida.setValoresAzarosos(new ProbabilidadArbitraria(distribucionSalidas));
    }

    @Override
    public void detenerSimulacion() {
        this.enEjecucion = false;
    }
    
    public void iniciarSimulacion() {
        
        // asigna la funciones de densidad de probabilidad y tiempos asociados a los eventos
        this.inicializarProbabilidades();
        
        while (reloj != tiempoFinalizacion){
            Evento eventoInminente = eventosFuturos.remove(0);
            String event = eventoInminente.getClass().getSimpleName();
            Avion item = eventoInminente.getEntidad();
            int time = eventoInminente.getTiempo(); 
            
            
            
            
            switch (event){
                case "Arribo":
                    Arribo arribo = (Arribo) eventoInminente;
                    if (servidor.getAtendiendo() != null){
                        servidor.setAtendiendo(item);
                        int duracion = Salida.calcularDuracion();
                        int tiempoSalida = duracion + time;
                        Salida newSalida = new Salida(tiempoSalida, item);
                        eventosFuturos.add(newSalida);
                    }
                    else{
                        servidor.cola.add(arribo);
                        int tamañoCola = servidor.getCola().size();
                    }
                    int tiempoArribos = Arribo.calcularDuracion();
                    int llegada = time + tiempoArribos;
                    Avion siguienteAvion = new Avion();
                    Arribo newArribo = new Arribo(llegada, siguienteAvion);
                    eventosFuturos.add(newArribo);
                    break;
                
                case "Salida":
                    Salida salida = (Salida) eventoInminente;
                    if (servidor.cola.isEmpty()){
                        servidor.setAtendiendo(null);
                    }
                    else{
                        Arribo atender = servidor.cola.remove();
                        int tiempoSalida = atender.getTiempo() + Salida.calcularDuracion();
                        Salida newSalida = new Salida(tiempoSalida, atender.getEntidad());
                        eventosFuturos.add(newSalida);
                    }
                    break;
            }
            
        }
    }
}
