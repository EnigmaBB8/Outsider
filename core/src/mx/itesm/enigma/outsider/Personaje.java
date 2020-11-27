package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static mx.itesm.enigma.outsider.Pantalla.ANCHO;

public class Personaje extends Objeto{
    private Animation<TextureRegion> animacion;
    private float timerAnimacion;

    //Caminar
    private float Dx=4;
    private EstadoCaminando estadoCaminando;

    // Salto
    private float yBase;
    private float tAire;
    private final float V0 = 80;
    private final float G = 10;
    private TextureRegion[][] texturasFrame;
    private float tVuelo;
    private EstadoKAIM estado;

    public Personaje(Texture textura, float x, float y){
        TextureRegion region=new TextureRegion(textura);
        texturasFrame=region.split(65,125);

        //Quieto
        sprite=new Sprite(texturasFrame[0][0]);
        sprite.setPosition(x,y);
        estado = EstadoKAIM.QUIETO;

        //Animación
        TextureRegion[] arrFrame={texturasFrame[0][0],texturasFrame[0][1],texturasFrame[0][2],texturasFrame[0][3]};
        animacion=new Animation<TextureRegion>(0.25f,arrFrame);
        animacion.setPlayMode(Animation.PlayMode.LOOP);
        timerAnimacion=0;

        //Salto
        yBase = y;

        //Dirección de desplazamiento
        estadoCaminando = EstadoCaminando.QUIETO_DERECHA;

    }

    public void saltar() {
        estado = EstadoKAIM.SALTANDO;
        tAire = 0;
        tVuelo = 2*V0/G;
    }

    public EstadoKAIM getEstado() {
        return estado;
    }

    public EstadoCaminando getEstadoCaminando() {
        return estadoCaminando;
    }

    public  void render(SpriteBatch batch) {
        actualizar();
        float delta = Gdx.graphics.getDeltaTime();
        timerAnimacion += delta;//calcula
        if(estado==EstadoKAIM.QUIETO){
            sprite.setRegion(texturasFrame [0][0]);
           sprite.setY(yBase);
            if(estadoCaminando == EstadoCaminando.DERECHA && sprite.isFlipX()){
                sprite.flip(true,false);

            }else if(estadoCaminando == EstadoCaminando.IZQUIERDA && !sprite.isFlipX()) {
                sprite.flip(true, false);

            }else if(estadoCaminando==EstadoCaminando.QUIETO_DERECHA && sprite.isFlipX()){
                sprite.flip(true, false);

            }else if(estadoCaminando==EstadoCaminando.QUIETO_IZQUIERDA && !sprite.isFlipX()){
                sprite.flip(true, false);

            }else{
                sprite.flip(false,false);
            }
            batch.draw(sprite,sprite.getX(),sprite.getY());

        }else if(estado == EstadoKAIM.CAMINANDO) {
            TextureRegion frame = animacion.getKeyFrame(timerAnimacion);
            //derecha e izquierda
            if(estadoCaminando == EstadoCaminando.DERECHA && frame.isFlipX()){
                frame.flip(true,false);

            }else if(estadoCaminando == EstadoCaminando.IZQUIERDA && !frame.isFlipX()) {
                frame.flip(true, false);

            }else if(estadoCaminando==EstadoCaminando.QUIETO_DERECHA && frame.isFlipX()){
                frame.flip(true, false);

            }else if(estadoCaminando==EstadoCaminando.QUIETO_IZQUIERDA && !frame.isFlipX()){
                frame.flip(true, false);

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

            }else if(estadoCaminando==EstadoCaminando.QUIETO_DERECHA && frame.isFlipX()){
                frame.flip(true, false);

            }else if(estadoCaminando==EstadoCaminando.QUIETO_IZQUIERDA && !frame.isFlipX()){
                frame.flip(true, false);
            }
            tAire += 20*delta;
            float y = yBase + V0*tAire - 0.5f*G*tAire*tAire;
            sprite.setY(y);
            if (tAire >= tVuelo) {
                sprite.setY(yBase);
                estado = EstadoKAIM.QUIETO;
            }
            batch.draw(frame,sprite.getX(),sprite.getY());
        }else if(estado==EstadoKAIM.DISPARANDO_FLECHAS){
            sprite.setRegion(texturasFrame[0][5]);
            batch.draw(sprite,sprite.getX(),sprite.getY());
            tAire += 20*delta;
            float y = yBase + V0*tAire - 0.5f*G*tAire*tAire;
            sprite.setY(y);
            if (tAire >= tVuelo) {
                sprite.setY(yBase);
                estado = EstadoKAIM.QUIETO;
            }

        }else if(estado==EstadoKAIM.DISPARANDO_BOLASMAGICAS){
            sprite.setRegion(texturasFrame[0][6]);
            batch.draw(sprite,sprite.getX(),sprite.getY());
            tAire += 20*delta;
            float y = yBase + V0*tAire - 0.5f*G*tAire*tAire;
            sprite.setY(y);
            if (tAire >= tVuelo) {
                sprite.setY(yBase);
                estado = EstadoKAIM.QUIETO;
            }

        }else if(estado==EstadoKAIM.DISPARANDO_BALAS){
            sprite.setRegion(texturasFrame[0][7]);
            batch.draw(sprite,sprite.getX(),sprite.getY());
            tAire += 20*delta;
            float y = yBase + V0*tAire - 0.5f*G*tAire*tAire;
            sprite.setY(y);
            if (tAire >= tVuelo) {
                sprite.setY(yBase);
                estado = EstadoKAIM.QUIETO;
            }

        }else if(estado==EstadoKAIM.DISPARANDO_LASERS){
            sprite.setRegion(texturasFrame[0][4]);
            batch.draw(sprite,sprite.getX(),sprite.getY());
            tAire += 20*delta;
            float y = yBase + V0*tAire - 0.5f*G*tAire*tAire;
            sprite.setY(y);
            if (tAire >= tVuelo) {
                sprite.setY(yBase);
                estado = EstadoKAIM.QUIETO;
            }
        }
    }

    private void actualizar() {
        if(estadoCaminando == EstadoCaminando.DERECHA){
            mover(Dx);

        }else if(estadoCaminando == EstadoCaminando.IZQUIERDA){
            mover(-Dx);

        }
        if (sprite.getX()>=ANCHO - 600 || sprite.getX()<=0) {
            if(sprite.getX() == ANCHO -sprite.getWidth()) {
                setEstadoCaminando(EstadoCaminando.QUIETO_IZQUIERDA);
            }
            else {
                setEstadoCaminando(EstadoCaminando.QUIETO_DERECHA);
            }
        }
    }

    public void mover(float dx) {
        sprite.setX(sprite.getX()+dx);
    }

    public void setEstadoCaminando(EstadoCaminando nuevoEstado) {
        estadoCaminando = nuevoEstado;
    }

    public void setEstado(EstadoKAIM nuevoEstadoKAIM){
        estado=nuevoEstadoKAIM;
    }
}
