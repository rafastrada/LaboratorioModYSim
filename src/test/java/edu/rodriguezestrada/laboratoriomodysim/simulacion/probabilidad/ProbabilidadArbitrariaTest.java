package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

import java.util.AbstractMap.SimpleEntry;
import java.util.Vector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author gestrada
 */
public class ProbabilidadArbitrariaTest {
    
    private ProbabilidadArbitraria probabilidades;
    
    public ProbabilidadArbitrariaTest() {
        Vector<SimpleEntry<Integer,Double>> vector = new Vector();
        
        vector.add(new SimpleEntry<Integer,Double>(4,0.5));
        vector.add(new SimpleEntry<Integer,Double>(6,0.5));
        
        this.probabilidades = new ProbabilidadArbitraria(vector);
    }

    @Test
    @DisplayName("Prueba la correcta obtencion de los valores")
    public void testProbabilidadAcumulada() {
        //fail("The test case is a prototype.");
        
        assertEquals(4, this.probabilidades.obtenerValor(0.0));
        assertEquals(4, this.probabilidades.obtenerValor(0.3));
        assertEquals(6, this.probabilidades.obtenerValor(0.5));
        assertEquals(6, this.probabilidades.obtenerValor(0.6));
        
        assertNotEquals(4,this.probabilidades.obtenerValor(0.5));
        assertNotEquals(4,this.probabilidades.obtenerValor(0.8));
    }
    
}
