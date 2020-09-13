package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaAcercaDe extends Pantalla {

    private final Juego juego;
    private Texture fondoAcercaDe;
    private Stage escenaAcercaDe;

    public PantallaAcercaDe(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoAcercaDe = new Texture("fondos/fondomenu.JPG");
        crearPantallaAD();
    }

    private void crearPantallaAD() {
        escenaAcercaDe = new Stage(vista);
        //Boton de regreso
        Texture btnNuevaPartida = new Texture("botones/botonR.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Inverso de boton de rgreso
        Texture btnNuevaPartidaInv = new Texture("botones/botonNPInv.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);

        btnNP.setPosition(ANCHO * .05f, ALTO * .96F, Align.topLeft);

        btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });


        escenaAcercaDe.addActor(btnNP);
        Gdx.input.setInputProcessor(escenaAcercaDe);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoAcercaDe, 0, 0);
        batch.end();

        escenaAcercaDe.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        fondoAcercaDe.dispose();
        batch.dispose();
    }
}
