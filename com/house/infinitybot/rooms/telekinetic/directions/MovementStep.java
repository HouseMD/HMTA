package com.house.infinitybot.rooms.telekinetic.directions;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import com.house.infinitybot.rooms.identifiers.GuardianPositionIdentifier;

public class MovementStep {

	Script script;
	int stepNumber;
	GuardianPositionIdentifier guardianPositionIdentifier;
	Directions direction;

	Position currentGuardianPosition;

	public MovementStep(Script script, int stepNumber, GuardianPositionIdentifier guardianPositionIdentifier, Directions direction) {
		this.script = script;
		this.stepNumber = stepNumber;
		this.guardianPositionIdentifier = guardianPositionIdentifier;
		this.direction = direction;
	}

	public int getStepNumber() {
		return stepNumber;
	}

	public Directions getDirection() {
		return direction;
	}

	public GuardianPositionIdentifier getGuardianPositionIdentifier() {
		return guardianPositionIdentifier;
	}
	
	public Position getCurrentGuandianPosition(){
		return currentGuardianPosition;
	}

	public void setcurrentGuardianPosition(Script script,Position currentExitPosition) {
		currentGuardianPosition = new Position(currentExitPosition.getX()+ guardianPositionIdentifier.xOffset, currentExitPosition.getY()+ guardianPositionIdentifier.yOffset, currentExitPosition.getZ()+ guardianPositionIdentifier.zOffset);
	}

}
