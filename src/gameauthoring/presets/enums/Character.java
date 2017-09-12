package gameauthoring.presets.enums;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gameengine.GameWorld;
import gameengine.attributes.Child;
import gameengine.attributes.Damages;
import gameengine.attributes.Health;
import gameengine.attributes.KillDeathCount;
import gameengine.attributes.Physical;
import gameengine.attributes.Player;
import gameengine.attributes.Range;
import gameengine.attributes.Shielded;
import gameengine.attributes.Spacial;
import gameengine.attributes.Team;
import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.entities.Entity;
import gameengine.entities.EntityInterface;
import gameengine.systems.abilities.CreateEntity;
import javafx.scene.input.KeyCode;

/**
 * 
 * 
 * @author DavidYoon
 *
 */
public enum Character {

	ARCHER(){
		public void setAttributes(GameWorld world, EntityInterface entity, String path){
			entity.addAttribute(new Spacial(0,0,0,150,true,SLEAGUE_FILEPATH+path));
			entity.addAttribute(new Team());
			entity.addAttribute(new Health(1000));
			entity.addAttribute(new Child());
			entity.addAttribute(new Player());
			entity.addAttribute(new Physical());
			entity.addAttribute(new KillDeathCount());
		}
		public EntityInterface createArrow(){
			List<AttributeInterface> attributes = new ArrayList<AttributeInterface>();
			attributes.add(new Range(50));
			attributes.add(new Spacial(0,0,0,400, false, SLEAGUE_FILEPATH + "projectiles" +File.separator + "Arrow.png"));
			attributes.add(new Damages(10));
			attributes.add(new Physical());
			Entity arrow = new Entity("arrow", attributes);
			return arrow;
		}
		public void setAbilities(GameWorld world, EntityInterface entity){
			world.addAbility(KeyCode.Q, entity, new CreateEntity(entity, createArrow(), world));
		}
	},
	
	AXMAN(){
		public void setAttributes(GameWorld world, EntityInterface entity, String path){
			entity.addAttribute(new Spacial(0,0,0,100,true,path));
			entity.addAttribute(new Team());
			entity.addAttribute(new Health(1000));
			entity.addAttribute(new Player());
			entity.addAttribute(new Physical());
			entity.addAttribute(new Damages(200));
			entity.addAttribute(new KillDeathCount());
		}
	},
	
	SHOOTER(){
		public void setAttributes(GameWorld world, EntityInterface entity, String path){
			entity.addAttribute(new Spacial(0,0,0,150,true,SLEAGUE_FILEPATH+path));
			entity.addAttribute(new Team());
			entity.addAttribute(new Health(1000));
			entity.addAttribute(new Child());
			entity.addAttribute(new Player());
			entity.addAttribute(new Physical());
			entity.addAttribute(new KillDeathCount());
		}
		public void setAbilities(GameWorld world, EntityInterface entity){
			world.addAbility(KeyCode.Q, entity, new CreateEntity(entity, createBullets(), world));
		}
		private EntityInterface createBullets() {
			List<AttributeInterface> attributes = new ArrayList<AttributeInterface>();
			attributes.add(new Range(100));
			attributes.add(new Spacial(0,0,0,400, false, SLEAGUE_FILEPATH+ "projectiles" + File.separator + "Bullet.png"));
			attributes.add(new Damages(20));
			attributes.add(new Physical());
			Entity arrow = new Entity("bullet", attributes);
			return arrow;
		}
	},
	
	BASE(){
		public void setAttributes(GameWorld world, EntityInterface entity, String path){
			entity.addAttribute(new Spacial(0,0,0,0,false,SLEAGUE_FILEPATH+path));
			entity.addAttribute(new Team());
			entity.addAttribute(new Health(10000));
			entity.addAttribute(new Physical());
			entity.addAttribute(new Damages(25));
			entity.addAttribute(new Child());
			
		}
	},
	
	HORSEMAN(){
		public void setAttributes(GameWorld world, EntityInterface entity, String path){
			entity.addAttribute(new Spacial(0,0,0,250,true,SLEAGUE_FILEPATH+path));
			entity.addAttribute(new Team());
			entity.addAttribute(new Health(1000));
			entity.addAttribute(new Player());
			entity.addAttribute(new Physical());
			entity.addAttribute(new Damages(100));
			entity.addAttribute(new KillDeathCount());
		}
	},
	
	SWORDSMAN(){
		public void setAttributes(GameWorld world, EntityInterface entity, String path){
			entity.addAttribute(new Spacial(0,0,0,100,true,SLEAGUE_FILEPATH+path));
			entity.addAttribute(new Team());
			entity.addAttribute(new Health(1000));
			entity.addAttribute(new Player());
			entity.addAttribute(new Physical());
			entity.addAttribute(new Damages(100));
			entity.addAttribute(new Shielded(200, 50));
			entity.addAttribute(new KillDeathCount());
		}
	},
	
	WIZARD(){
		public void setAttributes(GameWorld world, EntityInterface entity, String path){
			entity.addAttribute(new Spacial(0,0,0,150,true,SLEAGUE_FILEPATH+path));
			entity.addAttribute(new Team());
			entity.addAttribute(new Health(2000));
			entity.addAttribute(new Child());
			entity.addAttribute(new Player());
			entity.addAttribute(new Physical());
			entity.addAttribute(new KillDeathCount());
		}
	},
	
	PIKESMAN(){
		public void setAttributes(GameWorld world, EntityInterface entity, String path){
			entity.addAttribute(new Spacial(0,0,0,200,true,SLEAGUE_FILEPATH+path));
			entity.addAttribute(new Team());
			entity.addAttribute(new Health(1000));
			entity.addAttribute(new Player());
			entity.addAttribute(new Physical());
			entity.addAttribute(new Damages(150));
			entity.addAttribute(new KillDeathCount());
		}
	};

	public static final String SLEAGUE_FILEPATH = "resources" + File.separator + "images" + File.separator + "sleague"  + File.separator ;

	Character() {
		
	}
	
	public void addEntity(GameWorld world, EntityInterface entity, String path){
		setAttributes(world, entity, path);
		world.addEntity(entity);
		setAbilities(world, entity);
	}
	
	public void setAbilities(GameWorld world, EntityInterface entity) {
		
	}

	public void setAttributes(GameWorld world, EntityInterface entity, String path){
		
	}
	
}
