package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Fel;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.ProbabilidadEmpirica;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author gestrada
 */
public class ArriboTest {
    Fel fel;
    
    public ArriboTest() {
        this.fel = new Fel(100.0);
    }
    
    @BeforeAll
    public static void prepararProbabilidad() {
        ArrayList<AbstractMap.SimpleEntry<Double, Double>> probabilidades = new ArrayList<AbstractMap.SimpleEntry<Double,Double>>();
        
        probabilidades.add(new AbstractMap.SimpleEntry<Double,Double>(5.0,1.0));
        
        Arribo.setValoresAzarosos(new ProbabilidadEmpirica(probabilidades));
        Salida.setValoresAzarosos(new ProbabilidadEmpirica(probabilidades));
    }

    @Test
    public void testGenerarProximoArriboYSalida() {
        Avion avion1 = new Avion();
        Arribo inicio = new Arribo(0.0, avion1);
        
        inicio.procesarEvento(this.fel, true);
        
        this.fel.ordenarFEL();
        this.fel.forEach((evento) -> {
            System.out.println(evento.toString());
        });
        
        Evento evento1 = this.fel.remove(0), evento2 = this.fel.remove(0);
        
        Assertions.assertTrue(Salida.class.isInstance(evento1));
        Assertions.assertEquals(avion1,evento1.getEntidad());
        Assertions.assertEquals(5.0,evento1.getTiempo());
        
        Assertions.assertTrue(Arribo.class.isInstance(evento2));
        Assertions.assertEquals(5.0,evento2.getTiempo());
    }
    
}
