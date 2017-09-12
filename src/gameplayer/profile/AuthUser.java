package gameplayer.profile;

import java.awt.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.restfb.*;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

/**
 * @author joykim
 *
 */
public class AuthUser {
	private static final Dimension MAIN_MENU_SIZE = new Dimension(500, 302);
	private VBox myBox;
	private Pane myStack;
	private Button loginButton = new Button("enter");
	private ProfileMaster myProfileMaster;
	private Profile myCurrentProfile;
	private BooleanProperty start = new SimpleBooleanProperty();
	private User user;

	public BooleanProperty getStart() {
		return start;
	}

	public AuthUser(ProfileMaster profilemaster) {
		setUpScene();
		myProfileMaster = profilemaster;
		
	}

	private void setUpScene() {
		myStack = new StackPane();
		myStack.setId("pane");
		myStack.setPrefSize(500, 500);
		myBox = new VBox(10);
		myBox.setPadding(new Insets(100));
		Label loginLabel = new Label("Login Locally");
		TextField usernameField = new TextField();
		usernameField.setMaxWidth(myStack.getPrefWidth() - myBox.getPadding().getLeft() * 2);

		usernameField.setPromptText("Enter Username");

		loginButton.setOnAction(f -> {
			if (usernameField.getText().isEmpty() || usernameField.getText().trim().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Wait");
				alert.setContentText("Please fill in a username");
				alert.showAndWait();
			} else {
				authenticate(usernameField.getText());
				start.set(true);
			}
		});

		Label fbLabel = new Label("Login with Facebook");
		ImageView fbIcon = new ImageView(
				(new Image(this.getClass().getClassLoader().getResourceAsStream("resources/fb_icon.png"))));
		fbIcon.setFitHeight(20);
		fbIcon.setFitWidth(20);
		Button fbButton = new Button("", fbIcon); 
		fbButton.setOnAction(l -> {
			user = getFBUser();
			authenticate(user.getId());
			start.set(true);
		});
		HBox signupButtons = new HBox();
		signupButtons.getChildren().addAll(fbButton);

		myBox.getChildren().addAll(loginLabel, usernameField, loginButton, fbLabel, signupButtons);
		myStack.getChildren().add(myBox);
	}

	private boolean authenticate(String username) {
		
		// create profile or get profile from map, then pass it.
		//if user is not null (meaning facebook)
		this.myCurrentProfile = null;
		try {
			String imagePath = user.getPicture().getUrl();
			this.myCurrentProfile = myProfileMaster.getProfile(username);
			myCurrentProfile.setMyImagePath(imagePath);
			myCurrentProfile.setMyName(user.getName());
			return true;
		} catch (NullPointerException n) {
			this.myCurrentProfile = myProfileMaster.getProfile(Integer.toString(username.hashCode()));
			myCurrentProfile.setMyName(username);
			return false;
		}
	}

	public Profile getProfile() {
		return this.myCurrentProfile;
	}

	private User getFBUser() {
		String beforeID = "https://graph.facebook.com/oauth/authorize?type=user_agent&client_id=";
		String appID = "1832611867007096";
		String redirect = "&redirect_uri=";
		String domain = "http://google.com";
		String scope = "&scope=";
		String wantedParam = "public_profile,user_birthday,user_friends,user_games_activity,publish_actions,email";
		String authUrl = beforeID + appID + redirect + domain + scope + wantedParam;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(authUrl);
		String accessToken = "";
		while (true) {
			if (!driver.getCurrentUrl().contains("facebook.com")) {
				String url = driver.getCurrentUrl();
				accessToken = url.replaceAll(".*#access_token=(.+)&.*", "$1");
				System.out.println(accessToken);

				driver.quit();
				break;
			}
		}
		user = null;

		if (!accessToken.isEmpty()) {
			FacebookClient fbClient = new DefaultFacebookClient(accessToken, com.restfb.Version.LATEST);
			user = fbClient.fetchObject("me", User.class, Parameter.with("fields", "id,name,picture"));
		}
//		userName = user.getName();
		return user;
	}
	
	public void saveProfileMaster() {
		//myProfileMaster.saveProfileMaster();
	}

	public Image getProfilePicture() {
		return new Image(user.getPicture().getUrl());
	}

//	public String getName() {
//		return userName;
//	}

	public Node getNode() {
		myStack.setPrefSize(Screen.getPrimary().getBounds().getWidth(), Screen.getPrimary().getBounds().getHeight());
		return myStack;
	}

}
