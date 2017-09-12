package gameengine.utilities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author jacob
 * @author walker
 *
 */
public class LiveSet<T> extends HashSet<T> implements CollectionSubject{
	Set<LiveObserver> myObservers;

	public LiveSet(){
		super();
		myObservers = new HashSet<LiveObserver>();
	}

	public LiveSet(Collection<? extends T> c){
		super(c);
		myObservers = new HashSet<LiveObserver>();
	}

	public LiveSet(int initialCapacity, float loadFactor){
		super(initialCapacity, loadFactor);
		myObservers = new HashSet<LiveObserver>();
	}
	public LiveSet(int initialCapacity){
		super(initialCapacity);
		myObservers = new HashSet<LiveObserver>();
	}

	@Override
	public boolean add(T element) {
		if(super.add(element)) {
			notifyObservers();
			return true;
		}
		return false;
	}

	@Override
	public void clear(){
		super.clear();
		notifyObservers();
	}


	@Override
	public boolean remove(Object element){
		if(super.remove(element)){
			notifyObservers();
			return true;
		}
		return false;
	}

	@Override
	public void notifyObservers() {
		myObservers.forEach(observer->{
			observer.amend();
		});
	}

	@Override
	public void registerObserver(LiveObserver o){
		myObservers.add(o);
	}

	public void clearObservers(){
		myObservers.clear();
	}




}
