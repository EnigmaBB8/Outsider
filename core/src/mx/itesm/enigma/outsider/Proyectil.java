package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Proyectil extends Objeto {
    private final float VX = 1700;
    private final float VY = 3;
    private Texture texturaMovindo;
    private Texture texturaExplosion;

    private EstadoObjeto estadoFlecha;

    public Proyectil(Texture textura, float x, float y) {
        super(textura, x, y);
        //estadoFlecha=EstadoObjeto.MOVIENDO;
    }


    public Proyectil(Texture texturaMoviendo,Texture texturaExplosion, float x, float y) {
        super(texturaMoviendo, x, y);
        this.texturaMovindo=texturaMoviendo;
        this.texturaExplosion=texturaExplosion;
        estadoFlecha=EstadoObjeto.MOVIENDO;
    }

    public void moverDerecha() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dx = VX*lapso;
        sprite.setX(sprite.getX() + dx);
    }

    public void caida() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dy = VY*lapso;
        sprite.setY(sprite.getY() - dy);
    }
    public void setEstadoFlecha(EstadoObjeto estadoNuevo){
        this.estadoFlecha=estadoNuevo;
        switch (estadoNuevo){
            case MOVIENDO:
                sprite.setTexture(texturaMovindo);
                break;
            case EXPLOTANDO:
                sprite.setTexture(texturaExplosion);
                break;
        }
    }

    public void moverIzquierda() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dx = VX*lapso;
        sprite.setX(sprite.getX() - dx);
    }
}
