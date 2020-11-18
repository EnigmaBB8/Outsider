package mx.itesm.enigma.outsider;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PantallaCargando extends Pantalla {

    //Animación cargando
    private Sprite spriteCargando;
    public static final float TIEMPO_ENTRE_FRAMES = 0.05f;
    private float timerAnimacion = TIEMPO_ENTRE_FRAMES;
    private Texture texturaCargando;
    private Texture texturaFondo;

    //Asset Manager
    private AssetManager manager;

    //Referencia al juego
    private Juego juego;       //Para hacer set Screen

    //Identifica las pantallas del juego
    private Pantallas siguientePantalla;

    //Porcentaje de carga
    private int avance;

    //Para mostrar mensajes
    private Texto texto;

    public PantallaCargando(Juego juego, Pantallas siguientePantalla) {
        this.juego = juego;
        this.siguientePantalla = siguientePantalla;
    }

    @Override
    public void show() {
        juego.detenerMusica();
        this.texturaFondo = new Texture("Cargando/fondoloading.png");
        this.texturaCargando = new Texture("Cargando/loading.png");
        spriteCargando = new Sprite(texturaCargando);
        spriteCargando.setPosition(ANCHO/2 - spriteCargando.getWidth()/2, ALTO/2 -spriteCargando.getHeight()/2);
        cargarRecursos(siguientePantalla);
        texto= new Texto("Texto/game.fnt");
    }

    private void cargarRecursos(Pantallas siguientePantalla) {
        manager = juego.getManager();
        avance = 0;     //Porcentaje de carga
        switch (siguientePantalla) {
            case MENU:
                cargarRecursosMenu();
                break;
            case AYUDA:
                cargarRecursosAyuda();
                break;
            case HISTORIA:
                cargarRecursosHistoria();
                break;
            case REANUDAR:
                cargarRecursosReanudar();
                break;
            case CONFIGURACION:
                cargarRecursosConfiguracion();
                break;
            case ACERCA_DE:
                cargarRecursosAcercaDe();
                break;
            case ACERCA_DE_ABRAHAM:
                cargarRecursosAcercaDeAbraham();
                break;
            case ACERCA_DE_ITZEL:
                cargarRecursosAcercaDeItzel();
                break;
            case ACERCA_DE_KARLA:
                cargarRecursosAcercaDeKarla();
                break;
            case ACERDA_DE_MANUEL:
                cargarRecursosAcercaDeManuel();
                break;
            case NIVEL1:
                cargarRecursosNivel1();
                break;
            case NIVEL2:
                cargarRecursosNivel2();
                break;
            case NIVEL3:
                cargarRecursosNivel3();
                break;
        }
    }

    private void cargarRecursosNivel3() {
        //Fondos
        manager.load("fondos/fondonivel3.png", Texture.class);
        manager.load("fondos/PausaN3.png", Texture.class);

        //Sprites
        manager.load("sprites/pilaP3.png", Texture.class);
        manager.load("sprites/personaje.png", Texture.class);

        //Efectos
        manager.load("Efectos/salto.mp3", Sound.class);
        manager.load("Efectos/Flecha.mp3", Sound.class);
        manager.load("Efectos/bolaDeFuego.mp3", Sound.class);
        manager.load("Efectos/pocima.mp3", Sound.class);

        //Botones
        manager.load("botones/BtnPausa3.png", Texture.class);
        manager.load("botones/BotonIzquierda.png", Texture.class);
        manager.load("botones/BotonDerecha.png", Texture.class);
        manager.load("botones/BotonSaltar.png", Texture.class);
        manager.load("botones/BotonDisparar.png", Texture.class);

        manager.load("botones/BtnPausa3.png", Texture.class);
        manager.load("botones/BotonIzquierdaInv.png", Texture.class);
        manager.load("botones/BotonDerechaInv.png", Texture.class);
        manager.load("botones/BotonSaltarInv.png", Texture.class);
        manager.load("botones/BotonDispararInv.png", Texture.class);

        manager.load("botones/BtnReanudarN3.png", Texture.class);
        manager.load("botones/BtnMenuN3.png", Texture.class);
        manager.load("botones/BtnMusicN3.png", Texture.class);
        manager.load("botones/BtnSonidoN3.png", Texture.class);
        manager.load("botones/BtnReanudarN3Inv.png", Texture.class);
        manager.load("botones/BtnMenuN3Inv.png", Texture.class);
        manager.load("botones/BtnMusic3Inv.png", Texture.class);
        manager.load("botones/BtnSonidoN3Inv.png", Texture.class);
    }

    private void cargarRecursosNivel2() {
        //Fondos
        manager.load("fondos/fondonivel2.png", Texture.class);
        manager.load("fondos/PausaN2.png", Texture.class);

        //Sprites
        manager.load("sprites/pilaP2.png", Texture.class);
        manager.load("sprites/personaje.png", Texture.class);

        //Proyectiles
        manager.load("Proyectiles/bolasMagicas.png", Texture.class);
        manager.load("Proyectiles/pocimaNivel2.png", Texture.class);

        //Efectos
        manager.load("Efectos/salto.mp3", Sound.class);
        manager.load("Efectos/Flecha.mp3", Sound.class);
        manager.load("Efectos/bolaDeFuego.mp3", Sound.class);
        manager.load("Efectos/pocima.mp3", Sound.class);

        //Enemigos
        manager.load("Enemigos/Dragon1.PNG", Texture.class);
        manager.load("Enemigos/llamaradas.png", Texture.class);

        //Botones
        manager.load("botones/BtnPausa2.png", Texture.class);
        manager.load("botones/BotonIzquierda.png", Texture.class);
        manager.load("botones/BotonDerecha.png", Texture.class);
        manager.load("botones/BotonSaltar.png", Texture.class);
        manager.load("botones/BotonDisparar.png", Texture.class);
        manager.load("botones/BtnPausa2.png", Texture.class);
        manager.load("botones/BotonIzquierdaInv.png", Texture.class);
        manager.load("botones/BotonDerechaInv.png", Texture.class);
        manager.load("botones/BotonSaltarInv.png", Texture.class);
        manager.load("botones/BotonDispararInv.png", Texture.class);
        manager.load("botones/BtnReanudarN2.png", Texture.class);
        manager.load("botones/BtnMenuN2.png", Texture.class);
        manager.load("botones/BtnMusicN2.png", Texture.class);
        manager.load("botones/BtnSonidoN2.png", Texture.class);
        manager.load("botones/BtnReanudarN2Inv.png", Texture.class);
        manager.load("botones/BtnMenuN2Inv.png", Texture.class);
        manager.load("botones/BtnMusicN2Inv.png", Texture.class);
        manager.load("botones/BtnSonidoN2Inv.png", Texture.class);
        manager.load("botones/avanzar.png", Texture.class);

        //Historieta
        manager.load("Historieta/VNLvl2_1.PNG", Texture.class);
        manager.load("Historieta/VNLvl2_2.PNG", Texture.class);
        manager.load("Historieta/VNLvl2_3.PNG", Texture.class);
        manager.load("Historieta/VNLvl2_4.PNG", Texture.class);
        manager.load("Historieta/perdistelvl1.PNG", Texture.class);
    }

    private void cargarRecursosNivel1() {
        //Fondos
        manager.load("fondos/fondonivel1.JPG", Texture.class);
        manager.load("fondos/PausaN1.jpeg", Texture.class);

        //Sprites
        manager.load("sprites/pilaP.png", Texture.class);
        manager.load("sprites/pilaP.png", Texture.class);
        manager.load("sprites/personaje.png", Texture.class);

        //Proyectiles
        manager.load("Proyectiles/piedra.png", Texture.class);
        manager.load("Proyectiles/pocima.png", Texture.class);
        manager.load("Proyectiles/flecha1.png", Texture.class);

        //Efectos
        manager.load("Efectos/salto.mp3", Sound.class);
        manager.load("Efectos/Flecha.mp3", Sound.class);
        manager.load("Efectos/bolaDeFuego.mp3", Sound.class);
        manager.load("Efectos/pocima.mp3", Sound.class);

        //Enemigos
        manager.load("Enemigos/Titan1.PNG", Texture.class);
        manager.load("Enemigos/BolaDeFuego.png", Texture.class);

        //Texto
        //manager.load("Texto/game.fnt", Texture.class);

        //Botones
        manager.load("botones/BtnPausa.png", Texture.class);
        manager.load("botones/BotonIzquierda.png", Texture.class);
        manager.load("botones/BotonDerecha.png", Texture.class);
        manager.load("botones/BotonSaltar.png", Texture.class);
        manager.load("botones/BotonDisparar.png", Texture.class);
        manager.load("botones/BtnPausa1.png", Texture.class);
        manager.load("botones/BotonIzquierdaInv.png", Texture.class);
        manager.load("botones/BotonDerechaInv.png", Texture.class);
        manager.load("botones/BotonSaltarInv.png", Texture.class);
        manager.load("botones/BotonDispararInv.png", Texture.class);
        manager.load("botones/BtnReanudarN1.png", Texture.class);
        manager.load("botones/BtnMenuN1.png", Texture.class);
        manager.load("botones/BtnMusicN1.png", Texture.class);
        manager.load("botones/BtnSonidoN1.png", Texture.class);
        manager.load("botones/BtnReanudarN1Inv.png", Texture.class);
        manager.load("botones/BtnMenuN1Inv.png", Texture.class);
        manager.load("botones/BtnMusicN1Inv.png", Texture.class);
        manager.load("botones/BtnSonidoN1Inv.png", Texture.class);
        manager.load("botones/avanzar.png", Texture.class);
        manager.load("botones/omitir.png", Texture.class);
        manager.load("botones/PlayAgain.png", Texture.class);

        //Historieta
        manager.load("Historieta/VNLvl1_1.PNG", Texture.class);
        manager.load("Historieta/VNLvl1_2.PNG", Texture.class);
        manager.load("Historieta/VNLvl1_3.PNG", Texture.class);
        manager.load("Historieta/VNLvl1_4.PNG", Texture.class);
        manager.load("Historieta/perdistelvl1.PNG", Texture.class);
    }

    private void cargarRecursosAcercaDeManuel() {
        //Fondos
        manager.load("fondos/fondoacercadeM.png", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
    }

    private void cargarRecursosAcercaDeKarla() {
        //Fondos
        manager.load("fondos/fondoacercadeK.png", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
    }

    private void cargarRecursosAcercaDeItzel() {
        //Fondos
        manager.load("fondos/fondoacercadeI.png", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
    }

    private void cargarRecursosAcercaDeAbraham() {
        //Fondos
        manager.load("fondos/fondoacercadeA.png", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
    }

    private void cargarRecursosAcercaDe() {
        //Fondos
        manager.load("fondos/fondoacercade.png", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/AD_Karla1.png", Texture.class);
        manager.load("botones/AD_Manuel1.png", Texture.class);
        manager.load("botones/AD_Itzel1.png", Texture.class);
        manager.load("botones/AD_Abraham1.png", Texture.class);
        manager.load("botones/BtnHistory.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
        manager.load("botones/AD_Karla.png", Texture.class);
        manager.load("botones/AD_Manuel.png", Texture.class);
        manager.load("botones/AD_Itzel.png", Texture.class);
        manager.load("botones/AD_Abraham.png", Texture.class);
        manager.load("botones/BtnHistory1.png", Texture.class);

    }

    private void cargarRecursosConfiguracion() {
        //Fondos
        manager.load("fondos/fondoconfiguracion.png", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
        manager.load("botones/btnconfiguracionMusicaInv.png", Texture.class);
        manager.load("botones/btnconfiguracionMusica.png", Texture.class);
        manager.load("botones/btnconfiguracionSonidoInv.png", Texture.class);
        manager.load("botones/btnconfiguracionSonido.png", Texture.class);
    }

    private void cargarRecursosReanudar() {
        //Fondos
        manager.load("fondos/PantallaReanudar.png", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
    }

    private void cargarRecursosHistoria() {
        //Fondos
        manager.load("fondos/Historia.jpeg", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
    }

    private void cargarRecursosAyuda() {
        //Fondos
        manager.load("fondos/fondoPantallaAyuda.png", Texture.class);

        //Botones
        manager.load("botones/btnBack1.png", Texture.class);
        manager.load("botones/btnBack.png", Texture.class);
    }

    private void cargarRecursosMenu() {
        manager.load("fondos/fondomenu.jpeg", Texture.class);
        manager.load("botones/Logo.png", Texture.class);
        manager.load("botones/botonNP.png", Texture.class);
        manager.load("botones/botonR.png", Texture.class);
        manager.load("botones/botonAD.png", Texture.class);
        manager.load("botones/botonAyuda.png", Texture.class);
        manager.load("botones/botonC.png", Texture.class);
        manager.load("botones/botonNPInv.png", Texture.class);
        manager.load("botones/botonRInv.png", Texture.class);
        manager.load("botones/botonADInv.png", Texture.class);
        manager.load("botones/botonAyudaInv.png", Texture.class);
        manager.load("botones/botonCInv.png", Texture.class);
    }

    @Override
    public void render(float delta) {
        borrarPantalla(0.5f, 0.2f, 0.5f);

        batch.setProjectionMatrix(camara.combined);

        batch.begin();
        this.batch.draw(this.texturaFondo, 0.0F, 0.0F);
        spriteCargando.draw(batch);
        texto.mostrarMensaje(batch, avance + "%", ANCHO/2, ALTO/2);
        batch.end();

        //Actualizar
        timerAnimacion -= delta;
        if (timerAnimacion <= 0) {
            timerAnimacion = TIEMPO_ENTRE_FRAMES;
            spriteCargando.rotate(20);
        }

        //Actualizar carga
        actualizarCarga();
    }

    private void actualizarCarga() {
        if (manager.update()) {
            switch (siguientePantalla) {
                case MENU:
                    juego.setScreen(new PantallaMenu(juego));
                    break;
                case AYUDA:
                    juego.setScreen(new PantallaAyuda(juego));
                    break;
                case HISTORIA:
                    juego.setScreen(new PantallaH(juego));
                    break;
                case REANUDAR:
                    juego.setScreen(new PantallaReanudar(juego));
                    break;
                case CONFIGURACION:
                    juego.setScreen(new PantallaConfiguracion(juego));
                    break;
                case ACERCA_DE:
                    juego.setScreen(new PantallaAcercaDe(juego));
                    break;
                case ACERCA_DE_ABRAHAM:
                    juego.setScreen(new PantallaAcercaDeAbraham(juego));
                    break;
                case ACERCA_DE_ITZEL:
                    juego.setScreen(new PantallaAcercaDeItzel(juego));
                    break;
                case ACERCA_DE_KARLA:
                    juego.setScreen(new PantallaAcercaDeKarla(juego));
                    break;
                case ACERDA_DE_MANUEL:
                    juego.setScreen(new PantallaAcercaDeManuel(juego));
                    break;
                case NIVEL1:
                    juego.setScreen(new PantallaLucha1(juego));
                    break;
                case NIVEL2:
                    juego.setScreen(new PantallaLucha2(juego));
                    break;
                case NIVEL3:
                    juego.setScreen(new PantallaLucha3(juego));
                    break;
            }
        }
        avance = (int)(manager.getProgress()*100);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        texturaCargando.dispose();
    }
}
