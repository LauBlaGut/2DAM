import java.io.Serializable;

public class PersonajeOnePiece implements Serializable {
    String nombre;
    String rol;
    Fruta frutaDiablo;
    Barco nombreBarco;

    public PersonajeOnePiece(String nombre, String rol, Fruta frutaDiablo, Barco nombreBarco) {
        this.nombre = nombre;
        this.rol = rol;
        this.frutaDiablo = frutaDiablo;
        this.nombreBarco = nombreBarco;
    }
    public PersonajeOnePiece(){}

    public String getNombre() {
        return nombre;
    }
    public String getRol() {
        return rol;
    }
    public Fruta getFrutaDiablo() {
        return frutaDiablo;
    }
    public Barco getNombreBarco() {
        return nombreBarco;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public void setFrutaDiablo(Fruta frutaDiablo) {
        this.frutaDiablo = frutaDiablo;
    }
    public void setNombreBarco(Barco nombreBarco) {
        this.nombreBarco = nombreBarco;
    }


}
