package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PantallaLucha1 extends Pantalla {
    private final Juego juego;
    private Stage escenaNivel1;
    private Texture Titan1;
    private Texture fondoNivel1;

    //Personaje
    private Personaje personaje;
    private Texture texturaPersonaje;

    //Villano
    private Villano villano;
    private Texture texturaVillano;

    //Bolas de Fuego
    private Array<BolasDeFuego> arrBolasFuego;
    private float timerCrearBola;
    private float TIEMPO_CREA_BOLA = 1;
    private float tiempoBola = 1;
    private Texture texturaBolas;

    // Proyectil
    private Texture texturaProyectil;
    private Array<Proyectil> arrProyectil;

    //Texto
    private Texto texto;
    private float bateria=100;

    //Sonidos
    private Sound efectoSalto;

    //Pausa
    private EstadoJuego estado=EstadoJuego.JUGANDO; // Jugando, Perdiendo, Ganar y Perder
    private EscenaPausa escenaPausa;



    public PantallaLucha1(Juego juego) {
        this.juego = juego;
        //juego.detenerMusica();
        //juego.reproducirMusicaNivel1();
    }

    @Override
    public void show() {
        fondoNivel1 = new Texture("fondos/fondonivel1.JPG");
        Titan1 = new Texture("Enemigos/Titan1.png");
        crearNivel1();
        crearPersonaje();
        crearBolasFuego();
        crearProyectil();
        crearTexto();
        crearVillano();
        crearSonido();


    }

    private void crearSonido() {
        AssetManager manager = new AssetManager();
        manager.load("Efectos/salto.mp3", Sound.class);
        manager.finishLoading();
        efectoSalto = manager.get("Efectos/salto.mp3");
    }

    private void crearVillano() {
        texturaVillano=new Texture("Enemigos/Titan1.png");
        villano=new Villano(texturaVillano);
    }

    private void crearTexto() {
        texto=new Texto("Texto/game.fnt");
    }

    private void crearProyectil() {
        texturaProyectil = new Texture("Proyectiles/piedra.png");
        arrProyectil = new Array<>();
    }

    private void crearBolasFuego() {
        texturaBolas = new Texture("Enemigos/BolaDeFuego.png");
        arrBolasFuego = new Array<>();
    }

    private void crearPersonaje() {
        texturaPersonaje=new Texture("sprites/personaje.png");
        personaje=new Personaje(texturaPersonaje,ANCHO*0.05f,125);
    }

    private void crearNivel1() {

            escenaNivel1 = new Stage(vista);
            ///Boton de Pausa
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


            //Inverso de Pausa
            Texture btnNuevaPartidaInv = new Texture("botones/BtnMP1.png");
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
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (arrProyectil.size < 5) {
                        Proyectil proyectil = new Proyectil(texturaProyectil, personaje.sprite.getX(),
                                personaje.sprite.getY() + personaje.sprite.getHeight()*0.5f);
                        arrProyectil.add(proyectil);
                    }
                }
            });
            //Pausa
        btnNP.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(estado==EstadoJuego.JUGANDO) {
                    estado = EstadoJuego.PAUSADO;
                    //Crear escena Pausa
                    if(escenaPausa==null){
                        escenaPausa=new EscenaPausa(vista,batch);
                    }
                    Gdx.input.setInputProcessor(escenaPausa);
                    Gdx.app.log("Pausa","Cambia a Pausa");

                }else if (estado==EstadoJuego.PAUSADO){
                    estado=EstadoJuego.JUGANDO;
                    Gdx.app.log("Pausa","Cambia a Jugando");

                }
                return true;
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
        if(estado==EstadoJuego.JUGANDO) {
            actualizar();
            borrarPantalla(0, 0, 0.5f);
            batch.setProjectionMatrix(camara.combined);

            batch.begin();
            batch.draw(fondoNivel1, 0, 0);
            villano.render(batch);
            personaje.render(batch);
            escenaNivel1.draw();
            dibujarBolasFuego();
            dibujarProyectil();
            dibujarTexto();
            batch.end();
        }else if(estado==EstadoJuego.PAUSADO){
            escenaPausa.draw();
        }
    }

    private void dibujarTexto() {
        int VidaInt=(int)bateria;
        texto.mostrarMensaje(batch,VidaInt+"%",ANCHO*0.1f,ALTO*0.95f);
    }

    private void dibujarProyectil() {
        for (Proyectil proyectil :
                arrProyectil) {
            proyectil.render(batch);
        }
    }

    private void dibujarBolasFuego() {
        for (BolasDeFuego bola :
                arrBolasFuego) {
            bola.render(batch);
            bola.atacar();
        }
    }

    private void actualizar(){
        actualizarBolasFuego();
        actualizarProyectil();
    }

    private void actualizarProyectil() {
        for (int i=arrProyectil.size-1; i>=0; i--) {
            Proyectil proyectil = arrProyectil.get(i);
            proyectil.moverDerecha();
            proyectil.caida();
            if (proyectil.sprite.getX()>ANCHO) {
                arrProyectil.removeIndex(i);
            }
        }
    }

    private void actualizarBolasFuego() {
        timerCrearBola += Gdx.graphics.getDeltaTime();
        if (timerCrearBola>=TIEMPO_CREA_BOLA) {
            timerCrearBola = 0;
            TIEMPO_CREA_BOLA = tiempoBola + MathUtils.random()*2;
            if (tiempoBola>2) {
                tiempoBola -= 1;
            }
            BolasDeFuego bola = new BolasDeFuego(texturaBolas, 900, 220+MathUtils.random(1,3)*100);
            arrBolasFuego.add(bola);
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
        fondoNivel1.dispose();
        batch.dispose();
    }

     //Estados Juego
    private enum EstadoJuego {
     JUGANDO,
     PAUSADO,
     GANANO,
     PERDIO
    }

    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, SpriteBatch batch){
            super(vista,batch);
            Texture textura=new Texture("fondos/fondoPausaN1.png");
            Image imgPausa=new Image(textura);
            imgPausa.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);
            this.addActor(imgPausa); //Fondo

            //Botones
            // Boton Reanudar
            Texture bntReanudar = new Texture("botones/BtnReanudarN1.png");
            TextureRegionDrawable trReanudar = new TextureRegionDrawable(new TextureRegion(bntReanudar));

            //Boton Menu
            Texture bntMenu = new Texture("botones/BtnMenuN1.png");
            TextureRegionDrawable trMenu = new TextureRegionDrawable(new TextureRegion(bntMenu));
            //Boton Musica
            Texture bntMusica = new Texture("botones/BtnMusicN1.png");
            TextureRegionDrawable trMusica= new TextureRegionDrawable(new TextureRegion(bntMusica));
            //Boton Sonido
            Texture bntSonido = new Texture("botones/BtnSonidoN1.png");
            TextureRegionDrawable trSonido =new TextureRegionDrawable(new TextureRegion(bntSonido));

            //Inverso de Reanudar
            Texture btnReanudarInv= new Texture("botones/BtnReanudarN1Inv.png");
            TextureRegionDrawable trdBtReanudarInv = new TextureRegionDrawable(new TextureRegion(btnReanudarInv));

            //Inverso de Menu
            Texture btnMenuInv= new Texture("botones/BtnMenuN1Inv.png");
            TextureRegionDrawable trdBtMenuInv = new TextureRegionDrawable(new TextureRegion(btnMenuInv));
            //Inverso de Musica
            Texture btnMusicaInv= new Texture("botones/BtnMusicN1Inv.png");
            TextureRegionDrawable trdBtMusicanv = new TextureRegionDrawable(new TextureRegion(btnMusicaInv));
            //Inverso de Sonido
            Texture btnSonidoInv= new Texture("botones/BtnSonidoN1Inv.png");
            TextureRegionDrawable trdBtSonidonv = new TextureRegionDrawable(new TextureRegion(btnSonidoInv));


            ImageButton btnReanuda = new ImageButton(trReanudar, trdBtReanudarInv);
            ImageButton btnMenu = new ImageButton(trMenu, trdBtMenuInv);
            ImageButton btnMusica = new ImageButton(trMusica, trdBtMusicanv);
            ImageButton btnSonido = new ImageButton(trSonido, trdBtSonidonv);
            btnSonido.setPosition(ANCHO * .82f, ALTO *0.65f, Align.top);
            btnMusica.setPosition(ANCHO * .62f, ALTO *0.65f, Align.top);
            btnReanuda.setPosition(ANCHO * .10f, ALTO *0.65f, Align.topLeft);
            btnMenu.setPosition(ANCHO * .32f, ALTO *0.65f, Align.topLeft);
            btnReanuda.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado=EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaNivel1);
                    Gdx.app.log("Pausa","Reanuda");
                }

            });
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado=EstadoJuego.JUGANDO;
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
