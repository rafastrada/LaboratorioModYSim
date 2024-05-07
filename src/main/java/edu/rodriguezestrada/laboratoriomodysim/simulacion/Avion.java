package edu.rodriguezestrada.laboratoriomodysim.simulacion;

/**
 *
 * @author gestrada
 */
public class Avion {

    private static int cantidadEntidades = 0;

    public static int getCantidadEntidades() {
        return cantidadEntidades;
    }
    
    /**
     * Vuelve el contador de entidades a cero.
     * Util para tests unitarios.
     */
    public static void resetCantidadEntidades() {
        Avion.cantidadEntidades = 0;
    }

    private final int numeroEntidad;

    public Avion() {
        Avion.cantidadEntidades++;

        this.numeroEntidad = Avion.cantidadEntidades;
    }

    public int getNumeroEntidad() {
        return numeroEntidad;
    }

    public String toString(){
        return "Avion #" + numeroEntidad;
    }
}
