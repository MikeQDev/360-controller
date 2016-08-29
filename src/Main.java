import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Main {
	public Main() {
		Controller[] cList = ControllerEnvironment.getDefaultEnvironment()
				.getControllers();
		Controller c = null;
		for (Controller x : cList)
			if (x.getName().contains("XBOX")) {
				c = x;
				break;
			}

		Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		Point mouseInfo = null;
		int x, y;
		while (true) {
			mouseInfo = MouseInfo.getPointerInfo().getLocation();
			x = mouseInfo.x;
			y = mouseInfo.y;
			c.poll();
			EventQueue queue = c.getEventQueue();
			Event event = new Event();
			while (queue.getNextEvent(event)) {
				Component comp = event.getComponent();
				float value = event.getValue();
				float data = comp.getPollData();

				if (comp.getName().equals("Hat Switch")) {
					if (data == Component.POV.OFF) {
						System.out.println(comp + "off");
					} else if (data == Component.POV.UP) {
						System.out.println(comp + "UP");
					} else if (data == Component.POV.UP_RIGHT) {
						System.out.println(comp + "UP+RIGHT");
					} else if (data == Component.POV.RIGHT) {
						System.out.println(comp + "RIGHT");
					} else if (data == Component.POV.DOWN_RIGHT) {
						System.out.println(comp + "DOWN+RIGHT");
					} else if (data == Component.POV.DOWN) {
						System.out.println(comp + "DOWN");
					} else if (data == Component.POV.DOWN_LEFT) {
						System.out.println(comp + "DOWN+LEFT");
					} else if (data == Component.POV.LEFT) {
						System.out.println(comp + "LEFT");
					} else if (data == Component.POV.UP_LEFT) {
						System.out.println(comp + "UP+LEFT");
					} else { // shoudl never happen
						System.out.println(comp + "error");
					}
				} else if (comp.isAnalog()) {
					value *= 100;
					System.out.println(comp.getName() + " " + value);
					if (comp.getName().equals("X Axis")) {
						if (value < 0) {
							// move left
							r.mouseMove((int) (x - value), y);
						} else if (value > 0) {
							// move right
							r.mouseMove((int) (x + value), y);
						} else {
							// nothing?
						}
					} else if (comp.getName().equals("X Axis")) {
						if (value < 0) {
							// move up
							r.mouseMove(x, (int) (y - value));
						} else if (value > 0) {
							// move down
							r.mouseMove(x, (int) (y + value));
						} else {
							// nothing?
						}
					}
				} else {
					if (value == 1.0f) {
						System.out.println(comp.getName() + " " + "On");
					} else {
						System.out.println(comp.getName() + " " + "Off");
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new Main();
	}

}
