package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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

    public PantallaMenu(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondomenu = juego.getManager().get("fondos/fondomenu.jpeg");
        crearMenu();
        configurarMusica();
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

    private void crearMenu() {
        escenaMenu = new Stage(vista);
        //Logo
        Texture btnLogo = juego.getManager().get("botones/Logo.png");
        TextureRegionDrawable trdBtnLogo = new TextureRegionDrawable(new TextureRegion(btnLogo));

        //Boton de Nueva Partida
        Texture btnNuevaPartida = juego.getManager().get("botones/botonNP.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Boton de Acerca De
        Texture btnAcercaDe = juego.getManager().get("botones/botonAD.png");
        TextureRegionDrawable trdBtnAcercaDe = new TextureRegionDrawable(new TextureRegion(btnAcercaDe));

        //Boton de Ayuda
        Texture btnAyuda = juego.getManager().get("botones/botonAyuda.png");
        TextureRegionDrawable trdBtnAyuda = new TextureRegionDrawable(new TextureRegion(btnAyuda));

        //Boton de Configuracion
        Texture btnConf = juego.getManager().get("botones/botonC.png");
        TextureRegionDrawable trdBtnConf = new TextureRegionDrawable(new TextureRegion(btnConf));

        /*
        Botones inversos a los anterioires
         */

        //Logo
        Texture btnLogoInv = juego.getManager().get("botones/Logo.png");
        TextureRegionDrawable trdBtnLogoInv = new TextureRegionDrawable(new TextureRegion(btnLogoInv));

        //Inverso de boton de Nueva Partida
        Texture btnNuevaPartidaInv = juego.getManager().get("botones/botonNPInv.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        //Inverso de boton de Acerca De
        Texture btnAcercaDeInv = juego.getManager().get("botones/botonADInv.png");
        TextureRegionDrawable trdBtnAcercaDeInv = new TextureRegionDrawable(new TextureRegion(btnAcercaDeInv));

        //Inverso de boton de Acerca De
        Texture btnAyudaInv = juego.getManager().get("botones/botonAyudaInv.png");
        TextureRegionDrawable trdBtnAyudaDeInv = new TextureRegionDrawable(new TextureRegion(btnAyudaInv));

        //Inverso de boton de Configuracion
        Texture btnConfInv = juego.getManager().get("botones/botonCInv.png");
        TextureRegionDrawable trdBtnConfInv = new TextureRegionDrawable(new TextureRegion(btnConfInv));

        /*
        Creacion de los botones y sus inversos
         */
        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);
        ImageButton btnL = new ImageButton(trdBtnLogo, trdBtnLogoInv);
        ImageButton btnAD =  new ImageButton(trdBtnAcercaDe,trdBtnAcercaDeInv);
        ImageButton btnAy = new ImageButton(trdBtnAyuda,trdBtnAyudaDeInv);
        ImageButton btnC = new ImageButton(trdBtnConf,trdBtnConfInv);

        /*
        Presentacion de los botones
         */
        btnL.setPosition(ANCHO*.47f,ALTO*.98f, Align.topRight);
        btnNP.setPosition(ANCHO*.68f,ALTO*.9F, Align.topLeft);
        btnAD.setPosition(ANCHO*.6f,ALTO*.58F, Align.topLeft);
        btnC.setPosition(ANCHO*.6f,ALTO*.42F, Align.topLeft);
        btnAy.setPosition(ANCHO*.6f,ALTO*.26F, Align.topLeft);

        escenaMenu.addActor(btnNP);
        escenaMenu.addActor(btnL);
        escenaMenu.addActor(btnAD);
        escenaMenu.addActor(btnC);
        escenaMenu.addActor(btnAy);

        Gdx.input.setInputProcessor(escenaMenu);

        boolean b = btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.MAPA));
            }
        });

        boolean a = btnAD.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.ACERCA_DE));
            }
        });

        btnC.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.CONFIGURACION));
            }
        });

        btnAy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.AYUDA));
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
        juego.getManager().unload("fondos/fondomenu.jpeg");
        juego.getManager().unload("botones/Logo.png");
        juego.getManager().unload("botones/botonNP.png");
        juego.getManager().unload("botones/botonR.png");
        juego.getManager().unload("botones/botonAD.png");
        juego.getManager().unload("botones/botonAyuda.png");
        juego.getManager().unload("botones/botonC.png");
        juego.getManager().unload("botones/botonNPInv.png");
        juego.getManager().unload("botones/botonRInv.png");
        juego.getManager().unload("botones/botonADInv.png");
        juego.getManager().unload("botones/botonAyudaInv.png");
        juego.getManager().unload("botones/botonCInv.png");
        batch.dispose();
    }
}