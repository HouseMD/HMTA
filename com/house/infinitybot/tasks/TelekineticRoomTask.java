package com.house.infinitybot.tasks;

import java.awt.event.KeyEvent;

import org.osbot.rs07.api.Magic;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Spells;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.Condition;

import com.house.infinitybot.rooms.RoomManager;
import com.house.infinitybot.rooms.TelekineticRoom;
import com.house.infinitybot.rooms.telekinetic.directions.MovementStep;

public class TelekineticRoomTask extends RoomTask {

	protected int exitID = 23677;
	protected int guardianID = 6777;
	protected int guardianDoneID = 6779;

	protected TelekineticRoom currentRoom;

	public TelekineticRoomTask(Script script, RoomManager roomManager) {
		super(script, roomManager);
	}

	public void updateCurrentRoom() {
		currentRoom = (TelekineticRoom) roomManager.getCurrentRoom();
	}

	public boolean guardianIsOnFinish() {
		return false;
	}

	public boolean infoUpToDate() {

		updateCurrentRoom();

		if (currentRoom.getExitPosition() != null) {

			if (currentRoom.getLastExitPosition() != null) {
				if (currentRoom.getLastExitPosition().getX() == currentRoom.getExitPosition().getX() && currentRoom.getLastExitPosition().getY() == currentRoom.getExitPosition().getY() && currentRoom.getLastExitPosition().getZ() == currentRoom.getExitPosition().getZ())
					return true;
				else {
					currentRoom.setLastExitPosition(currentRoom.getExitPosition());
					return false;
				}
			} else {
				currentRoom.setLastExitPosition(currentRoom.getExitPosition());
				return false;
			}

		}

		return false;
	}

	public void takeStep(NPC guardian) {

		MovementStep currentStep = currentRoom.getCurrentMovementStep();
		MovementStep nextStep = currentRoom.getMovementStep(currentStep.getStepNumber() + 1);

		Position destination = null;

		Position me = getScript().myPosition();

		if (currentRoom.getSideArea(currentStep.getDirection()).contains(getScript().myPosition())) {

			if (!getScript().myPlayer().isMoving()) {

				if (!isCastingTelegrab()) {
					castTelegrab(guardian);
				}
				Magic magic = getScript().getMagic();
				if (magic.isSpellSelected()) {
					guardian.interact("Cast");
					sleepTill(5000, guardian == null);
				}

			}

		} else {
			if (nextStep != null) {

				switch (currentStep.getDirection()) {

				// NORTH
				case NORTH:

					switch (nextStep.getDirection()) {

					case NORTH:
						break;

					case SOUTH:

						if (currentRoom.northLeft.distance(me) <= currentRoom.northRight.distance(me))
							destination = currentRoom.northLeft;
						else
							destination = currentRoom.northRight;

						break;

					case EAST:
						destination = currentRoom.northRight;
						break;

					case WEST:
						destination = currentRoom.northLeft;
						break;

					}

					break;

				// SOUTH
				case SOUTH:

					switch (nextStep.getDirection()) {

					case NORTH:

						if (currentRoom.southLeft.distance(me) <= currentRoom.southRight.distance(me))
							destination = currentRoom.southLeft;
						else
							destination = currentRoom.southRight;

						break;

					case SOUTH:
						break;

					case EAST:
						destination = currentRoom.southRight;
						break;

					case WEST:
						destination = currentRoom.southLeft;
						break;

					}

					break;

				// EASTS
				case EAST:

					switch (nextStep.getDirection()) {

					case NORTH:
						destination = currentRoom.eastTop;
						break;

					case SOUTH:
						destination = currentRoom.eastBottom;
						break;

					case EAST:
						break;

					case WEST:

						if (currentRoom.westTop.distance(me) <= currentRoom.westBottom.distance(me))
							destination = currentRoom.eastTop;
						else
							destination = currentRoom.eastBottom;

						break;

					}

					break;

				// WEST
				case WEST:

					switch (nextStep.getDirection()) {

					case NORTH:
						destination = currentRoom.westTop;
						break;

					case SOUTH:
						destination = currentRoom.westBottom;
						break;

					case EAST:

						if (currentRoom.eastTop.distance(me) <= currentRoom.eastBottom.distance(me))
							destination = currentRoom.westTop;
						else
							destination = currentRoom.westBottom;

						break;

					case WEST:
						break;

					}

					break;
				}

				final Position destinationFinal;

				if (currentStep.getStepNumber() == 1) {
					destinationFinal = getClosestPositionFromArea(currentRoom.getSideArea(currentStep.getDirection()));
				} else
					destinationFinal = destination;

				WalkingEvent walk = new WalkingEvent(destinationFinal);
				walk.setMinDistanceThreshold(0);
				walk.setMiniMapDistanceThreshold(0);
				walk.setBreakCondition(new Condition() {
					@Override
					public boolean evaluate() {
						return me.getX() == destinationFinal.getX() && me.getY() == destinationFinal.getY() && me.getZ() == destinationFinal.getZ();
					}
				});

				getScript().execute(walk);

			} else {

				final Area desttinationFinal = currentRoom.getSideArea(currentStep.getDirection());

				WalkingEvent walk = new WalkingEvent(getClosestPositionFromArea(desttinationFinal));
				walk.setMinDistanceThreshold(0);
				walk.setMiniMapDistanceThreshold(0);
				walk.setBreakCondition(new Condition() {
					@Override
					public boolean evaluate() {
						return desttinationFinal.contains(me);
					}
				});

				getScript().execute(walk);

			}
		}

	}

	public Position getClosestPositionFromArea(Area area) {
		Position closest = area.getPositions().get(0);
		for (Position position : area.getPositions()) {
			if (position.distance(getScript().myPosition()) <= closest.distance(getScript().myPosition()))
				closest = position;
		}
		return closest;
	}

	public boolean isCastingTelegrab() {
		Magic magic = getScript().getMagic();
		if (magic.isSpellSelected() && magic.getSelectedSpellName() == "Telekinetic Grab") {
			return true;
		} else
			return false;
	}

	public void castTelegrab(NPC guardian) {
		Magic magic = getScript().getMagic();
		magic.castSpell(Spells.NormalSpells.TELEKINETIC_GRAB);
	}
	
	public void finishRoom(NPC guardianDone) {
		
		RS2Widget continue1 = getScript().getWidgets().get(229, 1);
		RS2Widget continue2 = getScript().getWidgets().get(231, 2);
		RS2Widget yes = getScript().getWidgets().get(219, 0, 1);
		RS2Widget continue3 = getScript().getWidgets().get(217, 2);

		if (continue1 == null && continue2 == null && yes == null && continue3 == null) {
			if (guardianDone.isVisible()) {
				guardianDone.interact("Talk-to");
				sleepTill(1000, continue1 != null);
			} else
				getScript().getWalking().walk(guardianDone);
		}
		
		if(continue1 != null && continue1.isVisible())
			Type(KeyEvent.VK_SPACE);
		
		if(continue2 != null && continue2.isVisible())
			Type(KeyEvent.VK_SPACE);
		
		if(yes != null && yes.isVisible())
			getScript().getKeyboard().typeKey("1".toCharArray()[0]);
		
		if(continue3 != null && continue3.isVisible())
			Type(KeyEvent.VK_SPACE);
		
	}

}
