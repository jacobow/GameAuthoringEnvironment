package gameauthoring.components.selectors.componentselectors;

import java.util.Comparator;

import javafx.scene.control.MenuItem;

/**
 * Utility Class created in order to generate a comparator that compares the names of different
 * MenuItems for use in sorting. (Sorts menuItems in alphabetical Order). Class is used in the 
 * AttributeSelector and AbilitySelector classes.
 * @author michaelseaberg
 *
 */
public class MenuComparator {
	public static Comparator<MenuItem> menuComparator = new Comparator<MenuItem>() {

		public int compare(MenuItem m1, MenuItem m2) {
		   String MenuItem1 = m1.getText();
		   String MenuItem2 = m2.getText();

		   return MenuItem1.compareTo(MenuItem2);
	    }};
}
