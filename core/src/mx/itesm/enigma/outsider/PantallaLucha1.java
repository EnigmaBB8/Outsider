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

public class PantallaLucha1 extends Pantalla {
    private final Juego juego;
    private Stage escenaNivel1;

    private Texture fondoNivel1;

    public PantallaLucha1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoNivel1 = new Texture("fondos/fondoMenu.JPG");
        crearNivel1();
    }

    private void crearNivel1() {
        escenaNivel1 = new Stage(vista);
        //Boton de regreso a menu
        Texture btnFlechaAtras = new Texture("botones/flechaAtras.png");
        TextureRegionDrawable trdBtnFlechaAtras = new TextureRegionDrawable(new TextureRegion(btnFlechaAtras));

        //Inverso de boton de regreso a menu
        Texture btnFlechaAtrasInv = new Texture("botones/flechaAtras.png");
        TextureRegionDrawable trdBtnFlechaAtrasInv = new TextureRegionDrawable(new TextureRegion(btnFlechaAtrasInv));

        ImageButton btnFA = new ImageButton(trdBtnFlechaAtras, trdBtnFlechaAtrasInv);

        btnFA.setPosition(ANCHO * .75f, ALTO * .3F, Align.topLeft);

        btnFA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });


        escenaNivel1.addActor(btnFA);
        Gdx.input.setInputProcessor(escenaNivel1);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoNivel1, 0, 0);
        batch.end();

        escenaNivel1.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        fondoNivel1.dispose();
        batch.dispose();

    }
}
