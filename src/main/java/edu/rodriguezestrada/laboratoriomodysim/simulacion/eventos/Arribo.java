package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Fel;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Probabilidad;

/**
 *
 * @author gestrada
 */
public class Arribo extends Evento {

    private static Probabilidad valoresAzarosos = null;

    public static Probabilidad getValoresAzarosos() {
        return valoresAzarosos;
    }

    public static void setValoresAzarosos(Probabilidad valoresAzarosos) {
        Arribo.valoresAzarosos = valoresAzarosos;
    }
    
    public static Double calcularDuracion() {
        return Arribo.valoresAzarosos.generarValor();
    }
    
    public Arribo(Double tiempo, Avion entidad) {
        super(tiempo, entidad);
    }

    /**
     * Procesa el evento Arribo, generando el proximo evento.
     * Si el servidor a donde ingresa la entidad no está atendiendo, entonces tiene que generar su Salida también.
     * Para esto se indica por parámetro si el servidor esta libre previo a la inserción.
     * @param eventosFuturos FEL.
     * @param servidorLibre Si se encuentra libre el servidor a donde pasa la entidad.
     */
    public void procesarEvento(Fel eventosFuturos, boolean servidorLibre) {
        // genera proximo evento y se introduce en la FEL
        eventosFuturos.add(new Arribo(
                this.tiempo + Arribo.calcularDuracion(),
                new Avion()));
        
        // si el servidor esta libre, se crea la salida para esta instancia
        if (servidorLibre) eventosFuturos.add(
                    new Salida(this.tiempo + Salida.calcularDuracion(), this.entidad));
        
        eventosFuturos.ordenarFEL();
    }
    
    @Override
    public String toString(){
        return "(A, " + this.entidad + ", " + this.tiempo + ")";
    }
}
