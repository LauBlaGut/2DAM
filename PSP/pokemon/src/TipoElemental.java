import java.io.Serializable;

public class TipoElemental implements Serializable {
    String elemento;
    String debilidad;


    public TipoElemental(String elemento, String debilidad) {
        this.elemento = elemento;
        this.debilidad = debilidad;
    }

    public String getElemento() {
        return elemento;
    }

    public String getDebilidad() {
        return debilidad;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public void setDebilidad(String debilidad) {
        this.debilidad = debilidad;
    }

    @Override
    public String toString() {
        return "TipoElemental{" +
                "Elemento='" + elemento +
                "\nDebilidad='" + debilidad +
                '}';
    }
}
