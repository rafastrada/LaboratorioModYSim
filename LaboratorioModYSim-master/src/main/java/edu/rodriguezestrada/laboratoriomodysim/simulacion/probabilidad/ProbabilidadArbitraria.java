package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author gestrada
 */
public class ProbabilidadArbitraria implements Probabilidad {
    
    private final ArrayList<SimpleEntry<Integer, Double>> densidad_probabilidad;

    public ProbabilidadArbitraria(ArrayList<SimpleEntry<Integer, Double>> densidad_probabilidad) {
        this.densidad_probabilidad = densidad_probabilidad;
    }

    protected Integer obtenerValor(Double numeroAzaroso) {
        Double probabilidad_acumulada = 0.0;
        Integer salida = null;
        
        Iterator<SimpleEntry<Integer, Double>> iterador = this.densidad_probabilidad.iterator();
        while (iterador.hasNext()) {
            // contenedor temporal
            SimpleEntry<Integer, Double> campo = iterador.next();
            probabilidad_acumulada += campo.getValue(); 
            
            if (numeroAzaroso < probabilidad_acumulada) {
                salida = campo.getKey();
                break;
            }
        }
        
        return salida;
    }

    @Override
    public Double generarValor() {
        return this.obtenerValor(Math.random());
    }
}
