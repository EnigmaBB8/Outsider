package mx.itesm.enigma.outsider;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Juego extends Game {

	public Music musicaFondo;
	public Music musicaFondo1;

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
		manager.finishLoading();
		musicaFondo = manager.get("Musica/musicaMenu.mp3");
		musicaFondo.setLooping(true);
		musicaFondo.play();
	}
	public void reproducirMusicaNivel1 (){
		AssetManager manager = new AssetManager();
		manager.load("Musica/musicaNivel1.mp3", Music.class);
		manager.finishLoading();
		musicaFondo1 = manager.get("Musica/musicaNivel1.mp3");
		musicaFondo1.setLooping(true);
		musicaFondo1.play();
	}

	public void detenerMusica (){
		musicaFondo.pause();
	}

	public void detenerMusicaAll (){
		musicaFondo.stop();
		musicaFondo1.stop();
	}

}
