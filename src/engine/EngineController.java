package engine;

import player.PlayerManager;

public interface EngineController {
	public void start();
	public void addListener(Runnable listener);
	public void addWorld(World w);
	public void setCurrentWorld(String s);
	public void setPlayerManager(PlayerManager currentPlayerManager);
}
