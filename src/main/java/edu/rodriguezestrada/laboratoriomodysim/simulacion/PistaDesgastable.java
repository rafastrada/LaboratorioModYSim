package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Probabilidad;

/**
 * Pista a la cual los arribos le producen desgaste en base a una distribución de probabilidad asignada.
 * 
 * @author gestrada
 */
public class PistaDesgastable extends Pista {
    private double desgaste;
    public static final double desgasteDefault = 3000.0;
    
    private static Probabilidad distribucionValoresDesgaste = null;

    /**
     * Constructor por defecto. Inicia la pista con el valor por defecto de unidades de desgaste.
     */
    public PistaDesgastable() {
        super();
        
        // inicia a la pista con 3000 unidades de desgaste por defecto
        this.desgaste = PistaDesgastable.desgasteDefault;
    }

    /**
     * Obten la distribución de los valores de desgaste producido por un aterrizaje de las pistas.
     * 
     * @return Probabilidad de valores de desgaste.
     */
    public static Probabilidad getDistribucionValoresDesgaste() {
        return distribucionValoresDesgaste;
    }

    /**
     * Asigna la distribucion de los valores de desgaste producido por un aterrizaje a las pistas.
     * 
     * @param distribucionValoresDesgaste Distribucion de los valores de desgaste de las pistas
     */
    public static void setDistribucionValoresDesgaste(Probabilidad distribucionValoresDesgaste) {
        PistaDesgastable.distribucionValoresDesgaste = distribucionValoresDesgaste;
    }

    /**
     * Procesa la llegada de un Avion en la pista. 
     * @param ingresante Avion que arriba a la pista.
     * @param reloj Momento (clock) en el que llega la entidad a la pista.
     * @throws Exception Se produce un error si el valor de reloj ingresado es menor a uno previamente utilizado.
     */
    @Override
    public void ingresoDeAvion(Avion ingresante, Double reloj) throws Exception {
        super.ingresoDeAvion(ingresante, reloj);
        
        this.desgaste -= PistaDesgastable.distribucionValoresDesgaste.generarValor();
    }

    /**
     * Permite obtener el desgaste actual de la pista.
     * @return Desgaste de la pista.
     */
    public double getDesgaste() {
        return desgaste;
    }
}
