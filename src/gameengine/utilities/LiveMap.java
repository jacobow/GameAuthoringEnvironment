package gameengine.utilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author jacob
 * @author walker
 *
 */
public class LiveMap<K,T> extends HashMap<K,T> implements CollectionSubject {
	Set<LiveObserver> myObservers;

	public LiveMap(){
		super();
		myObservers = new HashSet<LiveObserver>();
	}

	public LiveMap(int initialCapacity){
		super(initialCapacity);
		myObservers = new HashSet<LiveObserver>();
	}

	public LiveMap(int initialCapacity, float loadFactor){
		super(initialCapacity,loadFactor);
		myObservers = new HashSet<LiveObserver>();
	}

	public LiveMap(Map<? extends K, ? extends T> map){
		super(map);
		myObservers = new HashSet<LiveObserver>();
	}

	@Override
	public T put(K k, T t){
		T toReturn = super.put(k,t);
		notifyObservers();
		return toReturn;		
	}

	@Override
	public void putAll(Map<? extends K, ? extends T> map){
		super.putAll(map);
		notifyObservers();
	}
	
	@Override
	public T remove(Object k){
		T toReturn = super.remove(k);
		notifyObservers();
		return toReturn;
	}
	
	@Override
	public boolean remove(Object k, Object t){
		if(super.remove(k, t)){
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
	public void notifyObservers() {
		myObservers.forEach(observer->observer.amend());
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
