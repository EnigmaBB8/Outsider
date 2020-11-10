package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaLucha2 extends Pantalla {
    private final Juego juego;
    private Stage escenaNivel2;
    private Texture fondoNivel2;
    private Texture pilaP2;
    private Texture pilaV2;

    //Personaje
    private Personaje personaje;
    private Texture texturaPersonaje;
    // Proyectil
    private Texture texturaProyectil;
    private Array<BolasMagicas> arrBolasMagicas;

    //Sonidos
    private Sound efectoSalto;
    private Sound efectoFlecha;

    //Texto
    private Texto texto;
    private float bateriaN2=100;
    private float vidaVillano2 = 100;

    //Pausa
    private PantallaLucha2.EstadoJuego estado= PantallaLucha2.EstadoJuego.JUGANDO; // Jugando, Perdiendo, Ganar y Perder
    private PantallaLucha2.EscenaPausa escenaPausa;

    public PantallaLucha2(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoNivel2 = new Texture("fondos/fondonivel2.png");
        pilaP2 = new Texture("sprites/pilaP2.png");
        pilaV2 = new Texture("sprites/pilaP2.png");
        crearNivel2();
        crearPersonaje();
        crearBolaMagica();
        crearSonido();
        crearTexto();
    }

    private void crearTexto() {
        texto=new Texto("Texto/game.fnt");
    }

    private void crearSonido() {
        AssetManager manager = new AssetManager();
        manager.load("Efectos/salto.mp3", Sound.class);
        manager.load("Efectos/Flecha.mp3", Sound.class);
        manager.finishLoading();
        efectoSalto = manager.get("Efectos/salto.mp3");
        efectoFlecha = manager.get("Efectos/Flecha.mp3");

    }

    private void crearBolaMagica() {
        texturaProyectil = new Texture("Proyectiles/bolasMagicas.png");
        arrBolasMagicas = new Array<>();
    }

    private void crearPersonaje() {
        texturaPersonaje=new Texture("sprites/personaje.png");
        personaje=new Personaje(texturaPersonaje,ANCHO*0.05f,125);
    }

    private void crearNivel2() {
        escenaNivel2 = new Stage(vista);
        ///Boton de Pausa
        Texture btnNuevaPartida = new Texture("botones/BtnPausa2.png");
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

        //Inverso de Pausa
        Texture btnNuevaPartidaInv = new Texture("botones/BtnPausa2.png");
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
        ImageButton bntDerecha = new ImageButton(trBntDer,trdBtnDeIn);
        ImageButton bntSalta = new ImageButton(trBntSaltar,trBntSaltarIn);
        ImageButton bntDisparas = new ImageButton(trTirar,trBntDispararInv);

        btnNP.setPosition(ANCHO * .46f, ALTO * .96F, Align.topLeft);
        btnIzquierda.setPosition(ANCHO*.05f,ALTO*.148f,Align.topLeft);
        bntDerecha.setPosition(ANCHO*.15f,ALTO*.14f,Align.topLeft);
        bntSalta.setPosition(ANCHO*.70f,ALTO*.15f, Align.topLeft);
        bntDisparas.setPosition(ANCHO*.85f,ALTO*.15f,Align.topLeft);

        //Boton derecha
        bntDerecha.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstadoCaminando(EstadoCaminando.DERECHA);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstadoCaminando(EstadoCaminando.QUIETO_DERECHA);
            }
        });

        //Boton izquierda
        btnIzquierda.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstadoCaminando(EstadoCaminando.IZQUIERDA);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstadoCaminando(EstadoCaminando.QUIETO_IZQUIERDA);
            }
        });

        //Boton saltar
        bntSalta.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (personaje.getEstado() != EstadoKAIM.SALTANDO) {
                    personaje.saltar();
                    efectoSalto.play();
                }
            }
        });
        // Disparo
        bntDisparas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) { super.clicked(event, x, y);
                if (arrBolasMagicas.size < 5) {
                    BolasMagicas BolasMagicas = new BolasMagicas(texturaProyectil, personaje.sprite.getX(), personaje.sprite.getY() + personaje.sprite.getHeight()*0.5f);
                    arrBolasMagicas.add(BolasMagicas);
                    efectoFlecha.play();

                } }
        });
        //Pausa
        btnNP.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(estado== PantallaLucha2.EstadoJuego.JUGANDO) {
                    estado = PantallaLucha2.EstadoJuego.PAUSADO;
                    //Crear escena Pausa
                    if(escenaPausa==null){
                        escenaPausa=new PantallaLucha2.EscenaPausa(vista,batch);
                    }
                    Gdx.input.setInputProcessor(escenaPausa);
                    Gdx.app.log("Pausa","Cambia a Pausa");

                }else if (estado== PantallaLucha2.EstadoJuego.PAUSADO){
                    estado= PantallaLucha2.EstadoJuego.JUGANDO;
                    Gdx.app.log("Pausa","Cambia a Jugando");

                }
                return true;
            }
        });


        escenaNivel2.addActor(btnNP);
        escenaNivel2.addActor(btnIzquierda);
        escenaNivel2.addActor(bntDerecha);
        escenaNivel2.addActor(bntSalta);
        escenaNivel2.addActor(bntDisparas);
        Gdx.input.setInputProcessor(escenaNivel2);


    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        if(estado == PantallaLucha2.EstadoJuego.JUGANDO) {
            actualizar();

            batch.begin();
            batch.draw(fondoNivel2, 0, 0);
            batch.draw(pilaP2,ANCHO*0.03f,ALTO*0.83f);
            batch.draw(pilaV2,ANCHO*0.8f,ALTO*0.83f);
            personaje.render(batch);
            escenaNivel2.draw();

            dibujarBolasMagicas();
            dibujarVidaPersonaje();
            dibujarVidaVillano();

            batch.end();

        }else if(estado == PantallaLucha2.EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }
    }

    private void dibujarVidaPersonaje() {
        int vidaPersonaje = (int)bateriaN2;
        texto.mostrarMensaje(batch,vidaPersonaje+"%",ANCHO*0.11f,ALTO*0.93f);
    }

    private void dibujarVidaVillano() {
        int VidaVillano2 = (int)vidaVillano2;
        texto.mostrarMensaje(batch,VidaVillano2+"%",ANCHO*0.88f,ALTO*0.93f);
    }

    private void dibujarBolasMagicas() {
        for (BolasMagicas proyectil :
                arrBolasMagicas) {
            proyectil.render(batch);
        }
    }

    private void actualizar() {
        actualizarProyectil();
    }

    private void actualizarProyectil() {
        for (int i=arrBolasMagicas.size-1; i>=0; i--) {
            BolasMagicas proyectil = arrBolasMagicas.get(i);
            proyectil.moverDerecha();
            if (proyectil.sprite.getX()>ANCHO) {
                arrBolasMagicas.removeIndex(i);
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        fondoNivel2.dispose();
        batch.dispose();
    }

    //Estados Juego
    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        GANANDO1,
        GANANDO2,
        GANANDO3,
        GANANDO4,
        PERDIO
    }
    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, SpriteBatch batch){
            super(vista,batch);
            Texture textura=new Texture("fondos/PausaN2.png");
            Image imgPausa=new Image(textura);
            imgPausa.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);
            this.addActor(imgPausa); //Fondo

            //Botones
            // Boton Reanudar
            Texture bntReanudar = new Texture("botones/BtnReanudarN2.png");
            TextureRegionDrawable trReanudar = new TextureRegionDrawable(new TextureRegion(bntReanudar));
            //Boton Menu
            Texture bntMenu = new Texture("botones/BtnMenuN2.png");
            TextureRegionDrawable trMenu = new TextureRegionDrawable(new TextureRegion(bntMenu));
            //Boton Musica
            Texture bntMusica = new Texture("botones/BtnMusicN2.png");
            TextureRegionDrawable trMusica= new TextureRegionDrawable(new TextureRegion(bntMusica));
            //Boton Sonido
            Texture bntSonido = new Texture("botones/BtnSonidoN2.png");
            TextureRegionDrawable trSonido =new TextureRegionDrawable(new TextureRegion(bntSonido));

            //Inverso de Reanudar
            Texture btnReanudarInv= new Texture("botones/BtnReanudarN2Inv.png");
            TextureRegionDrawable trdBtReanudarInv = new TextureRegionDrawable(new TextureRegion(btnReanudarInv));
            //Inverso de Menu
            Texture btnMenuInv= new Texture("botones/BtnMenuN2Inv.png");
            TextureRegionDrawable trdBtMenuInv = new TextureRegionDrawable(new TextureRegion(btnMenuInv));
            //Inverso de Musica
            Texture btnMusicaInv= new Texture("botones/BtnMusicN2Inv.png");
            TextureRegionDrawable trdBtMusicanv = new TextureRegionDrawable(new TextureRegion(btnMusicaInv));
            //Inverso de Sonido
            Texture btnSonidoInv= new Texture("botones/BtnSonidoN2Inv.png");
            TextureRegionDrawable trdBtSonidonv = new TextureRegionDrawable(new TextureRegion(btnSonidoInv));

            ImageButton btnReanuda = new ImageButton(trReanudar, trdBtReanudarInv);
            ImageButton btnMenu = new ImageButton(trMenu, trdBtMenuInv);
            ImageButton btnMusica = new ImageButton(trMusica, trdBtMusicanv);
            ImageButton btnSonido = new ImageButton(trSonido, trdBtSonidonv);
            btnSonido.setPosition(ANCHO * .82f, ALTO *0.60f, Align.top);
            btnMusica.setPosition(ANCHO * .62f, ALTO *0.60f, Align.top);
            btnReanuda.setPosition(ANCHO * .10f, ALTO *0.60f, Align.topLeft);
            btnMenu.setPosition(ANCHO * .32f, ALTO *0.60f, Align.topLeft);

            btnReanuda.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado= PantallaLucha2.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaNivel2);
                    Gdx.app.log("Pausa","Reanuda");
                }
            });
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado= PantallaLucha2.EstadoJuego.JUGANDO;
                    juego.setScreen(new PantallaMenu(juego));
                }
            });

            this.addActor(btnReanuda);
            this.addActor(btnMenu);
            this.addActor(btnMusica);
            this.addActor(btnSonido);
        }
    }
}
