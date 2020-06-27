package it.polito.tdp.food.model;

public class FoodPeso implements Comparable<FoodPeso>{
	
private Food food;
private Double peso;






public FoodPeso(Food food, Double peso) {
	super();
	this.food = food;
	this.peso = peso;
}






public Food getFood() {
	return food;
}






public void setFood(Food food) {
	this.food = food;
}






public Double getPeso() {
	return peso;
}






public void setPeso(Double peso) {
	this.peso = peso;
}






@Override

public int compareTo(FoodPeso o) {
	return this.peso.compareTo(o.peso);
}






@Override
public String toString() {
	return food + "   " + peso + "\n";
}





}
