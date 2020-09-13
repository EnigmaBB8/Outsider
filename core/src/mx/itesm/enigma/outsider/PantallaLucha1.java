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

        ///Boton de regreso a menu
        Texture btnNuevaPartida = new Texture("botones/BtnMP.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Inverso de boton de regreso a menu
        Texture btnNuevaPartidaInv = new Texture("botones/BtnMP.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);

        btnNP.setPosition(ANCHO * .86f, ALTO * .17F, Align.topLeft);

        btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaNivel1.addActor(btnNP);
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
