// ======================================================
//
// MTBA - SSG - Gestion Specifique.
//
// pack : com.thales.sat.lsr.common.misc
// file : StringUtils.java
//
// Historique
// ------------------------------------------------------
// Creation : 28 mars 2007
// Auteur   : t0049984
// ------------------------------------------------------
// Modification :
// Date         :
// Auteur       :
//
// ======================================================


package com.tyrcho;

/**
 Classe <code>StringUtils</code> :
 Utilitaires pour les String.
 @author t0049984
 */
public class StringUtils
{
	public static String LINE_SEPARATOR=System.getProperty("line.separator");

	/**
	Methode <code>nullOrEmpty</code> : tient compte de la casse 
	@param s
	@return boolean : vrai si la chaine est vide ou nulle.
	*/
	public static boolean nullOrEmpty(String s)
	{
		return (s== null) || s.length()==0; 
	}
	
	/**
	Methode <code>equalsOrBothNull</code> : Tient compte de la casse.
	@param s1
	@param s2
	@return boolean : vrai si les deux chaines sont equivalentes ou si les deux sont nulles
	*/
	public static boolean equalsOrBothNull(String s1, String s2)
	{
		if (s1 != null && s2!= null) return s1.equals(s2);
		else if (s1 == null && s2== null) return true;
		return false;
	}
	
	/**
	Methode <code>equalsAndNotNull</code> :  Tient compte de la casse.
	@param s1
	@param s2
	@return boolean : vrai si les deux chaines sont equivalentes et non nulles.
	*/
	public static boolean equalsAndNotNull(String s1, String s2)
	{
		if ((s1 == null) || (s2 == null)) return false;
		return s1.equals(s2);
	}


}
