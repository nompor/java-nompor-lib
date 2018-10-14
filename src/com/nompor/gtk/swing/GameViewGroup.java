package com.nompor.gtk.swing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

public class GameViewGroup extends GameView {
	private LinkedHashSet<GameView> views;

	public GameViewGroup(GameView... viewList) {
		for ( GameView view : viewList ) views.add(view);
	}
	public GameViewGroup() {
		this(new GameView[0]);
	}

	public void addView(GameView view) {
		views.add(view);
	}

	public void removeView(GameView view) {
		views.remove(view);
	}

	public void clear() {
		views.clear();
	}

	public Stream<GameView> stream(){
		return views.stream();
	}

	public void process() {stream().forEach(v->v.process());}
	public void draw(Graphics g) {stream().forEach(v->v.draw(g));}
	public void mouseDragged(MouseEvent e) {stream().forEach(v->v.mouseDragged(e));}
	public void mouseMoved(MouseEvent e) {stream().forEach(v->v.mouseMoved(e));}
	public void mouseClicked(MouseEvent e) {stream().forEach(v->v.mouseClicked(e));}
	public void mousePressed(MouseEvent e) {stream().forEach(v->v.mousePressed(e));}
	public void mouseReleased(MouseEvent e) {stream().forEach(v->v.mouseReleased(e));}
	public void mouseEntered(MouseEvent e) {stream().forEach(v->v.mouseEntered(e));}
	public void mouseExited(MouseEvent e) {stream().forEach(v->v.mouseExited(e));}
	public void keyTyped(KeyEvent e) {stream().forEach(v->v.keyTyped(e));}
	public void keyPressed(KeyEvent e) {stream().forEach(v->v.keyPressed(e));}
	public void keyReleased(KeyEvent e) {stream().forEach(v->v.keyReleased(e));}
	public void windowActivated(WindowEvent e) {stream().forEach(v->v.windowActivated(e));}
	public void windowClosed(WindowEvent e) {stream().forEach(v->v.windowClosed(e));}
	public void windowClosing(WindowEvent e) {stream().forEach(v->v.windowClosing(e));}
	public void windowDeactivated(WindowEvent e) {stream().forEach(v->v.windowDeactivated(e));}
	public void windowDeiconified(WindowEvent e) {stream().forEach(v->v.windowDeiconified(e));}
	public void windowIconified(WindowEvent e) {stream().forEach(v->v.windowIconified(e));}
	public void windowOpened(WindowEvent e) {stream().forEach(v->v.windowOpened(e));}
	public void start() {stream().forEach(v->v.start());}
	public void end() {stream().forEach(v->v.end());}
}
