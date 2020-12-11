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
        configurarMusica();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void configurarMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        if(musicaFondo==true){
            //Prender musica
            juego.reproducirMusica();
            juego.detenerMusicaN1();
            juego.detenerMusicaN2();
        }
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

        btnNP.setPosition(ANCHO * .48f, ALTO * .39F, Align.topLeft);

        btnNP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                juego.setScreen(new PantallaCargando(juego, Pantallas.MENU));

            }
        });
        escenaConf.addActor(btnNP);
        Gdx.input.setInputProcessor(escenaConf);


        //Boton musica
        Texture btnMusica = new Texture("botones/btnconfiguracionMusica.png");
        TextureRegionDrawable trdBtMusica = new TextureRegionDrawable(new TextureRegion(btnMusica));
        //inverso boton musica
        Texture btnMusicaInv = new Texture("botones/btnconfiguracionMusicaInv.png");
        TextureRegionDrawable trdBtMusicaInv = new TextureRegionDrawable(new TextureRegion(btnMusicaInv));

        //Boton Música (Efecto Apagado/Encendido)
        final Button.ButtonStyle estiloPrendido=new Button.ButtonStyle(trdBtMusica,trdBtMusicaInv,null);
        final Button.ButtonStyle estiloApagado=new Button.ButtonStyle(trdBtMusicaInv,trdBtMusica,null);

        final ImageButton.ImageButtonStyle Prendido=new ImageButton.ImageButtonStyle(estiloPrendido);
        final ImageButton.ImageButtonStyle Apagado=new ImageButton.ImageButtonStyle(estiloApagado);

        final ImageButton btnM = new ImageButton(trdBtMusica, trdBtMusicaInv);
        btnM.setPosition(ANCHO * .7F, ALTO * .3F, Align.center);

        //Listener Música
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
                    btnM.setStyle(Prendido);
                    juego.reproducirMusica();
                    preferencias.putBoolean("General",true);
                }else{
                    //Apagar musica
                    btnM.setStyle(Apagado);
                    juego.detenerMusica();
                    juego.detenerMusicaN1();
                    juego.detenerMusicaN2();
                    juego.detenerMusicaN3();
                    preferencias.putBoolean("General",false);
                }
                preferencias.flush();
                Gdx.app.log("MUSICA VALOR FINAL"," "+musicaFondo);

            }
        });

        //Boton sonido
        Texture btnSonido = new Texture("botones/btnconfiguracionSonido.png");
        TextureRegionDrawable trdBtnSonido = new TextureRegionDrawable(new TextureRegion(btnSonido));
        //inverso boton sonido
        Texture btnSonidoInv = new Texture("botones/btnconfiguracionSonidoInv.png");
        TextureRegionDrawable trdBtnSonidoInv = new TextureRegionDrawable(new TextureRegion(btnSonidoInv));

        //Boton Sonido (Efecto Apagado/Encendido)
        final Button.ButtonStyle estiloPrendidoSonido = new Button.ButtonStyle(trdBtnSonido,trdBtnSonidoInv,null);
        final Button.ButtonStyle estiloApagadosonido = new Button.ButtonStyle(trdBtnSonidoInv,trdBtnSonido,null);

        final ImageButton.ImageButtonStyle PrendidoSonido = new ImageButton.ImageButtonStyle(estiloPrendidoSonido);
        final ImageButton.ImageButtonStyle ApagadoSonido =new ImageButton.ImageButtonStyle(estiloApagadosonido);

        final ImageButton btnS = new ImageButton(trdBtnSonido, trdBtnSonidoInv);
        btnS.setPosition(ANCHO * .86F, ALTO * .3F, Align.center);

        //Leer preferencias
        final Preferences preferences=Gdx.app.getPreferences("Sonido");
        boolean Sonido=preferences.getBoolean("GeneralSonido");
        if(Sonido==true){
            btnS.setStyle(PrendidoSonido);
        }else{
            btnS.setStyle(ApagadoSonido);
        }

        btnS.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Preferences preferenciasSonido = Gdx.app.getPreferences("Sonido");
                boolean Sonido = preferenciasSonido.getBoolean("GeneralSonido");
                Gdx.app.log("SonidoB", " " + Sonido);
                if(Sonido==false) {
                    //Prender Sonido
                    btnS.setStyle(PrendidoSonido);
                    preferenciasSonido.putBoolean("GeneralSonido",true);
                    //preferences.flush();
                }else{
                    //Apagar Sonido
                    btnS.setStyle(ApagadoSonido);
                    preferenciasSonido.putBoolean("GeneralSonido",false);
                    //preferences.flush();
                }
                preferenciasSonido.flush();
            }
        });

        escenaConf.addActor(btnM);
        escenaConf.addActor(btnS);
        Gdx.input.setInputProcessor(escenaConf);

        //Configuracion para la vista del boton Musica
        //Leer Preferencias
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        if(musicaFondo==true){
            //Música prendida
            btnM.setStyle(Prendido);
        }else{
            //Música Apagada
            btnM.setStyle(Apagado);
        }
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
        juego.getManager().unload("fondos/fondoconfiguracion.png");
        juego.getManager().unload("botones/btnBack1.png");
        juego.getManager().unload("botones/btnBack.png");
        juego.getManager().unload("botones/btnconfiguracionMusica.png");
        juego.getManager().unload("botones/btnconfiguracionMusicaInv.png");
        juego.getManager().unload("botones/btnconfiguracionSonido.png");
        juego.getManager().unload("botones/btnconfiguracionSonidoInv.png");
        batch.dispose();
    }
}
