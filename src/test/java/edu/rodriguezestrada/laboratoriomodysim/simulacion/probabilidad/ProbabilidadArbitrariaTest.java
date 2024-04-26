package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author gestrada
 */
public class ProbabilidadArbitrariaTest {
    
    private ProbabilidadArbitraria probabilidades;
    
    public ProbabilidadArbitrariaTest() {
        ArrayList<SimpleEntry<Integer,Double>> vector = new ArrayList();
        
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
        
        assertNull(this.probabilidades.obtenerValor(1.0));
        
        assertNotEquals(4,this.probabilidades.obtenerValor(0.5));
        assertNotEquals(4,this.probabilidades.obtenerValor(0.8));
    }
    
}
