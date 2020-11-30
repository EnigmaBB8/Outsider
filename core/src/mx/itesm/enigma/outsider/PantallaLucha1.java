package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
    private Texture fondoNivel1;
    private Texture pilaP;
    private Texture pilaV;

    //Personaje
    private Personaje personaje;
    private Texture texturaPersonaje;
    private Texture texturaVillano;

    //Villano
    private Villano villano;

    //Bolas de Fuego
    private Array<BolasDeFuego> arrBolasFuego;
    private float timerCrearBola;
    private float TIEMPO_CREA_BOLA = 1;
    private float tiempoBola = 4;
    private Texture texturaBolas;

    // Flechas
    private Texture texturaFlechaMoviendo;
    private Texture texturaFlechaExplotando;
    private Array<Proyectil> arrFlecha;

    // Piedra
    private Texture texturaPiedra;
    private Array<Piedra> arrPiedra;
    private float timerCrearPiedra;
    private float TIEMPO_CREA_PIEDRA = 1;
    private float tiempoPiedra = 1;

    //Texto
    private Texto texto;
    private float bateria=100;
    private float vidaVillano=100;

    //Sonidos
    private Sound efectoSalto;
    private Sound efectoFlecha;
    private Sound efectoPocima;

    //Pocimas
    private Texture texturaPocima;
    private Array<Pocimas> arrPocimas;
    private float timerCrearPocima;
    private float TIEMPO_CREA_POCIMA = 4;
    private float tiempoPocima = 15;

    //Pausa
    private EstadoJuego estado=EstadoJuego.JUGANDO; // Jugando, Perdiendo, Ganar y Perder
    private EscenaPausa escenaPausa;

    // Ganando
    private EscenaGanando escenaGanando;

    //Perdió
    private EscenaPerdio escenaPerdio;

    //Titan muriendo
    private EscenaMuriendo escenaMuriendo;

    //Generador de Niveles
    private int NivelDisponible;

    public PantallaLucha1(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoNivel1 = juego.getManager().get("fondos/fondonivel1.png");
        pilaP = juego.getManager().get("sprites/pilaP.png");
        pilaV = juego.getManager().get("sprites/pilaP.png");
        crearNivel1();
        crearPiedra();
        crearPersonaje();
        crearBolasFuego();
        arrFlecha = new Array<>();
        crearTexto();
        crearVillano();
        crearSonido();
        crearPocima();
        configurarMusica();

    }

    private void configurarMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        Gdx.app.log("MUSICA NIVEL 1"," "+musicaFondo);
        if(musicaFondo==true){
            //Prender musica
            preferencias.putBoolean("GeneralSonido",true);
            juego.detenerMusica();
            juego.detenerMusicaN4();
            juego.detenerMusicaN3();
            juego.detenerMusicaN2();
            juego.reproducirMusicaNivel1();
        }
    }

    private void crearPiedra() {
        texturaPiedra = juego.getManager().get("Proyectiles/piedra.png");
        arrPiedra = new Array<>();
    }

    private void crearPocima() {
        texturaPocima = juego.getManager().get("Proyectiles/pocima.png");
        arrPocimas = new Array<>();
    }

    private void crearSonido() {
        AssetManager manager = new AssetManager();
        manager.load("Efectos/salto.mp3", Sound.class);
        manager.load("Efectos/Flecha.mp3", Sound.class);
        manager.load("Efectos/pocima.mp3", Sound.class);
        manager.finishLoading();
        efectoSalto = manager.get("Efectos/salto.mp3");
        efectoFlecha = manager.get("Efectos/Flecha.mp3");
        efectoPocima = manager.get("Efectos/pocima.mp3");
    }

    private void crearVillano() {
        texturaVillano = juego.getManager().get("Enemigos/Titan1.PNG");
        villano=new Villano(texturaVillano);
    }

    private void crearTexto() {
        texto=new Texto("Texto/game.fnt");
    }

    private Proyectil crearProyectil() {
        texturaFlechaMoviendo = juego.getManager().get("Proyectiles/flecha1.png");
       texturaFlechaExplotando = juego.getManager().get("Efectos/explosion.png");
        Proyectil flecha=new Proyectil(texturaFlechaMoviendo,texturaFlechaExplotando,
                personaje.sprite.getX() + personaje.sprite.getWidth()*0.6f,
                personaje.sprite.getY() + personaje.sprite.getHeight()*0.12f);
        return flecha;
    }

    private void crearBolasFuego() {
        texturaBolas = juego.getManager().get("Enemigos/BolaDeFuego.png");
        arrBolasFuego = new Array<>();
    }

    private void crearPersonaje() {
        texturaPersonaje = juego.getManager().get("sprites/personaje.png");
        personaje=new Personaje(texturaPersonaje,ANCHO*0.05f,125);
    }

    private void crearNivel1() {
        escenaNivel1 = new Stage(vista);
        ///Boton de Pausa
        Texture btnNuevaPartida = juego.getManager().get("botones/BtnPausa1.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Boton Izquierda
        //Texture bntIz = new Texture("botones/BotonIzquierda.png");
        Texture bntIz = juego.getManager().get("botones/BotonIzquierda.png");
        TextureRegionDrawable trBntIz = new TextureRegionDrawable(new TextureRegion(bntIz));

        //Boton Derecha
        //Texture bntDer = new Texture("botones/BotonDerecha.png");
        Texture bntDer = juego.getManager().get("botones/BotonDerecha.png");
        TextureRegionDrawable trBntDer = new TextureRegionDrawable(new TextureRegion(bntDer));

        //Boton Saltar
        //Texture bntSaltar = new Texture("botones/BotonSaltar.png");
        Texture bntSaltar = juego.getManager().get("botones/BotonSaltar.png");
        TextureRegionDrawable trBntSaltar = new TextureRegionDrawable(new TextureRegion(bntSaltar));

        // Boton Disparar
        //Texture bntDispara = new Texture("botones/BotonDisparar.png");
        Texture bntDispara = juego.getManager().get("botones/BotonDisparar.png");
        TextureRegionDrawable trTirar = new TextureRegionDrawable(new TextureRegion(bntDispara));

        //Inverso de Pausa
        //Texture btnNuevaPartidaInv = new Texture("botones/BtnPausa1.png");
        Texture btnNuevaPartidaInv = juego.getManager().get("botones/BtnPausa1.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        //Inverso de Boton Izquierda
        //Texture btnIzIn = new Texture("botones/BotonIzquierdaInv.png");
        Texture btnIzIn = juego.getManager().get("botones/BotonIzquierdaInv.png");
        TextureRegionDrawable trdBtnIzIn = new TextureRegionDrawable(new TextureRegion(btnIzIn));

        //Inverso de Boton Derecha
        //Texture bntDerIn = new Texture("botones/BotonDerechaInv.png");
        Texture bntDerIn = juego.getManager().get("botones/BotonDerechaInv.png");
        TextureRegionDrawable trdBtnDeIn = new TextureRegionDrawable(new TextureRegion(bntDerIn));

        //Inverso de boton Saltar
        //Texture bntSaltarIn = new Texture("botones/BotonSaltarInv.png");
        Texture bntSaltarIn = juego.getManager().get("botones/BotonSaltarInv.png");
        TextureRegionDrawable trBntSaltarIn = new TextureRegionDrawable(new TextureRegion(bntSaltarIn));

        //Inverso de Boton Disparar
        //Texture bntDisparaIn = new Texture("botones/BotonDispararInv.png");
        Texture bntDisparaIn = juego.getManager().get("botones/BotonDispararInv.png");
        TextureRegionDrawable trBntDispararInv = new TextureRegionDrawable(new TextureRegion(bntDisparaIn));

        ImageButton btnNP = new ImageButton(trdBtNuevaPartida, trdBtNuevaPartidaInv);
        ImageButton btnIzquierda = new ImageButton(trBntIz,trdBtnIzIn);
        ImageButton bntDerecha = new ImageButton(trBntDer,trdBtnDeIn);
        ImageButton bntSalta = new ImageButton(trBntSaltar,trBntSaltarIn);
        ImageButton bntDisparas = new ImageButton(trTirar,trBntDispararInv);

        btnNP.setPosition(ANCHO * .46f, ALTO * .96F, Align.topLeft);
        btnIzquierda.setPosition(ANCHO*.05f,ALTO*.170f,Align.topLeft);
        bntDerecha.setPosition(ANCHO*.15f,ALTO*.160f,Align.topLeft);
        bntSalta.setPosition(ANCHO*.70f,ALTO*.165f, Align.topLeft);
        bntDisparas.setPosition(ANCHO*.85f,ALTO*.165f,Align.topLeft);

        //Boton derecha
        bntDerecha.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(personaje.getEstado() != EstadoKAIM.SALTANDO){
                    personaje.setEstado(EstadoKAIM.CAMINANDO);
                    personaje.setEstadoCaminando(EstadoCaminando.DERECHA);
                }
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(personaje.getEstado() != EstadoKAIM.SALTANDO) {
                    personaje.setEstadoCaminando(EstadoCaminando.QUIETO_DERECHA);
                    personaje.setEstado(EstadoKAIM.QUIETO);
                }
            }
        });

        //Boton izquierda
        btnIzquierda.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (personaje.getEstado() != EstadoKAIM.SALTANDO) {
                    personaje.setEstado(EstadoKAIM.CAMINANDO);
                    personaje.setEstadoCaminando(EstadoCaminando.IZQUIERDA);
                }
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(personaje.getEstado() != EstadoKAIM.SALTANDO) {
                    personaje.setEstadoCaminando(EstadoCaminando.QUIETO_IZQUIERDA);
                    personaje.setEstado(EstadoKAIM.QUIETO);
                }
            }
        });

        //Boton saltar
        bntSalta.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Preferences preferencias = Gdx.app.getPreferences("Sonido");
                boolean Sonido = preferencias.getBoolean("GeneralSonido");
                if (personaje.getEstado() != EstadoKAIM.SALTANDO ) {
                    personaje.saltar();
                    if(Sonido==true) {
                        efectoSalto.play();
                    }
                }
                }
        });

        // Disparo
        bntDisparas.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Preferences preferencias = Gdx.app.getPreferences("Sonido");
                boolean Sonido = preferencias.getBoolean("GeneralSonido");
                if (arrFlecha.size < 50) {
                    Proyectil flecha = crearProyectil();
                    arrFlecha.add(flecha);
                    if(personaje.getEstado()!=EstadoKAIM.DISPARANDO_FLECHAS){
                        personaje.setEstado(EstadoKAIM.DISPARANDO_FLECHAS);
                    }
                    if (Sonido == true) {
                        efectoFlecha.play();
                    }
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
        borrarPantalla(0, 0, 0.5f);
        batch.setProjectionMatrix(camara.combined);
        if(estado==EstadoJuego.JUGANDO) {
            actualizar();

            batch.begin();
            batch.draw(fondoNivel1, 0, 0);
            batch.draw(pilaP,ANCHO*0.03f,ALTO*0.83f);
            batch.draw(pilaV,ANCHO*0.8f,ALTO*0.83f);
            villano.render(batch);
            personaje.render(batch);
            escenaNivel1.draw();

            dibujarBolasFuego();
            dibujarProyectil();
            dibujarVidaPersonaje();
            dibujarVidaVillano();
            dibujarPocimas();
            dibujarPiedras();

            batch.end();
        } else if(estado == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        } else if (estado == EstadoJuego.MURIENDO1){
            escenaMuriendo.draw();
        } else if (estado == EstadoJuego.GANANDO1 || estado == EstadoJuego.GANANDO2 || estado == EstadoJuego.GANANDO3
                || estado == EstadoJuego.GANANDO4) {
            batch.begin();
            batch.draw(fondoNivel1, 0, 0);
            batch.end();
            escenaGanando.draw();
        } else if (estado == EstadoJuego.PERDIO) {
            escenaPerdio.draw();
        }
    }

    private void dibujarPiedras() {
        for (Piedra piedra :
                arrPiedra) {
            piedra.render(batch);
            piedra.atacar();
        }
    }

    private void dibujarPocimas() {
        for (Pocimas pocimas :
                arrPocimas) {
            pocimas.render(batch);
        }
    }

    private void dibujarVidaVillano() {
        int VidaVillano=(int)vidaVillano;
        texto.mostrarMensaje(batch,VidaVillano+"%",ANCHO*0.88f,ALTO*0.92f);
    }

    private void dibujarVidaPersonaje() {
        int VidaInt=(int)bateria;
        texto.mostrarMensaje(batch,VidaInt+"%",ANCHO*0.11f,ALTO*0.92f);
    }

    private void dibujarProyectil() {
        for (Proyectil flecha :
                arrFlecha) {
           flecha.render(batch);
        }
    }

    private void dibujarBolasFuego() {
        for (BolasDeFuego bola :
                arrBolasFuego) {
            bola.render(batch);
            bola.atacar();
        }
    }

    private void actualizar() {
        actualizarBolasFuego();
        actualizarProyectil();
        actualizarPocimas();
        actualizarPiedra();

        //Colisiones entre los objetos
        verificarChoquesBolasPersonaje();
        verificarChoquesAEnemigo();
        verificarPocimaTomada();
        veriicarChoquePiedra();
    }

    private void veriicarChoquePiedra() {
        for (int i = arrPiedra.size-1; i >= 0; i--) {
            Piedra piedra = arrPiedra.get(i);
            // COLISION!!!
            if (piedra.sprite.getBoundingRectangle().overlaps(personaje.sprite.getBoundingRectangle())){
                arrPiedra.removeIndex(i);
                // Aumentar puntos
                bateria -= 3;
                break;
            }
        }
    }

    private void verificarPocimaTomada() {
        Preferences preferencias = Gdx.app.getPreferences("Sonido");
        boolean Sonido = preferencias.getBoolean("GeneralSonido");
        for (int i = arrPocimas.size-1; i >= 0; i--) {
            Pocimas pocima = arrPocimas.get(i); //Pocima
            // COLISION!!!
            if (pocima.sprite.getBoundingRectangle().overlaps(personaje.sprite.getBoundingRectangle())
                    && bateria<93) {
                arrPocimas.removeIndex(i);
                // Aumentar puntos
                bateria += 7;
                if(Sonido==true) {
                    efectoPocima.play();
                }
                break;
            }
        }
    }

    private void verificarChoquesAEnemigo() {
        Preferences preferences=Gdx.app.getPreferences("Nivel");
        int Nivel=preferences.getInteger("NivelGeneral");
        for (int i=arrFlecha.size-1; i>=0; i--) {
            Proyectil flecha = arrFlecha.get(i);
            // COLISION!!!
            Rectangle rectVillano = villano.sprite.getBoundingRectangle();
            rectVillano.x += rectVillano.width/3;
            if (flecha.sprite.getBoundingRectangle().overlaps(rectVillano)) {
                if(flecha.getEstado()== EstadoObjeto.MOVIENDO) {
                    // Descontar puntos
                    vidaVillano -= 20;
                    flecha.setEstado(EstadoObjeto.EXPLOTANDO);
                }else if(flecha.getEstado()== EstadoObjeto.DESAPARECE){
                    arrFlecha.removeIndex(i);
                }
                break;
            } else if (vidaVillano <= 0) {
                estado = EstadoJuego.MURIENDO1;
                NivelDisponible=2;
                villano.setEstado(EstadoVillano.MUERTO);
                if(Nivel<2) {
                    preferences.putInteger("NivelGeneral", NivelDisponible);
                    preferences.flush();
                }
                if (escenaMuriendo == null) {
                    escenaMuriendo = new EscenaMuriendo(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaMuriendo);
            }
        }
    }

    private void verificarChoquesBolasPersonaje() {
        for (int i = arrBolasFuego.size-1; i>=0; i--) {
            BolasDeFuego bola = arrBolasFuego.get(i);
            if (personaje.sprite.getBoundingRectangle().overlaps(bola.sprite.getBoundingRectangle())) {
                arrBolasFuego.removeIndex(i);
                bateria -= 5;
                break;
            } else if (bateria <= 0) {
                estado = EstadoJuego.PERDIO;
                if (escenaPerdio == null) {
                    escenaPerdio = new EscenaPerdio(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaPerdio);
            }
        }
    }

    private void actualizarPiedra() {
        timerCrearPiedra += Gdx.graphics.getDeltaTime();
        if (timerCrearPiedra>=TIEMPO_CREA_PIEDRA) {
            timerCrearPiedra = 0;
            TIEMPO_CREA_PIEDRA = tiempoPiedra + MathUtils.random()*.1f;
            if (tiempoPiedra>1) {
                tiempoPiedra -= 1;
            }
            Piedra piedra = new Piedra(texturaPiedra, 1+MathUtils.random(1,70)*10, ALTO*0.9f);
            arrPiedra.add(piedra);
        }
    }

    private void actualizarPocimas() {
        timerCrearPocima += Gdx.graphics.getDeltaTime();
        if (timerCrearPocima>=TIEMPO_CREA_POCIMA) {
            timerCrearPocima = 0;
            TIEMPO_CREA_POCIMA = tiempoPocima + MathUtils.random()*.4f;
            if (tiempoPocima>2) {
                tiempoPocima -= 1;
            }
            Pocimas pocima = new Pocimas(texturaPocima, 200+MathUtils.random(1,5)*100, 120);
            arrPocimas.add(pocima);
        }
    }

    private void actualizarProyectil() {
        for (int i=arrFlecha.size-1; i>=0; i--) {
            Proyectil flecha = arrFlecha.get(i);
            flecha.moverDerecha();
            flecha.caida();
            if (flecha.sprite.getX() > ANCHO) {
                arrFlecha.removeIndex(i);
            }
        }
    }

    private void actualizarBolasFuego() {
        timerCrearBola += Gdx.graphics.getDeltaTime();
        if (timerCrearBola>=TIEMPO_CREA_BOLA) {
            timerCrearBola = 0;
            TIEMPO_CREA_BOLA = tiempoBola + MathUtils.random()*.4f;
            if (tiempoBola>2) {
                tiempoBola -= 1;
            }
            BolasDeFuego bola = new BolasDeFuego(texturaBolas, 950, 400);
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
        //Fondos
        juego.getManager().unload("fondos/fondonivel1.png");
        juego.getManager().unload("fondos/PausaN1.png");

        //Sprites
        juego.getManager().unload("sprites/pilaP.png");
        juego.getManager().unload("sprites/pilaP.png");
        juego.getManager().unload("sprites/personaje.png");

        //Proyectiles
        juego.getManager().unload("Proyectiles/piedra.png");
        juego.getManager().unload("Proyectiles/pocima.png");
        juego.getManager().unload("Proyectiles/flecha1.png");

        //Efectos
        juego.getManager().unload("Efectos/salto.mp3");
        juego.getManager().unload("Efectos/Flecha.mp3");
        juego.getManager().unload("Efectos/pocima.mp3");

        //Enemigos
        juego.getManager().unload("Enemigos/Titan1.png");
        juego.getManager().unload("Enemigos/BolaDeFuego.png");
        juego.getManager().unload("MuerteVillanos/muerteT1.png");

        //Texto
        juego.getManager().unload("Texto/game.fnt");

        //Botones
        juego.getManager().unload("botones/BotonIzquierda.png");
        juego.getManager().unload("botones/BotonDerecha.png");
        juego.getManager().unload("botones/BotonSaltar.png");
        juego.getManager().unload("botones/BotonDisparar.png");
        juego.getManager().unload("botones/BtnPausa1.png");
        juego.getManager().unload("botones/BotonIzquierdaInv.png");
        juego.getManager().unload("botones/BotonDerechaInv.png");
        juego.getManager().unload("botones/BotonSaltarInv.png");
        juego.getManager().unload("botones/BotonDispararInv.png");

        juego.getManager().unload("botones/BtnReanudarN1.png");
        juego.getManager().unload("botones/BtnMenuN1.png");
        juego.getManager().unload("botones/BtnMusicN1.png");
        juego.getManager().unload("botones/BtnSonidoN1.png");
        juego.getManager().unload("botones/BtnReanudarN1Inv.png");
        juego.getManager().unload("botones/BtnMenuN1Inv.png");
        juego.getManager().unload("botones/BtnMusicN1Inv.png");
        juego.getManager().unload("botones/BtnSonidoN1Inv.png");

        juego.getManager().unload("botones/avanzar.png");
        juego.getManager().unload("botones/omitir.png");
        juego.getManager().unload("botones/PlayAgain.png");

        juego.getManager().unload("BtnGanar/menu.png");
        juego.getManager().unload("BtnGanar/continuar.png");

        //Historieta
        juego.getManager().unload("Historieta/VNLvl1_1.PNG");
        juego.getManager().unload("Historieta/VNLvl1_2.PNG");
        juego.getManager().unload("Historieta/VNLvl1_3.PNG");
        juego.getManager().unload("Historieta/VNLvl1_4.PNG");

        juego.getManager().unload("Historieta/perdistelvl1.PNG");

        batch.dispose();
    }

    //Estados Juego
    private enum EstadoJuego {
        JUGANDO,
        PAUSADO,
        MURIENDO1,
        GANANDO1,
        GANANDO2,
        GANANDO3,
        GANANDO4,
        PERDIO
    }

    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, SpriteBatch batch){
            super(vista,batch);
            Texture textura = juego.getManager().get("fondos/PausaN1.png");
            Image imgPausa = new Image(textura);
            imgPausa.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);
            this.addActor(imgPausa); //Fondo

            //Botones
            // Boton Reanudar
            Texture bntReanudar = juego.getManager().get("botones/BtnReanudarN1.png");
            TextureRegionDrawable trReanudar = new TextureRegionDrawable(new TextureRegion(bntReanudar));

            //Boton Menu
            Texture bntMenu = juego.getManager().get("botones/BtnMenuN1.png");
            TextureRegionDrawable trMenu = new TextureRegionDrawable(new TextureRegion(bntMenu));

            //Boton Musica
            Texture bntMusica = juego.getManager().get("botones/BtnMusicN1.png");
            TextureRegionDrawable trMusica= new TextureRegionDrawable(new TextureRegion(bntMusica));

            //Boton Sonido
            Texture bntSonido = juego.getManager().get("botones/BtnSonidoN1.png");
            TextureRegionDrawable trSonido =new TextureRegionDrawable(new TextureRegion(bntSonido));

            //Inverso de Reanudar
            Texture btnReanudarInv = juego.getManager().get("botones/BtnReanudarN1Inv.png");
            TextureRegionDrawable trdBtReanudarInv = new TextureRegionDrawable(new TextureRegion(btnReanudarInv));

            //Inverso de Menu
            Texture btnMenuInv = juego.getManager().get("botones/BtnMenuN1Inv.png");
            TextureRegionDrawable trdBtMenuInv = new TextureRegionDrawable(new TextureRegion(btnMenuInv));

            //Inverso de Musica
            Texture btnMusicaInv = juego.getManager().get("botones/BtnMusicN1Inv.png");
            TextureRegionDrawable trdBtMusicanv = new TextureRegionDrawable(new TextureRegion(btnMusicaInv));

            //Inverso de Sonido
            final Texture btnSonidoInv = juego.getManager().get("botones/BtnSonidoN1Inv.png");
            TextureRegionDrawable trdBtSonidoInv = new TextureRegionDrawable(new TextureRegion(btnSonidoInv));

            ImageButton btnReanuda = new ImageButton(trReanudar, trdBtReanudarInv);
            ImageButton btnMenu = new ImageButton(trMenu, trdBtMenuInv);

            //Botón Música (Efecto Apagado/Encendido)
            final Button.ButtonStyle estiloPrendidoMusica=new Button.ButtonStyle(trMusica,trdBtMusicanv,null);
            final Button.ButtonStyle estiloApagadoMusica=new Button.ButtonStyle(trdBtMusicanv,trMusica,null);
            final ImageButton.ImageButtonStyle PrendidoMusica=new ImageButton.ImageButtonStyle(estiloPrendidoMusica);
            final ImageButton.ImageButtonStyle ApagadoMusica=new ImageButton.ImageButtonStyle(estiloApagadoMusica);
            final ImageButton btnMusica = new ImageButton(trMusica, trdBtMusicanv);

            //Botón Sonido (Efecto Apagado/Encendido)
            final Button.ButtonStyle estiloPrendidoSonido=new Button.ButtonStyle(trSonido,trdBtSonidoInv,null);
            final Button.ButtonStyle estiloApagadoSonido=new Button.ButtonStyle(trdBtSonidoInv,trSonido,null);

            final ImageButton.ImageButtonStyle PrendidoSonido=new ImageButton.ImageButtonStyle(estiloPrendidoSonido);
            final ImageButton.ImageButtonStyle ApagadoSonido=new ImageButton.ImageButtonStyle(estiloApagadoSonido);
            final ImageButton btnSonido = new ImageButton(trSonido,trdBtSonidoInv);

            btnSonido.setPosition(ANCHO * .82f, ALTO *0.60f, Align.top);
            btnMusica.setPosition(ANCHO * .62f, ALTO *0.60f, Align.top);
            btnReanuda.setPosition(ANCHO * .10f, ALTO *0.60f, Align.topLeft);
            btnMenu.setPosition(ANCHO * .32f, ALTO *0.60f, Align.topLeft);

            //Leer Preferencias Música
            Preferences preferencias = Gdx.app.getPreferences("Musica");
            boolean musicaFondo = preferencias.getBoolean("General");
            if(musicaFondo==true){
                //Música prendida
                btnMusica.setStyle(PrendidoMusica);
            }else{
                //Música Apagada
                btnMusica.setStyle(ApagadoMusica);
            }

            //Leer preferencias Sonido
            final Preferences preferences=Gdx.app.getPreferences("Sonido");
            boolean Sonido=preferences.getBoolean("GeneralSonido");
            if(Sonido==true){
                //Sonido Prendido
                btnSonido.setStyle(PrendidoSonido);
            }else{
                //Sonido Apagado
                btnSonido.setStyle(ApagadoSonido);
            }

            //Listener Reanudar
            btnReanuda.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado=EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaNivel1);
                    Gdx.app.log("Pausa","Reanuda");
                }
            });
            //Listener Menú
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado=EstadoJuego.JUGANDO;
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MAPA));
                }
            });
            //Listener Música
            btnMusica.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Preferences preferencias = Gdx.app.getPreferences("Musica");
                    boolean musicaFondo = preferencias.getBoolean("General");
                    Gdx.app.log("MUSICA 3", " " + musicaFondo);
                    if(musicaFondo==false) {
                        //Prender musica
                        btnMusica.setStyle(PrendidoMusica);
                        juego.reproducirMusicaNivel1();
                        juego.detenerMusica();
                        preferencias.putBoolean("General",true);
                    }else{
                        btnMusica.setStyle(ApagadoMusica);
                        juego.detenerMusicaN1();
                        juego.detenerMusica();
                        preferencias.putBoolean("General",false);
                    }
                }
            });
            //Listener Sonido
            btnSonido.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Preferences preferencias = Gdx.app.getPreferences("Sonido");
                    boolean Sonido = preferencias.getBoolean("GeneralSonido");
                    Gdx.app.log("SonidoB", " " + Sonido);
                    if(Sonido==false) {
                        //Prender Sonido
                        btnSonido.setStyle(PrendidoSonido);
                        preferencias.putBoolean("GeneralSonido",true);
                        preferences.flush();
                    }else{
                        //Apagar Sonido
                        btnSonido.setStyle(ApagadoSonido);
                        preferencias.putBoolean("GeneralSonido",false);
                        preferences.flush();
                    }

                }
            });
            this.addActor(btnReanuda);
            this.addActor(btnMenu);
            this.addActor(btnMusica);
            this.addActor(btnSonido);
        }
    }

    private class EscenaGanando extends Stage {
        private Image imgGanando;
        public EscenaGanando(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            if (estado == EstadoJuego.GANANDO1) {
                //Texture textura1 = new Texture("Historieta/VNLvl1_1.PNG");
                Texture textura1 = juego.getManager().get("Historieta/VNLvl1_1.PNG");
                imgGanando = new Image(textura1);
                imgGanando.setPosition(ANCHO/2-textura1.getWidth()/2, ALTO/2-textura1.getHeight()/2);
                Gdx.app.log("Ganando1", "Sí entra");
                this.addActor(imgGanando);
            }

            //Boton Omitir
            Texture btnOmitir = juego.getManager().get("botones/omitir.png");
            TextureRegionDrawable trOmitir = new TextureRegionDrawable(new TextureRegion(btnOmitir));
            final ImageButton btnOmitirFinal = new ImageButton(trOmitir,trOmitir);
            btnOmitirFinal.setPosition(ANCHO*0.2F,ALTO*0.98F, Align.topRight);
            btnOmitirFinal.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL2));
                }
            });
            this.addActor(btnOmitirFinal);

            // Boton Avanzar
            Texture btnAvanzar = juego.getManager().get("botones/avanzar.png");
            TextureRegionDrawable trAvanzar = new TextureRegionDrawable(new TextureRegion(btnAvanzar));
            final ImageButton btnAvanza = new ImageButton(trAvanzar, trAvanzar);
            btnAvanza.setPosition(ANCHO * 0.2f, ALTO * 0.84f, Align.topRight);
            btnAvanza.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (estado == EstadoJuego.GANANDO1) {
                        estado = EstadoJuego.GANANDO2;
                        Gdx.app.log("Ganando2", "Sí cambia");
                        //Texture textura2 = new Texture("Historieta/VNLvl1_2.PNG");
                        Texture textura2 = juego.getManager().get("Historieta/VNLvl1_2.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura2);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO2) {
                        estado = EstadoJuego.GANANDO3;
                        Gdx.app.log("Ganando3", "Sí cambia");
                        //Texture textura3 = new Texture("Historieta/VNLvl1_3.PNG");
                        Texture textura3 = juego.getManager().get("Historieta/VNLvl1_3.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura3);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO3) {
                        estado = EstadoJuego. GANANDO4;
                        Gdx.app.log("Ganando4", "Sí cambia");
                        //Texture textura4 = new Texture("Historieta/VNLvl1_4.PNG");
                        Texture textura4 = juego.getManager().get("Historieta/VNLvl1_4.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura4);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO4) {
                        juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL2));
                        btnAvanza.toFront();
                    }
                }
            });
            this.addActor(btnAvanza);
        }
    }

    private class EscenaPerdio extends Stage {
        public EscenaPerdio(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            //Texture textura = new Texture("Historieta/perdistelvl1.PNG");
            Texture textura = juego.getManager().get("Historieta/perdistelvl1.PNG");
            Image imgPerdio = new Image(textura);
            imgPerdio.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);

            this.addActor(imgPerdio);

            //Boton de Jugar de Nuevo
            Texture btnJugarDeNuevo = juego.getManager().get("botones/PlayAgain.png");
            TextureRegionDrawable trJugarDeNuevo = new TextureRegionDrawable(new TextureRegion(btnJugarDeNuevo));
            final ImageButton btnJugarDeNuevoNivel = new ImageButton(trJugarDeNuevo,trJugarDeNuevo);
            btnJugarDeNuevoNivel.setPosition(ANCHO*.8f,ALTO*0.4F, Align.topRight);
            btnJugarDeNuevoNivel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL1));
                }
            });
            this.addActor(btnJugarDeNuevoNivel);

            // Boton Avanzar
            //Texture btnAvanzar = new Texture("botones/avanzar.png");
            Texture btnAvanzar = juego.getManager().get("botones/avanzar.png");
            TextureRegionDrawable trAvanzar = new TextureRegionDrawable(new TextureRegion(btnAvanzar));
            ImageButton btnAvanza = new ImageButton(trAvanzar, trAvanzar);
            btnAvanza.setPosition(ANCHO/2, ALTO * 0.2f, Align.bottom);
            btnAvanza.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MAPA));

                    Preferences preferencias = Gdx.app.getPreferences("Musica");
                    boolean musicaFondo = preferencias.getBoolean("General");
                    Gdx.app.log("MUSICA 3", " " + musicaFondo);
                    if(musicaFondo==true) {
                        //Prender musica
                        juego.reproducirMusica();
                        juego.detenerMusicaN1();
                    }
                }
            });
            this.addActor(btnAvanza);
        }
    }

    private class EscenaMuriendo extends Stage {
        private Image imgMuriendo;
        public EscenaMuriendo(final Viewport vista, final SpriteBatch batch) {
            super(vista, batch);
            if (estado == EstadoJuego.MURIENDO1) {
                Texture textura1 = juego.getManager().get("MuerteVillanos/muerteT1.png");
                imgMuriendo = new Image(textura1);
                imgMuriendo.setPosition(ANCHO/2-textura1.getWidth()/2, ALTO/2-textura1.getHeight()/2);
                Gdx.app.log("Muriendo1", "Sí entra");
                this.addActor(imgMuriendo);
            }

            //Boton a menu
            Texture btnOmitir = juego.getManager().get("BtnGanar/menu.png");
            TextureRegionDrawable trOmitir = new TextureRegionDrawable(new TextureRegion(btnOmitir));
            final ImageButton btnOmitirFinal = new ImageButton(trOmitir,trOmitir);
            btnOmitirFinal.setPosition(ANCHO*.62f,ALTO*.53F, Align.topRight);
            btnOmitirFinal.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MENU));
                }
            });
            this.addActor(btnOmitirFinal);

            // Boton continuar
            Texture btnAvanzar = juego.getManager().get("BtnGanar/continuar.png");
            TextureRegionDrawable trAvanzar = new TextureRegionDrawable(new TextureRegion(btnAvanzar));
            final ImageButton btnAvanza = new ImageButton(trAvanzar, trAvanzar);
            btnAvanza.setPosition(ANCHO*.62f, ALTO * 0.3f, Align.topRight);
            btnAvanza.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                     if (estado == EstadoJuego.MURIENDO1) {
                        estado = EstadoJuego.GANANDO1;
                        if (escenaGanando == null) {
                            escenaGanando = new EscenaGanando(vista, batch);;
                        }
                        Gdx.input.setInputProcessor(escenaGanando);
                        btnAvanza.toFront();
                    }
                }
            });
            this.addActor(btnAvanza);
        }
    }
}