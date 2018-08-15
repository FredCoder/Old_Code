package com.fren.plane.model;

public interface Fireable {
	public void onFire(int power);
	public void addMissile(Missile m);
	public void removeMissile(Missile m);
}
