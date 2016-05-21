package isg.project.core.gfx.navigable;

import playn.core.Mouse.ButtonEvent;

// rappresenta un generico stato di gioco.
public interface Navigable 
{
	Navigable onMouseDown(ButtonEvent event);
	void show();
	void hide();
	void update(int delta);
}

