package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Drones extends Objeto {
    private final float VX = 1100;

    public Drones(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void atacar() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dx = VX*lapso;
        sprite.setX(sprite.getX() - dx);
    }
}
