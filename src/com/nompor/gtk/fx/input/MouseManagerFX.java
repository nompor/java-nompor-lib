package com.nompor.gtk.fx.input;

import java.util.HashMap;

import com.nompor.gtk.input.AbstractMouseManager;

import javafx.scene.input.MouseButton;

public class MouseManagerFX extends AbstractMouseManager<MouseButton> {

	public MouseManagerFX() {
		super(new HashMap<>());
	}

}
