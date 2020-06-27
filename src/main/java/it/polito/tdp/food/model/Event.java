package it.polito.tdp.food.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
     
	private LocalDateTime orario;
	private Stazione s;
	
	
	
	public Event(LocalDateTime orario, Stazione s) {
		super();
		this.orario = orario;
		this.s = s;
	}
	
	
	



	public LocalDateTime getOrario() {
		return orario;
	}






	public Stazione getS() {
		return s;
	}






	@Override
	public int compareTo(Event o) {
		return this.orario.compareTo(o.orario);
	}

}
