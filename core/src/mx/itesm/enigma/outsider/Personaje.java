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
    private EstadoCaminado estados;
    private float Dx=10;

    //Salto
    private EstadosPersonaje estadoPersonaje;

    public Personaje(Texture textura, float x, float y){
        TextureRegion region=new TextureRegion(textura);
        TextureRegion[][] texturasFrame=region.split(80,125);
        //Quieto
        sprite=new Sprite(texturasFrame[0][0]);
        sprite.setPosition(x,y);
        //Animación
        TextureRegion[] arrFrame={texturasFrame[0][3],texturasFrame[0][2],texturasFrame[0][1]};
        animacion=new Animation<TextureRegion>(0.1f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;

        //Salto
        estadoPersonaje=EstadosPersonaje.CAMINANDO;

        //Dirección de desplazamiento
        estados=EstadoCaminado.quieto;


    }

    public EstadosPersonaje getEstadoPersonaje() {
        return estadoPersonaje;
    }


    public  void render(SpriteBatch batch){
        actualizar();
        float delta= Gdx.graphics.getDeltaTime();
        timerAnimacion +=delta;//calcula
        if(estadoPersonaje==EstadosPersonaje.CAMINANDO) {
            TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
            //derecha e izquierda
            if(estados==EstadoCaminado.derecha && frame.isFlipX()){
                frame.flip(true,false);

            }else if(estados==EstadoCaminado.izquierda && frame.isFlipX()){
                frame.flip(false,true);

            }else{
                frame.flip(false,false);
            }
            batch.draw(frame,sprite.getX(),sprite.getY());
        }
    }

    private void actualizar() {
        if(estados==EstadoCaminado.derecha){
            mover(Dx);

        }else if(estados==EstadoCaminado.izquierda){
            mover(-Dx);

        }

    }

    public void mover(float dx) {
        sprite.setX(sprite.getX()+dx);
    }

    public void setEstadosCaminando(EstadoCaminado nuevoEstados) {
        this.estados = nuevoEstados;
    }
}
