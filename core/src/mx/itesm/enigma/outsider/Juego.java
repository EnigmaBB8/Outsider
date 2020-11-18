package mx.itesm.enigma.outsider;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {

	private AssetManager manager;
	protected Music musicaFondo;
	protected Music musicaNivel1;
	protected Music musicaNivel2;

	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("Musica/musicaMenu.mp3", Music.class);
		manager.finishLoading();
		musicaFondo = manager.get("Musica/musicaMenu.mp3");
		musicaFondo.setVolume(0.1f);
		musicaFondo.setLooping(true);

		AssetManager managerNivel1 = new AssetManager();
		managerNivel1.load("Musica/musicaNivel1.mp3", Music.class);
		managerNivel1.finishLoading();
		musicaNivel1 = managerNivel1.get("Musica/musicaNivel1.mp3");
		musicaNivel1.setLooping(true);
		musicaNivel1.setVolume(0.1f);

		AssetManager managerNivel2 = new AssetManager();
		managerNivel2.load("Musica/musicaNivel2.mp3", Music.class);
		managerNivel2.finishLoading();
		musicaNivel2 = managerNivel2.get("Musica/musicaNivel2.mp3");
		musicaNivel2.setLooping(true);
		musicaNivel2.setVolume(0.1f);

		//setScreen(new PantallaMenu(this));
		setScreen(new PantallaCargando(this, Pantallas.MENU));
	}

	public AssetManager getManager() {
		return manager;
	}

	@Override
	public void render() {
		super.render();
	}

	public void reproducirMusica (){
		musicaFondo.play();
	}

	public void reproducirMusicaNivel1 (){ musicaNivel1.play(); }

	public void reproducirMusicaNivel2 (){ musicaNivel2.play(); }

	public void detenerMusica () { musicaFondo.pause(); }

	public void detenerMusicaN1 () { musicaNivel1.pause(); }

	public void detenerMusicaN2 () { musicaNivel2.pause(); }
}