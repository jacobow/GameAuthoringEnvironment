package gameauthoring.presets.enums;

import gameengine.GameWorld;
import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.MoveBackward;
import gameengine.systems.abilities.MoveForward;
import gameengine.systems.abilities.MoveToClick;
import gameengine.systems.abilities.RotateLeft;
import gameengine.systems.abilities.RotateRight;
import javafx.scene.input.KeyCode;

/**
 * @author DavidYoon
 *
 */
public enum Input {

	KEYBOARD_WASD(){
		public void setInput(GameWorld world, EntityInterface entity){
			world.addAbility(KeyCode.W, entity,new MoveForward(entity));
			world.addAbility(KeyCode.S, entity,new MoveBackward(entity));
			world.addAbility(KeyCode.A, entity,new RotateLeft(entity));
			world.addAbility(KeyCode.D, entity,new RotateRight(entity));
		}
	},
	
	KEYBOARD_IJKL(){
		public void setInput(GameWorld world, EntityInterface entity){
			world.addAbility(KeyCode.I, entity,new MoveForward(entity));
			world.addAbility(KeyCode.K, entity,new MoveBackward(entity));
			world.addAbility(KeyCode.J, entity,new RotateLeft(entity));
			world.addAbility(KeyCode.L, entity,new RotateRight(entity));
		}
	},
	
	XBOX(){
		public void setInput(GameWorld world, EntityInterface entity){
			
		}
	},
	
	PLAYSTATION(){
		public void setInput(GameWorld world, EntityInterface entity){
		
		}
	},
	
	MOUSE_MOVETOCLICK(){
		public void setInput(GameWorld world, EntityInterface entity){
			world.setMouseClickAbility(new MoveToClick(entity));
		}
	};

	private GameWorld world;

	Input() {
		setInput();
	}
	
	public void setInput(){
		
	}
	
}
