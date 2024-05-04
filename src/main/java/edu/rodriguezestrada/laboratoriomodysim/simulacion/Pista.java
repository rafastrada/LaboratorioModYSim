package edu.rodriguezestrada.laboratoriomodysim.simulacion;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.Objects;

/**
 *
 * @author gestrada
 */
public class Pista {
    
    private Avion atendiendo = null;
    
    // par clave,valor donde la clave es el avion y el valor el tiempo en el que entro a la cola
    private ArrayDeque<SimpleEntry<Avion,Double>> cola; 
    
    private Estadisticas estadisticasPista;

    public Estadisticas getEstadisticasPista() {
        return estadisticasPista;
    }
    
    // tiempo en que empezo la ultima atencion
    // NOTA: la variable guarda tambien el tiempo en el que comiemza el tiempo libre del servidor
    private double tiempoUltimaAtencion = 0;
    
    public ArrayDeque<SimpleEntry<Avion, Double>> getCola() {
        return cola;
    }
    
    public Avion getAtendiendo() {
        return atendiendo;
    }

    public Pista() {
        this.cola = new ArrayDeque();
        this.estadisticasPista = new Estadisticas();
    }

    public boolean isOcupado() {
        return Objects.nonNull(this.atendiendo);
    }
    
    private Double getTiempoUltimoArribo() {
        if (!this.cola.isEmpty()) return this.cola.peekLast().getValue();
        else return 0.0;
    }
    
    public void ingresoDeAvion(Avion ingresante, Double reloj) throws Exception {
        
        // si la cola esta vacia, peek devuelve null, hay que manejar para que devuelva 0        
        double diferenciaUltimoSuceso = reloj - Double.max(
                this.tiempoUltimaAtencion, 
                this.getTiempoUltimoArribo());
        
        // control para evitar que un ingreso se realice con reloj anterior al ultimo evento
        if (diferenciaUltimoSuceso >= 0) {

            // si el servidor no esta ocupado pasa a ser atendido
            if (!this.isOcupado()) {
                // se registra el tiempo de ocio
                this.estadisticasPista.addOcio(diferenciaUltimoSuceso);
                
                this.atendiendo = ingresante;
                this.tiempoUltimaAtencion = reloj;
                this.estadisticasPista.addAvionesAterrizajes();
            }
            // si el servidor esta ocupado, la entidad pasa a la cola de espera
            else {
                this.cola.add(new SimpleEntry<>(ingresante,reloj));
                this.estadisticasPista.addTamanioCola(this.cola.size());    // al cambiar la cola, se mide
            }

            this.estadisticasPista.addAvionesArribos();
        } else
            throw new Exception("ERROR: Se ha ingresado un valor de reloj pasado.");
    }
    
    public void salidaDeAvion(Avion saliente, Double reloj) throws Exception {
        // control para evitar que una salida se realice con reloj anterior al ultimo evento
        if (reloj >= Double.max(this.tiempoUltimaAtencion, 
                                this.getTiempoUltimoArribo())) {
        

            // si el avion pasado por parametro es el atendido, este sale
            if (this.atendiendo.equals(saliente)) {
                // tiempo que estuvo en el servidor el avion saliente
                this.estadisticasPista.addTransito(reloj - this.tiempoUltimaAtencion);

                // si hay cola
                if (!this.cola.isEmpty()) {
                    // variable temporal para la extraccion de la fila
                    SimpleEntry<Avion,Double> siguiente = this.cola.remove();
                    this.estadisticasPista.addTamanioCola(this.cola.size());    // al cambiar la cola, se registra

                    // el primero en espera pasa a ser atendido
                    this.atendiendo = siguiente.getKey();

                    // se registra su tiempo de espera
                    this.estadisticasPista.addEspera(reloj - siguiente.getValue());
                    this.estadisticasPista.addAvionesAterrizajes();
                }
                else this.atendiendo = null;
                this.tiempoUltimaAtencion = reloj;
            } else {
                throw new Exception("ERROR: La entidad indicada no está siendo atendida.");
            }
        } else throw
                new Exception("ERROR: Se ha ingresado un valor de reloj pasado.");
    }
}