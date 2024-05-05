package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Servidor que administra multiples Pistas (servidores).
 * Los eventos se procesan por medio de la interfaz establecida para interactuar con Arribos y Salidas.
 * @author gestrada
 */
public class PistasMultiples extends ArrayList<Pista> implements Servidor {

    private CriterioSeleccionPista criterioSeleccionPista = null;
    
    /**
     * 
     * @param criterio Criterio de servidor para elegir la Pista encargada de cada Arribo.
     */
    public PistasMultiples(CriterioSeleccionPista criterio) {
        super();
        
        this.criterioSeleccionPista = criterio;
    }

    public PistasMultiples(Collection<? extends Pista> c, CriterioSeleccionPista criterio) {
        super(c);
        
        this.criterioSeleccionPista = criterio;
    }
    
    @Override
    public void ingresoDeAvion(Arribo ingresante) throws Exception {
        this.criterioSeleccionPista.pistaEncargada(this)
                .ingresoDeAvion(ingresante);
    }

    @Override
    public void salidaDeAvion(Salida saliente) throws Exception {
        // busqueda de la pista que esta atendiendo al Avion saliente
        this.stream()       // transforma a 'stream' (forma ordenada de objetos que maneja java)
                .filter(pista -> pista.getAtendiendo().equals(saliente.getEntidad()))   // filtra la pista que atienda la entidad
                .findAny().orElseThrow()    // si no existe arroja una excepcion
                .salidaDeAvion(saliente); // le transfiere la entidad para procesar su salida
    }
}
