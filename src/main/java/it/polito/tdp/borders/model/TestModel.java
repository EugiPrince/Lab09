package it.polito.tdp.borders.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();

		System.out.println("TestModel -- TODO");
		
//		System.out.println("Creo il grafo relativo al 2000");
//		model.createGraph(2000);
		
//		List<Country> countries = model.getCountries();
//		System.out.format("Trovate %d nazioni\n", countries.size());

//		System.out.format("Numero componenti connesse: %d\n", model.getNumberOfConnectedComponents());
		
//		Map<Country, Integer> stats = model.getCountryCounts();
//		for (Country country : stats.keySet())
//			System.out.format("%s %d\n", country, stats.get(country));		
		
		System.out.println("Grafo relativo al 2000.\n");
		model.creaGrafo(2000);
		System.out.println("Numero vertici: "+model.numVertici()+" Numero archi: "+model.numArchi()+"\n");
		//System.out.println(model.boh());
		System.out.println("Elenco dei paesi:\n"+model.elencoStati());
		System.out.println("Numero componenti connesse: "+model.numComponentiConnesse());
		
		/*
		Country partenza = new Country(2, "USA", "United States of America");
		Map<Country, Country> mappa = model.trovaVicini(partenza);
		for(Country c : mappa.keySet()) {
			System.out.format("%s -> %s\n", c, mappa.get(c));
		}
		*/
		
		Country partenza = new Country(2, "USA", "United States of America");
		//Country partenza = new Country(20, "CAN", "Canada");
		//Country partenza = new Country(135, "PER", "Peru");
		//Country partenza = new Country(220, "FRN", "France");
		List<Country> vicini = model.trovaVicini(partenza);
		for(Country c : vicini)
			System.out.println(c+"\n");
		System.out.println(vicini.size());
		
		/*
		List<Country> vicini = model.trovaVicini2(partenza);
		for(Country c : vicini)
			System.out.println(c+"\n");
		*/
		
		Set<Country> prova = model.trovaVicini2(partenza);
		for(Country c : prova)
			System.out.println(c+"\n");
		System.out.println(prova.size());
	}

}
