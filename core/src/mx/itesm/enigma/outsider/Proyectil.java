package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Proyectil extends Objeto {
    private final float VX = 350;

    public Proyectil(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void moverDerecha() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dx = VX*lapso;
        sprite.setX(sprite.getX() + dx);
    }
}
