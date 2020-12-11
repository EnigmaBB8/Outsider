package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void configurarMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        Gdx.app.log("MUSICA Mapa", " " + musicaFondo);
        if (musicaFondo == true) {
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
        final int NivelDisponible=0;

        ///Boton de regreso a menu
        Texture btnNuevaPartida = new Texture("botones/btnBack1.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        ///Boton a Nivel 1
        Texture botonNivel1 = new Texture("botones/BotonNivel1.png");
        TextureRegionDrawable trBotonNivel1 = new TextureRegionDrawable(new TextureRegion(botonNivel1));
        ImageButton btnNivel1 = new ImageButton(trBotonNivel1, trBotonNivel1);

        ///Boton a Nivel 2
        Texture botonNivel2 = new Texture("botones/BotonNivel2.png");
        TextureRegionDrawable trBotonNivel2 = new TextureRegionDrawable(new TextureRegion(botonNivel2));

        Texture botonNivelSAcc2 = new Texture("fondos/bloqueoNivel2.png");
        TextureRegionDrawable botonNivelSAcc2I = new TextureRegionDrawable(new TextureRegion(botonNivelSAcc2));

        //Botón Nivel 2 (Efecto Acceso/Sin Acceso al Nivel)
        final Button.ButtonStyle estiloAccesoNivel2 = new Button.ButtonStyle(trBotonNivel2, trBotonNivel2, null);
        final Button.ButtonStyle estiloSinAccesoNivel2 = new Button.ButtonStyle(botonNivelSAcc2I, botonNivelSAcc2I, null);
        final ImageButton.ImageButtonStyle AccesoNivel2 = new ImageButton.ImageButtonStyle(estiloAccesoNivel2);
        final ImageButton.ImageButtonStyle SinAccesoNivel2 = new ImageButton.ImageButtonStyle(estiloSinAccesoNivel2);
        final ImageButton btnNivel2 = new ImageButton(trBotonNivel2, trBotonNivel2);

        ///Boton a Nivel 3
        Texture botonNivel3 = new Texture("botones/BotonNivel3.png");
        TextureRegionDrawable trBotonNivel3 = new TextureRegionDrawable(new TextureRegion(botonNivel3));

        Texture botonNivelSAcc = new Texture("fondos/bloqueoNivel3.png");
        TextureRegionDrawable botonNivelSAcc3 = new TextureRegionDrawable(new TextureRegion(botonNivelSAcc));

        //Botón Nivel 3 (Efecto Acceso/Sin Acceso al Nivel)
        final Button.ButtonStyle estiloAccesoNivel3 = new Button.ButtonStyle(trBotonNivel3, trBotonNivel3, null);
        final Button.ButtonStyle estiloSinAccesoNivel3 = new Button.ButtonStyle(botonNivelSAcc3, botonNivelSAcc3, null);
        final ImageButton.ImageButtonStyle AccesoNivel3 = new ImageButton.ImageButtonStyle(estiloAccesoNivel3);
        final ImageButton.ImageButtonStyle SinAccesoNivel3 = new ImageButton.ImageButtonStyle(estiloSinAccesoNivel3);
        final ImageButton btnNivel3 = new ImageButton(trBotonNivel3, trBotonNivel3);

        ///Boton a Nivel 4
        Texture botonNivel4 = new Texture("botones/BotonNivel4.png");
        TextureRegionDrawable trBotonNivel4 = new TextureRegionDrawable(new TextureRegion(botonNivel4));

        Texture botonNivelSAcc4 = new Texture("fondos/bloqueoNivel4.png");
        TextureRegionDrawable botonNivelSAcc4I = new TextureRegionDrawable(new TextureRegion(botonNivelSAcc4));

        //Botón Nivel 4 (Efecto Acceso/Sin Acceso al Nivel)
        final Button.ButtonStyle estiloAccesoNivel4 = new Button.ButtonStyle(trBotonNivel4, trBotonNivel4, null);
        final Button.ButtonStyle estiloSinAccesoNivel4 = new Button.ButtonStyle(botonNivelSAcc4I, botonNivelSAcc4I, null);
        final ImageButton.ImageButtonStyle AccesoNivel4 = new ImageButton.ImageButtonStyle(estiloAccesoNivel4);
        final ImageButton.ImageButtonStyle SinAccesoNivel4 = new ImageButton.ImageButtonStyle(estiloSinAccesoNivel4);
        final ImageButton btnNivel4 = new ImageButton(trBotonNivel4, trBotonNivel4);

        //Inverso de boton de regreso a menu

        Texture btnNuevaPartidaInv = new Texture("botones/btnBack.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));
        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);


        btnNP.setPosition(ANCHO * .01f, ALTO * .97F, Align.topLeft);
        btnNivel1.setPosition(ANCHO * .42f, ALTO * .32F, Align.topLeft);
        btnNivel2.setPosition(ANCHO * .7f, ALTO * .62F, Align.topLeft);
        btnNivel3.setPosition(ANCHO * .38f, ALTO * .77F, Align.topLeft);
        btnNivel4.setPosition(ANCHO * .57f, ALTO * .96F, Align.topLeft);

        //Leer Preferencias
        // Nivel 2

        Preferences preferences=Gdx.app.getPreferences("Nivel");
        int NivelActivado=preferences.getInteger("NivelGeneral",NivelDisponible);
        preferences.flush();

        if(NivelActivado>=2){
            btnNivel2.setStyle(AccesoNivel2);
        }else{
            btnNivel2.setStyle(SinAccesoNivel2);
        }
        // Nivel 3
        if(NivelActivado>=3){
            btnNivel3.setStyle(AccesoNivel3);
        }else{
            btnNivel3.setStyle(SinAccesoNivel3);
        }
        // Nivel 4
        if(NivelActivado==4){
            btnNivel4.setStyle(AccesoNivel4);
        }else{
            btnNivel4.setStyle(SinAccesoNivel4);
        }


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
                Preferences preferences=Gdx.app.getPreferences("Nivel");
                preferences.flush();
                int NivelActivado=preferences.getInteger("NivelGeneral",NivelDisponible);
                if(NivelActivado>=2) {
                    btnNivel2.setStyle(AccesoNivel2);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL2));
                }
                btnNivel2.setStyle(SinAccesoNivel2);
            }
        });

        btnNivel3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Preferences preferences=Gdx.app.getPreferences("Nivel");
                int NivelActivado=preferences.getInteger("NivelGeneral",NivelDisponible);
                preferences.flush();
                if(NivelActivado>=3) {
                    btnNivel3.setStyle(AccesoNivel3);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL3));
                }
                btnNivel3.setStyle(SinAccesoNivel3);
            }
        });

        btnNivel4.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                Preferences preferences=Gdx.app.getPreferences("Nivel");
                int NivelActivado=preferences.getInteger("NivelGeneral",NivelDisponible);
                preferences.flush();
                super.clicked(event, x, y);
                if(NivelActivado==4) {
                    btnNivel4.setStyle(AccesoNivel4);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL4));
                }
                btnNivel4.setStyle(SinAccesoNivel4);
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
        juego.getManager().unload("botones/BotonNivel2.png");
        juego.getManager().unload("botones/BotonNivel3.png");
        juego.getManager().unload("botones/BotonNivel4.png");
        juego.getManager().unload("fondos/bloqueoNivel2.png");
        juego.getManager().unload("fondos/bloqueoNivel3.png");
        juego.getManager().unload("fondos/bloqueoNivel4.png");
        batch.dispose();
    }
}