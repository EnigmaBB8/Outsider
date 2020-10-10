package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class PantallaLucha1 extends Pantalla {
    private final Juego juego;
    private Stage escenaNivel1;
    private Texture Titan1;
    private Texture fondoNivel1;
    //Personaje
    private Personaje personaje;
    private Texture texturaPersonaje;


    public PantallaLucha1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoNivel1 = new Texture("fondos/fondonivel1.JPG");
        Titan1 = new Texture("sprites/Titan1.png");
        crearNivel1();
        crearPersonaje();


    }

    private void crearPersonaje() {
        texturaPersonaje=new Texture("sprites/personaje.jpg");
        personaje=new Personaje(texturaPersonaje,ANCHO*0.05f,128);
    }

    private void crearNivel1() {

            escenaNivel1 = new Stage(vista);
            ///Boton de regreso a menu
            Texture btnNuevaPartida = new Texture("botones/BtnMP.png");
            TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));
            //Boton Izquierda
            Texture bntIz = new Texture("botones/BotonIzquierda.png");
            TextureRegionDrawable trBntIz = new TextureRegionDrawable(new TextureRegion(bntIz));
            //Boton Derecha
            Texture bntDer = new Texture("botones/BotonDerecha.png");
            TextureRegionDrawable trBntDer = new TextureRegionDrawable(new TextureRegion(bntDer));
            //Boton Saltar
            Texture bntSaltar = new Texture("botones/BotonSaltar.png");
            TextureRegionDrawable trBntSaltar = new TextureRegionDrawable(new TextureRegion(bntSaltar));
            // Boton Disparar
            Texture bntDispara = new Texture("botones/BotonDisparar.png");
            TextureRegionDrawable trTirar = new TextureRegionDrawable(new TextureRegion(bntDispara));


            //Inverso de boton de regreso a menu
            Texture btnNuevaPartidaInv = new Texture("botones/BtnMP.png");
            TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

            //Inverso de Boton Izquierda
            Texture btnIzIn = new Texture("botones/BotonIzquierdaInv.png");
            TextureRegionDrawable trdBtnIzIn = new TextureRegionDrawable(new TextureRegion(btnIzIn));

            //Inverso de Boton Derecha
            Texture bntDerIn = new Texture("botones/BotonDerechaInv.png");
            TextureRegionDrawable trdBtnDeIn = new TextureRegionDrawable(new TextureRegion(bntDerIn));

            //Inverso de boton Saltar
            Texture bntSaltarIn = new Texture("botones/BotonSaltarInv.png");
            TextureRegionDrawable trBntSaltarIn = new TextureRegionDrawable(new TextureRegion(bntSaltarIn));

            //Inverso de Boton Disparar
            Texture bntDisparaIn = new Texture("botones/BotonDispararInv.png");
            TextureRegionDrawable trBntDispararInv = new TextureRegionDrawable(new TextureRegion(bntDisparaIn));



            ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);
            ImageButton btnIzquierda = new ImageButton(trBntIz,trdBtnIzIn);
            final ImageButton bntDerecha = new ImageButton(trBntDer,trdBtnDeIn);
            ImageButton bntSalta = new ImageButton(trBntSaltar,trBntSaltarIn);
            ImageButton bntDisparas = new ImageButton(trTirar,trBntDispararInv);

            btnNP.setPosition(ANCHO * .86f, ALTO * .96F, Align.topLeft);
            btnIzquierda.setPosition(ANCHO*.05f,ALTO*.148f,Align.topLeft);
            bntDerecha.setPosition(ANCHO*.15f,ALTO*.14f,Align.topLeft);
            bntSalta.setPosition(ANCHO*.70f,ALTO*.15f, Align.topLeft);
            bntDisparas.setPosition(ANCHO*.85f,ALTO*.15f,Align.topLeft);

            btnNP.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaMenu(juego));
                }
            });
            bntDerecha.addListener(new ClickListener(){
                public void clicked(InputEvent event,float x, float y){
                    super.clicked(event, x, y);
                    personaje.sprite.setX(personaje.sprite.getX()+10);
                }
            });
        btnIzquierda.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                super.clicked(event, x, y);
                personaje.sprite.setX(personaje.sprite.getX()-10);


            }
        });


            escenaNivel1.addActor(btnNP);
            escenaNivel1.addActor(btnIzquierda);
            escenaNivel1.addActor(bntDerecha);
            escenaNivel1.addActor(bntSalta);
            escenaNivel1.addActor(bntDisparas);
            Gdx.input.setInputProcessor(escenaNivel1);
        }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        batch.draw(fondoNivel1, 0, 0);
        batch.draw(Titan1,800,100);
        personaje.render(batch);
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
