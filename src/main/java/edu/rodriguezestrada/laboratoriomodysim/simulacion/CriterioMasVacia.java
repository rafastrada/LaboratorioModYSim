package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import java.util.Iterator;
import java.util.Objects;

/**
 * Criterio de selección donde se elije la Pista más vacia.
 * La Pista mas vacia será aquella que, recorriendo de menor a mayor índice de arreglo, la primera que esté desocupada o la que su cola de espera sea menor.
 * @author gestrada
 */
public class CriterioMasVacia implements CriterioSeleccionPista {

    public CriterioMasVacia() {
    }
    
    /**
     * Devuelve la pista mas vacia.
     * @param aeropuerto Servidor de multiples Pistas de la cual se desea seleccionar bajo criterio.
     * @return Devuelve la Pista correspondiente bajo el criterio de la mas vacía.
     */
    @Override
    public PistaDesgastable pistaEncargada(PistasMultiples aeropuerto) {
        Iterator<PistaDesgastable> iterador = aeropuerto.iterator();
        PistaDesgastable salida = null;
        
        // se recorre las pistas disponibles
        while (iterador.hasNext()) {
            PistaDesgastable actual = iterador.next();
            
            // la primera pista libre que se encuentre es devuelta
            if (!actual.isOcupado()) return actual;
            // sino se recuerda la pista con cola de espera mas corta
            else if (Objects.nonNull(salida)) {
                if (actual.getCola().size() < salida.getCola().size()) salida = actual;
            }
            else salida = actual;   // asignacion para primera iteracion
        }
        
        return salida;
    }
}
