package gamedata.data;
import gameengine.GameWorld;

public class LevelData {
    private String music;
    private String gameName;
    private String levelName;
    private String background;
    private transient GameWorld world;


    public LevelData(String gameName, String levelName){
        this.gameName = gameName;
        this.levelName = levelName;
        world = new GameWorld();
    }    

    public GameWorld getGameWorld() {
        return world;
    }

    public void setGameWorld(GameWorld world) {
        this.world = world;
    }

    public void setBackground (String background){
        this.background = background;
    }

    public String getBackground () {
        return this.background;
    }

    public void setLevelName (String name) {
        this.levelName = name; 
    }

    public String getLevelName(){
        return this.levelName;
    }

    public void setGameName(String gameName){
        this.gameName = gameName;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

}
