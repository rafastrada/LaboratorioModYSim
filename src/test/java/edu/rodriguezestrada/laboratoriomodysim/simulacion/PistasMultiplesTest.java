package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gestrada
 */
public class PistasMultiplesTest {
    
    public PistasMultiplesTest() {
    }

    @Test
    public void testSalidaAvion() {
        PistaDesgastable pistas[] = { new PistaDesgastable(), new PistaDesgastable()};
        
        PistasMultiples aeropuerto = new PistasMultiples(List.of(pistas),new CriterioMasVacia());
        
        Avion avion1 = new Avion();
        
        try {
            aeropuerto.ingresoDeAvion(new Arribo(0.0, new Avion()));
            aeropuerto.ingresoDeAvion(new Arribo(1.0,avion1));
            aeropuerto.ingresoDeAvion(new Arribo(2.0, new Avion()));
            
            //aeropuerto.salidaDeAvion(new Salida(5.0, avion1));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Avion en prueba: " + avion1.toString());
        
        System.out.println("Antes de salida:");
        aeropuerto.forEach((pista)->{
            System.out.println(pista.getAtendiendo());
        });
        
        
        assertDoesNotThrow(() ->{
            aeropuerto.salidaDeAvion(new Salida(5.0, avion1));
            return false;
        });
        
        System.out.println("Despues de salida:");
        aeropuerto.forEach((pista)->{
            System.out.println(pista.getAtendiendo());
        });
        
        assertThrows(RuntimeException.class, ()->{
            aeropuerto.salidaDeAvion(new Salida(5.0, avion1));
        });
    }
    
}
