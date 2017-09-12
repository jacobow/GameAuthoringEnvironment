package gameengine.systems.coresystems.interfaces;

import gameengine.attributes.interfaces.PathInterface;

public interface AddPathInterface {
	
	abstract void addNewPath(PathInterface newPath);
	
	abstract void updateTrackedPaths();
}
