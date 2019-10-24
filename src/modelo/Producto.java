package modelo;

import javafx.beans.property.*;
import sun.java2d.pipe.SpanShapeRenderer;
import util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class Producto {
    private StringProperty descripcion;
    private IntegerProperty unidades;
    private FloatProperty precio;
    private ObjectProperty<LocalDate> fechaFabricacion;

    public Producto() {
        this(null, 0, 0);
    }

    public Producto(String descripcion, float precio, int unidades) {
        this.descripcion = new SimpleStringProperty(descripcion);
        this.precio = new SimpleFloatProperty(precio);
        this.unidades = new SimpleIntegerProperty(unidades);
        this.fechaFabricacion = new SimpleObjectProperty<>(LocalDate.now());
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public int getUnidades() {
        return unidades.get();
    }

    public IntegerProperty unidadesProperty() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades.set(unidades);
    }

    public float getPrecio() {
        return precio.get();
    }

    public FloatProperty precioProperty() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio.set(precio);
    }

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFechaFabricacion() {
        return fechaFabricacion.get();
    }

    public ObjectProperty<LocalDate> fechaFabricacionProperty() {
        return fechaFabricacion;
    }

    public void setFechaFabricacion(LocalDate fechaFabricacion) {
        this.fechaFabricacion.set(fechaFabricacion);
    }
}
