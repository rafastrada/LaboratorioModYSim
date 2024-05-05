package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.ProbabilidadArbitraria;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gestrada
 */
public class PistaDesgastableTest {
    private PistaDesgastable pista;
    
    public PistaDesgastableTest() {
        pista = new PistaDesgastable();
        
        pista.setDistribucionValoresDesgaste(
                new ProbabilidadArbitraria(new ArrayList<>(
               List.of(new AbstractMap.SimpleEntry<Double,Double>(10.0,1.0))
                ))
        );
        
    }

    @Test
    public void testDesgaste() {
        assertEquals(3000.0, pista.getDesgaste());
        
        try {
            pista.ingresoDeAvion(new Arribo(0.0, new Avion()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        assertEquals(2990, pista.getDesgaste());
    }
    
}
