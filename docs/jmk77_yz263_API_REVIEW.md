# Part 1

1. Flexibility in being able to attach any game engine (provided the same attributes and abilities as provided in the Game Authoring
Environment) and supporting a number of games to be played on the Player.

2. The only public methods into the Game Player itself are _init()_ and _step()_. The rest of the classes that are involved in the player have very few public methods, if not just one in most of the classes. 

3. Player is instantiated through the main application class. The Player receives information from Game Authoring Environment through data files (XML, CSS...). Player is connected to the Game Engine by having a controller that instantiates an instance of Engine which it will hold until the program ends.

4. FileNotFoundException, in the case the user asks for a game that doesn't exist
	- [handle this by popping up an alert button, cutting off the processes of trying to find the missing data files.]
Possible NullPointerExceptions if some attribute nodes in the files are not found. 
  
5. For the most part, there is very minimal crossover between frontend (Player) and backend (Engine).


# Part 2

1. Excited to work on getting the actual game play on the screen (!!!). Hopefully the transferring of data from the Engine will work well with the Player UI.

2. A bit worried about organizing the engine/authoring environment data well, as well as the data that Player has to write. 

3. Plan to implement heads up display

4. yes, they are descriptive and appropriate, maybe a bit basic but they work for our coding purposes.

5. Not yet, but possible use cases would be: wrong file/game name or the game does not exist,   
