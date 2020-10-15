package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Personaje extends Objeto{
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

    //Caminar
    private float Dx=2;
    private EstadoCaminando estadoCaminando;

    // Salto
    private float yBase;
    private float tAire;
    private final float V0 = 100;
    private final float G = 20;
    private float tVuelo;
    private EstadoKAIM estado;

    public Personaje(Texture textura, float x, float y){
        TextureRegion region=new TextureRegion(textura);
        TextureRegion[][] texturasFrame=region.split(80,125);

        //Quieto
        sprite=new Sprite(texturasFrame[0][0]);
        sprite.setPosition(x,y);

        //Animación
        TextureRegion[] arrFrame={texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3]};
        animacion=new Animation<TextureRegion>(0.25f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;

        //Salto
        yBase = y;
        estado = EstadoKAIM.CAMINANDO;

        //Dirección de desplazamiento
        estadoCaminando = EstadoCaminando.QUIETO;

    }

    public void saltar() {
        estado = EstadoKAIM.SALTANDO;
        tAire = 0;
        tVuelo = 2*V0/G;
    }

    public EstadoKAIM getEstado() {
        return estado;
    }


    public  void render(SpriteBatch batch){
        actualizar();
        float delta= Gdx.graphics.getDeltaTime();
        timerAnimacion +=delta;//calcula
        if(estado == EstadoKAIM.CAMINANDO) {
            TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
            //derecha e izquierda
            if(estadoCaminando == EstadoCaminando.DERECHA && frame.isFlipX()){
                frame.flip(true,false);

            }else if(estadoCaminando == EstadoCaminando.IZQUIERDA && !frame.isFlipX()){
                frame.flip(true,false);

            }else{
                frame.flip(false,false);
            }
            batch.draw(frame,sprite.getX(),sprite.getY());

        } else if (estado == EstadoKAIM.SALTANDO){
            TextureRegion frame = animacion.getKeyFrame(timerAnimacion);

            if(estadoCaminando == EstadoCaminando.SALTA_DERECHA && frame.isFlipX()){
                frame.flip(true,false);

            }else if(estadoCaminando == EstadoCaminando.SALTA_IZQUIERDA && !frame.isFlipX()){
                frame.flip(true,false);
            }

            tAire += 10*delta;
            float y = yBase + V0*tAire - 0.5f*G*tAire*tAire;
            sprite.setY(y);
            batch.draw(frame,sprite.getX(),sprite.getY());
            if (tAire >= tVuelo) {
                sprite.setY(yBase);
                estado = EstadoKAIM.CAMINANDO;
            }
        }
    }

    private void actualizar() {
        if(estadoCaminando == EstadoCaminando.DERECHA){
            mover(Dx);

        }else if(estadoCaminando == EstadoCaminando.IZQUIERDA){
            mover(-Dx);

        }

    }

    public void mover(float dx) {
        sprite.setX(sprite.getX()+dx);
    }

    public void setEstadoCaminando(EstadoCaminando nuevoEstado) {
        estadoCaminando = nuevoEstado;
    }
}
