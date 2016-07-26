package com.house.infinitybot.tasks;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public abstract class Task<S extends Script> {
	private final S script;

	public Task(S script) {
		this.script = script;
	}

	public final boolean execute() {
		if (validate())
			process();
		return true;
	}

	public abstract boolean process();

	public abstract boolean validate();

	public S getScript() {
		return script;
	}

	public void sleepTill(int check, boolean condition) {
		new ConditionalSleep(check) {
			@Override
			public boolean condition() throws InterruptedException {
				return condition;
			}
		}.sleep();
	}

}