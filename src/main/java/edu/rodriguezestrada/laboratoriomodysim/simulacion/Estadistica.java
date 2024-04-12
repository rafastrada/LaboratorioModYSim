package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 *
 * @author rodri
 */
public class Estadistica {
    protected ArrayList<Integer> tiemposOcio;
    protected ArrayList<Integer> tiemposServicio;
    protected ArrayList<Integer> tiemposEspera;
    protected ArrayDeque<Integer> iniciosEspera; 
    
    public Estadistica() {
        this.tiemposEspera = new ArrayList<>();
        this.tiemposOcio = new ArrayList<>();
        this.tiemposServicio = new ArrayList<>();
        this.iniciosEspera = new ArrayDeque<>();
    }
    
}
