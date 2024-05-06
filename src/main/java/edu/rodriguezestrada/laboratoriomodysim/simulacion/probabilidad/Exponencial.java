package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

/**
 * Clase que implementa una distribución de valores exponencial.
 * @author rodri
 */
public class Exponencial implements Probabilidad {
    private final Double media;  // variable con la inversa de lambda negativa ya calculada

    /**
     * Crea una distribución Exponencial a partir de la media
     * @param media Media de eventos por unidad
     */
    public Exponencial(Double media){
        this.media = media;
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
        return this.obtenerValor(Math.random());
    }
}
