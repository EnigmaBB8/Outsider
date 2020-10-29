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

	protected Music musicaFondo;
	protected Music musicaNivel1;

	@Override
	public void create() {

		Preferences preferencias = Gdx.app.getPreferences("Musica");
		preferencias.putBoolean("General",false);

		setScreen(new PantallaMenu(this));
		AssetManager manager = new AssetManager();
		manager.load("Musica/musicaMenu.mp3", Music.class);
		manager.finishLoading();
		musicaFondo = manager.get("Musica/musicaMenu.mp3");
		musicaFondo.setLooping(true);

		AssetManager manager1 = new AssetManager();
		manager1.load("Musica/musicaNivel1.mp3", Music.class);
		manager1.finishLoading();
		musicaNivel1 = manager1.get("Musica/musicaNivel1.mp3");
		musicaNivel1.setLooping(true);
	}
	@Override
	public void render() {
		super.render();
	}

	public void reproducirMusica (){
		musicaFondo.play();
	}

	public void reproducirMusicaNivel1 (){ musicaNivel1.play(); }

	public void detenerMusica () { musicaFondo.pause(); }

	public void detenerMusicaN1 () { musicaNivel1.pause(); }
}