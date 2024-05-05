package edu.rodriguezestrada.laboratoriomodysim.simulacion;

/**
 * Infertaz para método de selección de la Pista (servidor) encargada de recibir un Arribo para la clase PistasMultiples.
 * @author gestrada
 */
public interface CriterioSeleccionPista {
    Pista pistaEncargada(PistasMultiples aeropuerto);
}
