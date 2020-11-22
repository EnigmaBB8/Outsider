package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
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

    //Villano
    private Villano villano;
    private Texture texturaVillano;

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

    //Llamaradas
    private Array<Llamaradas> arrLlamaradas;
    private float timerCrearLlamarada;
    private float TIEMPO_CREA_LLAMARADA = 1;
    private float tiempoLlamarada = 1;
    private Texture texturaLlamaradas;

    //Pocimas
    private Texture texturaPocima;
    private Array<Pocimas> arrPocimas;
    private float timerCrearPocima;
    private float TIEMPO_CREA_POCIMA = 7;
    private float tiempoPocima = 10;

    //Pausa
    private PantallaLucha2.EstadoJuego estado = PantallaLucha2.EstadoJuego.JUGANDO; // Jugando, Perdiendo, Ganar y Perder
    private PantallaLucha2.EscenaPausa escenaPausa;

    //Ganando
    private EscenaGanando escenaGanando;

    //Perdió
    private EscenaPerdio escenaPerdio;

    //Sonidos
    private Sound efectoPocima;

    //Aspas
    private Texture texturaAspas;
    private Array<Aspas> arrAspas;
    private float timerCrearAspas;
    private float TIEMPO_CREA_ASPAS = 10;
    private float tiempoAspas = 10;


    public PantallaLucha2(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        //fondoNivel2 = new Texture("fondos/fondonivel2.png");
        fondoNivel2 = juego.getManager().get("fondos/fondonivel2.png");
        //pilaP2 = new Texture("sprites/pilaP2.png");
        pilaP2 = juego.getManager().get("sprites/pilaP2.png");
        //pilaV2 = new Texture("sprites/pilaP2.png");
        pilaV2 = juego.getManager().get("sprites/pilaP2.png");

        crearNivel2();
        crearVillano();
        crearPersonaje();
        crearBolaMagica();
        crearSonido();
        crearTexto();
        crearLlamaradas();
        crearPocima();
        crearAspas();
        ConfiguracionMusica();
        configuracionSonido();

    }

    private void configuracionSonido() {
        Preferences preferencias = Gdx.app.getPreferences("Sonido");
        boolean Sonido = preferencias.getBoolean("GeneralSonido");
        Gdx.app.log("SonidoC"," "+Sonido);
        if(Sonido==true){
            //Prender Sonido
            efectoFlecha.play();
            efectoSalto.play();
            efectoPocima.play();
        }
    }

    private void crearAspas() {
        texturaAspas = juego.getManager().get("Enemigos/aspas.png");
        arrAspas= new Array<>();
    }

    private void crearLlamaradas() {
        texturaLlamaradas = juego.getManager().get("Enemigos/llamaradas.png");
        arrLlamaradas = new Array<>();
    }

    private void crearPocima() {
        texturaPocima = juego.getManager().get("Proyectiles/pocimaNivel2.png");
        arrPocimas = new Array<>();
    }

    private void crearVillano() {
        texturaVillano = juego.getManager().get("Enemigos/Dragon1.PNG");
        villano=new Villano(texturaVillano);
    }

    private void ConfiguracionMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        Gdx.app.log("MUSICA 2"," "+musicaFondo);
        if(musicaFondo==true){
            //Prender musica
            juego.reproducirMusicaNivel2();
            juego.detenerMusica();
            juego.detenerMusicaN1();
            juego.detenerMusicaN3();
            juego.detenerMusicaN4();
        }
    }

    private void crearTexto() {
        texto=new Texto("Texto/game.fnt");
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

    private void crearBolaMagica() {
        //texturaProyectil = new Texture("Proyectiles/bolasMagicas.png");
        texturaProyectil = juego.getManager().get("Proyectiles/bolasMagicas.png");
        arrBolasMagicas = new Array<>();
    }

    private void crearPersonaje() {
        //texturaPersonaje=new Texture("sprites/personaje.png");
        texturaPersonaje = juego.getManager().get("sprites/personaje.png");
        personaje=new Personaje(texturaPersonaje,ANCHO*0.05f,125);
    }

    private void crearNivel2() {
        escenaNivel2 = new Stage(vista);
        ///Boton de Pausa
        //Texture btnNuevaPartida = new Texture("botones/BtnPausa2.png");
        Texture btnNuevaPartida = juego.getManager().get("botones/BtnPausa2.png");
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
        //Texture btnNuevaPartidaInv = new Texture("botones/BtnPausa2.png");
        Texture btnNuevaPartidaInv = juego.getManager().get("botones/BtnPausa2.png");
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
        btnIzquierda.setPosition(ANCHO*.05f,ALTO*.148f,Align.topLeft);
        bntDerecha.setPosition(ANCHO*.15f,ALTO*.14f,Align.topLeft);
        bntSalta.setPosition(ANCHO*.70f,ALTO*.15f, Align.topLeft);
        bntDisparas.setPosition(ANCHO*.85f,ALTO*.15f,Align.topLeft);

        //Boton derecha
        bntDerecha.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstado(EstadoKAIM.CAMINANDO);
                personaje.setEstadoCaminando(EstadoCaminando.DERECHA);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstadoCaminando(EstadoCaminando.QUIETO_DERECHA);
                personaje.setEstado(EstadoKAIM.QUIETO);
            }
        });

        //Boton izquierda
        btnIzquierda.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstado(EstadoKAIM.CAMINANDO);
                personaje.setEstadoCaminando(EstadoCaminando.IZQUIERDA);
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                personaje.setEstadoCaminando(EstadoCaminando.QUIETO_IZQUIERDA);
                personaje.setEstado(EstadoKAIM.QUIETO);
            }
        });

        //Boton saltar
        bntSalta.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Preferences preferencias = Gdx.app.getPreferences("Sonido");
                boolean Sonido = preferencias.getBoolean("GeneralSonido");
                if (personaje.getEstado() != EstadoKAIM.SALTANDO) {
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
                if (personaje.getEstadoCaminando() == EstadoCaminando.QUIETO_DERECHA || personaje.getEstadoCaminando() == EstadoCaminando.DERECHA ||
                        personaje.getEstadoCaminando() == EstadoCaminando.SALTA_DERECHA) {
                    if (arrBolasMagicas.size < 5) {
                        BolasMagicas BolasMagicas = new BolasMagicas(texturaProyectil, personaje.sprite.getX(), personaje.sprite.getY() + personaje.sprite.getHeight() * 0.5f);
                        arrBolasMagicas.add(BolasMagicas);
                    }
                }
            }
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
        borrarPantalla(0, 0, 0.5f);
        batch.setProjectionMatrix(camara.combined);
        if(estado == PantallaLucha2.EstadoJuego.JUGANDO) {
            actualizar();

            batch.begin();
            batch.draw(fondoNivel2, 0, 0);
            batch.draw(pilaP2,ANCHO*0.03f,ALTO*0.83f);
            batch.draw(pilaV2,ANCHO*0.8f,ALTO*0.83f);
            villano.render(batch);
            personaje.render(batch);
            escenaNivel2.draw();

            dibujarLlamaradas();
            dibujarBolasMagicas();
            dibujarVidaPersonaje();
            dibujarVidaVillano();
            dibujarPocimas();
            dibujarAspas();

            batch.end();

        }else if(estado == EstadoJuego.PAUSADO){
            escenaPausa.draw();
        } else if (estado == EstadoJuego.GANANDO1|| estado == EstadoJuego.GANANDO2 || estado == EstadoJuego.GANANDO3
                || estado == EstadoJuego.GANANDO4) {
            batch.begin();
            batch.draw(fondoNivel2, 0, 0);
            batch.end();
            escenaGanando.draw();
        } else if (estado == EstadoJuego.PERDIO) {
            escenaPerdio.draw();
        }

    }

    private void dibujarAspas() {
        for (Aspas aspas :
                arrAspas) {
            aspas.render(batch);
            aspas.atacar();
        }
    }

    private void dibujarLlamaradas() {
        for (Llamaradas llamaradas :
                arrLlamaradas) {
            llamaradas.render(batch);
            llamaradas.atacar();
        }
    }

    private void dibujarPocimas() {
        for (Pocimas pocimas :
                arrPocimas) {
            pocimas.render(batch);
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
        actualizarPocimas();
        actualizarLlamaradas();
        actualizarAspas();

        verificarChoquesAEnemigo();
        verificarPocimaTomada();
        verificarChoquesLlamaradaPersonaje();
        verficarChoquesAspas();
    }

    private void verficarChoquesAspas() {
        for (int i = arrAspas.size-1; i>=0; i--) {
            Aspas aspas = arrAspas.get(i);
            if (personaje.sprite.getBoundingRectangle().overlaps(aspas.sprite.getBoundingRectangle())) {
                arrAspas.removeIndex(i);
                bateriaN2 -= 8;
                break;
            }
        }
    }

    private void actualizarAspas() {
        timerCrearAspas += Gdx.graphics.getDeltaTime();
        if (timerCrearAspas>=TIEMPO_CREA_ASPAS) {
            timerCrearAspas = 0;
            TIEMPO_CREA_ASPAS = tiempoAspas + MathUtils.random()*.4f;
            if (tiempoAspas>2) {
                tiempoAspas -= 1;
            }
            Aspas aspas = new Aspas(texturaAspas, 0, 120);
            arrAspas.add(aspas);
        }
    }

    private void verificarChoquesLlamaradaPersonaje() {
        for (int i = arrLlamaradas.size-1; i>=0; i--) {
            Llamaradas llamaradas = arrLlamaradas.get(i);
            if (personaje.sprite.getBoundingRectangle().overlaps(llamaradas.sprite.getBoundingRectangle())) {
                arrLlamaradas.removeIndex(i);
                bateriaN2 -= 15;
                break;
            } else if (bateriaN2 <= 0) {
                estado = EstadoJuego.PERDIO;
                if (escenaPerdio == null) {
                    escenaPerdio = new EscenaPerdio(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaPerdio);
            }
        }
    }

    private void actualizarLlamaradas() {
        timerCrearLlamarada += Gdx.graphics.getDeltaTime();
        if (timerCrearLlamarada>=TIEMPO_CREA_LLAMARADA) {
            timerCrearLlamarada = 0;
            TIEMPO_CREA_LLAMARADA = tiempoLlamarada + MathUtils.random()*.4f;
            if (tiempoLlamarada>2) {
                tiempoLlamarada -= 1;
            }
            Llamaradas llamarada = new Llamaradas(texturaLlamaradas, 950, 400);
            arrLlamaradas.add(llamarada);
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

    private void verificarPocimaTomada() {
        for (int i = arrPocimas.size-1; i >= 0; i--) {
            Pocimas pocima = arrPocimas.get(i); //Pocima
            // COLISION!!!
            if (pocima.sprite.getBoundingRectangle().overlaps(personaje.sprite.getBoundingRectangle())
                    && bateriaN2<90) {
                arrPocimas.removeIndex(i);
                // Aumentar puntos
                bateriaN2 += 15;
                efectoPocima.play();
                break;
            }
        }
    }

    private void verificarChoquesAEnemigo() {
        for (int i=arrBolasMagicas.size-1; i>=0; i--) {
            BolasMagicas bolasMagicas = arrBolasMagicas.get(i);
            // COLISION!!!
            if (bolasMagicas.sprite.getBoundingRectangle().overlaps(villano.sprite.getBoundingRectangle())) {
                arrBolasMagicas.removeIndex(i);
                // Descontar puntos
                vidaVillano2 -= 10;
                break;
            } else if (vidaVillano2 == 0) {
                estado = EstadoJuego.GANANDO1;
                villano.setEstado(EstadoVillano.MUERTO);
                if (escenaGanando == null) {
                    escenaGanando = new EscenaGanando(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaGanando);
            }
        }
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
    public void dispose() {
        //Fondos
        juego.getManager().unload("fondos/fondonivel2.png");
        juego.getManager().unload("fondos/PausaN2.png");

        //Sprites
        juego.getManager().unload("sprites/pilaP2.png");
        juego.getManager().unload("sprites/pilaP2.png");
        juego.getManager().unload("sprites/personaje.png");

        //Proyectiles
        juego.getManager().unload("Proyectiles/bolasMagicas.png");
        juego.getManager().unload("Proyectiles/pocimaNivel2.png");

        //Efectos
        juego.getManager().unload("Efectos/salto.mp3");
        juego.getManager().unload("Efectos/Flecha.mp3");
        juego.getManager().unload("Efectos/bolaDeFuego.mp3");
        juego.getManager().unload("Efectos/pocima.mp3");

        //Enemigos
        juego.getManager().unload("Enemigos/Dragon1.PNG");
        juego.getManager().unload("Enemigos/llamaradas.png");
        juego.getManager().unload("Enemigos/aspas.png");

        //Texto
        juego.getManager().unload("Texto/game.fnt");

        //Botones
        juego.getManager().unload("botones/BtnPausa2.png");
        juego.getManager().unload("botones/BotonIzquierda.png");
        juego.getManager().unload("botones/BotonDerecha.png");
        juego.getManager().unload("botones/BotonSaltar.png");
        juego.getManager().unload("botones/BotonDisparar.png");
        juego.getManager().unload("botones/BtnPausa2.png");
        juego.getManager().unload("botones/BotonIzquierdaInv.png");
        juego.getManager().unload("botones/BotonDerechaInv.png");
        juego.getManager().unload("botones/BotonSaltarInv.png");
        juego.getManager().unload("botones/BotonDispararInv.png");

        juego.getManager().unload("botones/BtnReanudarN2.png");
        juego.getManager().unload("botones/BtnMenuN2.png");
        juego.getManager().unload("botones/BtnMusicN2.png");
        juego.getManager().unload("botones/BtnSonidoN2.png");
        juego.getManager().unload("botones/BtnReanudarN2Inv.png");
        juego.getManager().unload("botones/BtnMenuN2Inv.png");
        juego.getManager().unload("botones/BtnMusicN2Inv.png");
        juego.getManager().unload("botones/BtnSonidoN2Inv.png");

        juego.getManager().unload("botones/avanzarN2.png");
        juego.getManager().unload("botones/omitirN2.png");
        juego.getManager().unload("botones/PlayAgainN2.png");

        //Historieta
        juego.getManager().unload("Historieta/VNLvl2_1.PNG");
        juego.getManager().unload("Historieta/VNLvl2_2.PNG");
        juego.getManager().unload("Historieta/VNLvl2_3.PNG");
        juego.getManager().unload("Historieta/VNLvl2_4.PNG");

        juego.getManager().unload("Historieta/perdistelvl2.PNG");

        batch.dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

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
            //Texture textura=new Texture("fondos/PausaN2.png");
            Texture textura = juego.getManager().get("fondos/PausaN2.png");
            Image imgPausa=new Image(textura);
            imgPausa.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);
            this.addActor(imgPausa); //Fondo

            //Botones
            // Boton Reanudar
            //Texture bntReanudar = new Texture("botones/BtnReanudarN2.png");
            Texture bntReanudar = juego.getManager().get("botones/BtnReanudarN2.png");
            TextureRegionDrawable trReanudar = new TextureRegionDrawable(new TextureRegion(bntReanudar));
            //Boton Menu
            //Texture bntMenu = new Texture("botones/BtnMenuN2.png");
            Texture bntMenu = juego.getManager().get("botones/BtnMenuN2.png");
            TextureRegionDrawable trMenu = new TextureRegionDrawable(new TextureRegion(bntMenu));
            //Boton Musica
            //Texture bntMusica = new Texture("botones/BtnMusicN2.png");
            Texture bntMusica = juego.getManager().get("botones/BtnMusicN2.png");
            TextureRegionDrawable trMusica= new TextureRegionDrawable(new TextureRegion(bntMusica));
            //Boton Sonido
            //Texture bntSonido = new Texture("botones/BtnSonidoN2.png");
            Texture bntSonido = juego.getManager().get("botones/BtnSonidoN2.png");
            TextureRegionDrawable trSonido =new TextureRegionDrawable(new TextureRegion(bntSonido));

            //Inverso de Reanudar
            //Texture btnReanudarInv= new Texture("botones/BtnReanudarN2Inv.png");
            Texture btnReanudarInv = juego.getManager().get("botones/BtnReanudarN2Inv.png");
            TextureRegionDrawable trdBtReanudarInv = new TextureRegionDrawable(new TextureRegion(btnReanudarInv));
            //Inverso de Menu
            //Texture btnMenuInv= new Texture("botones/BtnMenuN2Inv.png");
            Texture btnMenuInv = juego.getManager().get("botones/BtnMenuN2Inv.png");
            TextureRegionDrawable trdBtMenuInv = new TextureRegionDrawable(new TextureRegion(btnMenuInv));
            //Inverso de Musica
            //Texture btnMusicaInv= new Texture("botones/BtnMusicN2Inv.png");
            Texture btnMusicaInv = juego.getManager().get("botones/BtnMusicN2Inv.png");
            TextureRegionDrawable trdBtMusicanv = new TextureRegionDrawable(new TextureRegion(btnMusicaInv));
            //Inverso de Sonido
            //Texture btnSonidoInv= new Texture("botones/BtnSonidoN2Inv.png");
            Texture btnSonidoInv = juego.getManager().get("botones/BtnSonidoN2Inv.png");
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
            Preferences preferences=Gdx.app.getPreferences("Sonido");
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
                    estado= PantallaLucha2.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaNivel2);
                    Gdx.app.log("Pausa","Reanuda");
                }
            });
            //Listener Menú
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado= PantallaLucha2.EstadoJuego.JUGANDO;
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
                        juego.reproducirMusicaNivel2();
                        juego.detenerMusica();
                        juego.detenerMusicaN1();
                        preferencias.putBoolean("General",true);
                    }else{
                        btnMusica.setStyle(ApagadoMusica);
                        juego.detenerMusicaN1();
                        juego.detenerMusicaN2();
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
                    if (Sonido == false) {
                        //Prender Sonido
                        btnSonido.setStyle(PrendidoSonido);
                        efectoSalto.play();
                        efectoFlecha.play();
                        efectoPocima.play();
                        preferencias.putBoolean("GeneralSonido", true);
                    } else {
                        //Apagar Sonido
                        btnSonido.setStyle(ApagadoSonido);
                        efectoSalto.stop();
                        efectoFlecha.stop();
                        efectoPocima.stop();
                        preferencias.putBoolean("GeneralSonido", false);
                    }
                }
            });

            this.addActor(btnReanuda);
            this.addActor(btnMenu);
            this.addActor(btnMusica);
            this.addActor(btnSonido);
        }
    }

    private class EscenaGanando extends Stage{
        private Image imgGanando;
        public EscenaGanando(Viewport vista, SpriteBatch batch){
            super(vista, batch);
            if (estado == PantallaLucha2.EstadoJuego.GANANDO1) {
                //Texture textura1 = new Texture("Historieta/VNLvl1_1.PNG");
                Texture textura1 = juego.getManager().get("Historieta/VNLvl2_1.PNG");
                imgGanando = new Image(textura1);
                imgGanando.setPosition(ANCHO/2-textura1.getWidth()/2, ALTO/2-textura1.getHeight()/2);
                Gdx.app.log("Ganando1", "Sí entra");
                this.addActor(imgGanando);
            }

            //Boton Omitir
            Texture btnOmitir = juego.getManager().get("botones/omitirN2.png");
            TextureRegionDrawable trOmitir = new TextureRegionDrawable(new TextureRegion(btnOmitir));
            final ImageButton btnOmitirFinal = new ImageButton(trOmitir,trOmitir);
            btnOmitirFinal.setPosition(ANCHO*0.91F,ALTO*0.94F, Align.topRight);
            btnOmitirFinal.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL3));
                }
            });
            this.addActor(btnOmitirFinal);

            // Boton Avanzar
            Texture btnAvanzar = juego.getManager().get("botones/avanzarN2.png");
            TextureRegionDrawable trAvanzar = new TextureRegionDrawable(new TextureRegion(btnAvanzar));
            final ImageButton btnAvanza = new ImageButton(trAvanzar, trAvanzar);
            btnAvanza.setPosition(ANCHO * 0.9f, ALTO * 0.8f, Align.topRight);
            btnAvanza.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (estado == EstadoJuego.GANANDO1) {
                        estado = EstadoJuego.GANANDO2;
                        Gdx.app.log("Ganando2", "Sí cambia");
                        //Texture textura2 = new Texture("Historieta/VNLvl1_2.PNG");
                        Texture textura2 = juego.getManager().get("Historieta/VNLvl2_2.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura2);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO2) {
                        estado = EstadoJuego.GANANDO3;
                        Gdx.app.log("Ganando3", "Sí cambia");
                        //Texture textura3 = new Texture("Historieta/VNLvl1_3.PNG");
                        Texture textura3 = juego.getManager().get("Historieta/VNLvl2_3.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura3);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO3) {
                        estado = EstadoJuego. GANANDO4;
                        Gdx.app.log("Ganando4", "Sí cambia");
                        //Texture textura4 = new Texture("Historieta/VNLvl1_4.PNG");
                        Texture textura4 = juego.getManager().get("Historieta/VNLvl2_4.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura4);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO4) {
                        juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL3));
                        btnAvanza.toFront();
                    }
                }
            });
            this.addActor(btnAvanza);

        }
    }

    private class EscenaPerdio extends Stage{
        public EscenaPerdio(Viewport vista, SpriteBatch batch) {
            super(vista, batch);
            //Texture textura = new Texture("Historieta/perdistelvl1.PNG");
            Texture textura = juego.getManager().get("Historieta/perdistelvl2.PNG");
            Image imgPerdio = new Image(textura);
            imgPerdio.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);

            this.addActor(imgPerdio);

            //Boton de Jugar de Nuevo
            Texture btnJugarDeNuevo = juego.getManager().get("botones/PlayAgainN2.png");
            TextureRegionDrawable trJugarDeNuevo = new TextureRegionDrawable(new TextureRegion(btnJugarDeNuevo));
            final ImageButton btnJugarDeNuevoNivel = new ImageButton(trJugarDeNuevo,trJugarDeNuevo);
            btnJugarDeNuevoNivel.setPosition(ANCHO*.7f,ALTO*0.3F, Align.topRight);
            btnJugarDeNuevoNivel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL2));
                }
            });
            this.addActor(btnJugarDeNuevoNivel);

            // Boton Avanzar
            //Texture btnAvanzar = new Texture("botones/avanzar.png");
            Texture btnAvanzar = juego.getManager().get("botones/avanzarN2.png");
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
}
