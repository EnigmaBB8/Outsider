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

public class PantallaAcercaDe extends Pantalla {

    private final Juego juego;
    private Texture fondoAcercaDe;
    private Stage escenaAcercaDe;

    public PantallaAcercaDe(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoAcercaDe = new Texture("fondos/fondoacercade.png");
        crearPantallaAD();
    }

    private void crearPantallaAD() {
        escenaAcercaDe = new Stage(vista);

        /*
        Botones de acción
         */

        ///Boton de regreso a menu
        Texture btnNuevaPartida = new Texture("botones/btnBack1.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Boton acerca de Karla
        Texture btnADKarla = new Texture("botones/AD_Karla1.png");
        TextureRegionDrawable trdBtnADKarla = new TextureRegionDrawable(new TextureRegion(btnADKarla));

        //Boton acerca de Manuel
        Texture btnADManuel = new Texture("botones/AD_Manuel1.png");
        TextureRegionDrawable trdBtnManuel = new TextureRegionDrawable(new TextureRegion(btnADManuel));

        //Boton acerca de Itzel
        Texture btnADItzel = new Texture("botones/AD_Itzel1.png");
        TextureRegionDrawable trdBtnItzel = new TextureRegionDrawable(new TextureRegion(btnADItzel));

        //Boton acerca de Abraham
        Texture btnADAbraham = new Texture("botones/AD_Abraham1.png");
        TextureRegionDrawable trdBtnAbraham = new TextureRegionDrawable(new TextureRegion(btnADAbraham));

        ///Boton de Historia
        Texture btnHistoria = new Texture("botones/BtnHistory.png");
        TextureRegionDrawable trdBtHistoria = new TextureRegionDrawable(new TextureRegion(btnHistoria));

        /*
        Botones inversos de accion
         */

        //Inverso de boton de regreso a menu
        Texture btnNuevaPartidaInv = new Texture("botones/btnBack.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        //Inverso de boton de Acerca de Karla
        Texture btnADKarlaInv = new Texture("botones/AD_Karla.png");
        TextureRegionDrawable trdBtnADKarlaInv = new TextureRegionDrawable(new TextureRegion(btnADKarlaInv));

        //Inverso de boton de Acerca de Manuel
        Texture btnADManuelInv = new Texture("botones/AD_Manuel.png");
        TextureRegionDrawable trdBtnManuelInv = new TextureRegionDrawable(new TextureRegion(btnADManuelInv));

        //Inverso de boton de Acerca de Itzel
        Texture btnADItzelInv = new Texture("botones/AD_Itzel.png");
        TextureRegionDrawable trdBtnItzelInv = new TextureRegionDrawable(new TextureRegion(btnADItzelInv));

        //Inverso de boton de Acerca de Abraham
        Texture btnADAbrahamInv = new Texture("botones/AD_Abraham.png");
        TextureRegionDrawable trdBtnAbrahmInv = new TextureRegionDrawable(new TextureRegion(btnADAbrahamInv));

        //Inverso de Historia
        Texture btnHistoriInv = new Texture("botones/BtnHistory1.png");
        TextureRegionDrawable trdBtHistoriaInv = new TextureRegionDrawable(new TextureRegion(btnHistoriInv ));

        /*
        Botones y su inicializacion
         */

        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);
        ImageButton btnK = new ImageButton(trdBtnADKarla, trdBtnADKarlaInv);
        ImageButton btnM = new ImageButton(trdBtnManuel, trdBtnManuelInv);
        ImageButton btnI = new ImageButton(trdBtnItzel, trdBtnItzelInv);
        ImageButton btnA = new ImageButton(trdBtnAbraham, trdBtnAbrahmInv);
        ImageButton btnH = new ImageButton(trdBtHistoria,trdBtHistoriaInv);

        /*
        Posicion e inicializacion de los botones
         */

        btnNP.setPosition(ANCHO * .83f, ALTO * .23F, Align.topLeft);
        btnK.setPosition(ANCHO*.15f,ALTO*.55f, Align.center);
        btnM.setPosition(ANCHO*.45f,ALTO*.55f, Align.center);
        btnI.setPosition(ANCHO*.15f,ALTO*.15f, Align.center);
        btnA.setPosition(ANCHO*.45f,ALTO*.15f, Align.center);
        btnH.setPosition(ANCHO * .84f, ALTO * .40F, Align.topLeft);

        /*
        Accion al darles click a los botones
         */

        btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        btnM.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDeManuel(juego));
            }
        });

        btnK.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDeKarla(juego));
            }
        });

        btnI.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDeItzel(juego));
            }
        });

        btnA.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaAcercaDeAbraham(juego));
            }
        });

        btnH.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaH(juego));
            }
        });

        /*
        Representación en escena de los botones
         */

        escenaAcercaDe.addActor(btnNP);
        escenaAcercaDe.addActor(btnK);
        escenaAcercaDe.addActor(btnM);
        escenaAcercaDe.addActor(btnI);
        escenaAcercaDe.addActor(btnA);
        escenaAcercaDe.addActor(btnH);
        Gdx.input.setInputProcessor(escenaAcercaDe);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoAcercaDe, 0, 0);
        batch.end();

        escenaAcercaDe.draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        fondoAcercaDe.dispose();
        /*juego.getManager().unload("fondos/fondoacercade.png");
        juego.getManager().unload("botones/btnBack1.png");
        juego.getManager().unload("botones/AD_Karla1.png");
        juego.getManager().unload("botones/AD_Manuel1.png");
        juego.getManager().unload("botones/AD_Itzel1.png");
        juego.getManager().unload("botones/AD_Abraham1.png");
        juego.getManager().unload("botones/BtnHistory.png");
        juego.getManager().unload("botones/btnBack.png");
        juego.getManager().unload("botones/AD_Karla.png");
        juego.getManager().unload("botones/AD_Manuel.png");
        juego.getManager().unload("botones/AD_Itzel.png");
        juego.getManager().unload("botones/AD_Abraham.png");
        juego.getManager().unload("botones/BtnHistory1.png");*/
        batch.dispose();
    }
}
