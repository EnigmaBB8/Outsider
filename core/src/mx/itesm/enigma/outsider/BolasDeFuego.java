package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BolasDeFuego extends Objeto {
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

    public BolasDeFuego(Texture textura, float x, float y){
        TextureRegion region = new TextureRegion(textura);
        TextureRegion[][] texturasFrame = region.split(51,70);

        TextureRegion[] arrFrame={texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3]};
        animacion=new Animation<TextureRegion>(0.1f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;

        sprite.setPosition(x,y);
    }

    public void atacar(){
        sprite.setX(sprite.getX()-10);
    }

    public void render(SpriteBatch batch){
        timerAnimacion += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, sprite.getX(),sprite.getY());
    }
}
