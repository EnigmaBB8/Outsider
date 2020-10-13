package mx.itesm.enigma.outsider;
//owo
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {

	private Music musicaFondo;

	@Override
	public void create() {
		setScreen(new PantallaMenu(this));
	}
	@Override
	public void render() {
		super.render();
	}

	public void reproducirMusica (){
		AssetManager manager = new AssetManager();
		manager.load("Musica/musicaMenu.mp3", Music.class);
		manager.finishLoading();    // ESPERA
		musicaFondo = manager.get("Musica/musicaMenu.mp3");
		musicaFondo.setVolume(0.1f);
		musicaFondo.setLooping(true);
		musicaFondo.play();
	}

	public void detenerMusica (){
		musicaFondo.pause();
	}

}
