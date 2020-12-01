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

public class PantallaHistoria extends Pantalla {
    private Juego juego;
    private Texture fondoHistoria;
    private Stage escenaHistoria;
    public PantallaHistoria(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        fondoHistoria=new Texture("fondos/Historia.jpeg");
        crearHistoria();
        configurarMusica();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);

    }

    private void configurarMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        Gdx.app.log("MUSICA 1", " " + musicaFondo);
        if(musicaFondo==true) {
            //Prender musica
            juego.reproducirMusica();
            juego.detenerMusicaN1();
            juego.detenerMusicaN2();
            juego.detenerMusicaN3();
            juego.detenerMusicaN4();
        }
    }

    private void crearHistoria() {
        escenaHistoria=new Stage(vista);
        ///Boton de regreso a acerca de
        Texture btnConf= new Texture("botones/btnBack1.png");
        TextureRegionDrawable trdBtconf = new TextureRegionDrawable(new TextureRegion(btnConf));

        //Inverso de boton de regreso a acerca de
        Texture btnConfInv = new Texture("botones/btnBack.png");
        TextureRegionDrawable trdBtConfInv = new TextureRegionDrawable(new TextureRegion(btnConfInv));

        ImageButton btnH = new ImageButton(trdBtconf,trdBtConfInv);

        btnH.setPosition(ANCHO * .8f, ALTO * .18F, Align.topLeft);

        btnH.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.ACERCA_DE));
            }
        });

        escenaHistoria.addActor(btnH);
        Gdx.input.setInputProcessor(escenaHistoria);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(fondoHistoria, 0, 0);
        batch.end();
        escenaHistoria.draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        juego.getManager().unload("fondos/Historia.jpeg");
        juego.getManager().unload("botones/btnBack1.png");
        juego.getManager().unload("botones/btnBack.png");
        batch.dispose();
    }
}
