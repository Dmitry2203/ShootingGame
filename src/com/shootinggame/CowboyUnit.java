package com.shootinggame;

/**
 * Description of our game unit.
 * id - unit identifier. 
 * hp - current health points, max value is MAX_HP=10
 */

public class CowboyUnit {
	private static final int MAX_HP = 10;	
    private int id;
    private int hp;
    
    public CowboyUnit(int id) {
        this.id = id;
        this.hp = MAX_HP;
    }

    public int getId() {
		return id;
	}

	public int getHp() {
		return hp;
	}
	
	public void decHp(int hp) {
		this.hp -= hp; 
	}	
}
