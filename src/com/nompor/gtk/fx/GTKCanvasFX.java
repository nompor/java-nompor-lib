package com.nompor.gtk.fx;

import com.nompor.gtk.GTKCanvas;
import com.nompor.gtk.GTKDefaultView;
import com.nompor.gtk.GTKView;
import com.nompor.gtk.draw.GTKGraphics;
import com.nompor.gtk.event.GTKKeyEvent;
import com.nompor.gtk.event.GTKMouseEvent;
import com.nompor.gtk.fx.draw.GTKGraphicsFX;
import com.nompor.gtk.fx.input.KeyCodeConverterFX;
import com.nompor.gtk.fx.input.MouseCodeConverterFX;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GTKCanvasFX extends GameCanvasFX implements GTKView, GTKCanvas{

	private GTKView view;
	private GTKGraphicsFX gtkg = new GTKGraphicsFX(this);
	private GTKKeyEvent key = new GTKKeyEvent();
	private GTKMouseEvent mouse = new GTKMouseEvent();

	public GTKCanvasFX(double width, double height) {
		this(new GTKDefaultView(), width, height);
	}
	public GTKCanvasFX(GTKView view, double width, double height) {
		super(width, height);
		this.view = view;
	}

	public void changeView(GTKView view) {
		GTKView oldView = this.view;
		if ( oldView != null ) oldView.stop();
		view.ready();
		this.view = view;
		if ( oldView != null ) oldView.end();
		view.start();
	}

	@Override
	public void draw(GraphicsContext g) {
		super.draw(g);
		gtkg.setGraphics(g);
		draw(gtkg);
	}

	@Override
	public void process() {
		super.process();
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
		mouse.code = MouseCodeConverterFX.toGTKCode(e.getButton());
		return mouse;
	}

	private GTKKeyEvent toKeyEvent(KeyEvent e) {
		key.code = KeyCodeConverterFX.toGTKCode(e.getCode());
		return key;
	}
	@Override
	public GTKView getGTKView() {
		return view;
	}
}
