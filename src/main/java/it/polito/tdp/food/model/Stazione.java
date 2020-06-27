package it.polito.tdp.food.model;

public class Stazione {
private int id;
private Food f;
private boolean libera;


public Stazione(int i) {
      this.id=i;
	this.libera = true;
}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public Food getF() {
	return f;
}


public void setF(Food f) {
	this.f = f;
}


public boolean isLibera() {
	return libera;
}


public void setLibera(boolean libera) {
	this.libera = libera;
}





}
