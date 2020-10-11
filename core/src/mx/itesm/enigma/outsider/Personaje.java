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
    private EstadoKAIM estado;
    private EstadoCaminando estadoCaminando;

    public Personaje(Texture textura, float x, float y){
        TextureRegion region=new TextureRegion(textura);
        TextureRegion[][] texturasFrame=region.split(80,125);

        //Quieto
        sprite=new Sprite(texturasFrame[0][0]);
        sprite.setPosition(x,y);

        //Animación
        TextureRegion[] arrFrame={texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3]};
        animacion=new Animation<TextureRegion>(0.1f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;

        //Salto
        estado = EstadoKAIM.CAMINANDO;

        //Dirección de desplazamiento
        estadoCaminando = EstadoCaminando.QUIETO;

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
