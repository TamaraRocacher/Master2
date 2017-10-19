package test;

import maps.MapsPackage;
import maps.Pedestrian;
import maps.PublicSpace;
import maps.Road;
import maps.Street;
import maps.Map;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.*;
import org.eclipse.emf.common.util.*;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;


public class test {
	static Map maMap = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
/*
		
		//Je charge l'instance map.xmi du méta-modèle maps.ecore
		Resource resource = chargerModele("model/Map1.xmi", MapsPackage.eINSTANCE);
		if (resource == null) System.err.println(" Erreur de chargement du modèle");
		//Instruction récupérant le modèle sous forme d'arbre à partir de la classe racine "map"
		Map maMap = (Map) resource.getContents().get(0);			
			
		*/
		Resource resource = chargerModele("model/Map1.xmi", MapsPackage.eINSTANCE);
		if (resource == null) System.err.println(" Erreur de chargement du modèle");
		
		maMap = (Map) resource.getContents().get(0);
		
		//Street pedSt = (Street) resource.getContents().get(1);
		System.out.println(maMap.getName());
		
		afficherStreet();
		afficherGrdPedStreet();
		adjStreet("wisteria");
		sqAdjStreet("hespéride");
		sqAdjStreet("round");
	}
	
	
	public static Resource chargerModele(String uri, EPackage pack) {
		   Resource resource = null;
		   try {
		      URI uriUri = URI.createURI(uri);
		      Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		      resource = (new ResourceSetImpl()).createResource(uriUri);
		      XMLResource.XMLMap xmlMap = new XMLMapImpl();
		      xmlMap.setNoNamespacePackage(pack);
		      java.util.Map options = new java.util.HashMap();
		      options.put(XMLResource.OPTION_XML_MAP, xmlMap);
		      resource.load(options);
		   }
		   catch(Exception e) {
		      System.err.println("ERREUR chargement du modèle : "+e);
		      e.printStackTrace();
		   }
		   return resource;
		}
	
	
	//Récupérer toutes les rues piétonnes (Pedetrian) dont la longueur dépasse 15m.
	public static void afficherGrdPedStreet() {
		System.out.println("Liste des rues pietonnes de + de 15m:");
		Collection<Road> roads = maMap.getRoads();
		for( Road r : roads){
			//System.out.println(r.getClass());
			if (r instanceof Pedestrian && r.getLenght()>=15 )
					System.out.println("\t"+r.getName()+" ("+r.getLenght()+"m)");
		}
		
		   
	}

	//Pour un nom de rue donné, trouver tous les noms des routes adjacentes.
	public static void adjStreet(String st) {
		System.out.println("Liste des rues adjacentes à "+st);
		Collection<Road> roads =  maMap.getRoads();
		EList<Road> adjroads = null;
		for( Road r : roads){
			if(r.getName().equals(st))
				 adjroads = r.getMeet();
		}
		for( Road ar : adjroads){
			System.out.println("\t"+ar.getName());
		}
		
		   
	}
	//Pour un nom de place (Square) donné, trouver tous les noms des routes la bordant.
	public static void sqAdjStreet(String sq) {
		System.out.println("Liste des rues adjacentes à "+sq);
		EList<PublicSpace> spaces =  maMap.getSpaces();
		EList<Road> adjroads = null;
		for(PublicSpace s : spaces){
			if(s.getName().equals(sq))
				 adjroads = s.getBorderedBy();
		}
		for( Road ar : adjroads){
			System.out.println("\t"+ar.getName());
		}
			   
	}
	//Récupérer et afficher les noms de toutes les rues (Street) d’une map donnée.
	public static void afficherStreet() {
		System.out.println("Liste des rues:");
		Collection<Road> roads = maMap.getRoads();
		for( Road r : roads){
			//System.out.println(r.getClass());
			if (r instanceof Street )
					System.out.println("\t"+r.getName());
		}
		
		   
		}
	
}