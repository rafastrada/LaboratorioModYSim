package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Arribo;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos.Salida;

/**
 * Establece las funciones básicas para que un servidor interactue con el arribo y salida de entidades.
 * (La interfaz está definida para entidades de tipo Avion especificamente)
 * @author gestrada
 */
public interface Servidor {
    
    /**
     * Ingreso de una entidad Avion al servidor.
     * @param ingresante Evento de Arribo de la entidad ingresante.
     */
    void ingresoDeAvion(Arribo ingresante) throws Exception;
    
    /**
     * Salida de una entidad Avion del servidor. 
     * La entidad debe encontrarse en atención, caso contrario lanzar una excepción.
     * @param saliente Evento de Salida de la entidad saliente.
     * @throws Exception Error en caso de no encontrarse en atención la entidad del evento 'saliente'.
     */
    void salidaDeAvion(Salida saliente) throws Exception;
}
