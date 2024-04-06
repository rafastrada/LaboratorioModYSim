package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Probabilidad;
import java.util.Comparator;

/**
 *
 * @author gestrada
 */
public abstract class Evento implements Comparator {

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

    @Override
    public int compare(Object o1, Object o2) {
        int salida = 1;
        Evento evento1 = (Evento) o1, evento2 = (Evento) o2;
        
        if (evento1.getTiempo() < evento2.getTiempo()) {
            salida = -1;
        }
        else {
            if (evento1.getTiempo() == evento2.getTiempo()
                    &&
                    evento1.getClass().getSimpleName().equals("Salida")) {
                salida = -1;
            }
        }
        
        return salida;
    }

}
