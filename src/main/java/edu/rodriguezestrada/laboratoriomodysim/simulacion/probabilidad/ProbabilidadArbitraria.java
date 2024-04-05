package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

import java.util.AbstractMap.SimpleEntry;
import java.util.Iterator;
import java.util.Vector;


/**
 *
 * @author gestrada
 */
public class ProbabilidadArbitraria implements Probabilidad {
    
    private final Vector<SimpleEntry<Integer, Double>> densidad_probabilidad;

    public ProbabilidadArbitraria(Vector<SimpleEntry<Integer, Double>> densidad_probabilidad) {
        this.densidad_probabilidad = densidad_probabilidad;
    }

    private Integer obtenerValor(Double numeroAzaroso) {
        Double probabilidad_acumulada = 0.0;
        Integer salida = null;
        
        Iterator<SimpleEntry<Integer, Double>> iterador = this.densidad_probabilidad.iterator();
        while (iterador.hasNext()) {
            // contenedor temporal
            SimpleEntry<Integer, Double> campo = iterador.next();
            
            if (numeroAzaroso >= probabilidad_acumulada &&
                    numeroAzaroso < probabilidad_acumulada + campo.getValue()) {
                salida = campo.getKey();
                break;
            }
            else probabilidad_acumulada += campo.getValue();
        }
        
        return salida;
    }

    @Override
    public Integer generarValor() {
        return this.obtenerValor(Math.random());
    }
}
