#Use Cases

###Game Player
 * User sees a main menu with buttons to start the game, see controls/ rules for the game, can click to begin the game. 
 * visualize and view all games and be able to choose one or multiple to play in a home menu
 * achievements click on a game in the home and be able to view high scores/potential other achievements 
 * Heads Up Display- displays the user stats during the game 
 * Be able to save the preferences of the game 
 * Be able to set preferences/setting for a game mid game 
 * Mid game menu to set settings, save a  * Your character passes a checkpoint/you click the save button and all of the games data/preferences are saved to a data file to be loaded later
 * A player presses “restart” in the middle of the game and the game resets back to its initial starting state and quit 
 * Save state of the game, save by time, and save button in menu
 * Be able to run multiple games at the same time simultaneously or with one game paused in the background 
 * Home button that allows the user to return to the choose a game screen mid game 
 *  UI components to Customize character 
 * Press play the game and game player is initialized 
 * be able to save and load game settings after quitting player
 * Player presses a key and we send it to game engine

###Game Authoring Environment

 *  Menu Customization 
 * User can click on terrain options and graphically place them onto a map, creating the terrain there.
 * User can create a new sprite that performs a designated task 
 * User can click and drag created sprites and objects onto the field to position the object.
 * User can associate certain abilities with a sprite. 
 * User can drag and drop a base onto the screen.
 * User can decide an image/shape that will be used for the display.
 * User setting up the scenery of the game, ie setting the background of the display and adding objects to the background that are immovable.
 * User can select to create a new game or open an existing game project from the File menu
 * User can save the current scene as a data file.
 * User can view all of the current objects in a section of the GUI and select a certain object to highlight it on the screen.
 * User can move through tabs to edit different levels.
 * User can design the game splash screen used to start the game.

###Game Engine

 * Zones will affect sprites and other collidable objects in certain ways (ie heal zone, damaging zone, base zone).
 * Crowds of AI sprites can be created and given simple movement routines
 * Non-player character will have ability to have random movement, or have certain movement paths attached to it.
 * Movement at Map edge is toroidal and to prevent movement through edges, walls will be defined at borders.
 * Moving a sprite with keyboard input or mouse input (as decided on a per - game basis) will visually display the moving sprite. 
 * Sprites will be differentiated by attributes, so that multiple kinds of characters/turrets/players can be implemented
 * Adding new type of ability that can be given to character will add ability as type of Sprite that contains certain attributes and maps to an input. The ability can be added by Authoring Environment to a character object.
 * Player destroys/interacts with a base object and sees graphically that they won the game. 
 * Player collides with a carryable object and picks it up, gaining more abilities/attributes 
 * Collision interacts differently between non-carriables (ie ability damages character, scenery unaffected by collision, characters can’t move into walls).
 * Levels can be given win/lose conditions and linked together in sequence
