package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author gestrada
 */
public class AvionTest {
    
    public AvionTest() {
    }

    @BeforeAll
    public static void inicializacion() {
        // vuelve a cero la cantidad de aviones contados por la clase
        Avion.resetCantidadEntidades();
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
    
    @AfterAll
    public static void limpieza() {
        // vuelve la cantidad de aviones contados por la clase a cero
        Avion.resetCantidadEntidades();
    }
}
