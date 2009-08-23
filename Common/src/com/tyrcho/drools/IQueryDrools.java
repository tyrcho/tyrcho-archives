package com.tyrcho.drools;

import java.util.Collection;

/**
 * Moteur de requêtes Drools : encapsule les appels à Drools pour manipuler en
 * entrée et en sortie uniquement des collections d'objets (les faits).
 * 
 * @author daviot
 */
public interface IQueryDrools {

	/**
	 * Effectue la requête sur base des règles chargées et sur une nouvelle
	 * instance de la mémoire.
	 * 
	 * @param facts
	 *            les faits qui seront insérés
	 * @return les faits présents dans la mémoire après l'exécution
	 */
	public Collection<Object> query(Iterable<?> facts);

}