package edu.rodriguezestrada.laboratoriomodysim.simulacion.probabilidad;

/**
 *
 * @author rodri
 */
public class Uniforme implements Probabilidad {
    private final Integer inferior, superior;
    
    public Uniforme(Integer inferior, Integer superior){
        this.inferior = inferior;
        this.superior = superior;
    }

    protected Double obtenerValor(Double random){
        Double x = this.inferior + (this.superior - this.inferior) * random;
        return x;
    }
    @Override
    public Double generarValor() {
        return this.obtenerValor(Math.random());
    }
}
