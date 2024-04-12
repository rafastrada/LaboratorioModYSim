package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Evento;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.ProbabilidadArbitraria;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
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
    private Estadistica estadisticas;
    
    // tiempo que termina la simulacion
    public final int tiempoFinalizacion = 40320;

    public SimulacionParteUno() {
        this.enEjecucion = false;
        this.servidor = new Pista();
        
        this.eventosFuturos = new Fel(this.tiempoFinalizacion);
        
        // se inserta entidad cero
        this.eventosFuturos.add(new Arribo(0,new Avion()));
        eventosFuturos.ordenarFEL();
        
        this.estadisticas = new Estadistica();
        
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
    
    /**
     * inicia simulacion para la parte uno
     */
    @Override
    public void iniciarSimulacion() {
        
        // asigna la funciones de densidad de probabilidad y tiempos asociados a los eventos
        this.inicializarProbabilidades();
        
        ArrayList<Integer> tiemposDeServicio = new ArrayList<>();
        ArrayList<Integer> tiemposDeEspera = new ArrayList<>();
        
        while (reloj < tiempoFinalizacion){
            
            Evento eventoInminente = eventosFuturos.remove(0);
            String event = eventoInminente.getClass().getSimpleName();
            Avion item = eventoInminente.getEntidad();
            int momentoDelEvento = eventoInminente.getTiempo();
            reloj = momentoDelEvento;
            
            switch (event){
                case "Arribo":
                    Arribo arribo = (Arribo) eventoInminente;
                    if (servidor.getAtendiendo() == null){
                        servidor.setAtendiendo(item);
                        // finalizar tiempo de ocio numero #
                        int finOcio = reloj;
                        // agregar al arra el tiempo de ocio calculado (reloj-inicioOcio)
                        //int tiempoOcio = finOcio - inicioOcio;
                        int duracion = Salida.calcularDuracion();
                        System.out.println("Salida del " + item + " >>>>>> " + duracion);
                        int tiempoSalida = duracion + momentoDelEvento;
                        Salida newSalida = new Salida(tiempoSalida, item);
                        eventosFuturos.add(newSalida);
                        if (!(tiemposDeServicio.contains(duracion))){
                            tiemposDeServicio.add(duracion);
                        }
                    }
                    else{
                        servidor.cola.add(arribo);
                        
                        // inicio tiempo de espera del arribo # (asociar el arribo al clock)
                        estadisticas.iniciosEspera.add(reloj);

                        //int tamañoCola = servidor.getCola().size();
                    }
                    int tiempoArribos = Arribo.calcularDuracion();
                    System.out.println("tiempo entre arribos: " + tiempoArribos);
                    int llegada = momentoDelEvento + tiempoArribos;
                    Avion siguienteAvion = new Avion();
                    Arribo newArribo = new Arribo(llegada, siguienteAvion);
                    eventosFuturos.add(newArribo);
                    break;
                
                case "Salida":
                    Salida salida = (Salida) eventoInminente;
                    if (servidor.cola.isEmpty()){
                        servidor.setAtendiendo(null);
                        // iniciar tiempo de ocio numero #
                        int inicioOcio = reloj;
                    }
                    else{
                        Arribo atender = servidor.cola.remove();
                        
                        
                        // finalizar tiempo de espera del avion # (reloj-inicioTiempoEspera)
                        int inicioEspera = estadisticas.iniciosEspera.remove();
                        int espera = reloj - inicioEspera;
                        // agregar tiempo de espera al array tiempos de espera
                        estadisticas.tiemposEspera.add(espera);
      
                        servidor.setAtendiendo(atender.getEntidad());
                        int tiempoServis = Salida.calcularDuracion();
                        if (!(tiemposDeServicio.contains(tiempoServis))){
                            tiemposDeServicio.add(tiempoServis);
                        }
                        System.out.println("tiempo del servicio del " + atender.getEntidad() + " >>>>> " + tiempoServis);
                        int tiempoSalida = reloj + tiempoServis;
                        Salida newSalida = new Salida(tiempoSalida, atender.getEntidad());
                        eventosFuturos.add(newSalida);
                    }
                    break;
            }
            eventosFuturos.ordenarFEL();
            System.out.println("PROCESANDO: " + eventoInminente.getClass().getSimpleName());
            System.out.println("RELOJ: " + reloj);
            System.out.println("AVION EN SERVIDOR");
            System.out.println(servidor.getAtendiendo());
            System.out.println("FEL: ");
            System.out.println(eventosFuturos);
            System.out.println("COLA: ");
            System.out.println(servidor.cola);
            System.out.println("---------------------------");
        }
        System.out.println("-------------- ESTADISTICAS ----------------");
        System.out.println(" ");
        System.out.println("CANTIDAD DE AERONAVES QUE HAN ARRIBADO: " + servidor.cola.getLast().getEntidad().getNumeroEntidad());
        int cantidadAterrizados = servidor.getAtendiendo().getNumeroEntidad();
        System.out.println("CANTIDAD DE NAVES ATERRIZADAS: " + cantidadAterrizados);
        
        /*float tiempoMedio = servidor.generarTiempoPromedio(reloj, cantidadAterrizados);*/
        int tiempoMax = Collections.max(tiemposDeServicio);
        int tiempoMin = Collections.min(tiemposDeServicio);
        System.out.println("TIEMPOS EN SISTEMA");
        System.out.println("Medio: " + 0 + " | Maximo: " + tiempoMax + " | Minimo: " + tiempoMin);
        System.out.println("TIEMPOS DE ESPERA");
        int tiempoEsperaMax = Collections.max(estadisticas.tiemposEspera);
        int tiempoEsperaMin = Collections.min(estadisticas.tiemposEspera);
        
        System.out.println("Medio: " + 0 + " | Maximo: " + tiempoEsperaMax + " | Minimo: " + tiempoEsperaMin);
        
    }
}
