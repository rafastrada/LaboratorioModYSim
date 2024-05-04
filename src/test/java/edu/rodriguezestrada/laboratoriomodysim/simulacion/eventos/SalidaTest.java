package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Fel;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.ProbabilidadArbitraria;
import java.util.AbstractMap;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author gestrada
 */
public class SalidaTest {
    private Fel fel;
    public SalidaTest() {
        this.fel = new Fel(100.0);
        
        ArrayList<AbstractMap.SimpleEntry<Double,Double>> probabilidades = new ArrayList<>();
        
        probabilidades.add(new AbstractMap.SimpleEntry<>(5.0,1.0));
        
        Salida.setValoresAzarosos(new ProbabilidadArbitraria(probabilidades));
    }

    @Test
    public void testProximaSalida() {
        Avion avion1 = new Avion();
        Salida anterior = new Salida(5.0, null);
        int tiempoDeProximo = 10;
        
        anterior.procesarEvento(this.fel, avion1);
        
        this.fel.forEach((evento)->{
            System.out.println(evento.toString());
        });
        
        Evento proximo = this.fel.remove(0);
        
        assertTrue(Salida.class.isInstance(proximo));
        assertEquals(proximo.getTiempo(), tiempoDeProximo);
        assertEquals(proximo.getEntidad(), avion1);
    }
    
}
