package gameplayer.profile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameplayer.gamescores.HighScoreLists;
import javafx.scene.image.ImageView;
public class Profile {
    
    private String myName;
    private String myImagePath;
    private transient ImageView profilePicture;
    private Map<String,HighScoreLists> myScoreListMap;
    private String myUserID;


    public Profile(String userID) {
        myName = "";
        myUserID = userID;
        myScoreListMap = new HashMap<String,HighScoreLists>();
        setMyImagePath("resources/images/duketurtlesmall.png");

    }
    
    public String getMyName() {
        return myName;
    }
    
    public void setMyName(String myName) {
        this.myName = myName;
    }
    
    public void addHighScore (String name, HighScoreLists highScore){
    	System.out.println("shit");
        myScoreListMap.put("Default", highScore);
    }
    
    public Map<String,HighScoreLists> getMyHighScores() {
        return myScoreListMap;
    }
    
//    public void setMyHighScores(List<HighScoreLists> myHighScores) {
//        this.myHighScoreLists = myHighScores;
//    }

	public String getMyImagePath() {
		return myImagePath;
	}

	public void setMyImagePath(String myImagePath) {
		this.myImagePath = myImagePath;
	}





}