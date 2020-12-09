package mx.itesm.enigma.outsider;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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

public class PantallaLucha4 extends Pantalla {
    private final Juego juego;
    private Stage escenaNivel4;
    private Texture fondoNivel4;
    private Texture pilaP4;
    private Texture pilaV4;

    //Personaje
    private Personaje personaje;
    private Texture texturaPersonaje;

    //Misiles
    private Array<Misiles> arrMisiles;
    private float timerCrearMisil;
    private float TIEMPO_CREA_MISIL = 1;
    private float tiempoMisil = 1;
    private Texture texturaMisil;

    // Proyectil
    private Texture texturaProyectilMoviendose;
    private Texture texturaProyectilExplotando;
    private Array<Proyectil> arrProyectil;


    //Sonidos
    private Sound efectoSalto;
    private Sound efectoLaser;
    private Sound efectoPocima;

    //Texto
    private Texto texto;
    private float bateriaN4 = 100;
    private float vidaVillanoN4 = 100;

    //Pausa
    private PantallaLucha4.EstadoJuego estado= PantallaLucha4.EstadoJuego.JUGANDO; // Jugando, Perdiendo, Ganar y Perder
    private PantallaLucha4.EscenaPausa escenaPausa;

    //Ganando
    private EscenaGanando escenaGanando;

    //Perdió
    private EscenaPerdio escenaPerdio;

    //Robot muriendo
    private  EscenaMuriendo escenaMuriendo;

    //Escena Final
    private  EscenaFinal escenaFinal;

    //Villano
    private Villano villano;
    private Texture texturaVillano;

    //Pocimas
    private Texture texturaPocima;
    private Array<Pocimas> arrPocimas;
    private float timerCrearPocima;
    private float TIEMPO_CREA_POCIMA = 3;
    private float tiempoPocima = 10;

    //Drones
    private Texture texturaDrones;
    private Array<Drones> arrDrones;
    private float timerCrearDrones;
    private float TIEMPO_CREA_DRONES = 1;
    private float tiempoDrones = 1;

    //Nivel Disponible
    private int NivelDisponible=4;

    public PantallaLucha4(Juego juego) {
        this.juego = juego;
    }

    @Override
    public void show() {
        fondoNivel4 = juego.getManager().get("fondos/fondonivel4.png");
        pilaP4 = juego.getManager().get("sprites/pilaP4.png");
        pilaV4 = juego.getManager().get("sprites/pilaP4.png");
        crearNivel3();
        crearPersonaje();
        arrProyectil=new Array<>();
        crearSonido();
        crearTexto();
        configurarMusica();
        crearVillano();
        crearPocima();
        crearMisiles();
        crearDrones();
        Gdx.input.setCatchKey(Input.Keys.BACK, true);
    }

    private void crearDrones() {
        texturaDrones = juego.getManager().get("Enemigos/dron.png");
        arrDrones= new Array<>();
    }

    private void crearMisiles() {
        texturaMisil = juego.getManager().get("Enemigos/misil.png");
        arrMisiles = new Array<>();
    }

    private Proyectil crearProyectil() {
        texturaProyectilMoviendose=new Texture("Proyectiles/rayos.png");
        texturaProyectilExplotando=new Texture("Efectos/explosion4.png");
        Proyectil proyectil=new Proyectil(texturaProyectilMoviendose,texturaProyectilExplotando,
                personaje.sprite.getX() + personaje.sprite.getWidth()*0.6f,
                personaje.sprite.getY() + personaje.sprite.getHeight()*0.12f);
        return proyectil;
    }

    private void crearPocima() {
        texturaPocima = juego.getManager().get("Proyectiles/pocimaNivel4.png");
        arrPocimas = new Array<>();
    }

    private void crearVillano() {
        texturaVillano = juego.getManager().get("Enemigos/Robot1.png");
        villano=new Villano(texturaVillano);
    }

    private void configurarMusica() {
        Preferences preferencias = Gdx.app.getPreferences("Musica");
        boolean musicaFondo = preferencias.getBoolean("General");
        Gdx.app.log("MUSICA NIVEL 4"," "+musicaFondo);
        if(musicaFondo==true){
            //Prender musica
            juego.reproducirMusicaNivel4();
            juego.detenerMusicaN3();
            juego.detenerMusicaN2();
            juego.detenerMusicaN1();
            juego.detenerMusica();
        }
    }

    private void crearTexto() {
        texto=new Texto("Texto/game.fnt");
    }

    private void crearSonido() {
        AssetManager manager = new AssetManager();
        manager.load("Efectos/salto.mp3", Sound.class);
        manager.load("Efectos/laser.mp3", Sound.class);
        manager.load("Efectos/pocima.mp3", Sound.class);
        manager.finishLoading();
        efectoSalto = manager.get("Efectos/salto.mp3");
        efectoLaser = manager.get("Efectos/laser.mp3");
        efectoPocima = manager.get("Efectos/pocima.mp3");

    }

    private void crearPersonaje() {
        texturaPersonaje = juego.getManager().get("sprites/personaje.png");
        personaje=new Personaje(texturaPersonaje,ANCHO*0.13f,125);
    }

    private void crearNivel3() {
        escenaNivel4 = new Stage(vista);
        ///Boton de Pausa
        Texture btnNuevaPartida = juego.getManager().get("botones/BtnPausa4.png");
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
        Texture btnNuevaPartidaInv = juego.getManager().get("botones/BtnPausa4.png");
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
                if (personaje.getEstado() != EstadoKAIM.SALTANDO) {
                    personaje.saltar();
                    if (Sonido==true) {
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
                    Preferences preferencias = Gdx.app.getPreferences("Sonido");
                    boolean Sonido = preferencias.getBoolean("GeneralSonido");
                    if (arrProyectil.size < 10) {
                        Proyectil proyectil = crearProyectil();
                        arrProyectil.add(proyectil);
                        if(personaje.getEstado()!=EstadoKAIM.DISPARANDO_LASERS){
                            personaje.setEstado(EstadoKAIM.DISPARANDO_LASERS);
                        }
                        if (Sonido == true) {
                            efectoLaser.play();
                        }
                    }
                }
            }
        });
        //Pausa
        btnNP.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if(estado== PantallaLucha4.EstadoJuego.JUGANDO) {
                    estado = PantallaLucha4.EstadoJuego.PAUSADO;
                    //Crear escena Pausa
                    if(escenaPausa==null){
                        escenaPausa=new PantallaLucha4.EscenaPausa(vista,batch);
                    }
                    Gdx.input.setInputProcessor(escenaPausa);
                    Gdx.app.log("Pausa","Cambia a Pausa");

                }else if (estado== PantallaLucha4.EstadoJuego.PAUSADO){
                    estado= PantallaLucha4.EstadoJuego.JUGANDO;
                    Gdx.app.log("Pausa","Cambia a Jugando");

                }
                return true;
            }
        });


        escenaNivel4.addActor(btnNP);
        escenaNivel4.addActor(btnIzquierda);
        escenaNivel4.addActor(bntDerecha);
        escenaNivel4.addActor(bntSalta);
        escenaNivel4.addActor(bntDisparas);
        Gdx.input.setInputProcessor(escenaNivel4);


    }

    @Override
    public void render(float delta) {
        borrarPantalla();
        batch.setProjectionMatrix(camara.combined);
        if (estado == PantallaLucha4.EstadoJuego.JUGANDO) {
            actualizar();

            batch.begin();
            batch.draw(fondoNivel4, 0, 0);
            batch.draw(pilaP4, ANCHO * 0.03f, ALTO * 0.83f);
            batch.draw(pilaV4, ANCHO * 0.8f, ALTO * 0.83f);
            personaje.render(batch);
            villano.render(batch);
            escenaNivel4.draw();

            dibujarProyectil();
            dibujarVidaPersonaje();
            dibujarVidaVillano();
            dibujarPocimas();
            dibujarDrones();
            dibujarMisiles();

            batch.end();

        } else if (estado == PantallaLucha4.EstadoJuego.PAUSADO) {
            escenaPausa.draw();
        } else if (estado == EstadoJuego.MURIENDO1) {
            escenaMuriendo.draw();
        } else if (estado == EstadoJuego.GANANDO1 || estado == EstadoJuego.GANANDO2 || estado == EstadoJuego.GANANDO3 || estado == EstadoJuego.GANANDO4){
            batch.begin();
            batch.draw(fondoNivel4, 0 , 0);
            batch.end();
            escenaGanando.draw();
        }else if (estado == EstadoJuego.FINAL1 || estado == EstadoJuego.FINAL2 || estado == EstadoJuego.FINAL3){
            escenaFinal.draw();
        } else if (estado == EstadoJuego.PERDIO) {
            escenaPerdio.draw();
        }

    }

    private void dibujarDrones() {
        for (Drones drones :
                arrDrones) {
            drones.render(batch);
            drones.atacar();
        }
    }

    private void dibujarMisiles() {
        for (Misiles misiles :
                arrMisiles) {
            misiles.render(batch);
            misiles.atacar();
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
        int vidaPersonaje = (int)bateriaN4;
        texto.mostrarMensaje(batch,vidaPersonaje+"%",ANCHO*0.11f,ALTO*0.93f);
    }

    private void dibujarVidaVillano() {
        int VidaVillano2 = (int)vidaVillanoN4;
        texto.mostrarMensaje(batch,VidaVillano2+"%",ANCHO*0.88f,ALTO*0.93f);
    }

    private void actualizar() {
        verificarPocimaTomada();
        verificarChoquesAEnemigo();
        verificarChoquesMisilPersonaje();
        verificarChoquesDrones();
        verificarChoquesLaserDron();

        actualizarProyectil();
        actualizarPocimas();
        actualizarMisiles();
        actualizarDrones();
    }

    private void verificarChoquesLaserDron() {
        for (int i=arrProyectil.size-1; i>=0; i--) {
           Proyectil proyectil = arrProyectil.get(i);
            for (int j=arrDrones.size-1; j>=0; j--){
                Drones drones = arrDrones.get(j);
                // COLISION!!!
                if (proyectil.sprite.getBoundingRectangle().overlaps(drones.sprite.getBoundingRectangle())) {
                    arrProyectil.removeIndex(i);
                    arrDrones.removeIndex(j);
                }
            }
        }
    }

    private void verificarChoquesDrones() {
        for (int i = arrDrones.size-1; i>=0; i--) {
            Drones drones = arrDrones.get(i);
            if (personaje.sprite.getBoundingRectangle().overlaps(drones.sprite.getBoundingRectangle())) {
                arrDrones.removeIndex(i);
                bateriaN4 -= 4;
                break;
            }
        }
    }

    private void actualizarDrones() {
        timerCrearDrones += Gdx.graphics.getDeltaTime();
        if (timerCrearDrones>=TIEMPO_CREA_DRONES) {
            timerCrearDrones = 0;
            TIEMPO_CREA_DRONES = tiempoDrones + MathUtils.random()*.4f;
            if (tiempoDrones>2) {
                tiempoDrones -= 1;
            }
            Drones drones = new Drones(texturaDrones, ANCHO*0.97f, 120+MathUtils.random(1,40)*10);
            arrDrones.add(drones);
        }
    }

    private void verificarChoquesMisilPersonaje() {
        for (int i = arrMisiles.size-1; i>=0; i--) {
            Misiles misiles = arrMisiles.get(i);
            if (personaje.sprite.getBoundingRectangle().overlaps(misiles.sprite.getBoundingRectangle())) {
                arrMisiles.removeIndex(i);
                bateriaN4 -= 4;
                break;
            } else if (bateriaN4 <= 0) {
                estado = PantallaLucha4.EstadoJuego.PERDIO;
                if (escenaPerdio == null) {
                    escenaPerdio = new PantallaLucha4.EscenaPerdio(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaPerdio);
            }
        }
    }

    private void actualizarMisiles() {
        timerCrearMisil += Gdx.graphics.getDeltaTime();
        if (timerCrearMisil>=TIEMPO_CREA_MISIL) {
            timerCrearMisil = 0;
            TIEMPO_CREA_MISIL = tiempoMisil + MathUtils.random()*.4f;
            if (tiempoMisil>2) {
                tiempoMisil -= 1;
            }
            Misiles misiles = new Misiles(texturaMisil, 920 + MathUtils.random(1,3)*10, 100 + MathUtils.random(1,5)*100);
            arrMisiles.add(misiles);
        }
    }

    private void verificarChoquesAEnemigo() {
        for (int i=arrProyectil.size-1; i>=0; i--) {
           Proyectil proyectil = arrProyectil.get(i); //Proyectil atacante

            // COLISION!!!
            Rectangle rectVillano = villano.sprite.getBoundingRectangle();
            rectVillano.x += rectVillano.width/3;
            if (proyectil.sprite.getBoundingRectangle().overlaps(rectVillano)) {
                if(proyectil.getEstado()== EstadoObjeto.MOVIENDO) {
                    // Descontar puntos
                    vidaVillanoN4 -= 0.2;
                    proyectil.setEstado(EstadoObjeto.EXPLOTANDO);
                }else if(proyectil.getEstado()== EstadoObjeto.DESAPARECE){
                    arrProyectil.removeIndex(i);
                }
                break;
            } else if (vidaVillanoN4 < 1) {
                estado = EstadoJuego.MURIENDO1;
                villano.setEstado(EstadoVillano.MUERTO);
                if (escenaMuriendo == null) {
                    escenaMuriendo = new EscenaMuriendo(vista, batch);
                }
                Gdx.input.setInputProcessor(escenaMuriendo);

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
                    && bateriaN4<96) {
                arrPocimas.removeIndex(i);
                // Aumentar puntos
                bateriaN4 += 5;
                if(Sonido==true) {
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

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        //Fondos
        juego.getManager().unload("fondos/fondonivel4.png");
        juego.getManager().unload("fondos/PausaN4.png");

        //Sprites
        juego.getManager().unload("sprites/pilaP4.png");
        juego.getManager().unload("sprites/personaje.png");

        //Proyectiles
        juego.getManager().unload("Proyectiles/rayos.png");
        juego.getManager().unload("Proyectiles/pocimaNivel4.png");

        //Efectos
        juego.getManager().unload("Efectos/salto.mp3");
        juego.getManager().unload("Efectos/laser.mp3");
        juego.getManager().unload("Efectos/pocima.mp3");
        juego.getManager().unload("Efectos/explosion4.png");

        //Enemigos
        juego.getManager().unload("Enemigos/Robot1.png");
        juego.getManager().unload("Enemigos/misil.png");
        juego.getManager().unload("Enemigos/dron.png");
        juego.getManager().unload("MuerteVillanos/muerteR1.png");

        //Texto
        juego.getManager().unload("Texto/game.fnt");

        //Botones
        juego.getManager().unload("botones/BtnPausa4.png");
        juego.getManager().unload("botones/BotonIzquierda.png");
        juego.getManager().unload("botones/BotonDerecha.png");
        juego.getManager().unload("botones/BotonSaltar.png");
        juego.getManager().unload("botones/BotonDisparar.png");
        juego.getManager().unload("botones/BtnPausa2.png");
        juego.getManager().unload("botones/BotonIzquierdaInv.png");
        juego.getManager().unload("botones/BotonDerechaInv.png");
        juego.getManager().unload("botones/BotonSaltarInv.png");
        juego.getManager().unload("botones/BotonDispararInv.png");

        juego.getManager().unload("botones/BtnReanudarN4.png");
        juego.getManager().unload("botones/BtnMenuN4.png");
        juego.getManager().unload("botones/BtnMusicN4.png");
        juego.getManager().unload("botones/BtnSonidoN4.png");
        juego.getManager().unload("botones/BtnReanudarN4Inv.png");
        juego.getManager().unload("botones/BtnMenuN4Inv.png");
        juego.getManager().unload("botones/BtnMusicN4Inv.png");
        juego.getManager().unload("botones/BtnSonidoN4Inv.png");

        juego.getManager().unload("botones/avanzarN4.png");
        juego.getManager().unload("botones/avanzarN4P.png");
        juego.getManager().unload("botones/omitirN4.png");
        juego.getManager().unload("botones/PlayAgainN4.png");
        juego.getManager().unload("BtnGanar/final.png");

        //Historieta
        juego.getManager().unload("Historieta/VNLvl4_1.PNG");
        juego.getManager().unload("Historieta/VNLvl4_2.PNG");
        juego.getManager().unload("Historieta/VNLvl4_3.PNG");
        juego.getManager().unload("Historieta/VNLvl4_4.PNG");

        juego.getManager().unload("Historieta/perdistelvl4.PNG");

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
        FINAL1,
        FINAL2,
        FINAL3,
        PERDIO
    }
    private class EscenaPausa extends Stage {
        public EscenaPausa(Viewport vista, SpriteBatch batch){
            super(vista,batch);
            Texture textura = juego.getManager().get("fondos/PausaN4.png");
            Image imgPausa=new Image(textura);
            imgPausa.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);
            this.addActor(imgPausa); //Fondo

            //Botones
            // Boton Reanudar
            Texture bntReanudar = juego.getManager().get("botones/BtnReanudarN4.png");
            TextureRegionDrawable trReanudar = new TextureRegionDrawable(new TextureRegion(bntReanudar));
            //Boton Menu
            Texture bntMenu = juego.getManager().get("botones/BtnMenuN4.png");
            TextureRegionDrawable trMenu = new TextureRegionDrawable(new TextureRegion(bntMenu));
            //Boton Musica
            Texture bntMusica = juego.getManager().get("botones/BtnMusicN4.png");
            TextureRegionDrawable trMusica= new TextureRegionDrawable(new TextureRegion(bntMusica));
            //Boton Sonido
            Texture bntSonido = juego.getManager().get("botones/BtnSonidoN4.png");
            TextureRegionDrawable trSonido =new TextureRegionDrawable(new TextureRegion(bntSonido));

            //Inverso de Reanudar
            Texture btnReanudarInv = juego.getManager().get("botones/BtnReanudarN4Inv.png");
            TextureRegionDrawable trdBtReanudarInv = new TextureRegionDrawable(new TextureRegion(btnReanudarInv));
            //Inverso de Menu
            Texture btnMenuInv = juego.getManager().get("botones/BtnMenuN4Inv.png");
            TextureRegionDrawable trdBtMenuInv = new TextureRegionDrawable(new TextureRegion(btnMenuInv));
            //Inverso de Musica
            Texture btnMusicaInv = juego.getManager().get("botones/BtnMusicN4Inv.png");
            TextureRegionDrawable trdBtMusicanv = new TextureRegionDrawable(new TextureRegion(btnMusicaInv));
            //Inverso de Sonido
            Texture btnSonidoInv = juego.getManager().get("botones/BtnSonidoN4Inv.png");
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
                    estado= PantallaLucha4.EstadoJuego.JUGANDO;
                    Gdx.input.setInputProcessor(escenaNivel4);
                    Gdx.app.log("Pausa","Reanuda");
                }
            });

            //Listener Menú
            btnMenu.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    estado= PantallaLucha4.EstadoJuego.JUGANDO;
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MENU));
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
                        juego.reproducirMusicaNivel4();
                        preferencias.putBoolean("General",true);
                    }else{
                        btnMusica.setStyle(ApagadoMusica);
                        juego.detenerMusicaN1();
                        juego.detenerMusicaN4();
                        preferencias.putBoolean("General",false);
                    }
                    preferencias.flush();
                }
            });
            //Listener Sonido
            btnSonido.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Preferences preferenciasSonido = Gdx.app.getPreferences("Sonido");
                    boolean Sonido = preferenciasSonido.getBoolean("GeneralSonido");
                    Gdx.app.log("SonidoB", " " + Sonido);
                    if (Sonido == false) {
                        //Prender Sonido
                        btnSonido.setStyle(PrendidoSonido);
                        preferenciasSonido.putBoolean("GeneralSonido", true);
                    } else {
                        //Apagar Sonido
                        btnSonido.setStyle(ApagadoSonido);
                        preferenciasSonido.putBoolean("GeneralSonido", false);
                    }
                    preferenciasSonido.flush();
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
        public EscenaGanando(final Viewport vista, final SpriteBatch batch){
            super(vista, batch);
            if (estado == EstadoJuego.GANANDO1) {
                Texture textura1 = juego.getManager().get("Historieta/VNLvl4_1.PNG");
                imgGanando = new Image(textura1);
                imgGanando.setPosition(ANCHO/2-textura1.getWidth()/2, ALTO/2-textura1.getHeight()/2);
                Gdx.app.log("Ganando1", "Sí entra");
                this.addActor(imgGanando);
            }

            //Boton Omitir
            Texture btnOmitir = juego.getManager().get("botones/omitirN4.png");
            TextureRegionDrawable trOmitir = new TextureRegionDrawable(new TextureRegion(btnOmitir));
            final ImageButton btnOmitirFinal = new ImageButton(trOmitir,trOmitir);
            btnOmitirFinal.setPosition(ANCHO*0.2F,ALTO*0.98F, Align.topRight);
            btnOmitirFinal.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MAPA));
                }
            });
            this.addActor(btnOmitirFinal);

            // Boton Avanzar
            Texture btnAvanzar = juego.getManager().get("botones/avanzarN4.png");
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
                        Texture textura2 = juego.getManager().get("Historieta/VNLvl4_2.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura2);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO2) {
                        estado = EstadoJuego.GANANDO3;
                        Gdx.app.log("Ganando3", "Sí cambia");
                        Texture textura3 = juego.getManager().get("Historieta/VNLvl4_3.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura3);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO3) {
                        estado = EstadoJuego. GANANDO4;
                        Gdx.app.log("Ganando4", "Sí cambia");
                        Texture textura4 = juego.getManager().get("Historieta/VNLvl4_4.PNG");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura4);
                        imgGanando.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.GANANDO4) {
                        estado = EstadoJuego.FINAL1;
                        if (escenaFinal == null) {
                            escenaFinal = new EscenaFinal(vista, batch);
                        }
                        Gdx.input.setInputProcessor(escenaFinal);
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
            Texture textura = juego.getManager().get("Historieta/perdistelvl4.PNG");
            Image imgPerdio = new Image(textura);
            imgPerdio.setPosition(ANCHO/2-textura.getWidth()/2,ALTO/2-textura.getHeight()/2);

            this.addActor(imgPerdio);

            //Boton de Jugar de Nuevo
            Texture btnJugarDeNuevo = juego.getManager().get("botones/PlayAgainN4.png");
            TextureRegionDrawable trJugarDeNuevo = new TextureRegionDrawable(new TextureRegion(btnJugarDeNuevo));
            final ImageButton btnJugarDeNuevoNivel = new ImageButton(trJugarDeNuevo,trJugarDeNuevo);
            btnJugarDeNuevoNivel.setPosition(ANCHO*.9f,ALTO*0.34F, Align.topRight);
            btnJugarDeNuevoNivel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.NIVEL4));
                }
            });
            this.addActor(btnJugarDeNuevoNivel);

            // Boton Avanzar
            Texture btnAvanzar = juego.getManager().get("botones/avanzarN4P.png");
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

    private class EscenaMuriendo extends Stage{
        private Image imgMuriendo;
        public EscenaMuriendo(final Viewport vista, final SpriteBatch batch) {
            super(vista, batch);
            if (estado == PantallaLucha4.EstadoJuego.MURIENDO1) {
                Texture textura1 = juego.getManager().get("MuerteVillanos/muerteR1.png");
                imgMuriendo = new Image(textura1);
                imgMuriendo.setPosition(ANCHO/2-textura1.getWidth()/2, ALTO/2-textura1.getHeight()/2);
                Gdx.app.log("Muriendo1", "Sí entra");
                this.addActor(imgMuriendo);
            }

            // Boton final
            Texture btnAvanzar = juego.getManager().get("BtnGanar/final.png");
            TextureRegionDrawable trAvanzar = new TextureRegionDrawable(new TextureRegion(btnAvanzar));
            final ImageButton btnAvanza = new ImageButton(trAvanzar, trAvanzar);
            btnAvanza.setPosition(ANCHO*.62f, ALTO * 0.4f, Align.topRight);
            btnAvanza.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (estado == PantallaLucha4.EstadoJuego.MURIENDO1) {
                        estado = PantallaLucha4.EstadoJuego.GANANDO1;
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
    private class EscenaFinal extends Stage{
        private Image imgFinal;
        public EscenaFinal(final Viewport vista, final SpriteBatch batch) {
            super(vista, batch);
            if (estado == EstadoJuego.FINAL1) {
                Texture textura1 = juego.getManager().get("Historieta/final1.png");
                imgFinal = new Image(textura1);
                imgFinal.setPosition(ANCHO/2-textura1.getWidth()/2, ALTO/2-textura1.getHeight()/2);
                Gdx.app.log("Final 1", "Sí entra");
                this.addActor(imgFinal);
            }

            //Boton Omitir
            Texture btnOmitir = juego.getManager().get("botones/omitirN4.png");
            TextureRegionDrawable trOmitir = new TextureRegionDrawable(new TextureRegion(btnOmitir));
            final ImageButton btnOmitirFinal = new ImageButton(trOmitir,trOmitir);
            btnOmitirFinal.setPosition(ANCHO*0.2F,ALTO*0.98F, Align.topRight);
            btnOmitirFinal.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    juego.setScreen(new PantallaCargando(juego, Pantallas.MENU));
                }
            });
            this.addActor(btnOmitirFinal);

            // Boton Avanzar
            Texture btnAvanzar = juego.getManager().get("botones/avanzarN4.png");
            TextureRegionDrawable trAvanzar = new TextureRegionDrawable(new TextureRegion(btnAvanzar));
            final ImageButton btnAvanza = new ImageButton(trAvanzar, trAvanzar);
            btnAvanza.setPosition(ANCHO * 0.2f, ALTO * 0.84f, Align.topRight);
            btnAvanza.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    if (estado == EstadoJuego.FINAL1) {
                        estado = EstadoJuego.FINAL2;
                        Gdx.app.log("Muriendo2", "Sí cambia");
                        Texture textura2 = juego.getManager().get("Historieta/final2.png");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura2);
                        imgFinal.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.FINAL2) {
                        estado = EstadoJuego.FINAL3;
                        Gdx.app.log("Muriendo3", "Sí cambia");
                        Texture textura3 = juego.getManager().get("Historieta/final3.png");
                        TextureRegionDrawable nuevaImagen = new TextureRegionDrawable(textura3);
                        imgFinal.setDrawable(nuevaImagen);
                        btnAvanza.toFront();
                    } else if (estado == EstadoJuego.FINAL3) {
                        juego.setScreen(new PantallaCargando(juego, Pantallas.MENU));
                        btnAvanza.toFront();
                    }
                }
            });
            this.addActor(btnAvanza);
        }
    }
}
