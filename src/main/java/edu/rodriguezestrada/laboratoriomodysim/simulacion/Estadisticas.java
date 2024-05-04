package edu.rodriguezestrada.laboratoriomodysim.simulacion;

/**
 *
 * @author rodri
 */
public class Estadisticas {
    private Double avionesArribos = 0.0;
    private Double avionesAterrizajes = 0.0;
    
    private Double transitoTotal = 0.0;
    private Double transitoMinimo = Double.MAX_VALUE;
    private Double transitoMaximo = 0.0;
    
    private Double esperaTotal = 0.0;
    private Double esperaCantidad = 0.0;
    private Double esperaMinimo = Double.MAX_VALUE;
    private Double esperaMaximo = 0.0;
    
    private Double ocioTotal = 0.0;
    private Double ocioMinimo = Double.MAX_VALUE;
    private Double ocioMaximo = 0.0;
    
    private Integer colaTamanioMinimo = Integer.MAX_VALUE;
    private Integer colaTamanioMaximo = 0;
    
    private Double tiempoSimulacion = 0.0;

    public Estadisticas() {
    }
    
    public void addAvionesArribos() {
        this.avionesArribos++;
    }
    
    public void addAvionesAterrizajes() {
        this.avionesAterrizajes++;
    }
    
    public void addTransito(Double tiempoTransito) {
        this.transitoTotal += tiempoTransito;
        this.transitoMinimo = Double.min(this.transitoMinimo, tiempoTransito);
        this.transitoMaximo = Double.max(this.transitoMaximo, tiempoTransito);
    }
    
    public void addEspera(Double tiempoEspera) {
        this.esperaCantidad++;
        this.esperaTotal += tiempoEspera;
        if (tiempoEspera > 0.0)
            this.esperaMinimo = Double.min(this.esperaMinimo, tiempoEspera);
        this.esperaMaximo = Double.max(this.esperaMaximo, tiempoEspera);
    }
    
    public void addOcio(Double tiempoOcio) {
        if (tiempoOcio > 0) {
            this.ocioTotal += tiempoOcio;
            this.ocioMinimo = Double.min(this.ocioMinimo, tiempoOcio);
            this.ocioMaximo = Double.max(this.ocioMaximo, tiempoOcio);
        }
    }
    
    public void addTamanioCola(Integer tamanioCola) {
        if (tamanioCola != 0) {
            this.colaTamanioMinimo = Integer.min(this.colaTamanioMinimo, tamanioCola);
            
            this.colaTamanioMaximo = Integer.max(this.colaTamanioMaximo, tamanioCola);
        }
    }

    public Double getAvionesArribos() {
        return avionesArribos;
    }

    public Double getAvionesAterrizajes() {
        return avionesAterrizajes;
    }

    public Double getTransitoMinimo() {
        return transitoMinimo;
    }

    public Double getTransitoMaximo() {
        return transitoMaximo;
    }

    public Double getEsperaMinimo() {
        return Double.min(esperaMinimo, esperaMaximo);
    }

    public Double getEsperaMaximo() {
        return esperaMaximo;
    }

    public Double getOcioTotalNominal() {
        return ocioTotal;
    }

    public void setTiempoSimulacion(Double tiempoSimulacion) {
        this.tiempoSimulacion = tiempoSimulacion;
    }
    
    public Double getOcioTotalProporcional(Double tiempoSimulacion) {
        return Double.valueOf(this.ocioTotal) /
                Double.valueOf(tiempoSimulacion);
    }

    public Double getOcioTotalProporcional() {
        return Double.valueOf(this.ocioTotal) /
                Double.valueOf(this.tiempoSimulacion);
    }
    
    public Double getOcioMinimo() {
        return Double.min(ocioMinimo, ocioMaximo);
    }

    public Double getOcioMaximo() {
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
