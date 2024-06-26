package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Estadisticas;
import java.util.List;

/**
 * Extiende las estadísticas básicas para incluir el nivel de desgaste de una cantidad arbitraria de pistas.
 * @author gestrada
 */
public class EstadisticasDesgaste extends Estadisticas {
    private List<Double> desgastePistas;

    
    public EstadisticasDesgaste() {
        super();
        desgastePistas = null;
    }

    public EstadisticasDesgaste(int avionesArribos,
            int avionesAterrizajes,
            double transitoTotal,
            double transitoMinimo,
            double transitoMaximo,
            double esperaTotal,
            double esperaMinimo,
            double esperaMaximo,
            double ocioTotal,
            double ocioMinimo,
            double ocioMaximo,
            int colaTamanioMinimo,
            int colaTamanioMaximo,
            double tiempoSimulacion,
            List<Double> desgastePistas
    ) {
        super(avionesArribos, avionesAterrizajes, transitoTotal, transitoMinimo, transitoMaximo, esperaTotal, esperaMinimo, esperaMaximo, ocioTotal, ocioMinimo, ocioMaximo, colaTamanioMinimo, colaTamanioMaximo, tiempoSimulacion);
        this.desgastePistas = desgastePistas;
    }
    public List<Double> getDesgastePistas() {
        return desgastePistas;
    }
    
}
