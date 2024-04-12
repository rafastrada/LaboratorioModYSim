package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gestrada
 */
public class AvionTest {
    
    public AvionTest() {
    }

    @Test
    public void testEquals() {
        Avion avion1 = new Avion(), avion2 = new Avion();
        int cantidadAviones = 2;
        
        System.out.println("Entidad de avion1: " + avion1.getNumeroEntidad());
        System.out.println("Entidad de avion2: " + avion2.getNumeroEntidad());
        
        assertNotEquals(avion1, avion2, "Dos instancias distintas no deberían ser iguales");
        assertEquals(avion1, avion1, "La misma instancia al compararse consigo misma deberían ser iguales");
        assertEquals(cantidadAviones, Avion.getCantidadEntidades(), "La cantidad de aviones instanciados no coincide con lo indicado por la clase");
    }
    
}
