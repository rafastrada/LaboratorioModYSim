package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import java.util.ArrayList;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author gestrada
 */
public class EventoTest {
    
    public EventoTest() {
    }

    @Test
    @DisplayName("Tests de comparación de Eventos")
    public void testComparacion() throws Exception {
        Evento.Comparador comparador = new Evento.Comparador();
        int expResult = -1;
        
        int result = comparador.compare(new Arribo(1.0, null), new Arribo(2.0, null));
        assertEquals(expResult, result, "Los arribos no se ordenan correctamente por tu reloj");
        
        result = comparador.compare(new Salida(1.0, null), new Salida(2.0, null));
        assertEquals(expResult, result,"Las salidas no se ordenan correctamente por su reloj");
        
        result = comparador.compare(new Salida(1.0, null), new Arribo(1.0, null));
        assertEquals(expResult, result,"Los eventos no se ordenan correctamente por su clase");
        //fail("La comparacion no es correcta.");
    }
    
    @Test
    @DisplayName("Test de ordenación de Eventos en una colección")
    public void testOrdenacion() {
//        EventoImpl comparador = new EventoImpl();
        
        ArrayList<Evento> lista = new ArrayList<Evento>();
        
        lista.add(new Fin(10.0));
        lista.add(new Arribo(10.0,null));
        lista.add(new Arribo(15.0,null));
        lista.add(new Arribo(5.0,null));
        lista.add(new Arribo(0.0,null));
        lista.add(new Salida(10.0,null));
        lista.add(new Salida(8.0,null));
        lista.add(new Salida(0.0,null));
        
        // se ordena
        lista.sort(new Evento.Comparador());
        
        Iterator<Evento> iterador = lista.iterator();
        Evento visor;
        while (iterador.hasNext()) {
            visor = iterador.next();
            
            System.out.println(visor.toString());
        }
        
        
        
        assertTrue(true);
    }

    public class EventoImpl extends Evento {

        public EventoImpl() {
            super(0.0, null);
        }

        public EventoImpl(double tiempo) {
            super(tiempo, null);
        }
    }
    
}
