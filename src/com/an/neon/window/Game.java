package com.an.neon.window;

import java.awt.Canvas;

public class Game extends Canvas implements Runnable{


	private static final long serialVersionUID = -790904758956689328L;

	public void run() {
		
	}
	public static void main(String args[]) {
		new Window(800,600,"Neon Platform Game Prototype", new Game());
	}
}
