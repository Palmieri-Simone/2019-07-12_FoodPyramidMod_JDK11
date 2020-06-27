package it.polito.tdp.food.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;


public class Simulatore {
	// PARAMETRI DI SIMULAZIONE
	     int nStazioni=0;
	     List<Stazione> stazioni;
	     
	     LocalDateTime inizio=LocalDateTime.of(2020, 01, 01, 00, 00);
	    
	// OUTPUT DA CALCOLARE
	int cibiPreparati=0;
	Duration tempoTotale;
	
	     // STATO DEL SISTEMA
	     Graph <Food,DefaultWeightedEdge> graph;
	 	List<Food> cucinati=new ArrayList<>();
	 	Model model;

	     
	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;

	// INIZIALIZZAZIONE
	public void init(int nStazioni,Graph graph, Food origine, Model model) {
		this.nStazioni=nStazioni;
		this.stazioni=new ArrayList<>();
		this.queue = new PriorityQueue<>();
		this.graph=graph;
		this.model=model;
		
		for(int i=0;i<this.nStazioni;i++) {
			Stazione stazione=new Stazione(i);
		    stazioni.add(stazione);
		}		
		// generiamo eventi iniziali
	    
		for(Food f: Graphs.successorListOf(this.graph, origine)) {
			Duration durata=Duration.of((long) this.graph.getEdgeWeight(this.graph.getEdge(origine, f)),ChronoUnit.HOURS); 
			if(!this.cucinati.contains(f)) {

			for(Stazione s:stazioni) {
				 if(s.isLibera()) {
					 s.setF(f);
					 s.setLibera(false);
					 Event e=new Event(inizio.plus(durata),s);
					 this.queue.add(e);
					 this.cibiPreparati++;
					 cucinati.add(f);
					 System.out.println("ok");
					 break;

			 }
		 }
	}
			 
			
			
			 
		}
	
	
	}

	// ESECUZIONE
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}
	}
	

	private void processEvent(Event e) {
	
		e.getS().setLibera(true);
		
		Food ciboTerminato=e.getS().getF();
		
		List<FoodPeso> migliori=this.model.getTutti(ciboTerminato);
		
		for(FoodPeso fp : migliori ) {
			
				Duration durata=Duration.of(fp.getPeso().longValue(), ChronoUnit.SECONDS);
				System.out.println(durata.toSeconds());
					if(!this.cucinati.contains(fp.getFood())) {

				for(Stazione s:stazioni) {
					 if(s.isLibera()) {
						 s.setF(fp.getFood());
						 s.setLibera(false);
						 Event e1=new Event(e.getOrario().plus(durata),s);			
					     this.queue.add(e1);
					     this.cibiPreparati++;
						 cucinati.add(fp.getFood());
						 this.tempoTotale=Duration.between(inizio, e.getOrario());
						 break;
				}							

	     	}
	     
					}
		}

	
}

	public int getCibiPreparati() {
		return cibiPreparati;
	}


	public Duration getTempoTotale() {
		return tempoTotale;
	}

	
}
