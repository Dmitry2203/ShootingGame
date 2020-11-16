package com.shootinggame;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that describes shooting game simulation.
 */
public class Game {
	
    private static final int MAX_GUN_POWER = 5;	
    private static final Logger logger = new Logger();
    private final DoubleLinkedRingBuffer<CowboyUnit> cowboyUnits = new DoubleLinkedRingBuffer<>();

    /**
     * Class constructor that receives count of cowboys in the ring.
     * @param n - count of cowboys.
     * @throws Exception - 
     */
    public Game(int n) throws Exception{
        initializeShooters(n);
    }
    
    /**
     * Initialize cowboys units in the list and select random unit as active.
     * @param n - count of cowboys in the ring.
     * @throws Exception
     */
    public void initializeShooters(int n) throws Exception{
        if(n >= 2){
            for(int i = 0;i < n;i++){
                cowboyUnits.add(new CowboyUnit(i));
            }
            cowboyUnits.setCurrentByValue(ThreadLocalRandom.current().nextInt() % n);
        }
        else{
            throw new Exception("Count of shooters must be more that two.");
        }
    }

    /**
     * Generate shooting power for active cowboy.
     * @return - random  [1..MAX_GUN_POWER]
     */
    public int takeShootingPower(){
        return ThreadLocalRandom.current().nextInt(1, MAX_GUN_POWER + 1);
    }

    /**
     * Function for simulating game process. Also here we write out log file.
     */
    public void gameProcess() {
        logger.startSession();
        do{
            Node<CowboyUnit> target =
                    (cowboyUnits.getCurrentNode().getItem().getHp() % 2 == 0)
                    ? cowboyUnits.getNextNode()
                    : cowboyUnits.getPrevNode();

            int shootingPower = takeShootingPower();
            target.getItem().decHp(shootingPower);

            logger.putShootStat(cowboyUnits.getCurrentNode().getItem(), target.getItem(), shootingPower);

            if(target.getItem().getHp() <= 0){
                cowboyUnits.remove(target);
            }
            else{
                cowboyUnits.setCurrentByNode(target);
            }
        }while(cowboyUnits.size() > 1);

        logger.putWinnerStat(cowboyUnits.getCurrentNode().getItem());
        logger.closeSession();
    }
}
