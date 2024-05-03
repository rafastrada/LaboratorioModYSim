package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Fel;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.ProbabilidadArbitraria;
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
        this.fel = new Fel(100);
    }
    
    @BeforeAll
    public static void prepararProbabilidad() {
        ArrayList<AbstractMap.SimpleEntry<Integer, Double>> probabilidades = new ArrayList<AbstractMap.SimpleEntry<Integer,Double>>();
        
        probabilidades.add(new AbstractMap.SimpleEntry<Integer,Double>(5,1.0));
        
        Arribo.setValoresAzarosos(new ProbabilidadArbitraria(probabilidades));
        Salida.setValoresAzarosos(new ProbabilidadArbitraria(probabilidades));
    }

    @Test
    public void testSomeMethod() {
        Avion avion1 = new Avion();
        //Arribo inicio = new Arribo(0, avion1);
        
        //Avion entidadSalida = inicio.procesarEvento(this.fel, true);
        
        this.fel.ordenarFEL();
        this.fel.forEach((evento) -> {
            System.out.println(evento.toString());
        });
        
        Evento evento1 = this.fel.remove(0), evento2 = this.fel.remove(0);
        
        Assertions.assertTrue(Salida.class.isInstance(evento1));
        Assertions.assertEquals(evento1.getEntidad(),avion1);
        Assertions.assertEquals(evento1.getTiempo(), 5);
        
        Assertions.assertTrue(Arribo.class.isInstance(evento2));
        Assertions.assertEquals(evento2.getTiempo(),5);
    }
    
}
