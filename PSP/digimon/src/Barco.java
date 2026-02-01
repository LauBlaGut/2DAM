import java.io.Serializable;

public class Barco implements Serializable {
    String nombre;
    String tripulantes;
    String tipo;
    Integer altura;
    Integer longitud;

    public Barco(String nombre, String tripulantes, String tipo, Integer altura, Integer longitud) {
        this.nombre = nombre;
        this.tripulantes = tripulantes;
        this.tipo = tipo;
        this.altura = altura;
        this.longitud = longitud;
    }

    public Barco(){}

    public String getNombre() {
        return nombre;
    }

    public String getTripulantes() {
        return tripulantes;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getAltura() {
        return altura;
    }

    public Integer getLongitud() {
        return longitud;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTripulantes(String tripulantes) {
        this.tripulantes = tripulantes;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public void setLongitud(Integer longitud) {
        this.longitud = longitud;
    }
}
