package com.house.infinitybot;

import java.util.ArrayList;

import org.osbot.rs07.script.Script;

import com.house.infinitybot.rooms.RoomManager;
import com.house.infinitybot.tasks.Task;

@SuppressWarnings({ "rawtypes" })
public abstract class TaskScript extends Script {

	public static boolean started = true;

	private final ArrayList<Task> tasks = new ArrayList<>();
	protected RoomManager roomManager;

	@Override
	public int onLoop() {

		if (started) {
			
			roomManager.update();

			if (tasks.size() > 0) {

				for (Task task : tasks) {
					task.execute();
				}

			} else
				log("No tasks found!");

		}

		return 600;
	}

	protected final void addTask(Task task) {
		tasks.add(task);
	}
}
