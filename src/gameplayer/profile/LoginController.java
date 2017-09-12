package gameplayer.profile;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class LoginController {
	
	//Login button (within main (both authoring and player) or just player?)
	private Button myLoginButton;
	private Button myLogoutButton;
	
	//private create a Profile.java class with separate user.java objects
	//private create an Achievements.java class
	
	//need to think about high scores: need a private and public scoring for friends and blah blah. 
	
	public LoginController() {
		//myLoginButton
		//create the button that will take program to AuthUser.java
		//myLogoutButton
		//create the button that will save data and log out, reset profile blah blah 
	}

	
	//Authorizes user
		//facebook
		//or within app
	//keeps user information within a Profile Class
			//Profile class: keeps track of high scores and whatnot
		//multiple users?
		//keep past user information or decide to store that within facebook and get all that information there?
	//logs out of users
	//so, keeps track of current active user(s)
	
	
	//Achievements: 
		//ID
		//name of achieve
		//description
		//icon?
		//keeping a list order of achievements so that the unlocked ones appear on the list first
	
		//hidden state
		//revealed state
		//unlocked state
	
		//incremental achievements
		//points
	
	
	// String authUrl =
	// "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=" +
	// appId
	// + "&redirect_uri=" + domain + "&scope=public_profile,user_about_me,"
	// +
	// "user_actions.books,user_actions.fitness,user_actions.music,user_actions.news,user_actions.video,user_birthday,user_education_history,"
	// +
	// "user_events,user_photos,user_friends,user_games_activity,user_hometown,user_likes,user_location,user_photos,user_relationship_details,"
	// +
	// "user_relationships,user_religion_politics,user_status,user_tagged_places,user_videos,user_website,user_work_history,ads_management,ads_read,email,"
	// +
	// "manage_pages,publish_actions,read_insights,read_page_mailboxes,rsvp_event";
}
