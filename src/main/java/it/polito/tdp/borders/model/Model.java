package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao;
	private SimpleGraph<Country, DefaultEdge> graph;
	private Map<Integer, Country> idMap;
	private ConnectivityInspector<Country, DefaultEdge> ci;
	private Map<Country, Country> visita = new HashMap<>();

	public Model() {
		dao = new BordersDAO();
		this.idMap = new HashMap<>();
	}

	/**
	 * Crea il grafo rispettando (spero) i vincoli, ovvero aggiunge i vertici e gli archi solo nel caso in cui l'anno
	 * sia minore ecc.. Doppio ciclo for sulla stessa lista (non mi piace)...
	 * @param anno
	 */
	public void creaGrafo(int anno) {
		this.graph = new SimpleGraph<>(DefaultEdge.class);
		
		//Mappa vuota che viene riempita con tutti i paesi
		this.dao.loadAllCountries(idMap);
		
		List<Border> borders = this.dao.getCountryPairs(anno);
		
		/*for(Border b : borders) {
			//Aggiungo solo i vertici che mi servono, prendi oggetto Country dalla mappa a cui corrisponde il codice in B
			this.graph.addVertex(idMap.get(b.getC1()));
		}*/
		Graphs.addAllVertices(this.graph, idMap.values());
		
		for(Border b : borders) {
			this.graph.addEdge(idMap.get(b.getC1()), idMap.get(b.getC2()));
		}
		
		//Devo costruirlo qua perche' gli devo passare un grafo riempito, non vuoto
		this.ci = new ConnectivityInspector<Country, DefaultEdge>(this.graph);
	}
	
	/*public String boh() {
		return this.graph.edgeSet().toString();
	}*/
	
	public String elencoStati() {
		String s = "";
		
		for(Country c : this.graph.vertexSet())
			s += c.toString()+", numero paesi confinanti(via terra): "+this.graph.degreeOf(c)+"\n";
		
		return s;
	}
	
	/**
	 * Il metodo connectedSets() ritorna una lista con tutti i set di componenti connesse nel grafo... quindi
	 * la size della List dovrebbe corrispondere al numero di componenti connesse
	 * @return
	 */
	public int numComponentiConnesse() {
		return this.ci.connectedSets().size();
	}
	
	public Collection<Country> allCountries() {
		return this.graph.vertexSet();
	}
	
	public int numVertici() {
		return this.graph.vertexSet().size();
	}
	
	public int numArchi() {
		return this.graph.edgeSet().size();
	}
	
	/**
	 * Trova tutti i paesi raggiungibili partendo da quello indicato come partenza. Metodo che utilizza il
	 * BreadthFirstIterator e poi il TrasversalListener
	 * @param partenza
	 * @return
	 */
	public List<Country> trovaVicini(Country partenza) {
		visita.put(partenza, null);
		List<Country> vicini = new ArrayList<>();
		
		GraphIterator<Country, DefaultEdge> bfv = new BreadthFirstIterator<>(graph);
		bfv.addTraversalListener(new TraversalListener<Country, DefaultEdge>() {
			
			@Override
			public void vertexTraversed(VertexTraversalEvent<Country> e) {}
			
			@Override
			public void vertexFinished(VertexTraversalEvent<Country> e) {}
			
			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
				DefaultEdge edge = e.getEdge(); //Arco attraversato
				Country c1 = graph.getEdgeSource(edge);
				Country c2 = graph.getEdgeTarget(edge);
				
				if(!visita.containsKey(c2) && visita.containsKey(c1)) {
					//Allora c2 e' raggiungibile da c1
					visita.put(c2, c1);
				}
				else if(!visita.containsKey(c1) && visita.containsKey(c2)) {
					//Allora in questo caso significa che c1 e' raggiungibile da c2
					visita.put(c1, c2);
				}
			}
			
			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {}
			
			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {}
		});
		
		while(bfv.hasNext()) {
			bfv.next();
		}
		
		//Per farla ordinata (?)
		for(Country c : visita.keySet())
			vicini.add(c);
		
		return vicini;
	}
	
	//Non e' la cosa richiesta, restituisce solo i paesi confinanti
	public List<Country> confinanti(Country partenza) {
		List<Country> vicini = new ArrayList<>();
		vicini = Graphs.neighborListOf(this.graph, partenza);
		return vicini;
	}
	
	//Metodo iterativo da implementare, in che modo?
	public Set<Country> trovaVicini2(Country partenza) {
		//List<Country> vicini = new ArrayList<>();
		//List<Country> daVisitare = new ArrayList<>();
		
		Set<Country> daVisitare = ci.connectedSetOf(partenza);
		
		//daVisitare.add(partenza);
		//daVisitare.addAll(Graphs.neighborListOf(this.graph, partenza));
		
		//return vicini;
		return daVisitare;
	}
	
}
