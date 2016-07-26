package com.house.infinitybot.tasks;

import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.script.Script;

import com.house.infinitybot.rooms.RoomManager;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class RoomTask extends Task {

	protected RoomManager roomManager;

	public RoomTask(Script script, RoomManager roomManager) {
		super(script);
		this.roomManager = roomManager;
	}

	@Override
	public boolean process() {
		return false;
	}

	@Override
	public boolean validate() {
		return false;
	}

	public void Type(int ke) {
		try {
			getScript().getKeyboard().pressKey(ke);
			MethodProvider.sleep(900);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
