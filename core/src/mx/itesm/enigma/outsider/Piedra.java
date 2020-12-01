package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Piedra extends Objeto {
    private final float VY = 1000;

    public Piedra(Texture textura, float x, float y) {
        super(textura, x, y);
    }

    public void atacar() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dy = VY*lapso;
        sprite.setY(sprite.getY() - dy);
    }
}
