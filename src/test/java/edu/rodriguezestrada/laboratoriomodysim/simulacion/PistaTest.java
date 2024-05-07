package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
import java.util.ArrayList;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author gestrada
 */
public class PistaTest {
    public PistaTest() {
    }

    @Test
    public void testEstadisticasIngresoSimple() {
        Pista servidor = new Pista();
        Avion avion = new Avion();
        double t0 = 0, t1 = 10, tdelta = t1 - t0;
        
        try {
            servidor.ingresoDeAvion(new Arribo(t0, avion));
            servidor.salidaDeAvion(new Salida(t1, avion));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        Estadisticas d = servidor.getEstadisticasPista();
        
        assertEquals(d.getAvionesArribos(), 1);
        assertEquals(d.getAvionesAterrizajes(), 1);
        assertEquals(d.getTransitoMaximo(), tdelta);
        assertEquals(d.getTransitoMinimo(), tdelta);
        
        assertEquals(d.getColaTamanioMaximo(), 0);
        //assertEquals(d.getColaTamanioMinimo(), 0);
    }
    
    @Test
    public void testEstadisticasBalanceado() {
        // test sin espera ni ocio
        Pista servidor = new Pista();
        ArrayList<Avion> aviones = new ArrayList<>();
        double reloj = 0; int cantidad = 10;
        
        for (int i = 0; i < cantidad; i++) aviones.add(new Avion());
        
        Iterator<Avion> iterador = aviones.iterator();
        try {
            while (iterador.hasNext()) {
                Avion avion = iterador.next();
                servidor.ingresoDeAvion(new Arribo(reloj, avion));
                reloj += 10;
                servidor.salidaDeAvion(new Salida(reloj, avion));
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        Estadisticas d = servidor.getEstadisticasPista();
        
        assertEquals(d.getAvionesArribos(), cantidad);
        assertEquals(d.getAvionesAterrizajes(), cantidad);
        
        assertEquals(d.getTransitoMedio(), 10.0);
        assertEquals(d.getTransitoMaximo(), 10);
        assertEquals(d.getTransitoMinimo(), 10);
    }
    
    @Test
    public void testEstadisticasEspera() {
        // test sin ocio
        Pista servidor = new Pista();
        double reloj = 0;
        Avion aviones[] = { new Avion(), new Avion(), new Avion(), new Avion()};
        
        try {
            servidor.ingresoDeAvion(new Arribo(reloj, aviones[0]));
            reloj += 5;
            servidor.ingresoDeAvion(new Arribo(reloj, aviones[1]));
            reloj += 10;
            servidor.salidaDeAvion(new Salida(reloj, aviones[0]));
            reloj += 5;
            servidor.ingresoDeAvion(new Arribo(reloj, aviones[2]));
            reloj += 5;
            servidor.ingresoDeAvion(new Arribo(reloj, aviones[3]));
            reloj += 5;
            servidor.salidaDeAvion(new Salida(reloj, aviones[1]));
            reloj += 5;
            servidor.salidaDeAvion(new Salida(reloj, aviones[2]));
            reloj += 5;
            servidor.salidaDeAvion(new Salida(reloj, aviones[3]));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        Estadisticas d = servidor.getEstadisticasPista();
        double transitoMedioEsperado = reloj / aviones.length;
        
        assertEquals(d.getAvionesArribos(), aviones.length);
        assertEquals(d.getAvionesAterrizajes(), aviones.length);
        
        assertEquals(transitoMedioEsperado, d.getTransitoMedio());
        assertEquals(15, d.getTransitoMaximo());
        assertEquals(5, d.getTransitoMinimo());
        
        System.out.println(d.getEsperaMedio());
        assertEquals(10.0, d.getEsperaMedio());
        assertEquals(10.0, d.getEsperaMaximo());
        assertEquals(10.0, d.getEsperaMinimo());
        
        assertEquals(2, d.getColaTamanioMaximo());
        assertEquals(1, d.getColaTamanioMinimo());
    }
    
    @Test
    public void testEstadisticasOcio() {
        // test de ocio, sin espera
        Pista servidor = new Pista();
        double reloj = 0;
        Avion aviones[] = { new Avion(), new Avion(), new Avion()};
        
        try {
            servidor.ingresoDeAvion(new Arribo(reloj, aviones[0]));
            reloj += 10;
            servidor.salidaDeAvion(new Salida(reloj, aviones[0]));
            reloj += 5;
            servidor.ingresoDeAvion(new Arribo(reloj, aviones[1]));
            reloj += 10;
            servidor.salidaDeAvion(new Salida(reloj, aviones[1]));
            reloj += 5;
            servidor.ingresoDeAvion(new Arribo(reloj, aviones[2]));
            reloj += 10;
            servidor.salidaDeAvion(new Salida(reloj, aviones[2]));
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        Estadisticas d = servidor.getEstadisticasPista();
        
        assertEquals(d.getAvionesArribos(), aviones.length);
        assertEquals(d.getAvionesAterrizajes(), aviones.length);
        
        assertEquals(d.getTransitoMedio(), 10.0);
        assertEquals(d.getTransitoMaximo(), 10);
        assertEquals(d.getTransitoMinimo(), 10);
        
        assertEquals(10, d.getOcioTotalNominal());
        assertEquals(5, d.getOcioMaximo());
        assertEquals(5, d.getOcioMinimo());
        
        assertEquals(0, d.getColaTamanioMaximo());
        assertEquals(0, d.getColaTamanioMinimo());
    }
}
