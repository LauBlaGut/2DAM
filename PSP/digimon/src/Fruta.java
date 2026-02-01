import java.io.Serializable;

public class Fruta implements Serializable {
    String nombre;
    String descripcion;
    String apariencia;

    public Fruta(String nombre, String descripcion, String apariencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.apariencia = apariencia;
    }
    public Fruta(){}

    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getApariencia() {
        return apariencia;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setApariencia(String apariencia) {
        this.apariencia = apariencia;
    }
}
