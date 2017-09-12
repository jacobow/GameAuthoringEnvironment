package gameengine.utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author jacob
 * @author walker
 *
 */
public class LiveList<T> extends ArrayList<T> implements CollectionSubject{

	private Set<LiveObserver> myObservers;

	public LiveList() {
		myObservers = new HashSet<LiveObserver>();
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(observer -> observer.amend());
	}

	@Override
	public void registerObserver(LiveObserver o) {
		myObservers.add(o);

	}

	@Override
	public void clearObservers() {
		myObservers.clear();
	}

}
