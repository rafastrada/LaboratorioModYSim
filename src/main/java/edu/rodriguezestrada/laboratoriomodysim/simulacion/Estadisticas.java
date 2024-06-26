package edu.rodriguezestrada.laboratoriomodysim.simulacion;

/**
 *
 * @author rodri
 */
public class Estadisticas {
    private Integer avionesArribos = 0;
    private Integer avionesAterrizajes = 0;
    
    private Double transitoTotal = 0.0;
    private Double transitoMinimo = Double.MAX_VALUE;
    private Double transitoMaximo = 0.0;
    
    private Double esperaTotal = 0.0;
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
    
    /**
     * Crea un objeto que contiene todos los valores predifinidos.
     * 
     * @param avionesArribos
     * @param avionesAterrizajes
     * @param transitoTotal
     * @param transitoMinimo
     * @param transitoMaximo
     * @param esperaTotal
     * @param esperaMinimo
     * @param esperaMaximo
     * @param ocioTotal
     * @param ocioMinimo
     * @param ocioMaximo
     * @param colaTamanioMinimo
     * @param colaTamanioMaximo
     * @param tiempoSimulacion 
     */
    public Estadisticas(
            int avionesArribos, int avionesAterrizajes,
            double transitoTotal, double transitoMinimo, double transitoMaximo,
            double esperaTotal, double esperaMinimo, double esperaMaximo,
            double ocioTotal, double ocioMinimo, double ocioMaximo,
            int colaTamanioMinimo, int colaTamanioMaximo,
            double tiempoSimulacion
    ) {
        this.avionesArribos = avionesArribos;
        this.avionesAterrizajes = avionesAterrizajes;
        
        this.transitoTotal = transitoTotal;
        this.transitoMinimo = transitoMinimo;
        this.transitoMaximo = transitoMaximo;
        
        this.esperaTotal = esperaTotal;
        this.esperaMinimo = esperaMinimo;
        this.esperaMaximo = esperaMaximo;
        
        this.ocioTotal = ocioTotal;
        this.ocioMinimo = ocioMinimo;
        this.ocioMaximo = ocioMaximo;
        
        this.colaTamanioMinimo = colaTamanioMinimo;
        this.colaTamanioMaximo = colaTamanioMaximo;
        
        this.tiempoSimulacion = tiempoSimulacion;
    }
    
    /**
     * Función que devuelve un flotante como un String con la cantidad de decimales pasado por parámetro.
     * Tiene por finalidad acortar las sentencias de impresión.
     * cDR = cadenaDecimalesReducidos
     * @param numero Flotante a transformar en String.
     * @param cantidadDecimales Cantidad de decimales que se imprimirá de 'numero'.
     * @return Cadena que representa el flotante truncado.
     */
    public static String cDR(Double numero, Integer cantidadDecimales) {
        return String.format("%.".concat(cantidadDecimales.toString()).concat("f"),
                numero);
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
        this.esperaTotal += tiempoEspera;
        if (tiempoEspera > 0.0)
            this.esperaMinimo = Double.min(this.esperaMinimo, tiempoEspera);
        this.esperaMaximo = Double.max(this.esperaMaximo, tiempoEspera);
    }
    
    public void addOcio(Double tiempoOcio) {
        if (tiempoOcio > 0.0) {
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

    public Integer getAvionesArribos() {
        return avionesArribos;
    }

    public Integer getAvionesAterrizajes() {
        return avionesAterrizajes;
    }

    public Double getTransitoMinimo() {
        if (this.transitoTotal != 0) return transitoMinimo;
        else return 0.0;
    }
    
    /**
     * Devuelve el tránsito total, suma del tránsito de cada entidad en el servidor.
     * @return Suma total de los tiempos de tránsito
     */
    public Double getTransitoTotal() {
        return this.transitoTotal;
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

    public Double getEsperaTotal() {
        return esperaTotal;
    }

    public Double getOcioTotal() {
        return ocioTotal;
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
        if (this.ocioTotal != 0)
            return Double.min(ocioMinimo, ocioMaximo);
        else return this.tiempoSimulacion;
    }

    public Double getOcioMaximo() {
        if (this.ocioTotal != 0)
            return ocioMaximo;
        else return this.tiempoSimulacion;
    }

    public Integer getColaTamanioMinimo() {
        return Integer.min(colaTamanioMinimo, colaTamanioMaximo);
    }

    public Integer getColaTamanioMaximo() {
        return colaTamanioMaximo;
    }
    
    public Double getTransitoMedio() {
        if (this.avionesAterrizajes != 0)
            return Double.valueOf(this.transitoTotal) /
                Double.valueOf(this.avionesAterrizajes);
        else return 0.0;
    }
    
    /**
     * Devuelve la espera media de los aviones que transitaron por la pista.
     * @return Espera media de los aviones transitados
     */
    public Double getEsperaMedio() {
        if (this.avionesAterrizajes != 0)
            return Double.valueOf(this.esperaTotal) /
                Double.valueOf(this.avionesAterrizajes);
        else return 0.0;
    }

    
    
    @Override
    public String toString() {
        return "Estadisticas{" + 
                "\n\tCantidad de Aeronaves aterrizadas: " + this.getAvionesAterrizajes() +
                "\n\tCantidad de Aeronaves arribadas: " + this.getAvionesArribos() +
                "\n\tTiempo en sistema:" +
                "\n\tMedio:\t" + this.getTransitoMedio() + "\tMáximo:\t" + this.getTransitoMaximo() + "\tMínimo:\t" + this.getTransitoMinimo() +
                "\n\tTiempo de espera:" +
                "\n\tMedio:\t" + this.getEsperaMedio() + "\tMáximo:\t" + this.getEsperaMaximo() + "\tMínimo:\t" + this.getEsperaMinimo() +
                "\n\tTiempo de ocio:" +
                "\n\tTotal:\t" + Estadisticas.cDR(this.getOcioTotalProporcional()*100, 2) + "%" +
                "\tMáximo:\t" + this.getOcioMaximo() +
                "\tMínimo:\t" + this.getOcioMinimo() +
                "\n\tTamaño de Cola de Espera:" +
                "\n\tMáximo:\t" + this.getColaTamanioMaximo() + "\tMínimo:\t" + this.getColaTamanioMinimo() +
                '}';
    }
    
}
