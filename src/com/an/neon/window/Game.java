package com.an.neon.window;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import com.an.neon.framework.KeyInput;
import com.an.neon.framework.ObjectId;
import com.an.neon.objects.Player;

public class Game extends Canvas implements Runnable{


	private static final long serialVersionUID = -790904758956689328L;

	private boolean running = false;
	private Thread thread;
	public static int WIDTH, HEIGHT;
	
	//Object
	Handler handler;
	Random rand = new Random();
	
	private void init() {
		WIDTH = getWidth();
		HEIGHT = getHeight();
		handler = new Handler();
		handler.addObject(new Player(100,100,handler,ObjectId.Player));
		handler.createLevel();
		this.addKeyListener(new KeyInput(handler));
	}
	
	
	public synchronized void start() { //using synchronized when using thread
		if(running)    //fail safe method
			return;
		
		running = true;
		thread= new Thread(this);
		thread.start();
	}
	public void run() {   //running the game at 60 ticks / second
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS"+frames+"TICKS: "+updates);
				frames = 0;
				updates = 0;
			}
		}
	}
	private void tick() {
		handler.tick();
	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy(); //it will automatically equals itself to null
		if(bs == null) {
			this.createBufferStrategy(3); //loading another window before the first window is done
		
			return;
		}
		Graphics g = bs.getDrawGraphics();
		///////////////////////////////////
		//Here you can draw for the game
		
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		handler.render(g);
		///////////////////////////////////
		g.dispose();
		bs.show();
		
	}
	public static void main(String args[]) {
		new Window(800,600,"Neon Platform Game Prototype", new Game());
	}
}
