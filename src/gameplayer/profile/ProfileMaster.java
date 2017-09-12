package gameplayer.profile;
import java.util.HashMap;
import java.util.Map;
import gamedata.exceptions.FileIOError;
import gamedata.exceptions.LoadWorldException;
import gamedata.fileIO.FilePath;
import gamedata.fileIO.XMLFileIO;

public class ProfileMaster {

    private XMLFileIO xml;
    private Map<String, Profile> mapOfProfiles;
    //private XStream xml = new XStream(new DomDriver());

    public ProfileMaster(){
        xml = new XMLFileIO();
        mapOfProfiles = new HashMap<String, Profile>();
        //loadProfileMaster();
    }

    public Profile addProfile(String name, String userID, String url){
        loadProfileMaster();
        if (!mapOfProfiles.containsKey(userID)){
            Profile myProf = new Profile(userID);
            myProf.setMyImagePath(url);
            myProf.setMyName(name);
            mapOfProfiles.put(userID, myProf);
        }
        else{

        }
        saveProfileMaster();
        return mapOfProfiles.get(userID);
    }
    public Profile getProfile(String userID){
        loadProfileMaster();
        System.out.println(userID);
        if (!mapOfProfiles.containsKey(userID)){
            mapOfProfiles.put(userID, new Profile(userID));
            System.out.println("PRINTED NEW");
        }
        saveProfileMaster();
        return mapOfProfiles.get(userID);
    }

    public void loadProfileMaster(){
        StringBuilder profiles;
        try {
            profiles = xml.bufferReader(FilePath.PROFILEDATA.filepath());
            mapOfProfiles = (Map<String, Profile>) xml.getXStream().fromXML(profiles.toString());
        }
        catch (LoadWorldException e) {
            FileIOError IOError = new FileIOError("ErrorLoadProfile");
            IOError.displayMessage();
        }

    }

    public void saveProfileMaster(){
        XMLFileIO xml = new XMLFileIO();
        String output = xml.getXStream().toXML(mapOfProfiles);
        //	xml.writeFile(new File(FilePath.PROFILEDATA.filepath()),
        //			output, null);//new FileIOError("Profile Could Not Be Saved"));
    }


}