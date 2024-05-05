package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
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
     * Procesa el arribo de un Avion.
     * NOTA: Pista no se encarga de procesar proximos eventos ni manipular la FEL, solo extrae la Entidad y el Reloj del objeto Arribo.
     * 
     * @param ingresante Arribo a la pista de entidad Avion
     * @throws Exception Se produce cuando se ingresa un valor de reloj que es menor que uno ingresado previamente
     */
    @Override
    public void ingresoDeAvion(Arribo ingresante) throws Exception {
        super.ingresoDeAvion(ingresante);
        
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
