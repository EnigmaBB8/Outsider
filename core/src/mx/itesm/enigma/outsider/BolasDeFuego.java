package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class BolasDeFuego extends Objeto {

    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

     /*
     Inicializacion de las bolas de fuego
      */
    public BolasDeFuego(Texture textura, float x, float y){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturasFrame = region.split(70,51);
        TextureRegion[] arrFrame={texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3]};
        animacion=new Animation<TextureRegion>(0.1f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
        sprite = new Sprite(texturasFrame[0][0]); //Estado inicial de la bola de fuego
        sprite.setPosition(x,y);
    }
    /*
    Método que hace que se muevan las bolas de fuego
     */
    public void atacar(){
        sprite.setPosition(sprite.getX()- 7,sprite.getY() - 4);
    }

    /*
    Método que hace que aparezcan las bolas de fuego como animación
     */
    public void render(SpriteBatch batch){
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, sprite.getX(),sprite.getY());
    }
}
