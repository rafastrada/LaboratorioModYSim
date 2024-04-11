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

    private final int numeroEntidad;

    public Avion() {
        this.numeroEntidad = Avion.cantidadEntidades;

        Avion.cantidadEntidades++;
    }

    public int getNumeroEntidad() {
        return numeroEntidad;
    }

    public String toString(){
        return "Avion #" + numeroEntidad;
    }
}
