package modelo;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
public class ProductListWrapper {

    private List<Producto> productos = new LinkedList<Producto>();

    @XmlElement(name = "product")
    public List<Producto> getProductos() {
        return productos;
    }

    public void setPersons(List<Producto> productos) {
        this.productos = productos;
    }
}