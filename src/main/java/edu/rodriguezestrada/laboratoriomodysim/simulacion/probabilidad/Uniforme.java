package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

/**
 * Distribución de valores con probabilidad Uniforme.
 * @author rodri
 */
public class Uniforme implements Probabilidad {
    private final Double inferior, superior;

    /**
     * Crea una distribución de valores con probabilidad Uniforme indicando los límites del intervalo de los valores.
     * @param inferior Límite inferior.
     * @param superior Límite superior.
     */
    public Uniforme(Double inferior, Double superior){
        this.inferior = inferior;
        this.superior = superior;
    }

    /**
     * Genera el valor asociado a la frecuencia indicada en la distribución.
     * @param random Valor de la frecuencia.
     * @return Valor asociado a 'frecuencia'.
     */
    protected Double obtenerValor(Double random){
        return this.inferior + (this.superior - this.inferior) * random;
    }
    
    /**
     * Genera un valor con distribución Uniforme.
     * @return Valor con distribución Uniforme.
     */
    @Override
    public Double generarValor() {
        return this.obtenerValor(Math.random());
    }
}