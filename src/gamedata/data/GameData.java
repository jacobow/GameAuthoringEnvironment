package gamedata.data;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import configuration.MenuLanguage;
import gamedata.exceptions.FileIOError;
import gamedata.exceptions.LoadWorldException;
import gamedata.fileIO.ChooseFileName;
import gamedata.fileIO.XMLFileIO;
import gameengine.GameWorld;

public class GameData {

    private Map<String, LevelData> levels;
    private final String packageName;
    private String currentLevelName;
    private String gameName;
    private String previousUserName;
    private String userName;
    private String icon;


    public GameData(String name) {
        this.levels = new LinkedHashMap<String, LevelData>();
        this.packageName = name;
        this.gameName = name;
        this.userName = "Default";
        this.previousUserName = "Default";
        levels.clear();
    }


    public void setCurrentLevel(String name){
        currentLevelName = name;
    }


    private LevelData getLevelData (String name){
        setCurrentLevel(name);
        XMLFileIO fileIO = new XMLFileIO();
        GameWorld world;
        try {
            world = fileIO.loadWorld(this, currentLevelName);
            LevelData myLevel = this.levels.get(currentLevelName);
            myLevel.setGameWorld(world);
            System.out.println("Game World ID is" + world);
            return this.levels.get(currentLevelName);
        }
        catch (LoadWorldException e) {
            FileIOError loadLevelError = new FileIOError(MenuLanguage.getInstance().getValue("ErrorLoadGame"));
            loadLevelError.displayMessage();
            return null;
        }
    }
    
    public void resetCurrentGame(){
        for (String levelName : this.levels.keySet()){
            currentLevelName = levelName;
            resetCurrentLevel();
        }
        currentLevelName = this.levels.keySet().iterator().next();
    }

    public void resetCurrentLevel(){
        getLevelData(currentLevelName);
    }


    public LevelData getCurrentLevel(){
        return this.levels.get(currentLevelName);
    }

    public Collection<LevelData> getLevels(){
        return this.levels.values();
    }

    /**
     * REFACTOR THIS METHOD!!!!! CHECK FOR END OF GAME
     */
    public LevelData getNextLevel(){
        Iterator<String> levelIterator = this.levels.keySet().iterator();
        while (levelIterator.hasNext()){
            String myLevel = levelIterator.next();
            if (myLevel.equals(currentLevelName)){
                if (levelIterator.hasNext()){
                    currentLevelName = levelIterator.next();
                    return getLevelData(currentLevelName);
                }
                else{
                    return null;
                }
            }
        }
        return null;
    }


    public GameWorld getCurrentWorld(){
        checkCurrentLevel();
        LevelData myLevel = this.levels.get(currentLevelName);
        if (myLevel.getGameWorld() == null){
            getLevelData(currentLevelName);
        }
        return myLevel.getGameWorld();
        //        if(!isInit){
        //            XMLFileIO fileIO = new XMLFileIO();
        //            System.out.println("Current Level Name is " + currentLevelName);
        //            isInit = true;
        //            currentWorld =fileIO.loadWorld(this, currentLevelName);
        //            return currentWorld;
        //        }
        //        else{
        //        	System.out.println("GameData second time");
        //        	return currentWorld;
        //        }
    }

    private void checkCurrentLevel(){
        if (currentLevelName == null){
            for (String key : levels.keySet()){
                currentLevelName = key;
                break;
            }
        }   
    }

    public void saveGameAuthoring () {
        XMLFileIO fileIO = new XMLFileIO();
        fileIO.storeGame(this);
    }

    public void saveGamePlayer () {
        fillLevelData();
        ChooseFileName fileName = new ChooseFileName(MenuLanguage.getInstance().getValue("TextInputDialog"),
                                                     MenuLanguage.getInstance().getValue("SelectSaveFile"),
                                                     MenuLanguage.getInstance().getValue("FileName"));
        this.setName(fileName.getFileName());
        saveGameAuthoring();
    }

    public void fillLevelData(){
        String tempCurrentLevel = new String(currentLevelName);
        for (LevelData level: this.levels.values()){
            if (level.getGameWorld() == null){
                level = getLevelData(level.getLevelName());
            }
        }
        currentLevelName = tempCurrentLevel;
    }

    public LevelData getLevel (String name){
        return this.levels.get(name);
    }

    public LevelData addLevel (String name) {
        LevelData level = new LevelData(this.gameName, name);
        this.levels.put(name, level);
        checkCurrentLevel();
        return level;
    }

    public void removeLevel (String name) {
        this.levels.remove(name);
    }

    /**
     * Getters and Setters
     */
    public Map<String, LevelData> getLevelMap(){
        return this.levels;
    }

    public String getPackageName(){
        return this.packageName;
    }


    public String getName(){
        return this.gameName;
    }

    private void setName(String newName){
        this.gameName = newName;
        if (this.levels != null){
            for (LevelData levels : this.levels.values()){
                levels.setGameName(newName);
            }
        }
    }

    public String getIcon(){
        return this.icon;
    }

    public void setIcon (String icon){
        this.icon = icon;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getPreviousUser(){
        return this.previousUserName;
    }

    public void setPreviousUser(String packageName){
        this.previousUserName = packageName;
    }
}
