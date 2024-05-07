/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.rodriguezestrada.laboratoriomodysim;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.SimulacionParteUno;

/**
 *
 * @author gestrada
 */
public class LaboratorioModYSim {

    public static void main(String[] args) {
//        SimulacionParteUno simulacion = new SimulacionParteUno();
//        simulacion.iniciarSimulacion();

        SimulacionParteDos simulacion = new SimulacionParteDos(5);
        simulacion.iniciarSimulacion();
    }
}
