package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Llamaradas extends Objeto {
    private Animation<TextureRegion> animacion1;
    private float timerAnimacion1;

    /*
    Inicializacion de las llamaradas
     */
    public Llamaradas(Texture textura, float x, float y){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturasFrame = region.split(61,55);
        TextureRegion[] arrFrame1 = {texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3],
                texturasFrame[0][4],texturasFrame[0][5],texturasFrame[0][6],texturasFrame[0][2]
               };
        animacion1=new Animation<TextureRegion>(0.1f,arrFrame1);
        animacion1.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion1 = 0;
        sprite = new Sprite(texturasFrame[0][0]); //Estado inicial de la bola de fuego
        sprite.setPosition(x,y);
    }
    /*
    Método que hace que se muevan las llamaradas
     */
    public void atacar(){
        sprite.setPosition(sprite.getX() - 8,sprite.getY() - 4);
    }

    /*
    Método que hace que aparezcan las llamaradas como animación
     */
    public void render(SpriteBatch batch){
        timerAnimacion1 += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animacion1.getKeyFrame(timerAnimacion1);
        batch.draw(frame, sprite.getX(),sprite.getY());
    }
}

