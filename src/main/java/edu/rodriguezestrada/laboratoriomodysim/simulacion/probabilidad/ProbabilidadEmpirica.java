package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;


/**
 * Distribución de valores Empírica Discreta.
 * @author gestrada
 */
public class ProbabilidadEmpirica implements Probabilidad {
    
    private final ArrayList<SimpleEntry<Double, Double>> densidad_probabilidad;
    private final Random random;

    public ProbabilidadEmpirica(ArrayList<SimpleEntry<Double, Double>> densidad_probabilidad) {
        this.densidad_probabilidad = densidad_probabilidad;
        this.random = null;
    }

    public ProbabilidadEmpirica(ArrayList<SimpleEntry<Double, Double>> densidad_probabilidad, Random random) {
        this.densidad_probabilidad = densidad_probabilidad;
        this.random = random;
    }

    protected Double obtenerValor(Double numeroAzaroso) {
        Double probabilidad_acumulada = 0.0;
        Double salida = null;
        
        Iterator<SimpleEntry<Double, Double>> iterador = this.densidad_probabilidad.iterator();
        while (iterador.hasNext()) {
            // contenedor temporal
            SimpleEntry<Double, Double> campo = iterador.next();
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
        return this.obtenerValor(
                Optional.ofNullable(random).map(Random::nextDouble)
                .orElse(Math.random())
        );
    }
}
