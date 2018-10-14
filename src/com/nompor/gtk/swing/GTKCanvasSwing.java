package com.nompor.gtk.swing;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.nompor.gtk.GTKCanvas;
import com.nompor.gtk.GTKDefaultView;
import com.nompor.gtk.GTKView;
import com.nompor.gtk.awt.draw.GTKGraphicsAWT;
import com.nompor.gtk.awt.input.KeyCodeConverter;
import com.nompor.gtk.awt.input.MouseCodeConverter;
import com.nompor.gtk.draw.GTKGraphics;
import com.nompor.gtk.event.GTKKeyEvent;
import com.nompor.gtk.event.GTKMouseEvent;

public class GTKCanvasSwing extends GameView implements GTKView, GTKCanvas {

	private GTKView view = new GTKDefaultView();
	private GTKGraphicsAWT gtkg = new GTKGraphicsAWT(getGraphics());
	private GTKKeyEvent key = new GTKKeyEvent();
	private GTKMouseEvent mouse = new GTKMouseEvent();

	public void changeView(GTKView view) {
		GTKView oldView = this.view;
		if ( oldView != null ) oldView.stop();
		view.ready();
		this.view = view;
		if ( oldView != null ) oldView.end();
		view.start();
	}

	public void changeViewAnimation(GTKView view) {
	}


	public void changeViewDefaultAnimation(GTKView view) {
	}

	@Override
	public void draw(Graphics g) {
		gtkg.setGraphics(g);
		draw(gtkg);
	}

	@Override
	public void process() {
		view.process();
	}

	@Override
	public void draw(GTKGraphics g) {
		view.draw(gtkg);
	}

	@Override
	public void mouseDragged(GTKMouseEvent e) {
		view.mouseDragged(e);
	}

	@Override
	public void mouseMoved(GTKMouseEvent e) {
		view.mouseMoved(e);
	}

	@Override
	public void mouseClicked(GTKMouseEvent e) {
		view.mouseClicked(e);
	}

	@Override
	public void mousePressed(GTKMouseEvent e) {
		view.mousePressed(e);
	}

	@Override
	public void mouseReleased(GTKMouseEvent e) {
		view.mouseReleased(e);
	}
	@Override
	public void keyTyped(GTKKeyEvent e) {
		view.keyTyped(e);
	}
	@Override
	public void keyPressed(GTKKeyEvent e) {
		view.keyPressed(e);
	}
	@Override
	public void keyReleased(GTKKeyEvent e) {
		view.keyReleased(e);
	}
	@Override
	public void changeOutEnd() {
		view.changeOutEnd();
	}
	@Override
	public void changeOutStart() {
		view.changeOutStart();
	}
	@Override
	public void ready() {
		view.ready();
	}
	@Override
	public void stop() {
		view.stop();
	}
	@Override
	public void end() {
		view.end();
	}
	@Override
	public void changeInStart() {
		view.changeInStart();
	}
	@Override
	public void start() {
		view.start();
	}
	@Override
	public void changeInEnd() {
		view.changeInEnd();
	}

	public void mouseDragged(MouseEvent e) {
		mouseDragged(toMouseEvent(e));
	}
	public void mouseMoved(MouseEvent e) {
		mouseMoved(toMouseEvent(e));
	}
	public void mouseClicked(MouseEvent e) {
		mouseClicked(toMouseEvent(e));
	}
	public void mousePressed(MouseEvent e) {
		mousePressed(toMouseEvent(e));
	}
	public void mouseReleased(MouseEvent e) {
		mouseReleased(toMouseEvent(e));
	}
	public void keyTyped(KeyEvent e) {
		keyTyped(toKeyEvent(e));
	}
	public void keyPressed(KeyEvent e) {
		keyPressed(toKeyEvent(e));
	}
	public void keyReleased(KeyEvent e) {
		keyReleased(toKeyEvent(e));
	}

	private GTKMouseEvent toMouseEvent(MouseEvent e) {
		mouse.x = (int) e.getX();
		mouse.y = (int) e.getY();
		mouse.code = MouseCodeConverter.toGTKCode(e.getButton());
		return mouse;
	}

	private GTKKeyEvent toKeyEvent(KeyEvent e) {
		key.code = KeyCodeConverter.toGTKCode(e.getKeyCode());
		return key;
	}

	@Override
	public GTKView getGTKView() {
		return view;
	}
}
