import java.io.Serializable;

public class Pokemon implements Serializable {
    int numeroPokedex;
    String nombre;
    TipoElemental elemento;
    Ataque atq;

    public Pokemon(int numeroPokedex, String nombre, TipoElemental elemento, Ataque atq) {
        this.numeroPokedex = numeroPokedex;
        this.nombre = nombre;
        this.elemento = elemento;
        this.atq = atq;
    }

    public int getNumeroPokedex() {
        return numeroPokedex;
    }
    public String getNombre() {
        return nombre;
    }
    public TipoElemental getElemento() {
        return elemento;
    }
    public Ataque getAtq() {
        return atq;
    }
    public void setNumeroPokedex(int numeroPokedex) {
        this.numeroPokedex = numeroPokedex;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setElemento(TipoElemental elemento) {
        this.elemento = elemento;
    }
    public void setAtq(Ataque atq) {
        this.atq = atq;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "numeroPokedex=" + numeroPokedex +
                ", nombre='" + nombre + '\'' +
                ", elemento=" + elemento +
                ", atq=" + atq +
                '}';
    }
}
