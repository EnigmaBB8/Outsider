package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Espinas extends Objeto{
    //private final float VY = 30;

    public Espinas (Texture textura, float x, float y){
        super(textura, x, y);
    }

    public void atacar() {
        float lapso = Gdx.graphics.getDeltaTime();
        //float dy = VY*lapso;
        sprite.setY(sprite.getY());
    }
}
