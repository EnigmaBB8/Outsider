package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Proyectil extends Objeto {
    private final float VX = 1100;
    private final float VY = 3;
    private Texture texturaMoviendo;
    private Texture texturaExplotando;
    private EstadoObjeto estado;
    private float timerExplota;

    public Proyectil(Texture textura, float x, float y) {
        super(textura, x, y);
        estado = EstadoObjeto.MOVIENDO;
    }

    public Proyectil(Texture texturaMoviendo, Texture texturaExplotando, float x, float y){
        super(texturaMoviendo, x, y);
        this.texturaMoviendo = texturaMoviendo;
        this.texturaExplotando = texturaExplotando;
        estado = EstadoObjeto.MOVIENDO;
    }

    public EstadoObjeto getEstado() {
        return estado;
    }

    public void setEstado(EstadoObjeto estado) {
        this.estado = estado;
        switch (estado){
            case MOVIENDO:
                sprite.setTexture(texturaMoviendo);
                break;
            case EXPLOTANDO:
                sprite.setTexture(texturaExplotando);
                timerExplota = 0;
                break;
        }
    }

    public void moverDerecha() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dx = VX*lapso;
        if(estado == EstadoObjeto.MOVIENDO) {
            sprite.setX(sprite.getX() + dx);
        }
    }

    public void caida() {
        float lapso = Gdx.graphics.getDeltaTime();
        float dy = VY*lapso;
        sprite.setY(sprite.getY() - dy);
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        if (estado == EstadoObjeto.EXPLOTANDO){
            timerExplota += Gdx.graphics.getDeltaTime();
            if (timerExplota >= 1) {
                setEstado(EstadoObjeto.DESAPARECE);
            }
        }
    }
}
