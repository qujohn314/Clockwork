package application.entities;

import java.io.Serializable;

public class Player extends Entity implements Serializable{

	private static final long serialVersionUID = -7625029116892596346L;
	
	public int level;
	public int health;
	public int maxHealth;
	
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	
	
}
