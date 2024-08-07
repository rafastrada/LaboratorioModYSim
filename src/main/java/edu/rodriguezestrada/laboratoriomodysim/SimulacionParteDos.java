package edu.rodriguezestrada.laboratoriomodysim;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.CriterioMasSana;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.CriterioMasVacia;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Estadisticas;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.EstadisticasDesgaste;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Fel;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Pista;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.PistaDesgastable;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.PistasMultiples;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Evento;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Fin;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Exponencial;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Normal;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Probabilidad;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Uniforme;
import java.time.LocalTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author gestrada
 */
public class SimulacionParteDos {
    private PistasMultiples servidorMultiple;
    protected Fel eventosFuturos;
    
    // almacena las estadisticas de toda la simulacion
    private EstadisticasDesgaste estadisticasTotal = null;
    
    private boolean imprimirResultados = true;
    
    public Probabilidad distribucionArribosNormal;
    public Probabilidad distribucionSalidas;
    public Probabilidad distribucionArribosHorarioFuerte;
    public Probabilidad distribucionDesgastePista;
    
    private LocalTime reloj;
    
    private ArrayList<SimpleEntry<LocalTime,LocalTime>> horarioFuerte;
    
    /**
     * Tiempo en el que termina la simulacion, en minutos
     */
    public final double tiempoFinalizacion;

    /**
     * Constructor predeterminado.
     * Inicia el servidor multiple con el criterio de selección de Pista Mas Vacia,
     * iniciacion del reloj en cero,
     * y la FEL con el evento de Arribo de la primera entidad en el tiempo cero.
     * 
     * El tiempo de simulación es establecido a 4 semanas durante las 24 horas.
     */
    public SimulacionParteDos(int cantidadPistas) {
        this.tiempoFinalizacion = 60 * 24 * 7 * 4;
        
        // iniciacion servidor multiple
//        this.servidorMultiple = new PistasMultiples(new CriterioMasSana());
        this.servidorMultiple = new PistasMultiples(new CriterioMasVacia());
//        this.servidorMultiple = new PistasMultiples(new CriterioAleatorio());
        
        // se agregan las pistas
        for (int i = 0; i < cantidadPistas; i++)
            this.servidorMultiple.add(new PistaDesgastable());
        
        
        this.eventosFuturos = new Fel(this.tiempoFinalizacion);
        
        // establece el reloj a las 00:00 horas
        this.reloj = LocalTime.MIDNIGHT;
        
        
        // arribo de entidad cero
        this.eventosFuturos.add(new Arribo(0.0, new Avion()));
        eventosFuturos.ordenarFEL();    // se ordena la fel despues de agregar el primer arribo
        
        // establece los horarios de fuerte trafico aereo
        this.setHorarioFuerte();
        
        // establece las distribuciones de probabilidad
        this.inicializarProbabilidades();
    }
    
    /**
     * Se crea la simulación a partir de una cantidad de pistas y tiempo de finalización arbitrarios.
     * @param cantidadPistas Cantidad de pistas de tránsito
     * @param tiempoFinalizacion Tiempo en el que termina la simulación
     * @param generadorAleatorio Generador de números aleatorios
     * @param imprimirResultados Determina si imprime los resultados de la simulación por consola
     */
    public SimulacionParteDos(int cantidadPistas, double tiempoFinalizacion,Random generadorAleatorio, boolean imprimirResultados) {
        this.tiempoFinalizacion = tiempoFinalizacion;
        this.imprimirResultados = imprimirResultados;
        
        // iniciacion servidor multiple
//        this.servidorMultiple = new PistasMultiples(new CriterioAleatorio());
//        this.servidorMultiple = new PistasMultiples(new CriterioMasSana());
        this.servidorMultiple = new PistasMultiples(new CriterioMasVacia());
        
        // se agregan las pistas
        for (int i = 0; i < cantidadPistas; i++)
            this.servidorMultiple.add(new PistaDesgastable());
        
        
        this.eventosFuturos = new Fel(this.tiempoFinalizacion);
        
        // establece el reloj a las 00:00 horas
        this.reloj = LocalTime.MIDNIGHT;
        
        
        // arribo de entidad cero
        this.eventosFuturos.add(new Arribo(0.0, new Avion()));
        eventosFuturos.ordenarFEL();    // se ordena la fel despues de agregar el primer arribo
        
        // establece los horarios de fuerte trafico aereo
        this.setHorarioFuerte();
        
        // establece las distribuciones de probabilidad
        this.inicializarProbabilidades(generadorAleatorio);
    }
    
    /**
     * Funcion que establece los horarios de fuerte tráfico aereo, en el cual cambia la distribución de 
     * los Arribos.
     */
    private void setHorarioFuerte() {
        this.horarioFuerte = new ArrayList<>();
        
        this.horarioFuerte.add(
                new SimpleEntry(LocalTime.of(9, 0), LocalTime.of(13, 0))
        );
        this.horarioFuerte.add(
                new SimpleEntry(LocalTime.of(20, 0), LocalTime.of(23, 0))
        );
    }
    
    /**
     * Establece las distribuciones de valores con las que se ejecutará la simulación.
     * Para los Arribos establece inicialmente la distribución de horario de tráfico normal.
     */
    private void inicializarProbabilidades() {
        // arribos en horarios normales
        this.distribucionArribosNormal = new Exponencial(15.0);
        // arribos en horarios de alto trafico
        this.distribucionArribosHorarioFuerte = new Exponencial(9.0);
        
        // salidas
        this.distribucionSalidas = new Uniforme(10.0, 25.0);
        
        // desgaste de pistas
        this.distribucionDesgastePista = new Normal(5.0,1.0);
        
        // establece las distribuciones
        Arribo.setValoresAzarosos(distribucionArribosNormal);
        Salida.setValoresAzarosos(distribucionSalidas);
        PistaDesgastable.setDistribucionValoresDesgaste(distribucionDesgastePista);
    }
    
    private void inicializarProbabilidades(Random random) {
        // arribos en horarios normales
        this.distribucionArribosNormal = new Exponencial(15.0,random);
        // arribos en horarios de alto trafico
        this.distribucionArribosHorarioFuerte = new Exponencial(9.0,random);
        
        // salidas
        this.distribucionSalidas = new Uniforme(10.0, 25.0, random);
        
        // desgaste de pistas
        this.distribucionDesgastePista = new Normal(5.0,1.0, random);
        
        // establece las distribuciones
        Arribo.setValoresAzarosos(distribucionArribosNormal);
        Salida.setValoresAzarosos(distribucionSalidas);
        PistaDesgastable.setDistribucionValoresDesgaste(distribucionDesgastePista);
    }
    
    /**
     * Indica si el tiempo indicado se encuentra en horario de tráfico fuerte.
     * @param tiempo Tiempo a comparar con horario fuerte.
     * @return Verdadero si 'tiempo' se encuentra en horario de tráfico fuerte. Falso en caso contrario.
     */
    private boolean isInHorarioFuerte(LocalTime tiempo){
        return this.horarioFuerte.stream()  // revisa los intervalos de horario fuerte
                    // compara con cada intervalo del arreglo
                     .anyMatch(periodo -> tiempo.isAfter(periodo.getKey()) && //si es mayor al limite inferior
                            tiempo.isBefore(periodo.getValue())); // si es menor al limite superior
    }
    
    public void iniciarSimulacion() {
          
        if (imprimirResultados) {
            System.out.println("----- INICIO DE SIMULACION -----");
            System.out.println("Tiempo de simulación establecido: " +
                    this.tiempoFinalizacion + " minutos");
        }
        
        
        // captura de primer evento
        Evento eventoInminente = this.eventosFuturos.remove( 0);
        
        // variables para calcular el diferencial de tiempo
        Double tiempoEventoAnterior = eventoInminente.getTiempo();
        Double tiempoDeltaMinutos,tiempoDeltaSegundos;
        
        // mientras el evento extraido no sea de tipo FIN
        while (!Fin.class.isInstance(eventoInminente)) {
            
            // DEBUB BORRAR DESPUES
//            System.out.println(eventoInminente);
//            System.out.println(reloj);
            
            // AJUSTE DE HORARIO
            tiempoDeltaMinutos = eventoInminente.getTiempo() - tiempoEventoAnterior;   // diferencial tiempo
            reloj = reloj.plusMinutes(tiempoDeltaMinutos.longValue());     // suma los minutos al reloj
            tiempoDeltaSegundos = (tiempoDeltaMinutos - tiempoDeltaMinutos.longValue()) * 60;   // calculo de segundos pasados
            reloj = reloj.plusSeconds(tiempoDeltaSegundos.longValue());    // suma los segundos al reloj
            
            // si el horario actual es de mayor trafico aereo se cambia la distribucion de los arribos
            if (this.isInHorarioFuerte(reloj)) {  // si es horario fuerte
                    // y no esta ya establecida la distribucion correspondiente
                    if (!Arribo.getValoresAzarosos().equals(this.distribucionArribosHorarioFuerte))
                        Arribo.setValoresAzarosos(distribucionArribosHorarioFuerte); //se asigna
            }
            // si NO es horario fuerte y no estaba previamente asignada la probabilidad normal
            else if (!Arribo.getValoresAzarosos().equals(distribucionArribosNormal))
                Arribo.setValoresAzarosos(distribucionArribosNormal); //se asigna
            
            // CASO DE ARRIBO
            // ----------------------------------------------------
            if (Arribo.class.isInstance(eventoInminente)) {
                try {
                    // procesa el evento, genera el proximo y lo agrega a la fel
                    Arribo.class.cast(eventoInminente)
                            .procesarEvento(eventosFuturos, servidorMultiple.isAlgunoLibre());
                    // deriva la entidad al servidor
                    servidorMultiple.ingresoDeAvion(Arribo.class.cast(eventoInminente));
                } catch (Exception e) {
                    // imprime errores en terminal
                    System.out.println(e.getMessage());
                }
                
            // CASO DE SALIDA
            // ----------------------------------------------------
            } else if (Salida.class.isInstance(eventoInminente)) {
                try {
                    // se captura la pista donde estaba siendo atendido el avion para poder
                    // generarle al proximo avion su evento de salida correspondiente
                    int indiceDePista = servidorMultiple
                            .indiceAvionAtendiendo(eventoInminente.getEntidad());

                    // ordena la salida de la entidad del servidor/es
                    servidorMultiple.salidaDeAvion(Salida.class.cast(eventoInminente));
                    // procesa la salida, genera la proxima salida y la agrega a la fel
                    Salida.class.cast(eventoInminente)
                            .procesarEvento(eventosFuturos,
                                    servidorMultiple.get(indiceDePista).getAtendiendo());
                } catch (Exception e) {
                    // imprime errores por terminal
                    System.out.println(e.getMessage());
                }
            }
            
            
            // actualiza el momento del evento previo
            tiempoEventoAnterior = eventoInminente.getTiempo();
            
            // captura de siguiente evento
            eventoInminente = this.eventosFuturos.remove(0);
        }
        
        // cierre de pistas, permite calcular el ocio desde la ultima salida hasta el evento de fin
        servidorMultiple.forEach(pista -> pista.cierreOcio(tiempoFinalizacion));
        // inserta en los objetos 'estadisticas' de cada pista el tiempo de simulacion, que permite
        // calcular el ocio como porcentajes
        servidorMultiple.forEach(pista -> pista.getEstadisticasPista().setTiempoSimulacion(tiempoFinalizacion));
        
        
        // imprime estadisticas de todas las pistas
        // DEBUG
//        for (Pista pista : servidorMultiple) {
//            System.out.println("Pista #" + (servidorMultiple.indexOf(pista) + 1) );
//            System.out.println(pista.getEstadisticasPista().toString());
//        }
//        System.out.println("\n\n\n");
        
        // extrae las estadisticas de todas las pistas en una lista
        List<Estadisticas> estdGen = 
                servidorMultiple.stream().map(pista -> pista.getEstadisticasPista()).toList();
        
        // CALCULO DE ESTADISTICAS PARA MULTIPLES SERVIDORES
        this.estadisticasTotal = new EstadisticasDesgaste(
                //arribos
                estdGen.stream().collect(Collectors.summingInt(estd->estd.getAvionesArribos())),
                estdGen.stream().collect(Collectors.summingInt(estd->estd.getAvionesAterrizajes())),
                //transito
                estdGen.stream().collect(Collectors.summingDouble(estd->estd.getTransitoTotal())),
                estdGen.stream().map(estd->estd.getTransitoMinimo())
                        .filter(num->num !=0.0).min(Double::compare).orElse(0.0),
                estdGen.stream().map(estd->estd.getTransitoMaximo()).max(Double::compare).get(),
                //espera
                estdGen.stream().collect(Collectors.summingDouble(estd->estd.getEsperaTotal())),
                estdGen.stream().map(estd->estd.getEsperaMinimo())
                        .filter(num->num !=0.0).min(Double::compare).orElse(0.0), 
                estdGen.stream().map(estd->estd.getEsperaMaximo()).max(Double::compare).get(),
                //ocio
                estdGen.stream().collect(Collectors.averagingDouble(estd->estd.getOcioTotal())),
                estdGen.stream().map(estd->estd.getOcioMinimo())
                        .filter(num->num !=0.0).min(Double::compare).orElse(0.0),
                estdGen.stream().map(estd->estd.getOcioMaximo()).max(Double::compare).get(),
                //cola de espera
                estdGen.stream().map(estd->estd.getColaTamanioMinimo())
                        .filter(num->num !=0).min(Double::compare).orElse(0),
                estdGen.stream().map(estd->estd.getColaTamanioMaximo()).max(Double::compare).get(),
                this.tiempoFinalizacion,
                servidorMultiple.stream()
                        .map(pista->PistaDesgastable.class.cast(pista).getDesgaste())
                        .toList()
        );
        
        // opcion de no imprimir resultados para usar en replicacion de ejecuciones
        if (imprimirResultados) {
            System.out.println("-------------- ESTADISTICAS ----------------");
            System.out.println(" ");

            System.out.println("CANTIDAD DE NAVES ATERRIZADAS: " + 
                estadisticasTotal.getAvionesAterrizajes());
            System.out.println("CANTIDAD DE NAVES ARRIBADAS: " + 
                    estadisticasTotal.getAvionesArribos());

            System.out.println("\nTIEMPOS EN SISTEMA");
            System.out.println("Medio: " + estadisticasTotal.getTransitoMedio() +
                    " | Maximo: " + estadisticasTotal.getTransitoMaximo() +
                    " | Minimo: " + estadisticasTotal.getTransitoMinimo());

            System.out.println("\nTIEMPOS DE ESPERA");

            System.out.println("Medio: " + 
                    estadisticasTotal.getEsperaMedio() +
                    " | Maximo: " + estadisticasTotal.getEsperaMaximo() +
                    " | Minimo: " + estadisticasTotal.getEsperaMinimo());

            System.out.println("\nTIEMPOS DE OCIO");

            System.out.println("Total: " + 
                    Estadisticas.cDR((estadisticasTotal.getOcioTotalProporcional())*100,2)
                    + "%" + 
                    " | Maximo: " + estadisticasTotal.getOcioMaximo() + 
                    " | Minimo: " + estadisticasTotal.getOcioMinimo());

            System.out.println("\nTAMAÑO COLA DE ESPERA");
            System.out.println("Maximo: " + estadisticasTotal.getColaTamanioMaximo() + 
                    " | Minimo: " + estadisticasTotal.getColaTamanioMinimo());

            System.out.println("\nDESGASTE:");
            for (Pista pista : servidorMultiple) {
                System.out.println("Pista #" + (servidorMultiple.indexOf(pista)+1) +
                        ":\t" + PistaDesgastable.class.cast(pista).getDesgaste());
            }
        }
        
    }

    /**
     * Obtener las estadísticas de la simulación con cantidad arbitraria de servidores.
     * @return Objeto con estadísticas de la simulación
     */
    public EstadisticasDesgaste getEstadisticasTotal() {
        return estadisticasTotal;
    }
}
