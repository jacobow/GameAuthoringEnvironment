###Attributes and their information:

####The ones being used right now:

#####Base

What it does: 

Determines if the base has been 'seized' - used to win the game.

What it needs:
Nothing!
=======
#####Damage

What it does: 

Determines what the attack points are for an object. - used to decrease health points in a character.

What it needs:
Need a double value to set its attack point.

#####Health
What it does: 

Determines the health of an object. It is used to calculate how much health point is in a character.

What it needs:
Need a double value to set its max health point.


#####Physical

What it does: 

Determines if an object is physical. If it is physical and it runs into a SOLID, it will not move. 
If it is not physical and it runs into a solid, it will go through it. 

What it needs:
Just to be added. No parameters!

#####Solid

What it does: 

Determines if an object is solid. If it is solid, it acts as a wall to physical objects. See Physical.

What it needs:
Just to be added. No parameters!

#####Spacial

What it does:

This is a big one. Spacials are visible objects. They have location, orientation, speed, and bounds. They can be moved assuming the speed is >0. 

What it needs: 
Parameters:

 * double startingX: Starting x coordinate
 * double startingY: Starting y coordinate
 * double startingOrientation: Starting orientation (In degrees, 0 is to the right, 90 is up...i think...)
 * double initialVelocity: Speed. 
  * This cannot be modified directly, only through percentage modifiers. Therefore if an object is a wall, if you set the speed to Zero, it will never move, even if you give it keys to allow it to move. Recommended range (for now): 100-300
 * String filePath: The filePath which leads to the image which represents the object, relative to the source folder. 

Example implementation of adding an Entity that can move and stuff:

ArrayList<AttributeInterface> myAttributeList = new ArrayList<AttributeInterface>();
myAttributeList.add(new Spacial(50, 50, 0, 150,"resources/duketurtle.png"));
myAttributeList.add(new Physical());
Entity myEntity = new Entity("2",myAttributeList);


####The ones not being used right now: (ignore these)

#####Carryable

#####Projectile

#####Respawnable

#####Visible

#####Zone


