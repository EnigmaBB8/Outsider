package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
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

public class PantallaMapa extends Pantalla {
    private final Juego juego;
    private Stage escenaNivel1;
    private Texture fondoPantallaReanudar;

    public PantallaMapa(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoPantallaReanudar = new Texture("fondos/fondoMapa.png");
        crearReanudar();
        configurarMusica();

    }

    private void configurarMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        Gdx.app.log("MUSICA Mapa", " " + musicaFondo);
        if(musicaFondo==true) {
            //Prender musica
            juego.reproducirMusica();
            juego.detenerMusicaN1();
            juego.detenerMusicaN2();
            juego.detenerMusicaN3();
            juego.detenerMusicaN4();
        }
    }

    private void crearReanudar() {
        escenaNivel1 = new Stage(vista);

        ///Boton de regreso a menu
        Texture btnNuevaPartida = new Texture("botones/btnBack1.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        ///Boton a Nivel 1
        Texture botonNivel1 = new Texture("botones/BotonNivel1.png");
        TextureRegionDrawable trBotonNivel1 = new TextureRegionDrawable(new TextureRegion(botonNivel1));

        ///Boton a Nivel 2
        Texture botonNivel2 = new Texture("botones/BotonNivel2.png");
        TextureRegionDrawable trBotonNivel2 = new TextureRegionDrawable(new TextureRegion(botonNivel2));

        ///Boton a Nivel 3
        Texture botonNivel3 = new Texture("botones/BotonNivel3.png");
        TextureRegionDrawable trBotonNivel3 = new TextureRegionDrawable(new TextureRegion(botonNivel3));

        ///Boton a Nivel 4
        Texture botonNivel4 = new Texture("botones/BotonNivel4.png");
        TextureRegionDrawable trBotonNivel4 = new TextureRegionDrawable(new TextureRegion(botonNivel4));

        //Inverso de boton de regreso a menu
        Texture btnNuevaPartidaInv = new Texture("botones/btnBack.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        //Inverso de boton Nivel 1
        Texture botonNivel1Inv = new Texture("botones/BotonNivel1Inv.png");
        TextureRegionDrawable trBotonVivel1Inv = new TextureRegionDrawable(new TextureRegion(botonNivel1Inv));

        //Inverso de boton Nivel 2
        Texture botonNivel2Inv = new Texture("botones/BotonNivel2Inv.png");
        TextureRegionDrawable trBotonVivel2Inv = new TextureRegionDrawable(new TextureRegion(botonNivel2Inv));

        //Inverso de boton Nivel 3
        Texture botonNivel3Inv = new Texture("botones/BotonNivel3Inv.png");
        TextureRegionDrawable trBotonVivel3Inv = new TextureRegionDrawable(new TextureRegion(botonNivel3Inv));

        //Inverso de boton Nivel 4
        Texture botonNivel4Inv = new Texture("botones/BotonNivel4Inv.png");
        TextureRegionDrawable trBotonVivel4Inv = new TextureRegionDrawable(new TextureRegion(botonNivel4Inv));

        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);
        ImageButton btnNivel1 = new ImageButton(trBotonNivel1, trBotonVivel1Inv);
        ImageButton btnNivel2 = new ImageButton(trBotonNivel2, trBotonVivel2Inv);
        ImageButton btnNivel3 = new ImageButton(trBotonNivel3, trBotonVivel3Inv);
        ImageButton btnNivel4 = new ImageButton(trBotonNivel4, trBotonVivel4Inv);

        btnNP.setPosition(ANCHO * .85f, ALTO * .23F, Align.topLeft);
        btnNivel1.setPosition(ANCHO * .42f, ALTO * .32F, Align.topLeft);
        btnNivel2.setPosition(ANCHO * .7f, ALTO * .62F, Align.topLeft);
        btnNivel3.setPosition(ANCHO * .38f, ALTO * .77F, Align.topLeft);
        btnNivel4.setPosition(ANCHO * .57f, ALTO * .96F, Align.topLeft);

        btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.MENU));
            }
        });

        btnNivel1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL1));
            }
        });

        btnNivel2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL2));
            }
        });

        btnNivel3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL3));
            }
        });

        btnNivel4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL4));
            }
        });

        escenaNivel1.addActor(btnNP);
        escenaNivel1.addActor(btnNivel1);
        escenaNivel1.addActor(btnNivel2);
        escenaNivel1.addActor(btnNivel3);
        escenaNivel1.addActor(btnNivel4);
        Gdx.input.setInputProcessor(escenaNivel1);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoPantallaReanudar, 0, 0);
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
        juego.getManager().unload("fondos/fondoMapa.png");
        juego.getManager().unload("botones/btnBack1.png");
        juego.getManager().unload("botones/btnBack.png");
        juego.getManager().unload("botones/BotonNivel1.png");
        juego.getManager().unload("botones/BotonNivel1Inv.png");
        juego.getManager().unload("botones/BotonNivel2.png");
        juego.getManager().unload("botones/BotonNivel2Inv.png");
        juego.getManager().unload("botones/BotonNivel3.png");
        juego.getManager().unload("botones/BotonNivel3Inv.png");
        juego.getManager().unload("botones/BotonNivel4.png");
        juego.getManager().unload("botones/BotonNivel4Inv.png");
        batch.dispose();
    }
}