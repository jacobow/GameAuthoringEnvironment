package gamedata.fileIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.thoughtworks.xstream.XStream;
import configuration.MenuLanguage;
import gamedata.data.GameData;
import gamedata.data.LevelData;
import gamedata.exceptions.FileIOError;
import gamedata.exceptions.LoadWorldException;
import gamedata.xmlconverters.XStreamConverter;
import gameengine.GameWorld;

/**
 * NEED TO ADD EXCEPTION HANDLING FOR IO STUFF, NOT JUST STACKTRACE PRINTS!!!!!
 * @author Brian
 *
 */
public class XMLFileIO {
    
    private XStream myXStream;
    
    public XMLFileIO(){
        XStreamConverter XMLAliasing = new XStreamConverter();
        myXStream = XMLAliasing.registerXStreamConverters();
//        NamedMapConverter mapconverter = new NamedMapConverter(LiveMap.class, myXStream.getMapper(), "key", "value",
//                                                               LiveMap.class.)
        //myXStream.ignoreUnknownElements();
    }

    /**
     * Stores the state of the engine's data (gameworld)
     * 
     * @param filePath - Where
     * @param world - What
     * @throws IOException - CATCH THIS PROPERLY PLEASE!!!
     */
    
    public void storeGame(GameData game){
        File file = makeDirectory(game.getPackageName(), game.getUserName(), game.getName());
        streamOutputGameData(game, file);
        for (LevelData levels : game.getLevels()){
            File levelFile = makeDirectory(game.getPackageName(), game.getUserName(), game.getName(), levels.getLevelName());
            streamOutputLevels(levels, levelFile);
        }
    }
    
    private void streamOutputGameData(GameData gameData, File gameFile){
        String game = myXStream.toXML(gameData);
        writeFile(gameFile, game, null);
    }

    private void streamOutputLevels(LevelData level, File levelFile){
        System.out.println("Level Game World is" + level.getGameWorld());
        level.getGameWorld().clearAllObserver();
        String world = myXStream.toXML(level.getGameWorld());
        System.out.println("streaming out" + level.getGameWorld());
        writeFile(levelFile, world, null);//new FileIOError("Problem Saving Worlds"));
    }
    
    public void writeFile(File file, String output, FileIOError error){
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            fw.write(output);
            fw.close();
        } catch (IOException e) {
            error.displayMessage();
        }
    }
    
    public StringBuilder bufferReader(String filePath) throws LoadWorldException{
        BufferedReader reader;
        try{
        	System.out.println("The Filepath is" + filePath);
            reader = new BufferedReader( new FileReader (filePath));
            
        }
        catch(Exception e){
            reader = null;
        }
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");
        
        try {
            while( ( line = reader.readLine() ) != null ) {
                stringBuilder.append( line );
                stringBuilder.append( ls );
            }
            reader.close();
        }
        catch (IOException e) {
            throw new LoadWorldException(MenuLanguage.getInstance().getValue("ErrorLoadWorld"));
        }
        return stringBuilder;
    }
    
    public GameData loadGame(String gameType, String userName, String gameName) throws LoadWorldException {
        String filePath = findXMLFile(gameType, userName, gameName);
        System.out.println("Loaded Game Path is" + filePath);
        StringBuilder gameBuilder;
        GameData world;
        try {
            gameBuilder = bufferReader(filePath);
            world=(GameData)myXStream.fromXML(gameBuilder.toString());
            return world;
        }
        catch (LoadWorldException e) {
            throw e;
        }
    }
    
    
    
    public GameWorld loadWorld(GameData data, String levelName) throws LoadWorldException{
        StringBuilder worldBuilder;
        GameWorld world;
        try {
            worldBuilder = bufferReader(findXMLFile(data.getPackageName(), data.getPreviousUser(),
                                                                          data.getName(), levelName));
            world=(GameWorld)myXStream.fromXML(worldBuilder.toString());
            data.getLevel(levelName).setGameWorld(world);
            System.out.println("how many times is load world called?" );
            return world;
        }
        catch (LoadWorldException e) {
            throw e; 
        }
    }
    
    private File makeDirectory(String ... values){
        String filePath = findFilePath(values);
        filePath += FilePath.XML.filepath();
        return makeParentDirectory(new File(filePath.toString()));
    }
    
    private File makeParentDirectory(File file){
        File parent_directory = file.getParentFile();
        if (parent_directory != null)
        {
            parent_directory.mkdirs();
        }
        return file;
    }
    
    private String findFilePath(String ... values){
        StringBuilder filePath = new StringBuilder(FilePath.GAMEDATA.filepath());
        for (String folderName : values){
            folderName = folderName.replaceAll("\\+", "");
            filePath.append(folderName);
            filePath.append(File.separator);   
        }
        filePath.setLength(filePath.length() - 1);
        return filePath.toString();
    }
    
    private String findXMLFile(String ... values){
        String filePath = findFilePath(values);
        filePath += FilePath.XML.filepath();
        return filePath;
    }

	public XStream getXStream() {
		return this.myXStream;
	}

}