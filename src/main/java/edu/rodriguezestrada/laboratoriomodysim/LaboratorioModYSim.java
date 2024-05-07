/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.rodriguezestrada.laboratoriomodysim;

/**
 *
 * @author gestrada
 */
public class LaboratorioModYSim {

    public static void main(String[] args) {
//        SimulacionParteUno simulacion = new SimulacionParteUno();
//        simulacion.iniciarSimulacion();

        // valor por defecto
        int cantidadPistas = 5;

        for (String argumento : args) {
            if (argumento.startsWith("--pistas="))
                    cantidadPistas = Integer.valueOf(argumento.substring("--pistas=".length()));
        }

        SimulacionParteDos simulacion = new SimulacionParteDos(cantidadPistas);
        simulacion.iniciarSimulacion();
    }
}
