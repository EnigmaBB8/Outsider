package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaMenu extends Pantalla  {

    private final Juego juego;
    private Texture fondomenu;
    private Stage escenaMenu;
    private Texture Kaim;

    // Música / Efectos de sonido
    private Music musicaFondo;

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondomenu = new Texture("fondos/fondomenu.jpeg");
        crearMenu();
        crearAudio();
    }

    private void crearAudio() {
        AssetManager manager = new AssetManager();
        manager.load("Musica/musicaMenu.mp3", Music.class);  // programa la carga
        manager.finishLoading();    // ESPERA
        musicaFondo = manager.get("Musica/musicaMenu.mp3");
        musicaFondo.setVolume(0.1f);
        musicaFondo.setLooping(true);
        musicaFondo.play();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);
        //Logo
        Texture btnLogo = new Texture("botones/Logo.png");
        TextureRegionDrawable trdBtnLogo = new TextureRegionDrawable(new TextureRegion(btnLogo));

        //Boton de Nueva Partida
        Texture btnNuevaPartida = new Texture("botones/botonNP.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Boton de Reanudar
        Texture btnReanudar = new Texture("botones/botonR.png");
        TextureRegionDrawable trdBtnReanudar = new TextureRegionDrawable(new TextureRegion(btnReanudar));

        //Boton de Acerca De
        Texture btnAcercaDe = new Texture("botones/botonAD.png");
        TextureRegionDrawable trdBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(btnAcercaDe));

        //Boton de Ayuda
        Texture btnAyuda = new Texture("botones/botonAyuda.png");
        TextureRegionDrawable trdBtnAyuda = new TextureRegionDrawable(new TextureRegion(btnAyuda));

        //Boton de Configuracion
        Texture btnConf = new Texture("botones/botonC.png");
        TextureRegionDrawable trdBtnConf = new TextureRegionDrawable(new TextureRegion(btnConf));

        /*
        Botones inversos a los anterioires
         */

        //Logo
        Texture btnLogoInv = new Texture("botones/Logo.png");
        TextureRegionDrawable trdBtnLogoInv = new TextureRegionDrawable(new TextureRegion(btnLogoInv));

        //Inverso de boton de Nueva Partida
        Texture btnNuevaPartidaInv = new Texture("botones/botonNPInv.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        //Inverso de boton de Reanudar
        Texture btnReanudarInv = new Texture("botones/botonRInv.png");
        TextureRegionDrawable trdBtnReanudarInv = new TextureRegionDrawable(new TextureRegion(btnReanudarInv));

        //Inverso de boton de Acerca De
        Texture btnAcercaDeInv = new Texture("botones/botonADInv.png");
        TextureRegionDrawable trdBtnAcercaDeInv = new TextureRegionDrawable(new TextureRegion(btnAcercaDeInv));

        //Inverso de boton de Acerca De
        Texture btnAyudaInv = new Texture("botones/botonAyudaInv.png");
        TextureRegionDrawable trdBtnAyudaDeInv = new TextureRegionDrawable(new TextureRegion(btnAyudaInv));

        //Inverso de boton de Configuracion
        Texture btnConfInv = new Texture("botones/botonCInv.png");
        TextureRegionDrawable trdBtnConfInv = new TextureRegionDrawable(new TextureRegion(btnConfInv));

        /*
        Creacion de los botones y sus inversos
         */
        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);
        ImageButton btnR = new ImageButton(trdBtnReanudar, trdBtnReanudarInv);
        ImageButton btnL = new ImageButton(trdBtnLogo, trdBtnLogoInv);
        ImageButton btnAD =  new ImageButton(trdBtnAcercaDe,trdBtnAcercaDeInv);
        ImageButton btnAy = new ImageButton(trdBtnAyuda,trdBtnAyudaDeInv);
        ImageButton btnC = new ImageButton(trdBtnConf,trdBtnConfInv);

        /*
        Presentacion de los botones
         */
        btnL.setPosition(ANCHO*.47f,ALTO*.98f, Align.topRight);
        btnNP.setPosition(ANCHO*.60f,ALTO*.9F, Align.topLeft);
        btnR.setPosition(ANCHO*.6f,ALTO*.74F, Align.topLeft);
        btnAD.setPosition(ANCHO*.6f,ALTO*.58F, Align.topLeft);
        btnC.setPosition(ANCHO*.6f,ALTO*.42F, Align.topLeft);
        btnAy.setPosition(ANCHO*.6f,ALTO*.26F, Align.topLeft);

        escenaMenu.addActor(btnNP);
        escenaMenu.addActor(btnR);
        escenaMenu.addActor(btnL);
        escenaMenu.addActor(btnAD);
        escenaMenu.addActor(btnC);
        escenaMenu.addActor(btnAy);

        Gdx.input.setInputProcessor(escenaMenu);

        boolean b = btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaLucha1(juego));
            }
        });

        boolean c = btnR.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaReanudar(juego));
            }
        });

        boolean a = btnAD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDe(juego));
            }
        });

        btnC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaConfiguracion(juego));
            }
        });

        btnAy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAyuda(juego));
            }
        });
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondomenu, 0, 0);
        batch.end();

        escenaMenu.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        fondomenu.dispose();
        batch.dispose();
    }
}

