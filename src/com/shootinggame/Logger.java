package com.shootinggame;

/**
 * Class for logging to json file our operations during the game.
 * 
 * json file structure:
 * [struct for winner data]
 * [array of structs with shots]  
 */

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.*;
import java.nio.file.StandardOpenOption;
import java.math.BigInteger;
import java.util.*;
import org.json.simple.*;

public class Logger {
	
	/**
	 * Constant strings whose are names for json file records.
	 */
	private static String SHOOTING_LOG_STR = "shoots_log";	
	private static String SHOOTER_ID_STR = "shooter_id";
	private static String TARGET_ID_STR = "target_id";
	private static String SHOOT_POWER_STR = "shoot_power";
	private static String HITS_LEFT_STR = "health_left";
	private static String WINNER_STAT_STR = "winner_stat";
	
	private static String LOG_FILE_EXT = "json";
	private static String HASH_FILE_EXT = "md5";
	
    private static JSONObject jsonLog;
    private static JSONArray jsonArr;

    /**
     * Start new logging session
     */
    public void startSession() {
        jsonLog = new JSONObject();
        jsonArr = new JSONArray();
    }

    /**
     * End logging session and write all collected data to log file.
     */
    @SuppressWarnings("unchecked")
    public void closeSession(){
        jsonLog.put(SHOOTING_LOG_STR, jsonArr);
        saveLogToFile();
    }

    /**
     * Put to json file pair name->value.
     * @param name - name as string.
     * @param value - value as integer.
     */
    @SuppressWarnings("unchecked")
    public void putKeyValue(String name, Integer value){
        jsonLog.put(name, value);
    }

    /**
     * Put new record to array of shoots.
     * @param active - reference to active cowboy.
     * @param target - reference to target cowboy.
     * @param shootingPower - power of active cowboy shot.
     */
    @SuppressWarnings("unchecked")
    public void putShootStat(CowboyUnit active, CowboyUnit target, int shootingPower){
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put(SHOOTER_ID_STR, active.getId());
        m.put(TARGET_ID_STR, target.getId());
        m.put(SHOOT_POWER_STR, shootingPower);
        m.put(HITS_LEFT_STR, target.getHp());
        jsonArr.add(m);
    }
    
    /**
     * Put winner record to log file.
     * @param unit - reference to winner unit.
     */
    @SuppressWarnings("unchecked")
    public void putWinnerStat(CowboyUnit unit) {
        Map<String, Integer> m = new LinkedHashMap<>();
        m.put(SHOOTER_ID_STR, unit.getId());
        m.put(HITS_LEFT_STR, unit.getHp());
        jsonLog.put(WINNER_STAT_STR, m);
    }

    /**
     * Generate filename in format currentdate_currenttime.ext     
     * @param fileExt - extension for generated file name.
     * @return - filename.
     */
    public String generateFilename(String fileExt) {    	
        return getCurrectTimeAsString() + "." + fileExt;
    }

    /**
     * Generate string with current date and time.
     * It also may be needed for time-stamps in log file.
     * @return - current data and time as string.
     */
    public String getCurrectTimeAsString(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-ms");
        return formatter.format(new Date());
    }

    /**
     * Save all collected records to json file and generate file with hash from data in log file.
     */
    public void saveLogToFile(){
        try {
            String jsonStr = jsonLog.toJSONString();
            
            Files.write(Paths.get(generateFilename(LOG_FILE_EXT)), 
            		jsonStr.getBytes(StandardCharsets.UTF_8), 
            		StandardOpenOption.CREATE_NEW);
            
            Files.write(Paths.get(generateFilename(HASH_FILE_EXT)), 
            		calculateMD5FromString(jsonStr).getBytes(StandardCharsets.UTF_8), 
            		StandardOpenOption.CREATE_NEW);
        }
        catch (Exception ex) {
            System.out.println("Error writing log or hash file.");
        }
    }

    /**
     * Calculate MD5 hash from data.
     * @param data - data to hash.
     * @return - string in hex format of hashed data.
     */
    public String calculateMD5FromString(String data){
        String result = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes());
            result = new BigInteger(1, md5.digest()).toString(16).toUpperCase();
        }
        catch (Exception ex){        	
        	System.out.println("Error calculating hash code for log file.");
        }
        return result;
    }
}
