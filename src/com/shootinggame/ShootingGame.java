package com.shootinggame;

import java.util.*;

/**
 * Class with main() where we take number of cowboys and starts our game.  
 */
public class ShootingGame {

	public static void main(String[] args) {
        try{
            System.out.print( "Please enter players count(value): " );            
            int shootersCount = 0;
            try(Scanner consoleInput = new Scanner( System.in )){
            	shootersCount = consoleInput.nextInt();
            }
            
            Game shootingGame = new Game(shootersCount);
            shootingGame.gameProcess();
            
            System.out.print("Game over!");
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
	}
}
