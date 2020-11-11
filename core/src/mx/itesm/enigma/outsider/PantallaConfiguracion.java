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

public class PantallaConfiguracion extends Pantalla {

    private final Juego juego;
    private Texture fondoConf;
    private Stage escenaConf;

    public PantallaConfiguracion(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoConf = new Texture("fondos/fondoconfiguracion.png");
        crearPantallaConf();
    }

    private void crearPantallaConf() {
        escenaConf = new Stage(vista);
        ///Boton de regreso a menu
        Texture btnNuevaPartida = new Texture("botones/btnBack1.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Inverso de boton de regreso a menu
        Texture btnNuevaPartidaInv = new Texture("botones/btnBack.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);

        btnNP.setPosition(ANCHO * .8f, ALTO * .25F, Align.topLeft);

        btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaMenu(juego));

            }
        });
        escenaConf.addActor(btnNP);
        Gdx.input.setInputProcessor(escenaConf);


        //Boton musica
        Texture btnMusica = new Texture("botones/btnconfM.PNG");
        TextureRegionDrawable trdBtMusica = new TextureRegionDrawable(new TextureRegion(btnMusica));
        //inverso boton musica
        Texture btnMusicaInv = new Texture("botones/btnconfMI.PNG");
        TextureRegionDrawable trdBtMusicaInv = new TextureRegionDrawable(new TextureRegion(btnMusicaInv));


        ImageButton btnM = new ImageButton(trdBtMusica, trdBtMusicaInv);
        btnM.setPosition(ANCHO * .65F, ALTO * .3F, Align.center);


        //Listener M
        btnM.addListener(new ClickListener() {
            @Override
            //Necesita arreglarse despues de darle click again crashea
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Preferences preferencias = Gdx.app.getPreferences("Musica");
                boolean musicaFondo = preferencias.getBoolean("General");
                Gdx.app.log("MUSICA"," "+musicaFondo);
                if(musicaFondo==false){
                    //Prender musica
                    juego.reproducirMusica();
                    preferencias.putBoolean("General",true);
                }else{
                    //Apagar musica
                    juego.detenerMusica();
                    juego.detenerMusicaN1();
                    preferencias.putBoolean("General",false);
                }
                preferencias.flush();
                Gdx.app.log("MUSICA VALOR FINAL"," "+musicaFondo);

            }
        });

        escenaConf.addActor(btnM);

        Gdx.input.setInputProcessor(escenaConf);
    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        batch.draw(fondoConf, 0, 0);
        batch.end();
        escenaConf.draw();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        fondoConf.dispose();
        batch.dispose();
    }
}
