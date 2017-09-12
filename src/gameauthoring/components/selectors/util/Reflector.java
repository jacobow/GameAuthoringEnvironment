package gameauthoring.components.selectors.util;
import java.util.LinkedHashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;

/**
 * Purpose: used to give the user access to the Google Reflections library, which is used to search
 * through the classes in a package and find subclasses of a given class
 * Dependencies: the class is dependent on the the Google Reflections library
 * Example Use: used to allow the user to get all implementations of the WinConditionInterface class
 * so that they can be fed into the WinConditionSelector class 
 * 
 * @author Larissa Cox
 *
 */

public class Reflector {
	
	/**
	 * purpose: provides access to the Google reflections getSubTypesOf method,
	 * which returns all subtypes in a package of a given method
	 * @param interfaceClass the class whose subtypes the user wants access to
	 * @return the subtypes of interfaceClass
	 */
	public Set<String> getSubclassNames(Class interfaceClass){
		Reflections ref = new Reflections(ClasspathHelper.forPackage("gameengine"), new SubTypesScanner());
		Set<Class<?>> properties= ref.getSubTypesOf(interfaceClass);
		Set<String> propertyNames = new LinkedHashSet<String>();
 		for(Class<?> p : properties){
 			if(!p.getName().contains("Interface")){
 				propertyNames.add(p.getName());
 			}
 		}
 		return propertyNames;
	}
}