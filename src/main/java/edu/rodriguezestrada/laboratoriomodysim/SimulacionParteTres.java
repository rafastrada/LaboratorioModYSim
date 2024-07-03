package edu.rodriguezestrada.laboratoriomodysim;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Estadisticas;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.EstadisticasDesgaste;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 *
 * @author gestrada
 */
public class SimulacionParteTres {
    private final int cantidadReplicaciones;
    
    private final int cantidadPistas;
    private final double tiempoFinalizacion;
    
    private HashMap<String,Double> medias;
    private HashMap<String,Double> desviaciones;

    /**
     * Ejecuta replicas de simulaciones para obtener estadísticas por intervalos de 95% de confianza.
     * @param cantidadReplicaciones Número de replicaciones
     * @param cantidadPistas Cantidad de pistas de la simulación
     */
    public SimulacionParteTres(int cantidadReplicaciones, int cantidadPistas) {
        this.cantidadReplicaciones = cantidadReplicaciones;
        this.cantidadPistas = cantidadPistas;   
        this.tiempoFinalizacion = 60 * 24 * 7 * 4;
        
        this.medias = new HashMap<>();
        this.desviaciones = new HashMap<>();
    }
    
    /**
     * Constructor básico. Realiza 50 replicaciones de la simulación para obtener estadísticas.
     */
    public SimulacionParteTres() {
        this.cantidadReplicaciones = 50;
        this.cantidadPistas = 5;    // cantidad por defecto
        this.tiempoFinalizacion = 60 * 24 * 7 * 4;
        
        this.medias = new HashMap<>();
        this.desviaciones = new HashMap<>();
    }
    
    public void iniciarReplicacion() {
        // arreglo donde se colecta los resultados de cada replica
        ArrayList<EstadisticasDesgaste> estadisticasReplicaciones = new ArrayList<>();
        Random semillas = new Random(LocalTime.now().toNanoOfDay());
        
        for (int i = 0; i < cantidadReplicaciones; i++) {
            // reinicia semilla de generador de numeros aleatorios
            Random generadorAleatorios = new Random(semillas.nextLong());
            
            SimulacionParteDos replica = new SimulacionParteDos(cantidadPistas, 
                    tiempoFinalizacion, 
                    generadorAleatorios, 
                    false);
            
            replica.iniciarSimulacion();
            
            estadisticasReplicaciones.add(replica.getEstadisticasTotal());
        }
        
        // --------------------------------------------------------------------------------
        
        // arribos
        agregarMedidaInt(
                estadisticasReplicaciones,
                "nroArribos",
                EstadisticasDesgaste::getAvionesArribos
        );
        
        // aterrizajes
        agregarMedidaInt(
                estadisticasReplicaciones,
                "nroAterrizajes",
                EstadisticasDesgaste::getAvionesAterrizajes
        );
        
        
        // transito
        //medio
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "transitoMedio",
                EstadisticasDesgaste::getTransitoMedio
        );
        //minimo
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "transitoMinimo",
                EstadisticasDesgaste::getTransitoMinimo
        );
        //maximo
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "transitoMaximo",
                EstadisticasDesgaste::getTransitoMaximo
        );
        
        // espera
        //medio
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "esperaMedio",
                EstadisticasDesgaste::getEsperaMedio
        );
        //minimo
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "esperaMinimo",
                EstadisticasDesgaste::getEsperaMinimo
        );
        //maximo
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "esperaMaximo",
                EstadisticasDesgaste::getEsperaMaximo
        );
        
        // ocio
        //medio
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "ocioTotal",
                EstadisticasDesgaste::getOcioTotalProporcional
        );
        //minimo
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "ocioMinimo",
                EstadisticasDesgaste::getOcioMinimo
        );
        //maximo
        agregarMedidaDouble(
                estadisticasReplicaciones,
                "ocioMaximo",
                EstadisticasDesgaste::getOcioMaximo
        );
        
        // cola de espera
        //minimo
        agregarMedidaInt(
                estadisticasReplicaciones,
                "colaEsperaMinimo",
                EstadisticasDesgaste::getColaTamanioMinimo
        );
        //maximo
        agregarMedidaInt(
                estadisticasReplicaciones,
                "colaEsperaMaximo",
                EstadisticasDesgaste::getColaTamanioMaximo
        );
        
        // desgaste de pistas
        agregarMedidaPista(
                estadisticasReplicaciones.stream()
                        .map(replica->replica.getDesgastePistas())
                        .toList(),
                cantidadPistas
        );
        
        
        // PRESENTACION DE ESTADISTICAS
        System.out.println("-------------- ESTADISTICAS ----------------\n");
        System.out.println("Cantidad de pistas simuladas:\t" + cantidadPistas);
        System.out.println("Número de replicaciones:\t" + cantidadReplicaciones);
        System.out.println("Los valores son intervalos de confianza de 95% de probabilidad\n\n");

        System.out.println("CANTIDAD DE NAVES ATERRIZADAS:\t" + 
            medias.get("nroAterrizajes") + " +- " +
                normDesviacion(desviaciones.get("nroAterrizajes"),
                        cantidadReplicaciones));
        System.out.println("CANTIDAD DE NAVES ARRIBADAS:\t" + 
            medias.get("nroArribos") + " +- " +
                normDesviacion(desviaciones.get("nroArribos"),
                        cantidadReplicaciones)
                );

        System.out.println("\nTIEMPOS EN SISTEMA");
        System.out.println("\n\tMedio:\t" + medias.get("transitoMedio") + " +- " +
                    normDesviacion(desviaciones.get("transitoMedio"),cantidadReplicaciones) +
                "\n\tMaximo:\t" + medias.get("transitoMaximo") + " +- " +
                    normDesviacion(desviaciones.get("transitoMaximo"), cantidadReplicaciones) +
                "\n\tMinimo:\t" + medias.get("transitoMinimo") + " +- " +
                    normDesviacion(desviaciones.get("transitoMinimo"), cantidadReplicaciones)
        );

        System.out.println("\nTIEMPOS DE ESPERA");

        System.out.println("\n\tMedio:\t" + 
                medias.get("esperaMedio") + " +- " +
                        normDesviacion(desviaciones.get("esperaMedio"), cantidadReplicaciones) +
                "\n\tMaximo:\t" + medias.get("esperaMaximo") + " +- " +
                        normDesviacion(desviaciones.get("esperaMaximo"), cantidadReplicaciones) +
                "\n\tMinimo:\t" + medias.get("esperaMinimo") + " +- " +
                        normDesviacion(desviaciones.get("esperaMinimo"), cantidadReplicaciones)
        );

        System.out.println("\nTIEMPOS DE OCIO");

        System.out.println("\n\tTotal:\t" + 
                Estadisticas.cDR((
                        medias.get("ocioTotal")
                )*100,2) + "% +- " +
                Estadisticas.cDR(
                        normDesviacion(desviaciones.get("ocioTotal"), cantidadReplicaciones)
                        *100 , 2
                )
                + "%" + 
                "\n\tMaximo:\t" + medias.get("ocioMaximo") + " +- " +
                        normDesviacion(desviaciones.get("ocioMaximo"), cantidadReplicaciones) +
                "\n\tMinimo:\t" + medias.get("ocioMinimo") + " +- " +
                        normDesviacion(desviaciones.get("ocioMinimo"), cantidadReplicaciones)
        );

        System.out.println("\nTAMAÑO COLA DE ESPERA");
        System.out.println("\n\tMaximo:\t" + medias.get("colaEsperaMaximo") + " +- " +
                normDesviacion(desviaciones.get("colaEsperaMaximo"), cantidadReplicaciones) +
                "\n\tMinimo:\t" + medias.get("colaEsperaMinimo") + " +- " +
                normDesviacion(desviaciones.get("colaEsperaMinimo"), cantidadReplicaciones)
        );

        System.out.println("\nDESGASTE:");
        for (int i = 0; i<cantidadPistas; i++) {
            System.out.println("Pista #" + (i+1) +
                    ":\t" + medias.get("desgastePista" + i) + " +- " +
                    normDesviacion(desviaciones.get("desgastePista" + i), cantidadReplicaciones));
        }
    }
    
    private double normDesviacion(
            double desviacionMuestral,
            int cantidadMuestras
    ) {
        return (1.96 * desviacionMuestral) / Math.sqrt(cantidadMuestras);
    }
    
    private void agregarMedidaPista(List<List<Double>> listasDesgastes,
            int cantidadPistas) {
        for (int i = 0; i < cantidadPistas; i++) {
            final int indice = i;
            final double media = 
                    obtenerMedia(
                    listasDesgastes.stream().mapToDouble(lista->lista.get(indice)));
            final double varianza =
                    obtenerVarianza(
                            listasDesgastes.stream().map(lista->lista.get(indice)).toList(),
                            media);
            
            medias.put("desgastePista" + i, media);
            desviaciones.put("desgastePista" + i, Math.sqrt(varianza));
        }
    }
    
    private void agregarMedidaDouble(ArrayList<EstadisticasDesgaste> datos,
            String clave,
            Function<EstadisticasDesgaste, Double> mapeador) {
        double temporalMedia, temporalVarianza;
        
        temporalMedia = obtenerMedia(datos.stream()
                    .mapToDouble((dato) -> mapeador.apply(dato)));
        temporalVarianza = obtenerVarianza(
                    datos.stream()
                    .map(mapeador).toList(),
                    temporalMedia);
        medias.put(clave, temporalMedia);
        desviaciones.put(clave, Math.sqrt(temporalVarianza));
    }
    
    private void agregarMedidaInt(
            ArrayList<EstadisticasDesgaste> datos,
            String clave,
            Function<EstadisticasDesgaste, Integer> mapeador
    ) {
        double temporalMedia, temporalVarianza;
        
        temporalMedia = obtenerMedia(datos.stream()
                    .mapToDouble((dato) -> mapeador.apply(dato)));
        temporalVarianza = obtenerVarianza(
                    datos.stream()
                    .map(dato->mapeador.apply(dato).doubleValue()).toList(),
                    temporalMedia);
        medias.put(clave, temporalMedia);
        desviaciones.put(clave, Math.sqrt(temporalVarianza));
    }
    
    /**
     * Devuelve la media de un conjunto de números pasado por parámetro.
     * @param datos Conjunto de números
     * @return Media del conjunto de números
     */
    private static double obtenerMedia(DoubleStream datos) {
        //return datos.collect(Collectors.averagingDouble(dato->(Double)dato));
        return datos.average().orElse(0);
    }
    /**
     * Devuelve la varianza de un conjunto de números pasado por parámetro.
     * @param datos Conjunto de números
     * @param datosMedia Media del conjunto de números
     * @return Varianza del conjunto de números
     */
    private static double obtenerVarianza(List<Double> datos, double datosMedia) {
        double sumatoriaCuadrados =
                datos.stream().collect(Collectors.summingDouble(dato->(dato - datosMedia)*(dato - datosMedia)));
        
                
        return sumatoriaCuadrados / datos.stream().count();
    }
}
