package application;

import application.sprites.entities.Player;

public interface Interactable{
	public int getPriorityValue();
	public void interact(Player p);
}
