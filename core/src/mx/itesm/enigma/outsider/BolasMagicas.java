package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class BolasMagicas extends Objeto {

    private Animation<TextureRegion> animacion;
    private float timerAnimacion;
    private final float VX = 700;

    /*
    Inicializacion de las bolas de fuego
     */
    public BolasMagicas(Texture textura, float x, float y){

        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturasFrame = region.split(48,50);
        TextureRegion[] arrFrame={texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3],texturasFrame[0][4],
                texturasFrame[0][5],texturasFrame[0][6],texturasFrame[0][7]};
        animacion=new Animation<TextureRegion>(0.1f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
        sprite = new Sprite(texturasFrame[0][0]); //Estado inicial de la bola de magia
        sprite.setPosition(x,y);
    }
    /*
    Método que hace que se muevan las bolas de fuego
     */
    public void moverDerecha() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dx = VX*lapso;
        sprite.setX(sprite.getX() + dx);
    }

    public void render(SpriteBatch batch){
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, sprite.getX(),sprite.getY());
    }
}
