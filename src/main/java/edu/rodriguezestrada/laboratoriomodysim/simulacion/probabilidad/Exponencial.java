package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

import java.util.Optional;
import java.util.Random;

/**
 * Clase que implementa una distribución de valores exponencial.
 * @author rodri
 */
public class Exponencial implements Probabilidad {
    private final Double media;  // variable con la inversa de lambda negativa ya calculada
    private final Random random; // generador de numeros aleatorios    

    /**
     * Crea una distribución Exponencial a partir de la media
     * @param media Media de eventos por unidad
     */
    public Exponencial(Double media){
        this.media = media;
        this.random = null;
    }

    /**
     * Crea una distribución Exponencial, con un generador de números aleatorios arbitrario
     * @param media Media de los eventos por unidad
     * @param random Generador de números aleatorios
     */
    public Exponencial(Double media, Random random) {
        this.media = media;
        this.random = random;
    }

    public Double getMedia() {
        return media;
    }

    /**
     * Devuelve el valor Lambda de la distribución.
     * @return Media de la distribución.
     */
    public Double getLambda() {
        return 1/media;
    }

    /**
     * Obtiene el valor asociado a la frecuencia indicada.
     * NOTA: Por la forma de implementacion el valor dado no corresponde a la frecuencia introducida,
     * pero en llamadas sucesivas debe ofrecer la misma distribución.
     * @param random Valor de frecuencia.
     * @return Valor correspondiente a la frecuencia 'random'.
     */
    protected Double obtenerValor(double random){
        return (- this.media) * Math.log(random);
        
        //en muchas iteraciones debería proveer la misma distribucion que:
        //return this.lambdaInversaNeg * Math.log(1 - random);
    }

    /**
     * Genera un valor aleatorio con distribución exponencial.
     * @return Valor de distribución exponencial.
     */
    @Override
    public Double generarValor() {
        return this.obtenerValor(Optional.ofNullable(random)
        .map(Random::nextDouble).orElse(Math.random()));    // si no esta definido el generador, utiliza uno por defecto
    }
}
