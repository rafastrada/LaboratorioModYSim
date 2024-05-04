package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Evento;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Fin;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.ProbabilidadArbitraria;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

/**
 *
 * @author gestrada
 */
public class SimulacionParteUno {
    private Pista servidor;
    protected Fel eventosFuturos;
    
    // tiempo que termina la simulacion
    public final double tiempoFinalizacion = 
            60 * 24 * 7 * 4;
            //100;  //DEBUG

    public SimulacionParteUno() {
        this.servidor = new Pista();
        
        this.eventosFuturos = new Fel(this.tiempoFinalizacion);
        
        // se inserta entidad cero
        this.eventosFuturos.add(new Arribo(0.0,new Avion()));
        eventosFuturos.ordenarFEL();
    }
    
    public void inicializarProbabilidades() {
        
        ArrayList<SimpleEntry<Double, Double>> distribucionArribos = new ArrayList<SimpleEntry<Double, Double>>();
        distribucionArribos.add(new SimpleEntry<>(10.0, 0.35));
        distribucionArribos.add(new SimpleEntry<>(15.0, 0.45));
        distribucionArribos.add(new SimpleEntry<>(17.0, 0.2));
        
        Arribo.setValoresAzarosos(new ProbabilidadArbitraria(distribucionArribos));
        
        ArrayList<SimpleEntry<Double, Double>> distribucionSalidas = new ArrayList<SimpleEntry<Double, Double>>();
        distribucionSalidas.add(new SimpleEntry<>(8.0, 0.38));
        distribucionSalidas.add(new SimpleEntry<>(10.0, 0.32));
        distribucionSalidas.add(new SimpleEntry<>(13.0, 0.1));
        distribucionSalidas.add(new SimpleEntry<>(15.0, 0.2));
        
        Salida.setValoresAzarosos(new ProbabilidadArbitraria(distribucionSalidas));
    }

    /**
     * inicia simulacion para la parte uno
     */
    public void iniciarSimulacion() {
        
        // asigna la funciones de densidad de probabilidad y tiempos asociados a los eventos
        this.inicializarProbabilidades();
        
        System.out.println("----- INICIO DE SIMULACION -----");
        System.out.println("Tiempo de simulación establecido: " +
                this.tiempoFinalizacion + " minutos");
        
        // captura de primer evento
        Evento eventoInminente = this.eventosFuturos.remove(0);
        
        // mientras el evento extraido no sea de tipo FIN
        while (!Fin.class.isInstance(eventoInminente)){
            
            // DEBUG
            //System.out.println(eventoInminente.toString());
            
            // Caso de ARRIBO
            // -----------------------------------------------------------------------------
            if (Arribo.class.isInstance(eventoInminente)) {
                try {
                    Arribo.class.cast(eventoInminente)
                            .procesarEvento(this.eventosFuturos, !servidor.isOcupado());
                    servidor.ingresoDeAvion(
                            eventoInminente.getEntidad(),
                            eventoInminente.getTiempo());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else
                // Caso de SALIDA
                // -----------------------------------------------------------------------------
                if (Salida.class.isInstance(eventoInminente)) {
                    try {
                        servidor.salidaDeAvion(
                                eventoInminente.getEntidad(),
                                eventoInminente.getTiempo());
                        Salida.class.cast(eventoInminente)
                                .procesarEvento(this.eventosFuturos, servidor.getAtendiendo());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            
            
            // captura de siguiente evento
            eventoInminente = this.eventosFuturos.remove(0);
            
//            System.out.println("PROCESANDO: " + eventoInminente.getClass().getSimpleName());
//            System.out.println("RELOJ: " + reloj);
//            System.out.println("AVION EN SERVIDOR");
//            System.out.println(servidor.getAtendiendo());
//            System.out.println("FEL: ");
//            System.out.println(eventosFuturos);
//            System.out.println("COLA: ");
//            System.out.println(servidor.cola);
//            System.out.println("---------------------------");
        }
        
        Estadisticas resultados = servidor.getEstadisticasPista();
        
        System.out.println("-------------- ESTADISTICAS ----------------");
        System.out.println(" ");
        //System.out.println("CANTIDAD DE AERONAVES QUE HAN ARRIBADO: " + servidor.cola.getLast().getEntidad().getNumeroEntidad());
        System.out.println("CANTIDAD DE NAVES ATERRIZADAS: " + resultados.getAvionesAterrizajes());
        System.out.println("CANTIDAD DE NAVES ARRIBADAS: " + resultados.getAvionesArribos());
        /*float tiempoMedio = servidor.generarTiempoPromedio(reloj, cantidadAterrizados);*/
        
        System.out.println("TIEMPOS EN SISTEMA");
        System.out.println("Medio: " + resultados.getTransitoMedio() +
                " | Maximo: " + resultados.getTransitoMaximo() +
                " | Minimo: " + resultados.getTransitoMinimo());
        
        System.out.println("TIEMPOS DE ESPERA");
        
        System.out.println("Medio: " + resultados.getEsperaMedio() +
                " | Maximo: " + resultados.getEsperaMaximo() +
                " | Minimo: " + resultados.getEsperaMinimo());
        
        System.out.println("TIEMPOS DE OCIO");
        
        System.out.println("Total: " + (String.format("%.2f",resultados.getOcioTotalProporcional(this.tiempoFinalizacion)*100)) + "%" + 
                " | Maximo: " + resultados.getOcioMaximo() + 
                " | Minimo: " + resultados.getOcioMinimo());
        
        System.out.println("TAMAÑO COLA DE ESPERA");
        System.out.println("Maximo: " + resultados.getColaTamanioMaximo() + 
                " | Minimo: " + resultados.getColaTamanioMinimo());
        
    }
}
