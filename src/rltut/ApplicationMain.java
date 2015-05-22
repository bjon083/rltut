package rltut;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import rltut.screens.Screen;
import rltut.screens.StartScreen;
import asciiPanel.AsciiPanel;

/**
 * Its only responsibility is to create a window
 * and delegate input/output to other things.
 * @author Benny
 *
 */
public class ApplicationMain extends JFrame implements KeyListener {
	private static final long serialVersionUID = -347007960371623888L;
	
	private AsciiPanel terminal;
	private Screen screen;
	
	public ApplicationMain() {
		super();
		terminal = new AsciiPanel();
		add(terminal);
		pack();
		screen = new StartScreen();
		addKeyListener(this);
		repaint();
	}

	public void repaint(){
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }
	
	@Override
	public void keyPressed(KeyEvent e) {
		screen = screen.respondToUserInput(e);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }
	
	public static void main(String[] args) {
		ApplicationMain app = new ApplicationMain();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
