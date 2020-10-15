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

public class PantallaPausa extends Pantalla{
    private final Juego juego;
    private Texture fondoPausa;
    private Stage escenaPausa;
    public PantallaPausa(Juego juego) {
        this.juego=juego;
    }

    @Override
    public void show() {
        fondoPausa = new Texture("fondos/fondomenu.jpeg");
        crearPausa();

    }

    private void crearPausa() {
        escenaPausa=new Stage(vista);

        ///Boton de Reinicio
        Texture btnReinicio = new Texture("botones/BtnMP.png");
        TextureRegionDrawable trdBtReinicio = new TextureRegionDrawable(new TextureRegion(btnReinicio));
        //Boton Menu
        Texture bntMenu = new Texture("botones/BotonIzquierda.png");
        TextureRegionDrawable trBntMenu= new TextureRegionDrawable(new TextureRegion(bntMenu));
        //Boton Musica
        Texture bntMusica = new Texture("botones/BotonDerecha.png");
        TextureRegionDrawable trBntMusica = new TextureRegionDrawable(new TextureRegion(bntMusica));
        //Boton Sonido
        Texture bntSonido = new Texture("botones/BotonSaltar.png");
        TextureRegionDrawable trBntsonido = new TextureRegionDrawable(new TextureRegion(bntSonido));

         /*
        Botones inversos a los anterioires
         */


        //Inverso de boton de Reinicio
        Texture btnReinicioInv = new Texture("botones/BtnMP1.png");
        TextureRegionDrawable trdBtReinicioInv = new TextureRegionDrawable(new TextureRegion(btnReinicioInv));

        //Inverso de Boton Menu
        Texture btnMenuIn = new Texture("botones/BotonIzquierdaInv.png");
        TextureRegionDrawable trdBtnMenuIn = new TextureRegionDrawable(new TextureRegion(btnMenuIn));

        //Inverso de Boton Musica
        Texture bntMusicaIn = new Texture("botones/BotonDerechaInv.png");
        TextureRegionDrawable trdBtnMusicaIn = new TextureRegionDrawable(new TextureRegion(bntMusicaIn));

        //Inverso de boton Sonido
        Texture bntSonidoIn = new Texture("botones/BotonSaltarInv.png");
        TextureRegionDrawable trBntSonidoIn = new TextureRegionDrawable(new TextureRegion(bntSonidoIn));

        /*
        Creacion de los botones y sus inversos
         */
        ImageButton btnRe = new ImageButton(trdBtReinicio, trdBtReinicioInv);
        ImageButton btnMe = new ImageButton(trBntMenu, trdBtnMenuIn);
        ImageButton btnMu = new ImageButton(trBntMusica, trdBtnMusicaIn);
        ImageButton btnSo =  new ImageButton(trBntsonido,trBntSonidoIn );

        /*
        Presentacion de los botones
         */
        btnRe.setPosition(ANCHO*.29f,ALTO/2, Align.topRight);
        btnMe.setPosition(ANCHO*.39f,ALTO/2, Align.topLeft);
        btnMu.setPosition(ANCHO*.59f,ALTO/2, Align.topLeft);
        btnSo.setPosition(ANCHO*.79f,ALTO/2, Align.topLeft);

        btnRe.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaLucha1(juego));
            }
        });

        btnMe.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));
            }
        });

        escenaPausa.addActor(btnRe);
        escenaPausa.addActor(btnMe);
        escenaPausa.addActor(btnMu);
        escenaPausa.addActor(btnSo);

        Gdx.input.setInputProcessor(escenaPausa);

    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(fondoPausa,0,0);
        batch.end();

        escenaPausa.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        fondoPausa.dispose();
        batch.dispose();

    }
}
