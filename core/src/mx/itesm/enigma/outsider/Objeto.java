package mx.itesm.enigma.outsider;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*
Representa un elemento grafico en la pantalla
 */
public class Objeto {

    protected Sprite sprite; // las subclases pueden acceder/MODIFICAR directamente a Sprite

    public Objeto(Texture texture,float x,float y){
        sprite=new Sprite(texture);
        sprite.setPosition(x,y);
    }

    public Objeto(){
    }

    public void render(SpriteBatch batch){
        sprite.draw(batch);
    }

}

