package gameengine.utilities;

/**
 * 
 * @author jacob
 * @author walker
 *
 */
public interface CollectionSubject {
	
	public void notifyObservers();
	
	public void registerObserver(LiveObserver o);
	
	public void clearObservers();
}
