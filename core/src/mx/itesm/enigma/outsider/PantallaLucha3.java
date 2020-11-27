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

import java.awt.event.PaintEvent;

public class PantallaLucha3 extends Pantalla {
    private final Juego juego;
    private Stage escenaNivel3;
    private Texture fondoNivel3;
    private Texture pilaP3;
    private Texture pilaV3;

    //Personaje
    private Personaje personaje;
    private Texture texturaPersonaje;

    //Villano
    private Villano villano;
    private Texture texturaVillano;

    // Proyectil
    private Texture texturaProyectil;
    private Array<Proyectil> arrProyectil;

    //Cerebros
    private Array<Cerebros> arrCerebros;
    private float timerCrearCerebros;
    private float TIEMPO_CREA_CEREBRO = 1;
    private float tiempoCerebro = 1;
    private Texture texturaCerebro;

    //Pocimas
    private Texture texturaPocima;
    private Array<Pocimas> arrPocimas;
    private float timerCrearPocima;
    private float TIEMPO_CREA_POCIMA = 5;
    private float tiempoPocima = 8;

    //Sonidos
    private Sound efectoSalto;
    private Sound efectoBala;
    private Sound efectoPocima;

    //Texto
    private Texto texto;
    private float bateriaN3=100;
    private float vidaVillanoN3 = 100;

    //Zombies
    private Array<Zombies> arrZombies;
    private float timerCrearZombies;
    private float TIEMPO_CREA_ZOMBIE = 1;
    private float tiempoZombie = 1;
    private Texture texturaZombie;

    //Pausa
    private PantallaLucha3.EstadoJuego estado = PantallaLucha3.EstadoJuego.JUGANDO; // Jugando, Perdiendo, Ganar y Perder
    private PantallaLucha3.EscenaPausa escenaPausa;

    //Ganando
    private EscenaGanando escenaGanando;

    //Perdió
    private EscenaPerdio escenaPerdio;
    //Generador de Niveles
    private int NivelDisponible;

    public PantallaLucha3(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoNivel3 = juego.getManager().get("fondos/fondonivel3.png");
        pilaP3 = juego.getManager().get("sprites/pilaP3.png");
        pilaV3 = juego.getManager().get("sprites/pilaP3.png");
        crearNivel3();
        crearPersonaje();
        crearTexto();
        crearPocima();
        configurarMusica();
        crearProyectil();
        crearSonido();
        crearVillano();
        crearCerebros();
        crearZombies();
    }

    private void crearZombies() {
        texturaZombie = juego.getManager().get("Enemigos/zombie.png");
        arrZombies = new Array<>();
    }

    private void crearCerebros() {
        texturaCerebro = juego.getManager().get("Enemigos/cerebro.png");
        arrCerebros = new Array<>();
    }

    private void crearVillano() {
        texturaVillano = juego.getManager().get("Enemigos/Zombie1.PNG");
        villano=new Villano(texturaVillano);
    }

    private void crearProyectil() {
        texturaProyectil = juego.getManager().get("Proyectiles/bala.png");
        arrProyectil = new Array<>();
    }

    private void crearSonido() {
        AssetManager manager = new AssetManager();
        manager.load("Efectos/salto.mp3", Sound.class);
        manager.load("Efectos/bala.mp3", Sound.class);
        manager.load("Efectos/pocima.mp3", Sound.class);
        manager.finishLoading();
        efectoSalto = manager.get("Efectos/salto.mp3");
        efectoBala = manager.get("Efectos/bala.mp3");
        efectoPocima = manager.get("Efectos/pocima.mp3");
    }

    private void crearPocima() {
        texturaPocima = juego.getManager().get("Proyectiles/pocimaNivel3.png");
        arrPocimas = new Array<>();
    }

    private void configurarMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        Gdx.app.log("MUSICA NIVEL 3"," "+musicaFondo);
        if(musicaFondo==true){
            //Prender musica
            juego.reproducirMusicaNivel3();
            juego.detenerMusica();
            juego.detenerMusicaN2();
            juego.detenerMusicaN1();
            juego.detenerMusicaN4();
        }
    }

    private void crearTexto() {
        texto=new Texto("Texto/game.fnt");
    }

    private void crearPersonaje() {
        texturaPersonaje = juego.getManager().get("sprites/personaje.png");
        personaje=new Personaje(texturaPersonaje,ANCHO*0.05f,125);
    }

    private void crearNivel3() {
        escenaNivel3 = new Stage(vista);
        ///Boton de Pausa
        Texture btnNuevaPartida = juego.getManager().get("botones/BtnPausa3.png");
        TextureRegionDrawable trdBtNuevaPartida = new TextureRegionDrawable(new TextureRegion(btnNuevaPartida));

        //Boton Izquierda
        Texture bntIz = juego.getManager().get("botones/BotonIzquierda.png");
        TextureRegionDrawable trBntIz = new TextureRegionDrawable(new TextureRegion(bntIz));

        //Boton Derecha
        Texture bntDer = juego.getManager().get("botones/BotonDerecha.png");
        TextureRegionDrawable trBntDer = new TextureRegionDrawable(new TextureRegion(bntDer));

        //Boton Saltar
        Texture bntSaltar = juego.getManager().get("botones/BotonSaltar.png");
        TextureRegionDrawable trBntSaltar = new TextureRegionDrawable(new TextureRegion(bntSaltar));

        // Boton Disparar
        Texture bntDispara = juego.getManager().get("botones/BotonDisparar.png");
        TextureRegionDrawable trTirar = new TextureRegionDrawable(new TextureRegion(bntDispara));

        //Inverso de Pausa
        Texture btnNuevaPartidaInv = juego.getManager().get("botones/BtnPausa3.png");
        TextureRegionDrawable trdBtNuevaPartidaInv = new TextureRegionDrawable(new TextureRegion(btnNuevaPartidaInv));

        //Inverso de Boton Izquierda
        Texture btnIzIn = juego.getManager().get("botones/BotonIzquierdaInv.png");
        TextureRegionDrawable trdBtnIzIn = new TextureRegionDrawable(new TextureRegion(btnIzIn));

        //Inverso de Boton Derecha
        Texture bntDerIn = juego.getManager().get("botones/BotonDerechaInv.png");
        TextureRegionDrawable trdBtnDeIn = new TextureRegionDrawable(new TextureRegion(bntDerIn));

        //Inverso de boton Saltar
        Texture bntSaltarIn = juego.getManager().get("botones/BotonSaltarInv.png");
        TextureRegionDrawable trBntSaltarIn = new TextureRegionDrawable(new TextureRegion(bntSaltarIn));

        //Inverso de Boton Disparar
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
                Preferences preferencias = Gdx.app.getPreferences("Sonido");
                    boolean Sonido = preferencias.getBoolean("GeneralSonido");
                    if (arrProyectil.size < 5) {
                        Proyectil proyectil = new Proyectil(texturaProyectil, personaje.sprite.getX(), personaje.sprite.getY() + personaje.sprite.getHeight() * 0.5f);
                        arrProyectil.add(proyectil);
                        if (Sonido == true) {
                            efectoBala.play();
                        }
                    }
                }
        });
        //Pausa
        btnNP.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(estado== PantallaLucha3.EstadoJuego.JUGANDO) {
                    estado = PantallaLucha3.EstadoJuego.PAUSADO;
                    //Crear escena Pausa
                    if(escenaPausa==null){
                        escenaPausa=new PantallaLucha3.EscenaPausa(vista,batch);
                    }
                    Gdx.input.setInputProcessor(escenaPausa);
                    Gdx.app.log("Pausa","Cambia a Pausa");

                }else if (estado== PantallaLucha3.EstadoJuego.PAUSADO){
                    estado= PantallaLucha3.EstadoJuego.JUGANDO;
                    Gdx.app.log("Pausa","Cambia a Jugando");

                }
                return true;
            }
        });


        escenaNivel3.addActor(btnNP);
        escenaNivel3.addActor(btnIzquierda);
        escenaNivel3.addActor(bntDerecha);
        escenaNivel3.addActor(bntSalta);
        escenaNivel3.addActor(bntDisparas);
        Gdx.input.setInputProcessor(escenaNivel3);


    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        if(estado == PantallaLucha3.EstadoJuego.JUGANDO) {
            actualizar();

            batch.begin();
            batch.draw(fondoNivel3, 0, 0);
            batch.draw(pilaP3,ANCHO*0.03f,ALTO*0.83f);
            batch.draw(pilaV3,ANCHO*0.8f,ALTO*0.83f);
            personaje.render(batch);
            villano.render(batch);
            escenaNivel3.draw();

            dibujarVidaPersonaje();
            dibujarVidaVillano();
            dibujarPocimas();
            dibujarProyectil();
            dibujarCerebros();
            dibujarZombies();
            batch.end();

        }else if(estado == PantallaLucha3.EstadoJuego.PAUSADO){
            escenaPausa.draw();
        } else if (estado == EstadoJuego.GANANDO1|| estado == EstadoJuego.GANANDO2 || estado == EstadoJuego.GANANDO3
                || estado == EstadoJuego.GANANDO4) {
            batch.begin();
            batch.draw(fondoNivel3, 0, 0);
            batch.end();
            escenaGanando.draw();
        } else if (estado == EstadoJuego.PERDIO) {
            escenaPerdio.draw();
        }

    }

    private void dibujarZombies() {
        for (Zombies zombies :
                arrZombies) {
            zombies.render(batch);
            zombies.atacar();
        }
    }

    private void dibujarCerebros() {
        for (Cerebros cerebros :
                arrCerebros) {
            cerebros.render(batch);
            cerebros.atacar();
        }
    }

    private void dibujarProyectil() {
        for (Proyectil proyectil :
                arrProyectil) {
            proyectil.render(batch);
        }
    }

    private void dibujarPocimas() {
        for (Pocimas pocimas :
                arrPocimas) {
            pocimas.render(batch);
        }
    }

    private void dibujarVidaPersonaje() {
        int vidaPersonaje = (int)bateriaN3;
        texto.mostrarMensaje(batch,vidaPersonaje+"%",ANCHO*0.11f,ALTO*0.93f);
    }

    private void dibujarVidaVillano() {
        int VidaVillano3 = (int)vidaVillanoN3;
        texto.mostrarMensaje(batch,VidaVillano3+"%",ANCHO*0.88f,ALTO*0.93f);
    }


    private void actualizar() {
        actualizarPocimas();
        actualizarProyectil();
        actualizarCerebros();
        actuzalizarZombies();

        verificarPocimaTomada();
        verificarChoquesAEnemigo();
        verificarChoquesCerebroPersonaje();
        verificarChoquesZombiesPersonaje();
        verificarChoquesBalasZombies();
    }

    private void verificarChoquesBalasZombies() {
        for (int i=arrProyectil.size-1; i>=0; i--) {
            Proyectil proyectil = arrProyectil.get(i); //Proyectil atacante
            for (int j=arrZombies.size-1; j>=0; j--){
                Zombies zombies = arrZombies.get(j);
                // COLISION!!!
                if (proyectil.sprite.getBoundingRectangle().overlaps(zombies.sprite.getBoundingRectangle())) {
                    arrProyectil.removeIndex(i);
                    arrZombies.removeIndex(j);
                }
            }
        }
    }

    private void verificarChoquesZombiesPersonaje() {
        for (int i = arrZombies.size-1; i>=0; i--) {
            Zombies zombies = arrZombies.get(i);
            if (personaje.sprite.getBoundingRectangle().overlaps(zombies.sprite.getBoundingRectangle())) {
                arrZombies.removeIndex(i);
                bateriaN3 -= 10;
                break;
            } else if (bateriaN3 <= 0) {
                estado = PantallaLucha3.EstadoJuego.PERDIO;
                if (escenaPerdio == null) {
                    escenaPerdio = new PantallaLucha3.EscenaPerdio(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaPerdio);
            }
        }
    }

    private void actuzalizarZombies() {
        timerCrearZombies += Gdx.graphics.getDeltaTime();
        if (timerCrearZombies>=TIEMPO_CREA_ZOMBIE) {
            timerCrearZombies = 0;
            TIEMPO_CREA_ZOMBIE = tiempoZombie + MathUtils.random()*.4f;
            if (tiempoZombie>4) {
                tiempoZombie -= 1;
            }
            Zombies zombies = new Zombies(texturaZombie, ANCHO*.9f, 120);
            arrZombies.add(zombies);
        }
    }

    private void verificarChoquesCerebroPersonaje() {
        for (int i = arrCerebros.size-1; i>=0; i--) {
            Cerebros cerebros = arrCerebros.get(i);
            if (personaje.sprite.getBoundingRectangle().overlaps(cerebros.sprite.getBoundingRectangle())) {
                arrCerebros.removeIndex(i);
                bateriaN3 -= 10;
                break;
            } else if (bateriaN3 <= 0) {
                estado = PantallaLucha3.EstadoJuego.PERDIO;
                if (escenaPerdio == null) {
                    escenaPerdio = new PantallaLucha3.EscenaPerdio(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaPerdio);
            }
        }
    }

    private void actualizarCerebros() {
        timerCrearCerebros += Gdx.graphics.getDeltaTime();
        if (timerCrearCerebros>=TIEMPO_CREA_CEREBRO) {
            timerCrearCerebros = 0;
            TIEMPO_CREA_CEREBRO = tiempoCerebro + MathUtils.random()*.4f;
            if (tiempoCerebro>2) {
                tiempoCerebro -= 1;
            }
            Cerebros cerebros = new Cerebros(texturaCerebro, 950, 400);
            arrCerebros.add(cerebros);
        }
    }

    private void actualizarProyectil() {
        for (int i = arrProyectil.size - 1; i >= 0; i--) {
            if (personaje.getEstadoCaminando() == EstadoCaminando.QUIETO_DERECHA
                    || personaje.getEstadoCaminando() == EstadoCaminando.SALTA_DERECHA) {
                Proyectil proyectil = arrProyectil.get(i);
                proyectil.moverDerecha();
                proyectil.caida();
                if (proyectil.sprite.getX() > ANCHO) {
                    arrProyectil.removeIndex(i);
                }
            }
        }
    }

    private void verificarChoquesAEnemigo() {
        Preferences preferences=Gdx.app.getPreferences("Nivel");
        for (int i=arrProyectil.size-1; i>=0; i--) {
            Proyectil proyectil = arrProyectil.get(i); //Proyectil atacante
            // COLISION!!!
            if (proyectil.sprite.getBoundingRectangle().overlaps(villano.sprite.getBoundingRectangle())) {
                arrProyectil.removeIndex(i);
                // Descontar puntos
                vidaVillanoN3 -= 2;
                break;
            } else if (vidaVillanoN3 <= 0) {
                estado = EstadoJuego.GANANDO1;
                NivelDisponible=4;
                villano.setEstado(EstadoVillano.MUERTO);
                preferences.putInteger("NivelGeneral",NivelDisponible);
                preferences.flush();
                if (escenaGanando == null) {
                    escenaGanando = new EscenaGanando(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaGanando);
            }
        }
    }

    private void verificarPocimaTomada() {
        for (int i = arrPocimas.size-1; i >= 0; i--) {
            Pocimas pocima = arrPocimas.get(i); //Pocima
            // COLISION!!!
            Preferences preferencias = Gdx.app.getPreferences("Sonido");
            boolean Sonido = preferencias.getBoolean("GeneralSonido");
            if (pocima.sprite.getBoundingRectangle().overlaps(personaje.sprite.getBoundingRectangle())
                    && bateriaN3<90) {
                arrPocimas.removeIndex(i);
                // Aumentar puntos
                bateriaN3 += 5;
                if (Sonido == true) {
                    efectoPocima.play();
                }
                break;
            }
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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        //Fondos
        juego.getManager().unload("fondos/fondonivel3.png");
        juego.getManager().unload("fondos/PausaN3.png");

        //Sprites
        juego.getManager().unload("sprites/pilaP3.png");
        juego.getManager().unload("sprites/personaje.png");

        //Proyectiles
        juego.getManager().unload("Proyectiles/pocimaNivel3.png");
        juego.getManager().unload("Proyectiles/bala.png");

        //Efectos
        juego.getManager().unload("Efectos/salto.mp3");
        juego.getManager().unload("Efectos/bala.mp3");
        juego.getManager().unload("Efectos/pocima.mp3");

        //Enemigos
        juego.getManager().unload("Enemigos/Zombie1.PNG");
        juego.getManager().unload("Enemigos/cerebro.png");
        juego.getManager().unload("Enemigos/zombie.png");

        //Botones
        juego.getManager().unload("botones/BtnPausa3.png");
        juego.getManager().unload("botones/BotonIzquierda.png");
        juego.getManager().unload("botones/BotonDerecha.png");
        juego.getManager().unload("botones/BotonSaltar.png");
        juego.getManager().unload("botones/BotonDisparar.png");
        juego.getManager().unload("botones/BtnPausa3.png");
        juego.getManager().unload("botones/BotonIzquierdaInv.png");
        juego.getManager().unload("botones/BotonDerechaInv.png");
        juego.getManager().unload("botones/BotonSaltarInv.png");
        juego.getManager().unload("botones/BotonDispararInv.png");

        juego.getManager().unload("botones/BtnReanudarN3.png");
        juego.getManager().unload("botones/BtnMenuN3.png");
        juego.getManager().unload("botones/BtnMusicN3.png");
        juego.getManager().unload("botones/BtnSonidoN3.png");
        juego.getManager().unload("botones/BtnReanudarN3Inv.png");
        juego.getManager().unload("botones/BtnMenuN3Inv.png");
        juego.getManager().unload("botones/BtnMusic3Inv.png");
        juego.getManager().unload("botones/BtnSonidoN3Inv.png");

        juego.getManager().unload("botones/omitirN3.png");
        juego.getManager().unload("botones/avanzarN3.png");
        juego.getManager().unload("botones/PlayAgainN3.png");

        //Historieta
        //
        juego.getManager().unload("Historieta/VNLvl3_1.PNG");
        juego.getManager().unload("Historieta/VNLvl3_2.PNG");
        juego.getManager().unload("Historieta/VNLvl3_3.PNG");
        juego.getManager().unload("Historieta/VNLvl3_4.PNG");

        juego.getManager().unload("Historieta/perdistelvl3.PNG");
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
            Texture textura = juego.getManager().get("fondos/PausaN3.png");
            Image imgPausa=new Image(textura);
            imgPausa.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);
            this.addActor(imgPausa); //Fondo

            //Botones
            // Boton Reanudar
            Texture bntReanudar = juego.getManager().get("botones/BtnReanudarN3.png");
            TextureRegionDrawable trReanudar = new TextureRegionDrawable(new TextureRegion(bntReanudar));
            //Boton Menu
            Texture bntMenu = juego.getManager().get("botones/BtnMenuN3.png");
            TextureRegionDrawable trMenu = new TextureRegionDrawable(new TextureRegion(bntMenu));
            //Boton Musica
            Texture bntMusica = juego.getManager().get("botones/BtnMusicN3.png");
            TextureRegionDrawable trMusica= new TextureRegionDrawable(new TextureRegion(bntMusica));
            //Boton Sonido
            Texture bntSonido = juego.getManager().get("botones/BtnSonidoN3.png");
            TextureRegionDrawable trSonido =new TextureRegionDrawable(new TextureRegion(bntSonido));

            //Inverso de Reanudar
            Texture btnReanudarInv = juego.getManager().get("botones/BtnReanudarN3Inv.png");
            TextureRegionDrawable trdBtReanudarInv = new TextureRegionDrawable(new TextureRegion(btnReanudarInv));
            //Inverso de Menu
            Texture btnMenuInv = juego.getManager().get("botones/BtnMenuN3Inv.png");
            TextureRegionDrawable trdBtMenuInv = new TextureRegionDrawable(new TextureRegion(btnMenuInv));
            //Inverso de Musica
            Texture btnMusicaInv = juego.getManager().get("botones/BtnMusic3Inv.png");
            TextureRegionDrawable trdBtMusicanv = new TextureRegionDrawable(new TextureRegion(btnMusicaInv));
            //Inverso de Sonido
            Texture btnSonidoInv = juego.getManager().get("botones/BtnSonidoN3Inv.png");
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
                    estado = PantallaLucha3.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaNivel3);
                    Gdx.app.log("Pausa","Reanuda");
                }
            });

            //Listener Menú
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado= PantallaLucha3.EstadoJuego.JUGANDO;
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MAPA));
                }
            });

            //Listener Música
            btnMusica.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Preferences preferencias = Gdx.app.getPreferences("Musica");
                    boolean musicaFondo = preferencias.getBoolean("General");
                    Gdx.app.log("MUSICA 3", " " + musicaFondo);
                    if(musicaFondo==false) {
                        //Prender musica
                        btnMusica.setStyle(PrendidoMusica);
                        juego.reproducirMusicaNivel3();
                        juego.detenerMusicaN2();
                        juego.detenerMusicaN1();
                        juego.detenerMusica();
                        preferencias.putBoolean("General",true);
                    }else{
                        btnMusica.setStyle(ApagadoMusica);
                        juego.detenerMusicaN1();
                        juego.detenerMusicaN2();
                        juego.detenerMusicaN3();
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

    private class EscenaGanando extends Stage{
        private Image imgGanando;
        public EscenaGanando(Viewport vista, SpriteBatch batch){
            super(vista, batch);
            if (estado == PantallaLucha3.EstadoJuego.GANANDO1) {
                Texture textura1 = juego.getManager().get("Historieta/VNLvl3_1.PNG");
                imgGanando = new Image(textura1);
                imgGanando.setPosition(ANCHO/2-textura1.getWidth()/2, ALTO/2-textura1.getHeight()/2);
                Gdx.app.log("Ganando1", "Sí entra");
                this.addActor(imgGanando);
            }

            //Boton Omitir
            Texture btnOmitir = juego.getManager().get("botones/omitirN3.png");
            TextureRegionDrawable trOmitir = new TextureRegionDrawable(new TextureRegion(btnOmitir));
            final ImageButton btnOmitirFinal = new ImageButton(trOmitir,trOmitir);
            btnOmitirFinal.setPosition(ANCHO*0.91F,ALTO*0.98F, Align.topRight);
            btnOmitirFinal.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL4));
                }
            });
            this.addActor(btnOmitirFinal);

            // Boton Avanzar
            Texture btnAvanzar = juego.getManager().get("botones/avanzarN3.png");
            TextureRegionDrawable trAvanzar = new TextureRegionDrawable(new TextureRegion(btnAvanzar));
            final ImageButton btnAvanza = new ImageButton(trAvanzar, trAvanzar);
            btnAvanza.setPosition(ANCHO * 0.9f, ALTO * 0.84f, Align.topRight);
            btnAvanza.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (estado == PantallaLucha3.EstadoJuego.GANANDO1) {
                        estado = PantallaLucha3.EstadoJuego.GANANDO2;
                        Gdx.app.log("Ganando2", "Sí cambia");
                        Texture textura2 = juego.getManager().get("Historieta/VNLvl3_2.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura2);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == PantallaLucha3.EstadoJuego.GANANDO2) {
                        estado = PantallaLucha3.EstadoJuego.GANANDO3;
                        Gdx.app.log("Ganando3", "Sí cambia");
                        Texture textura3 = juego.getManager().get("Historieta/VNLvl3_3.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura3);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == PantallaLucha3.EstadoJuego.GANANDO3) {
                        estado = PantallaLucha3.EstadoJuego. GANANDO4;
                        Gdx.app.log("Ganando4", "Sí cambia");
                        Texture textura4 = juego.getManager().get("Historieta/VNLvl3_4.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura4);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == PantallaLucha3.EstadoJuego.GANANDO4) {
                        juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL4));
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
            Texture textura = juego.getManager().get("Historieta/perdistelvl3.PNG");
            Image imgPerdio = new Image(textura);
            imgPerdio.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);

            this.addActor(imgPerdio);

            //Boton de Jugar de Nuevo
            Texture btnJugarDeNuevo = juego.getManager().get("botones/PlayAgainN3.png");
            TextureRegionDrawable trJugarDeNuevo = new TextureRegionDrawable(new TextureRegion(btnJugarDeNuevo));
            final ImageButton btnJugarDeNuevoNivel = new ImageButton(trJugarDeNuevo,trJugarDeNuevo);
            btnJugarDeNuevoNivel.setPosition(ANCHO*.8f,ALTO*0.4F, Align.topRight);
            btnJugarDeNuevoNivel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL3));
                }
            });
            this.addActor(btnJugarDeNuevoNivel);

            // Boton Avanzar
            //Texture btnAvanzar = new Texture("botones/avanzar.png");
            Texture btnAvanzar = juego.getManager().get("botones/avanzarN31.png");
            TextureRegionDrawable trAvanzar = new TextureRegionDrawable(new TextureRegion(btnAvanzar));
            ImageButton btnAvanza = new ImageButton(trAvanzar, trAvanzar);
            btnAvanza.setPosition(ANCHO/2, ALTO * 0.2f, Align.bottom);
            btnAvanza.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MAPA));

                }
            });
            this.addActor(btnAvanza);
        }
    }
}