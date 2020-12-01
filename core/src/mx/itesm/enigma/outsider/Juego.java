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
	protected Music musicaNivel3;
	protected Music musicaNivel4;

	@Override
	public void create() {
		manager = new AssetManager();
		manager.load("Musica/musicaMenu.mp3", Music.class);
		manager.finishLoading();
		musicaFondo = manager.get("Musica/musicaMenu.mp3");
		musicaFondo.setLooping(true);

		AssetManager managerNivel1 = new AssetManager();
		managerNivel1.load("Musica/musicaNivel1.mp3", Music.class);
		managerNivel1.finishLoading();
		musicaNivel1 = managerNivel1.get("Musica/musicaNivel1.mp3");
		musicaNivel1.setLooping(true);

		AssetManager managerNivel2 = new AssetManager();
		managerNivel2.load("Musica/musicaNivel2.mp3", Music.class);
		managerNivel2.finishLoading();
		musicaNivel2 = managerNivel2.get("Musica/musicaNivel2.mp3");
		musicaNivel2.setLooping(true);

		AssetManager managerNivel3 = new AssetManager();
		managerNivel3.load("Musica/musicaNivel3.mp3", Music.class);
		managerNivel3.finishLoading();
		musicaNivel3 = managerNivel3.get("Musica/musicaNivel3.mp3");
		musicaNivel3.setLooping(true);

		AssetManager managerNivel4 = new AssetManager();
		managerNivel4.load("Musica/musicaNivel4.mp3", Music.class);
		managerNivel4.finishLoading();
		musicaNivel4 = managerNivel4.get("Musica/musicaNivel4.mp3");
		musicaNivel4.setLooping(true);

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

	public void reproducirMusicaNivel3 (){ musicaNivel3.play(); }

	public void reproducirMusicaNivel4 (){ musicaNivel4.play(); }

	public void detenerMusica () { musicaFondo.pause(); }

	public void detenerMusicaN1 () { musicaNivel1.pause(); }

	public void detenerMusicaN2 () { musicaNivel2.pause(); }

	public void detenerMusicaN3 () { musicaNivel3.pause(); }

	public void detenerMusicaN4 () { musicaNivel4.pause(); }
}