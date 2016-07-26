package com.house.infinitybot;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.ScriptManifest;

import com.house.infinitybot.rooms.Room;
import com.house.infinitybot.rooms.RoomManager;
import com.house.infinitybot.rooms.TelekineticRoom;
import com.house.infinitybot.rooms.hub.Hub_Room;
import com.house.infinitybot.tasks.telekinetic.Telekinetic_Room1;
import com.house.infinitybot.tasks.telekinetic.Telekinetic_Room2;
import com.house.infinitybot.tasks.telekinetic.Telekinetic_Room3;

@ScriptManifest(name = "HMTA", author = "House", version = 1.0, info = "", logo = "")
public class HMTA extends TaskScript {

	boolean debug = true;

	public static ArrayList<Position> pp = new ArrayList<Position>();

	ExecutorService taskExecutor = Executors.newFixedThreadPool(1);

	@Override
	public void onStart() {

		roomManager = new RoomManager(this);
		roomManager.init();

		addTask(new Hub_Room(this, roomManager));
		addTask(new Telekinetic_Room1(this, roomManager));
		addTask(new Telekinetic_Room2(this, roomManager));
		addTask(new Telekinetic_Room3(this, roomManager));

	}

	@Override
	public void onExit() {
		taskExecutor.shutdownNow();
	}

	@Override
	public void onPaint(Graphics2D g) {

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		Room cr = roomManager.getCurrentRoom();

		if (cr != null) {

			g.setColor(Color.WHITE);
			g.drawString("Current Room: " + cr.getName(), 10, 330);

		}

		if (debug) {

			TelekineticRoom currentRoom = (TelekineticRoom) roomManager.getCurrentRoom();

			if (currentRoom != null) {

				NPC gg = getNpcs().closest(6777);

				if (gg != null) {
					Position posg = gg.getPosition();
					Position posE = ((TelekineticRoom) roomManager.getCurrentRoom()).getExitPosition();

					g.drawString("diff: " + (posg.getX() - posE.getX()) + "/" + (posg.getY() - posE.getY()) + "/" + (posg.getZ() - posE.getZ()), 10, 310);

				}

				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

				g.setColor(Color.YELLOW);
				drawArea(g, currentRoom.northLeft, currentRoom.northRight);
				drawArea(g, currentRoom.southLeft, currentRoom.southRight);
				drawArea(g, currentRoom.eastTop, currentRoom.eastBottom);
				drawArea(g, currentRoom.westTop, currentRoom.westBottom);

				if (pp.size() > 0) {
					g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.10f));
					g.setColor(Color.PINK);
					for (Position p : pp) {
						g.fillPolygon(p.getPolygon(getBot()));
					}
				}

				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));

				g.setColor(Color.GREEN);
				g.fillPolygon(((TelekineticRoom) roomManager.getCurrentRoom()).getFinishPosition().getPolygon(getBot()));
				g.setColor(Color.BLACK);
				g.fillPolygon(((TelekineticRoom) roomManager.getCurrentRoom()).getSouthWestPosition().getPolygon(getBot()));
				if (((TelekineticRoom) roomManager.getCurrentRoom()).getGuardianPosition() != null) {
					g.setColor(Color.RED);
					g.fillPolygon(((TelekineticRoom) roomManager.getCurrentRoom()).getGuardianPosition().getPolygon(getBot()));
				}

			}
		}

		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

	}

	public void drawArea(Graphics2D g, Position p1, Position p2) {
		Area a = new Area(p1, p2);
		for (Position p : a.getPositions()) {
			g.fillPolygon(p.getPolygon(getBot()));
		}
	}

}
