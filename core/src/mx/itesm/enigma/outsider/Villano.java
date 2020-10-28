package mx.itesm.enigma.outsider;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;



public class Villano extends Objeto{
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;
    private EstadoVillano estado;

    public Villano(Texture textura){
        TextureRegion region=new TextureRegion(textura);
        TextureRegion[][] texturasFrame=region.split(423,500);

        //Inicio oWo
        sprite = new Sprite(texturasFrame[0][0]);
        estado = EstadoVillano.ATACANDO;
        sprite.setPosition(900,110);
        //Animación atacando (=‘ｘ‘=)
        TextureRegion[] arrFrame={texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3]};
        animacion=new Animation<TextureRegion>(0.15f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;
        estado = EstadoVillano.ATACANDO;

        //Muerto OWO
        estado = EstadoVillano.MUERTO;


    }

    public EstadoVillano getEstado() {
        return estado;
    }


    public  void render(SpriteBatch batch) {
        actualizar();
        float delta = Gdx.graphics.getDeltaTime();
        timerAnimacion += delta;//calcula
        TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
        batch.draw(frame, sprite.getX(), sprite.getY());
    }
//Well for now i have the class and it moves and everything,
// next time will do the part where it dies =ටᆼට=

    private void actualizar() {
        if(estado == EstadoVillano.MUERTO){
        }

    }
}
