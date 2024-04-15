package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Probabilidad;
import java.util.Comparator;

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
    
    protected int tiempo;
    protected Avion entidad;

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
    public String toString() {
//        return super.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        return this.getClass().getSimpleName() + "= Tiempo:" + String.valueOf(this.tiempo);
    }

    
    public static class Comparador implements Comparator<Evento> {
        
        public int compare(Evento evento1, Evento evento2) {
            int salida = 1;

            if (evento1.getTiempo() < evento2.getTiempo()) {
                salida = -1;
            }
            else {
                if (evento1.getTiempo() == evento2.getTiempo() &&
                    (evento1.getClass().getSimpleName().equals("Salida")
                            ||
                        // ubica el evento Fin antes que los Arribos con tiempo similar
                            (evento1.getClass().getSimpleName().equals("Fin")
                                && evento2.getClass().getSimpleName().equals("Arribo"))))
                        salida = -1;
                }

            return salida;
        }
    }
}
