package rltut.screens;

import java.awt.event.KeyEvent;

import rltut.World;
import rltut.WorldBuilder;
import asciiPanel.AsciiPanel;

public class PlayScreen implements Screen {

	private World world;
	private int centerX;
	private int centerY;
	private int screenWidth;
	private int screenHeight;
	
	public PlayScreen() {
		screenWidth = 80;
		screenHeight = 21;
		createWorld();
	}
	
	/**
	 * Creates a world.
	 */
	private void createWorld() {
		world = new WorldBuilder(90, 31)
			.makeCaves()
			.build();
	}

	/**
	 * Tells us how far along the X axis we should scroll.
	 * Makes sure we never try to scroll too far to the left or right.
	 * @return length
	 */
	public int getScrollX() {
		return Math.max(0, Math.min(centerX - screenWidth / 2, world.width() - screenWidth));
	}
	
	/**
	 * Tells us how far along the Y axis we should scroll.
	 * Makes sure we never try to scroll too far to the top or bottom.
	 * @return length
	 */
	public int getScrollY() {
		return Math.max(0, Math.min(centerY - screenHeight / 2, world.height() - screenHeight));
	}
	
	/**
	 * Displays tiles.
	 * Takes a left and top to know which section of the world it should display
	 * @param terminal
	 * @param left
	 * @param top
	 */
	private void displayTiles(AsciiPanel terminal, int left, int top) {
		for (int x = 0; x < screenWidth; x++) {
			for (int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;
				
				terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
			}
		}
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();
		
		displayTiles(terminal, left, top);
		
		terminal.write('X', centerX - left, centerY - top); // Show where we're actually looking
	}

	/**
	 * A method to scroll.
	 * Makes sure we're never scrolling out of bounds.
	 * @param mx
	 * @param my
	 */
	private void scrollBy(int mx, int my) {
		centerX = Math.max(0, Math.min(centerX + mx, world.width() - 1));
		centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()) {
		case KeyEvent.VK_ESCAPE: return new LoseScreen();
		case KeyEvent.VK_ENTER: return new WinScreen();
		
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_H: scrollBy(-1, 0); break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_L: scrollBy(1, 0); break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_K: scrollBy(0, -1); break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_J: scrollBy(0, 1); break;
		case KeyEvent.VK_Y: scrollBy(-1,-1); break;
		case KeyEvent.VK_U: scrollBy(1,-1); break;
		case KeyEvent.VK_B: scrollBy(-1,1); break;
		case KeyEvent.VK_N: scrollBy(1,1); break;
		}
		
		return this;
	}

}
