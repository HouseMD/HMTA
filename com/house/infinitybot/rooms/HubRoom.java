package com.house.infinitybot.rooms;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

public class HubRoom extends Room {

	int portInID = 23673;

	public HubRoom(Script script) {
		super(script, 0, RoomType.HUB, "Hub Room");
	}

	public boolean isRoom() {

		RS2Object portIn = script.getObjects().closest(portInID);

		if (portIn != null)
			return true;

		return false;

	}

}
