package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaAcercaDeItzel extends Pantalla {

    private final Juego juego;
    private Texture fondoAyuda;
    private Stage escenaAyuda;

    public PantallaAcercaDeItzel(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoAyuda = juego.getManager().get("fondos/fondoacercadeI.png");
        crearpantallaAyuda();
        configurarMusica();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void configurarMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        if(musicaFondo==true) {
            //Prender musica
            juego.reproducirMusica();
            juego.detenerMusicaN1();
            juego.detenerMusicaN2();
            juego.detenerMusicaN3();
            juego.detenerMusicaN4();
        }
    }

    private void crearpantallaAyuda() {
        escenaAyuda = new Stage(vista);
        ///Boton de regreso a acerca de
        Texture btnNuevaPartida = juego.getManager().get("botones/btnBack1.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Inverso de boton de a acerca de
        Texture btnNuevaPartidaInv = juego.getManager().get("botones/btnBack.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);

        btnNP.setPosition(ANCHO * .8f, ALTO * .25F, Align.topLeft);

        btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.ACERCA_DE));
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
        juego.getManager().unload("fondos/fondoacercadeI.png");
        juego.getManager().unload("botones/btnBack1.png");
        juego.getManager().unload("botones/btnBack.png");
        batch.dispose();
    }
}