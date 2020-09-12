package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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
        fondomenu = new Texture("fondos/fondomenu.JPG");
        crearMenu();
    }

    private void crearMenu() {
        escenaMenu = new Stage(vista);
        //Boton de Nueva Partida
        Texture btnNuevaPartida = new Texture("botones/botonNP.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Boton de Reanudar
        Texture btnReanudar = new Texture("botones/botonR.png");
        TextureRegionDrawable trdBtnReanudar = new TextureRegionDrawable(new TextureRegion(btnReanudar));

        //Inverso de boton de Nueva Partida
        Texture btnNuevaPartidaInv = new Texture("botones/botonNPInv.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        //Inverso de boton de Reanudar
        Texture btnReanudarInv = new Texture("botones/botonRInv.png");
        TextureRegionDrawable trdBtnReanudarInv = new TextureRegionDrawable(new TextureRegion(btnReanudarInv));


        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);
        ImageButton btnR = new ImageButton(trdBtnReanudar, trdBtnReanudarInv);
        btnNP.setPosition(ANCHO*.65f,ALTO*.75F, Align.topLeft);
        btnR.setPosition(ANCHO*.65f,ALTO*.60F, Align.topLeft);
        escenaMenu.addActor(btnNP);
        escenaMenu.addActor(btnR);
        Gdx.input.setInputProcessor(escenaMenu);
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
