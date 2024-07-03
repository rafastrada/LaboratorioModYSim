package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author gestrada
 */
public class CriterioMasSana implements CriterioSeleccionPista {

    public CriterioMasSana() {
    }
    
    
    @Override
    public PistaDesgastable pistaEncargada(PistasMultiples aeropuerto) {
        
        Iterator<PistaDesgastable> pistas = aeropuerto.iterator();
        int indice = 0;
        
        while (pistas.hasNext()) {
            PistaDesgastable pista = pistas.next();
            
            if (pista.getDesgaste() > aeropuerto.get(indice).getDesgaste()) indice = aeropuerto.indexOf(pista);
        }
        
        return aeropuerto.get(indice);
        }
}
    

