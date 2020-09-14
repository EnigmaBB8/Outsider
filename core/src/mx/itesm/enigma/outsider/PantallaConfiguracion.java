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

public class PantallaConfiguracion extends Pantalla {

    private final Juego juego;
    private Texture fondoConf;
    private Stage escenaConf;

    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoConf = new Texture("fondos/fondoconfiguracion.png");
        crearPantallaConf();
    }

    private void crearPantallaConf() {
        escenaConf = new Stage(vista);
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

        escenaConf.addActor(btnNP);
        Gdx.input.setInputProcessor(escenaConf);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoConf, 0, 0);
        batch.end();

        escenaConf.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        fondoConf.dispose();
        batch.dispose();
    }
}
