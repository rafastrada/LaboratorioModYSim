package edu.rodriguezestrada.laboratoriomodysim.simulacion.eventos;

import edu.rodriguezestrada.laboratoriomodysim.simulacion.Avion;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.Fel;
import edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad.Probabilidad;
import java.util.Objects;

/**
 *
 * @author rodri
 */
public class Salida extends Evento {
    
    private static Probabilidad valoresAzarosos = null;

    public static Probabilidad getValoresAzarosos() {
        return valoresAzarosos;
    }

    public static void setValoresAzarosos(Probabilidad valoresAzarosos) {
        Salida.valoresAzarosos = valoresAzarosos;
    }
    
    public static Double calcularDuracion() {
        return Salida.valoresAzarosos.generarValor();
    }
    
    public Salida(Double tiempo, Avion entidad){
        super(tiempo, entidad);
    }
    
    public void procesarEvento(Fel eventosFuturos, Avion entidadProxima) {
        // si hay proxima entidad para salir se genera su salida
        // (entidadProxima != null)
        if (Objects.nonNull(entidadProxima)) {
            eventosFuturos.add(
                new Salida(this.tiempo + Salida.calcularDuracion(), entidadProxima));
            
            eventosFuturos.ordenarFEL();
        }
    }
    
    @Override
    public String toString(){
        return "(S, " + this.entidad + ", " + this.tiempo + ")";
    }
}