package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Servidor que administra multiples Pistas (servidores).
 * Los eventos se procesan por medio de la interfaz establecida para interactuar con Arribos y Salidas.
 * @author gestrada
 */
public class PistasMultiples extends ArrayList<PistaDesgastable> implements Servidor {

    private CriterioSeleccionPista criterioSeleccionPista = null;
    
    /**
     * 
     * @param criterio Criterio de servidor para elegir la Pista encargada de cada Arribo.
     */
    public PistasMultiples(CriterioSeleccionPista criterio) {
        super();
        
        this.criterioSeleccionPista = criterio;
    }

    public PistasMultiples(Collection<? extends PistaDesgastable> c, CriterioSeleccionPista criterio) {
        super(c);
        
        this.criterioSeleccionPista = criterio;
    }
    
    @Override
    public void ingresoDeAvion(Arribo ingresante) throws Exception {
        PistaDesgastable temp = this.criterioSeleccionPista.pistaEncargada(this);
        temp.ingresoDeAvion(ingresante);
    }

    @Override
    public void salidaDeAvion(Salida saliente) throws Exception {
        // busqueda de la pista que esta atendiendo al Avion saliente
        this.stream()       // transforma a 'stream' (forma ordenada de objetos que maneja java)
                .filter(pista -> Objects.nonNull(pista.getAtendiendo()))             // quita las pistas libres
                .filter(pista -> pista.getAtendiendo().equals(saliente.getEntidad()))   // filtra la pista que atienda la entidad
                .findAny().orElseThrow()    // si no existe arroja una excepcion
                .salidaDeAvion(saliente); // le transfiere la entidad para procesar su salida
    }
    
    /**
     * Devuelve el indice de la Pista que esté atendiendo al Avion indicado.
     * @param avion Avion que tiene que estar atendiendo la Pista.
     * @return Indice de la Pista que esté atendiendo a 'avion'.
     * @throws Exception Si el Avion indicado no está siendo atendido por ninguna pista, se largará una excepción.
     */
    public int indiceAvionAtendiendo(Avion avion) throws Exception {
        // busca el indice de 
        return this.indexOf(this.stream()
                .filter(pista -> Objects.nonNull(pista.getAtendiendo())) // quita las pistas libres
                .filter(pista -> pista.getAtendiendo().equals(avion)) //filtra las pistas y obtiene la que esta atendiendo a avion
                .findAny().orElseThrow()    // si no encuentra ninguna pista, lanza una excepcion
        );
    }
    
    
    /**
     * Función que permite determinar si alguno de los servidores se encuentra libre para atender inmediatamente.
     * @return Verdadero si una Pista no se encuentra ocupada.
     */
    public boolean isAlgunoLibre() {
        return this.stream().map(Pista::isOcupado)
                    .anyMatch(estado -> estado == false);
    }
}
