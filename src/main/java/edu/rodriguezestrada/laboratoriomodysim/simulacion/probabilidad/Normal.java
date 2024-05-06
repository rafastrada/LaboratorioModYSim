package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

/**
 *
 * @author gestrada
 */
public class Normal implements Probabilidad {
    private final Double media, desviacionEstandar;

    /**
     * Cantidad de números aleatorios que se utilizan para generar el valor normal estandarizado
     */
    private static int cantidadAleatorios = 48;

    public static int getCantidadAleatorios() {
        return cantidadAleatorios;
    }

    public static void setCantidadAleatorios(int cantidadAleatorios) {
        Normal.cantidadAleatorios = cantidadAleatorios;
    }
    
    /**
     * Constructor básico. Crea una distribución Normal con media 0 y desviación 0.
     */
    public Normal() {
        this.media = 0.0;
        this.desviacionEstandar = 1.0;
    }
    
    /**
     * Crea una distribución Normal con la media y desviación estandar indicada.
     * @param media Media de la distribución Normal.
     * @param desviacionEstandar Desviación estandar de la distribución Normal.
     */
    public Normal(Double media, Double desviacionEstandar) {
        this.media = media;
        this.desviacionEstandar = desviacionEstandar;
    }
    
    /**
     * Genera un valor con distribución estandarizado.
     * (media 0 y varianza 1)
     * @return Valor normal estandarizado.
     */
    private Double generarValorEstandarizado() {
        // variable acumuladora
        Double acumulador = 0.0;
        
        // suma los numeros aleatorios
        for (int i = 1; i <= Normal.cantidadAleatorios; i++) {
            acumulador += Math.random();
        }
        
        return (acumulador - Normal.cantidadAleatorios/2) / (Normal.cantidadAleatorios/12);
    }

    /**
     * Genera un valor aleatorio con distribución Normal.
     * @return Valor aleatorio Normal.
     */
    @Override
    public Double generarValor() {
        return (this.generarValorEstandarizado() * this.desviacionEstandar) + this.media;
    }
}
