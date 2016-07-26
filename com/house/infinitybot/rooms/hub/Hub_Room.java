package com.house.infinitybot.rooms.hub;

import org.osbot.rs07.script.Script;

import com.house.infinitybot.rooms.RoomManager;
import com.house.infinitybot.tasks.RoomTask;

public class Hub_Room extends RoomTask {

	public Hub_Room(Script script, RoomManager roomManager) {
		super(script, roomManager);
	}

	@Override
	public boolean process() {

		return false;
	}

	@Override
	public boolean validate() {
		return roomManager.isCurrentRoom(0);
	}

}
