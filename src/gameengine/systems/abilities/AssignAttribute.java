
package gameengine.systems.abilities;

import gameengine.attributes.interfaces.AttributeInterface;
import gameengine.entities.EntityInterface;

public class AssignAttribute implements AbilityInterface{
        private EntityInterface entity;
        private AttributeInterface attribute;
        
        public AssignAttribute(EntityInterface entity){
            this.entity=entity;
            attribute=null;
        }
        
        public void setAttributeToAssign(AttributeInterface att){
        	attribute = att;
        }
        
        public void activate(double timePassed, double[] extraInputs) {
        	if(attribute!=null) entity.addAttribute(attribute);
        }

}
