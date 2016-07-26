package com.house.infinitybot.rooms;

import java.util.ArrayList;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

import com.house.infinitybot.HMTA;
import com.house.infinitybot.rooms.identifiers.FinishIdentifier;
import com.house.infinitybot.rooms.identifiers.MazeIdentifier;
import com.house.infinitybot.rooms.identifiers.RoomIdentifier;
import com.house.infinitybot.rooms.telekinetic.directions.Directions;
import com.house.infinitybot.rooms.telekinetic.directions.MovementStep;

public class TelekineticRoom extends Room {

	String identifyingObjectName;

	protected int exitID = 23677;
	protected int guardianID = 6777;
	protected int guardianDoneID = 6779;

	RoomIdentifier rid;
	FinishIdentifier fid;
	MazeIdentifier mid;

	protected Area mazeArea = null;

	public Position northLeft, northRight;
	public Position eastTop, eastBottom;
	public Position southLeft, southRight;
	public Position westTop, westBottom;

	protected Position lastExitPosition = null;

	protected Position finishedPosition = null;

	protected ArrayList<MovementStep> movementSteps = new ArrayList<MovementStep>();

	public TelekineticRoom(Script script, int id, String name, String identifyingObjectName, RoomIdentifier rid, FinishIdentifier fid, MazeIdentifier mid) {
		super(script, id, RoomType.TELEKINETIC, name);
		this.identifyingObjectName = identifyingObjectName;
		this.rid = rid;
		this.fid = fid;
		this.mid = mid;
	}

	public Area getSideArea(Directions side) {

		switch (side) {
		case NORTH:
			return new Area(northLeft, northRight);
		case SOUTH:
			return new Area(southLeft, southRight);
		case EAST:
			return new Area(eastTop, eastBottom);
		case WEST:
			return new Area(westTop, westBottom);
		default:
			return null;
		}

	}

	public RoomIdentifier getRoomIdentifier() {
		return rid;
	}

	public FinishIdentifier getFinishIdentifier() {
		return fid;
	}

	public MazeIdentifier getMazeIdentifier() {
		return mid;
	}

	public void updateMazeArea(int width, int height) {

		Position exit = getExitPosition();
		if (exit != null) {
			mazeArea = new Area(new Position[] { //
					getSouthWestPosition(), //
					new Position(getSouthWestPosition().getX(), getSouthWestPosition().getY() + getMazeIdentifier().getHeigth(), getSouthWestPosition().getZ()), //
					new Position(getSouthWestPosition().getX() + getMazeIdentifier().getWidth(), getSouthWestPosition().getY() + getMazeIdentifier().getHeigth(), getSouthWestPosition().getZ()), //
					new Position(getSouthWestPosition().getX() + getMazeIdentifier().getWidth(), getSouthWestPosition().getY(), getSouthWestPosition().getZ()) });
			HMTA.pp.addAll(mazeArea.getPositions());
		}

		script.log("Remapped " + getName() + " EXIT:" + getExitPosition().toString());

	}

	public void updateSidePositions(int width, int height) {

		if (getSouthWestPosition() != null) {

			southLeft = new Position(getSouthWestPosition().getX(), getSouthWestPosition().getY() - 1, getSouthWestPosition().getZ());
			southRight = new Position(getSouthWestPosition().getX() + width - 1, getSouthWestPosition().getY() - 1, getSouthWestPosition().getZ());

			northLeft = new Position(getSouthWestPosition().getX(), getSouthWestPosition().getY() + height, getSouthWestPosition().getZ());
			northRight = new Position(getSouthWestPosition().getX() + width - 1, getSouthWestPosition().getY() + height, getSouthWestPosition().getZ());

			westTop = new Position(getSouthWestPosition().getX() - 1, getSouthWestPosition().getY() + height - 1, getSouthWestPosition().getZ());
			westBottom = new Position(getSouthWestPosition().getX() - 1, getSouthWestPosition().getY(), getSouthWestPosition().getZ());

			eastTop = new Position(getSouthWestPosition().getX() + width, getSouthWestPosition().getY() + height - 1, getSouthWestPosition().getZ());
			eastBottom = new Position(getSouthWestPosition().getX() + width, getSouthWestPosition().getY(), getSouthWestPosition().getZ());

		}
	}

	public void updateGuardianPositions() {
		for (MovementStep step : movementSteps) {
			step.setcurrentGuardianPosition(script, getExitPosition());
		}
	}

	public void updateFinishPosition() {
		finishedPosition = getFinishPosition();
	}

	public void addMovementStep(MovementStep step) {
		movementSteps.add(step);
	}

	public MovementStep getMovementStep(int stepNumber) {
		for (MovementStep step : movementSteps) {
			if (step.getStepNumber() == stepNumber)
				return step;
		}
		return null;
	}

	public MovementStep getCurrentMovementStep() {
		for (MovementStep step : movementSteps) {
			if (getGuardianPosition().getX() == step.getCurrentGuandianPosition().getX() && getGuardianPosition().getY() == step.getCurrentGuandianPosition().getY() && getGuardianPosition().getZ() == step.getCurrentGuandianPosition().getZ()) {
				return step;
			}
		}
		return null;
	}

	public Position calculateOffsetPosition(Position ip, int x, int y, int z) {
		return new Position(ip.getX() + x, ip.getY() + y, ip.getZ() + z);
	}

	public Position getExitPosition() {
		if (script.getObjects().closest(exitID) != null)
			return script.getObjects().closest(exitID).getPosition();
		return null;
	}

	public Position getLastExitPosition() {
		return lastExitPosition;
	}

	public void setLastExitPosition(Position newPosition) {
		lastExitPosition = newPosition;
	}

	public Position getFinishPosition() {
		return new Position(getExitPosition().getX() + getFinishIdentifier().xOffset, getExitPosition().getY() + getFinishIdentifier().yOffset, getExitPosition().getZ() + getFinishIdentifier().zOffset);
	}

	public Position getSouthWestPosition() {
		return new Position(getExitPosition().getX() + getMazeIdentifier().xOffset, getExitPosition().getY() + getMazeIdentifier().yOffset, getExitPosition().getZ() + getMazeIdentifier().zOffset);
	}

	public Position getGuardianPosition() {
		return script.getNpcs().closest(guardianID).getPosition();
	}

	public boolean isRoom() {

		RS2Object exit = script.getObjects().closest(exitID);

		if (exit != null) {

			RS2Object object = getIdentifyingObject(new Position(exit.getX() + getRoomIdentifier().xOffset, exit.getY() + getRoomIdentifier().yOffset, exit.getZ() + getRoomIdentifier().zOffset));
			if (object != null)
				return true;
		}

		return false;

	}

	public RS2Object getIdentifyingObject(Position pos) {
		for (RS2Object rs2o : script.getObjects().getAll()) {
			if (rs2o.getPosition().getX() == pos.getX() && rs2o.getPosition().getY() == pos.getY() && rs2o.getPosition().getZ() == pos.getZ() && rs2o.getName().equals(identifyingObjectName))
				return rs2o;
		}
		return null;
	}

}
