package com.house.infinitybot.tasks.telekinetic;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;

import com.house.infinitybot.rooms.RoomManager;
import com.house.infinitybot.rooms.TelekineticRoom;
import com.house.infinitybot.rooms.identifiers.GuardianPositionIdentifier;
import com.house.infinitybot.rooms.telekinetic.directions.Directions;
import com.house.infinitybot.rooms.telekinetic.directions.MovementStep;
import com.house.infinitybot.tasks.TelekineticRoomTask;

public class Telekinetic_Room2 extends TelekineticRoomTask {

	public Telekinetic_Room2(Script script, RoomManager roomManager) {
		super(script, roomManager);
		((TelekineticRoom) roomManager.getRoom(2)).addMovementStep(new MovementStep(script, 1, new GuardianPositionIdentifier(-13, 4, 0), Directions.SOUTH));
		((TelekineticRoom) roomManager.getRoom(2)).addMovementStep(new MovementStep(script, 2, new GuardianPositionIdentifier(-13, -5, 0), Directions.WEST));
		((TelekineticRoom) roomManager.getRoom(2)).addMovementStep(new MovementStep(script, 3, new GuardianPositionIdentifier(-14, -5, 0), Directions.NORTH));
		((TelekineticRoom) roomManager.getRoom(2)).addMovementStep(new MovementStep(script, 4, new GuardianPositionIdentifier(-14, 3, 0), Directions.WEST));
		((TelekineticRoom) roomManager.getRoom(2)).addMovementStep(new MovementStep(script, 5, new GuardianPositionIdentifier(-16, 3, 0), Directions.SOUTH));
		((TelekineticRoom) roomManager.getRoom(2)).addMovementStep(new MovementStep(script, 6, new GuardianPositionIdentifier(-16, -3, 0), Directions.WEST));
		((TelekineticRoom) roomManager.getRoom(2)).addMovementStep(new MovementStep(script, 7, new GuardianPositionIdentifier(-20, -3, 0), Directions.SOUTH));
		((TelekineticRoom) roomManager.getRoom(2)).addMovementStep(new MovementStep(script, 8, new GuardianPositionIdentifier(-20, -5, 0), Directions.WEST));
	}

	@Override
	public boolean process() {

		if (!infoUpToDate()) {
			updateCurrentRoom();
			currentRoom.updateMazeArea(10, 10);
			currentRoom.updateSidePositions(10, 10);
			currentRoom.updateGuardianPositions();
			currentRoom.updateFinishPosition();
		}

		NPC guardianDone = getScript().getNpcs().closest(guardianDoneID);

		if (guardianDone != null) {

			finishRoom(guardianDone);

		} else {

			NPC guardian = getScript().getNpcs().closest(guardianID);

			if (guardian != null) {

				int cameraConfigs = getScript().getConfigs().get(629);

				if (cameraConfigs == 1073741826) {

					if (currentRoom.getCurrentMovementStep() != null) {
						takeStep(guardian);
					}

				} else {

					if (guardian.isVisible() && guardian.getPosition().distance(getScript().myPosition()) <= 2) {
						guardian.interact("Observe");
						sleepTill(2000, cameraConfigs == 1073741826);
					} else
						getScript().getWalking().walk(guardian);

				}

			}

		}

		return false;
	}

	@Override
	public boolean validate() {
		return roomManager.isCurrentRoom(2);
	}
}
