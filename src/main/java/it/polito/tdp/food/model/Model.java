package it.polito.tdp.food.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
Graph <Food,DefaultWeightedEdge> graph;
Map<Integer,Food> idFoods;
FoodDAO dao;
Simulatore sim;
List<FoodPeso> migliori=new ArrayList<>();
	
	public Model() {
		dao= new FoodDAO();
		idFoods=new HashMap<>();

}
	
	public void creaGrafo(int numeroPorzioni) {
	this.graph= new	SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);	
	this.dao.listAllFoods(idFoods);
	Graphs.addAllVertices(this.graph, this.dao.getVertici(idFoods, numeroPorzioni));
	
	for(Arco arco: this.dao.getArchi(idFoods, numeroPorzioni)) {
		if(arco.getCibo1()!=arco.getCibo2() && this.graph.containsVertex(arco.getCibo1()) && this.graph.containsVertex(arco.getCibo2())) {
	        
			if(arco.getPeso()>0)
				Graphs.addEdge(this.graph, arco.getCibo1(), arco.getCibo2(), arco.getPeso());
			else
				Graphs.addEdge(this.graph, arco.getCibo2(), arco.getCibo1(),Math.abs(arco.getPeso()));
			
			
		}
		}
	}
	
	
	public List<FoodPeso> getMigliori(Food origine){
		
		List<FoodPeso> migliori5=new ArrayList<>();

		for(Food f:Graphs.successorListOf(this.graph, origine)) {
           FoodPeso fp=new FoodPeso(f,this.graph.getEdgeWeight(this.graph.getEdge(origine, f)));
			migliori.add(fp);
		}
		Collections.sort(migliori);
       
		for(int i=0;i<5 && i<migliori.size();i++) {
			migliori5.add(migliori.get(i));
		}
		
		
		return migliori5;
		
	}
	 
	public  List<FoodPeso> getTutti(Food origine){
		return this.migliori;
	}
	
	
	
	public Set<Food> getTendina() {
		return this.graph.vertexSet();
	}
	
	public int getNumeroVertici() {
		return this.graph.vertexSet().size();
	}
	public int getNumeroArchi() {
		return this.graph.edgeSet().size();
	}
	
	public void simulazione(int nStazioni, Food origine) {
		this.sim=new Simulatore();
		sim.init(nStazioni, graph, origine, this);
		sim.run();
		
	}
	
	public Duration getTempoTotale() {
		return this.sim.getTempoTotale();
	}

	public int getCibiPreparati() {
		return this.sim.getCibiPreparati();
	}
	
}

