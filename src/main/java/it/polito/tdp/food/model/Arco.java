package it.polito.tdp.food.model;

public class Arco {
private Food cibo1;
private Food cibo2;
private Double peso;




public Arco(Food cibo1, Food cibo2, Double peso) {
	this.cibo1 = cibo1;
	this.cibo2 = cibo2;
	this.peso = peso;
}


public Food getCibo1() {
	return cibo1;
}
public Food getCibo2() {
	return cibo2;
}
public Double getPeso() {
	return peso;
}




}
