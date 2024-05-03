package edu.rodriguezestrada.laboratoriomodysim.simulacion;

/**
 *
 * @author rodri
 */
public class Estadisticas {
    private Integer avionesArribos = 0;
    private Integer avionesAterrizajes = 0;
    
    private Integer transitoTotal = 0;
    private Integer transitoMinimo = Integer.MAX_VALUE;
    private Integer transitoMaximo = 0;
    
    private Integer esperaTotal = 0;
    private Integer esperaCantidad = 0;
    private Integer esperaMinimo = Integer.MAX_VALUE;
    private Integer esperaMaximo = 0;
    
    private Integer ocioTotal = 0;
    private Integer ocioMinimo = Integer.MAX_VALUE;
    private Integer ocioMaximo = 0;
    
    private Integer colaTamanioMinimo = Integer.MAX_VALUE;
    private Integer colaTamanioMaximo = 0;
    
    private Integer tiempoSimulacion = 0;

    public Estadisticas() {
    }
    
    public void addAvionesArribos() {
        this.avionesArribos++;
    }
    
    public void addAvionesAterrizajes() {
        this.avionesAterrizajes++;
    }
    
    public void addTransito(Integer tiempoTransito) {
        this.transitoTotal += tiempoTransito;
        this.transitoMinimo = Integer.min(this.transitoMinimo, tiempoTransito);
        this.transitoMaximo = Integer.max(this.transitoMaximo, tiempoTransito);
    }
    
    public void addEspera(Integer tiempoEspera) {
        this.esperaCantidad++;
        this.esperaTotal += tiempoEspera;
        this.esperaMinimo = Integer.min(this.esperaMinimo, tiempoEspera);
        this.esperaMaximo = Integer.max(this.esperaMaximo, tiempoEspera);
    }
    
    public void addOcio(Integer tiempoOcio) {
        if (tiempoOcio > 0) {
            this.ocioTotal += tiempoOcio;
            this.ocioMinimo = Integer.min(this.ocioMinimo, tiempoOcio);
            this.ocioMaximo = Integer.max(this.ocioMaximo, tiempoOcio);
        }
    }
    
    public void addTamanioCola(Integer tamanioCola) {
        if (tamanioCola != 0) {
            this.colaTamanioMinimo = Integer.min(this.colaTamanioMinimo, tamanioCola);
            
            this.colaTamanioMaximo = Integer.max(this.colaTamanioMaximo, tamanioCola);
        }
    }

    public Integer getAvionesArribos() {
        return avionesArribos;
    }

    public Integer getAvionesAterrizajes() {
        return avionesAterrizajes;
    }

    public Integer getTransitoMinimo() {
        return transitoMinimo;
    }

    public Integer getTransitoMaximo() {
        return transitoMaximo;
    }

    public Integer getEsperaMinimo() {
        return Integer.min(esperaMinimo, esperaMaximo);
    }

    public Integer getEsperaMaximo() {
        return esperaMaximo;
    }

    public Integer getOcioTotalNominal() {
        return ocioTotal;
    }

    public void setTiempoSimulacion(Integer tiempoSimulacion) {
        this.tiempoSimulacion = tiempoSimulacion;
    }
    
    public Double getOcioTotalProporcional(Integer tiempoSimulacion) {
        return Double.valueOf(this.ocioTotal) /
                Double.valueOf(tiempoSimulacion);
    }

    public Double getOcioTotalProporcional() {
        return Double.valueOf(this.ocioTotal) /
                Double.valueOf(this.tiempoSimulacion);
    }
    
    public Integer getOcioMinimo() {
        return Integer.min(ocioMinimo, ocioMaximo);
    }

    public Integer getOcioMaximo() {
        return ocioMaximo;
    }

    public Integer getColaTamanioMinimo() {
        return Integer.min(colaTamanioMinimo, colaTamanioMaximo);
    }

    public Integer getColaTamanioMaximo() {
        return colaTamanioMaximo;
    }
    
    public Double getTransitoMedio() {
        return Double.valueOf(this.transitoTotal) /
                Double.valueOf(this.avionesAterrizajes);
    }
    
    public Double getEsperaMedio() {
        return Double.valueOf(this.esperaTotal) /
                Double.valueOf(this.esperaCantidad);
    }
    
}
