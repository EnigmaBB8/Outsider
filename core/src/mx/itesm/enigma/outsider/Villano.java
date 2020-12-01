package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Villano extends Objeto{
    private Animation<TextureRegion> animacion;
    private Animation<TextureRegion> animacionMuerte;
    private float timerAnimacion;
    private float timerAnimacionMuerte;
    private EstadoVillano estado;

    public Villano(Texture textura){
        TextureRegion region=new TextureRegion(textura);
        TextureRegion[][] texturasFrame=region.split(423,500);

        //Inicio oWo
        sprite = new Sprite(texturasFrame[0][0]);
        estado = EstadoVillano.ATACANDO;
        sprite.setPosition(890,110);

        //Animación atacando (=‘ｘ‘=)
        TextureRegion[] arrFrame={texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3]};
        animacion=new Animation<TextureRegion>(0.3f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
        estado = EstadoVillano.ATACANDO;

        //Animacion muerte
        TextureRegion[] arrFramemuerte={texturasFrame[0][4],texturasFrame[0][5],texturasFrame[0][6]};
        animacionMuerte=new Animation<TextureRegion>(0.2f,arrFramemuerte);
        animacionMuerte.setPlayMode(Animation.PlayMode.NORMAL);
        timerAnimacionMuerte=0;
    }

    public void setEstado(EstadoVillano estado) {
        this.estado = estado;
    }

    public EstadoVillano getEstado() {
        return estado;
    }


    public  void render(SpriteBatch batch) {
        actualizarVillano();
        float delta = Gdx.graphics.getDeltaTime();
        if (estado==EstadoVillano.ATACANDO){
            timerAnimacion += delta;//calcula
            TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
            batch.draw(frame, sprite.getX(), sprite.getY());
        }
        else {
            timerAnimacionMuerte += delta;//calcula
            TextureRegion frame = animacionMuerte.getKeyFrame(timerAnimacionMuerte);
            batch.draw(frame, sprite.getX(), sprite.getY());
        }
    }

    private void actualizarVillano() {
        if(estado == EstadoVillano.MUERTO){
        }
    }
}
