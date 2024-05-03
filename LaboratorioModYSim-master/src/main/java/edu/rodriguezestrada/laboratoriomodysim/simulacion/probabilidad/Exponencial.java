package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

/**
 *
 * @author rodri
 */
public class Exponencial implements Probabilidad{
    private final Integer mu;
    
    public Exponencial(Integer mu){
        this.mu = mu;
    }
    
    public Double obtenerValor(double random){
        Double valor = -this.mu * Math.log10(1-random);
        return valor;
    }

    @Override
    public Double generarValor() {
        return this.obtenerValor(Math.random());
    }
}
