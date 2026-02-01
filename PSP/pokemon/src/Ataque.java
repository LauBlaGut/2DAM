import java.io.Serializable;

public class Ataque implements Serializable {
    String ataque;
    int damage;

    public Ataque(String ataque, int damage) {
        this.ataque = ataque;
        this.damage = damage;
    }
    public String getAtaque() {
        return ataque;
    }
    public int getDamage() {
        return damage;
    }
    public void setAtaque(String ataque) {
        this.ataque = ataque;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
