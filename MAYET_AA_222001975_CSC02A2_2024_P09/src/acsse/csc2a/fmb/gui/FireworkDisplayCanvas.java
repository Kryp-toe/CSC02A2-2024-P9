/**
 * @author Mr J Orfao
 * @version P07
 * Custom FireworkDisplayCanvas that will Draw the FireworkLayout
 */
package acsse.csc2a.fmb.gui;

import java.util.ArrayList;

import acsse.csc2a.fmb.model.Entity;
import acsse.csc2a.fmb.model.Firework;
import acsse.csc2a.fmb.model.FireworkEntity;
import acsse.csc2a.fmb.model.FountainFirework;
import acsse.csc2a.fmb.model.RocketFirework;
import acsse.csc2a.fmb.particles.FountainParticleSystem;
import acsse.csc2a.fmb.particles.RocketParticleSystem;
import acsse.csc2a.fmb.pattern.EntityVisitor;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FireworkDisplayCanvas extends Canvas {
	// Attributes
	private ArrayList<FireworkEntity> entities = null;
	private static final int NUM_ROWS = 15;
	private static final int CELL_SIZE = 50;
	private static final int SIZE = NUM_ROWS * CELL_SIZE;
	private EntityVisitor visitor = null;
	RocketParticleSystem rocketSystem = new RocketParticleSystem();
	FountainParticleSystem fountainSystem = new FountainParticleSystem();

	/**
	 * Default constructor to set the size of the canvas
	 */
	public FireworkDisplayCanvas() {
		setWidth(SIZE);
		setHeight(SIZE);
		// Create a Visitor to draw the FireworkEntities
		visitor = new EntityVisitor(SIZE, CELL_SIZE);
	}

	/**
	 *
	 * Method to set the FireworkEntity array for drawing
	 *
	 * @param fireworkEntities ArrayList of FireworkEntity objects
	 */
	public void setFireworkEntities(ArrayList<FireworkEntity> fireworkEntities) {
		this.entities = fireworkEntities;
		// Redraw the canvas to show the FireworkDisplayCanvas objects
		redrawCanvas();

		// add the fireworks to their respective particle system
		for (Entity ent : entities) {

			if (ent instanceof FireworkEntity fwEnt) {
				Firework fw = fwEnt.getFirework();
				if (fw instanceof RocketFirework rocket) {
					rocketSystem.addFirework(ent.getXLocation() * CELL_SIZE, (NUM_ROWS - 1) * CELL_SIZE, rocket);
				} else if (fw instanceof FountainFirework fountain) {
					fountainSystem.addFirework(ent.getXLocation() * CELL_SIZE, (NUM_ROWS - 1) * CELL_SIZE, fountain);
				}
			}
		}
	}

	/**
	 * Method to draw FireworkEntity objects on the canvas
	 */
	public void redrawCanvas() {
		// GraphicsContext
		GraphicsContext gc = getGraphicsContext2D();
		drawBackGround(gc, false);
		// provide the visitor with the context
		visitor.setGraphicsContext(gc);

		// Draw each FireworkEntity using the visitor
		for (Entity entity : entities) {
			entity.accept(visitor);
		}
	}

	private void drawBackGround(GraphicsContext gc, boolean isNightTime) {
		// clear the canvas

		gc.clearRect(0, 0, getWidth(), getHeight());
		if (isNightTime) {
			gc.setFill(Color.rgb(0, 0, 0, 1));
			gc.fillRect(0, 0, getWidth(), getHeight());
			gc.setStroke(Color.WHITE);
		} else {
			gc.setStroke(Color.BLACK);
		}

		// Draw the grid

		// Begin drawing Grid
		for (int r = 0; r < 15; r++) {
			for (int c = 0; c < 15; c++) {
				// Draw Rectangle
				// you need to multiply the row and column by cell_size to get to the correct
				// pixel location
				gc.strokeRect(c * CELL_SIZE, r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
			}
		}
	}

	int numFrames = 0;

	public void startAnimation() {
		GraphicsContext gc = getGraphicsContext2D();

		new AnimationTimer() {
			@Override
			public void handle(long now) {

				double currentTime = now / 1e9; // Convert nanoseconds to seconds

				// Clear the entire canvas
				gc.clearRect(0, 0, getWidth(), getHeight());

				// Set the background to fully opaque black each frame
				gc.setFill(Color.BLACK);
				gc.fillRect(0, 0, getWidth(), getHeight());

				// Add a transparent black rectangle to create a fading trail effect
				// Adjust the alpha as necessary to balance visibility and fading
				gc.setFill(Color.rgb(0, 0, 0, 0.1));
				gc.fillRect(0, 0, getWidth(), getHeight());

				// Update and show systems
				rocketSystem.updateAndShow(gc, currentTime);
				fountainSystem.updateAndShow(gc, currentTime);

				// Optional: Clear the canvas if both systems are finished
				if (rocketSystem.isFinished() && fountainSystem.isFinished()) {
					gc.clearRect(0, 0, getWidth(), getHeight());
				}
			}

		}.start();
	}
}