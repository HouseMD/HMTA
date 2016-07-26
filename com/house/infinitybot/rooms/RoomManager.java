package com.house.infinitybot.rooms;

import java.util.ArrayList;

import org.osbot.rs07.script.Script;

import com.house.infinitybot.rooms.identifiers.FinishIdentifier;
import com.house.infinitybot.rooms.identifiers.MazeIdentifier;
import com.house.infinitybot.rooms.identifiers.RoomIdentifier;

public class RoomManager {

	Script script;

	Room currentRoom = null;

	ArrayList<Room> rooms = new ArrayList<Room>();

	public RoomManager(Script script) {
		this.script = script;
	}

	public void update() {

		if (currentRoom == null)
			doUpdate();

		else if (currentRoom != null && !currentRoom.isRoom())
			doUpdate();

	}

	public void init() {

		addRoom(new HubRoom(script));
		addRoom(new TelekineticRoom(script, 1, "Telekinetic Room 1", "Old Bookshelf", new RoomIdentifier(11, 5, 0), new FinishIdentifier(6, 7, 0), new MazeIdentifier(-3, 7, 0, 10, 10)));
		addRoom(new TelekineticRoom(script, 2, "Telekinetic Room 2", "Chair", new RoomIdentifier(3, -4, 0), new FinishIdentifier(-22, -5, 0), new MazeIdentifier(-22, -5, 0, 10, 10)));
		addRoom(new TelekineticRoom(script, 3, "Telekinetic Room 3", "Chair", new RoomIdentifier(3, -2, 0), new FinishIdentifier(2, -12, 0), new MazeIdentifier(-7, -21, 0, 10, 10)));
		
	}

	public void doUpdate() {

		Room newCurrentRoom = null;

		for (Room room : rooms) {

			switch (room.getRoomType()) {
			case HUB:
				HubRoom hroom = (HubRoom) room;
				if (hroom.isRoom()) {
					newCurrentRoom = hroom;
				}
				break;
			case TELEKINETIC:
				TelekineticRoom troom = (TelekineticRoom) room;
				if (troom.isRoom()) {
					newCurrentRoom = troom;
				}
				break;
			case ALCHEMIST:
				break;
			case ENCHANTMENT:
				break;
			case GRAVEYARD:
				break;
			default:
				currentRoom = null;
				break;
			}

		}

		if (newCurrentRoom != null) {
			currentRoom = newCurrentRoom;
		} else
			currentRoom = null;

	}

	public void addRoom(Room room) {
		rooms.add(room);
	}

	public Room getRoom(int id) {
		for (Room room : rooms) {
			if (room.getID() == id) {
				return room;
			}
		}
		return null;
	}

	public Room getCurrentRoom() {
		return currentRoom;
	}

	public boolean isCurrentRoom(int id) {
		if (currentRoom != null)
			return currentRoom.getID() == id;
		return false;
	}

}
