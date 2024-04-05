package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Probabilidad;

/**
 *
 * @author gestrada
 */
public abstract class Evento {

    private static Probabilidad valoresAzarosos = null;

    /**
     * Get the value of valoresAzarosos
     *
     * @return the value of valoresAzarosos
     */
    public static Probabilidad getValoresAzarosos() {
        return valoresAzarosos;
    }

    /**
     * Set the value of valoresAzarosos
     *
     * @param valoresAzarosos new value of valoresAzarosos
     */
    public static void setValoresAzarosos(Probabilidad valoresAzarosos) {
        Evento.valoresAzarosos = valoresAzarosos;
    }

    public static Integer calcularDuracion() {
        return Evento.valoresAzarosos.generarValor();
    }
    
    private final int tiempo;
    private final Avion entidad;

    public Evento(int tiempo, Avion entidad) {
        this.tiempo = tiempo;
        this.entidad = entidad;
    }

    public int getTiempo() {
        return tiempo;
    }

    /**
     * Devuelve la entidad asociada al evento
     *
     * @return the value of entidad
     */
    public Avion getEntidad() {
        return entidad;
    }

}
