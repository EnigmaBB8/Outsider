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

public class PantallaAcercaDeManuel extends Pantalla {

    private final Juego juego;
    private Texture fondoAyuda;
    private Stage escenaAyuda;

    public PantallaAcercaDeManuel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoAyuda = new Texture("fondos/fondoacercadeM.png");
        crearpantallaAyuda();
    }

    private void crearpantallaAyuda() {
        escenaAyuda = new Stage(vista);
        ///Boton de regreso a acerca de
        Texture btnNuevaPartida = new Texture("botones/BtnBack1.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Inverso de boton de regreso a acerca de
        Texture btnNuevaPartidaInv = new Texture("botones/BtnBack.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);

        btnNP.setPosition(ANCHO * .8f, ALTO * .25F, Align.topLeft);

        btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDe(juego));
            }
        });

        escenaAyuda.addActor(btnNP);
        Gdx.input.setInputProcessor(escenaAyuda);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(fondoAyuda, 0, 0);
        batch.end();
        escenaAyuda.draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        fondoAyuda.dispose();
        batch.dispose();
    }
}