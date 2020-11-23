package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Flecha extends Objeto {
    private final float VX = 1700;
    private final float VY = 3;
    private Texture texturaMoviendo;
    private Texture texturaExplotando;
    private EstadoObjeto estado;

    public Flecha(Texture textura, float x, float y) {
        super(textura, x, y);
        estado = EstadoObjeto.MOVIENDO;
    }

    public Flecha(Texture texturaMoviendo, Texture texturaExplotando, float x, float y){
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
                break;
        }
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
}
