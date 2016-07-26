package com.house.infinitybot.rooms;

import org.osbot.rs07.script.Script;

public class Room {

	Script script;

	int id;
	RoomType roomType;
	String name;

	public Room(Script script, int id, RoomType roomType, String name) {
		this.script = script;
		this.id = id;
		this.roomType = roomType;
		this.name = name;
	}

	public int getID() {
		return id;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public String getName() {
		return name;
	}

	public boolean isRoom() {
		return false;
	}

}
