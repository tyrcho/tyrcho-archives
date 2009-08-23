package com.tyrcho.drools;

import java.util.Collection;

/**
 * Moteur de requ�tes Drools : encapsule les appels � Drools pour manipuler en
 * entr�e et en sortie uniquement des collections d'objets (les faits).
 * 
 * @author daviot
 */
public interface IQueryDrools {

	/**
	 * Effectue la requ�te sur base des r�gles charg�es et sur une nouvelle
	 * instance de la m�moire.
	 * 
	 * @param facts
	 *            les faits qui seront ins�r�s
	 * @return les faits pr�sents dans la m�moire apr�s l'ex�cution
	 */
	public Collection<Object> query(Iterable<?> facts);

}