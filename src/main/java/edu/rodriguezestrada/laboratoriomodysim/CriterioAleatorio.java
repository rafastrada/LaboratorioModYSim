package edu.rodriguezestrada.laboratoriomodysim;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.CriterioSeleccionPista;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.PistaDesgastable;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.PistasMultiples;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author gestrada
 */
public class CriterioAleatorio implements CriterioSeleccionPista {

    public CriterioAleatorio() {
    }
    
    @Override
    public PistaDesgastable pistaEncargada(PistasMultiples aeropuerto) {
        Iterator<PistaDesgastable> iterador = aeropuerto.iterator();
        PistaDesgastable salida = null;
        List<PistaDesgastable> libres = aeropuerto.stream().filter(pista->!pista.isOcupado()).toList();
        int cantidadLibres = libres.size();
        
        if (cantidadLibres != 0) {
            Random generador = new Random();
            salida = libres.get(generador.nextInt(cantidadLibres));
        }
        else {
            // se recorre las pistas disponibles
            while (iterador.hasNext()) {
                PistaDesgastable actual = iterador.next();

                if (Objects.nonNull(salida)) {
                    if (actual.getCola().size() < salida.getCola().size()) salida = actual;
                }
                else salida = actual;   // asignacion para primera iteracion
            }
            
        }
        
        return salida;
    }
}
