package gameauthoring.components.selectors.componentselectors.abilityselector;

import gameauthoring.components.entitycreation.ControllerInputPopup;
import gameauthoring.components.entitycreation.KeyInputPopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * Purpose: this class allows the user to view the abilities they have added to an
 * entity and to assign key and controller input to them 
 * Dependencies: the class is dependent on the the AbilityMapping, KeyInputPopup, and 
 * ControllerInputPopup classes
 * Example Use: used to display that a MoveForward ability has been added to an entity
 * and to to assign that ability to the W key
 * 
 * @author Larissa Cox, Michael Seaberg
 *
 */

public class AbilityTable {
	private VBox myContentArea;
	private TableView<AbilityMapping> myMappingTable;
	private ObservableList<AbilityMapping> myMappings;
	private KeyInputPopup userKeyInput;
	private ControllerInputPopup userControllerInput;
	private int myNumControllers;

	public AbilityTable(VBox contentArea, int numControllers){
		myContentArea = contentArea;
		myNumControllers = numControllers;
		myMappingTable = new TableView<AbilityMapping>();
		myMappingTable.getStyleClass().add("table-view");
		myMappings = FXCollections.observableArrayList();
	}
	
	/**
	 * purpose: to create and return an VBox containing the table of abilities and
	 * their corresponding key and controller inputs that can be dynamically updated
	 * by the user
	 * @return the VBox containing the table
	 */
	public VBox createAbilitySection() {
		myMappingTable.setMaxWidth(450);
		myMappingTable.scrollTo(30);
		myMappingTable.setEditable(true);
		TableColumn<AbilityMapping, String> abilityColumn = createTableColumn("abilityName", "Ability", 150, false, null);
		TableColumn<AbilityMapping, String> keyColumn = createTableColumn("keyName", "Key", 100,  true, keyColumnHandler());
		TableColumn<AbilityMapping, String> controllerColumn = createTableColumn("controllerName", "Controller", 200, true, controllerColumnHandler());
		myMappingTable.setItems(myMappings);
		myMappingTable.getColumns().addAll(abilityColumn, keyColumn, controllerColumn);
		return myContentArea;
	}
	
	/**
	 * purpose: to provide access to the table of ability mappings that stores
	 * information and display for the table of abilities
	 * @return the table view of abilities
	 */
	public TableView<AbilityMapping> getMappingTable(){
		return myMappingTable;
	}
	
	/**
	 * purpose: provides access to the Observable list of ability mappings so
	 * that outside classes can watch the changes in this list
	 * @return the observable list of ability mappings
	 */
	public ObservableList<AbilityMapping> getMappings(){
		return myMappings;
	}
	
	private EventHandler<CellEditEvent<AbilityMapping, String>> keyColumnHandler(){
		return new EventHandler<CellEditEvent<AbilityMapping, String>>() {
			@Override
			public void handle(CellEditEvent<AbilityMapping, String> event) {
				userKeyInput = new KeyInputPopup();
				event.getRowValue().setMyKeyCode(userKeyInput.getKeyCode());
				myMappingTable.getColumns().get(0).setVisible(false);
				myMappingTable.getColumns().get(0).setVisible(true);
				TableColumn.editCommitEvent();
			}
		};
	}
	
	private EventHandler<CellEditEvent<AbilityMapping, String>> controllerColumnHandler(){
		return new EventHandler<CellEditEvent<AbilityMapping, String>>() {
			@Override
			public void handle(CellEditEvent<AbilityMapping, String> event) {
				userControllerInput = new ControllerInputPopup(myNumControllers);
				event.getRowValue().setMyControllerCode(userControllerInput.getControllerCode(),
						userControllerInput.getInputDisplayName(), userControllerInput.getControllerNumber());
				myMappingTable.getColumns().get(0).setVisible(false);
				myMappingTable.getColumns().get(0).setVisible(true);
				TableColumn.editCommitEvent();
			}
		};
	}

	private TableColumn<AbilityMapping, String> createTableColumn(String cellName, String colName, int colWidth, boolean editable,
			EventHandler<CellEditEvent<AbilityMapping, String>> handler) {
		TableColumn<AbilityMapping, String> column = new TableColumn<AbilityMapping, String>(
				colName);
		column.setMinWidth(colWidth);
		column.setCellValueFactory(new PropertyValueFactory<AbilityMapping, String>(cellName));
		column.setEditable(editable);
		column.setOnEditStart(handler);
		return column;
	}
	
}
