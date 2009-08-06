package com.tyrcho.introspection;



import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;


/********************************************************************************
 * Classe ou interface :IntrospectPanel
 * 
 * @description	panneau d'introspection d'un objet.
 * 
 * Historique :
 * 
 * date creation : Nov 19, 2004
 * @since V1.0 
 * @author P01172
 * 
 * date de modification :
 * auteur
 * nature de la modification :
 *********************************************************************************/
public class IntrospectPanel extends JFrame 
{
	private Object 	_cible;
	private JTree 	_tree = null ;
	// Champ selectionne.
	private Object		_selected = null;
	
	private TreeNode _selectedNode = null;
	
	// Zone d'affichage
	private JTextField	_textfield = new JTextField();
	// Tableau croise entre hashcode des treenodes et une reference sur l'instance valide pour le Field qu'ils contiennent.
	private LinkedHashMap _references = new LinkedHashMap();
	
	// Pour pouvoir editer les types de base d'une instance d'objet dit 'englobant'
	private LinkedHashMap _objets_englobant = new LinkedHashMap();
	// Fields lies aux variables de type primitif
	private LinkedHashMap _fieldBase = new LinkedHashMap();
	// Pour editer une telle var, on prend l'objet englobant lie au node. On retrouve le Field puis on effectue un Set dans le contexte ainsi recupere.

	/** *********************************************
	 * IntrospectPanel : Constructeur
	 * ==============================================
	 * @author P01172 --- Nov 19, 2004
	 * ==============================================
	 * @param source
	 ********************************************** */
	public IntrospectPanel(Object source)
	{
		super(source.getClass().getName() + " : " + source.hashCode());
		
		_cible = source;
		Container cont = getContentPane();
		cont.setLayout(new BorderLayout());
		
		//Class cl  = _cible.getClass();
		
		Field[] fields = getMembers(source);
		
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(_cible);
		
		// TREE des membres.
		for (int i = 0; i < fields.length; i++)
		{
			rootNode.add( buildNode(_cible, fields[i]));
		}
		_tree = new JTree(rootNode);
		_tree.addTreeSelectionListener(new TreeSelectionListener()
		{
			public void valueChanged(TreeSelectionEvent event)
			{
				// RAS des variables temporaires.
				_selectedNode = null;
				IntrospectPanel.this._textfield.setText("");
				
				TreePath tp = event.getNewLeadSelectionPath();
				if (tp != null)
				{
					_selectedNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
					Object obj = _references.get(_selectedNode);
					if (obj != null ) // feuille ou tableau
					{	setTextField(obj);	}
				} 
			}
		}
		);
		JScrollPane scroll = new JScrollPane(_tree);
		cont.add (scroll, BorderLayout.CENTER);
		cont.add(_textfield, BorderLayout.SOUTH);

		_textfield.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent ke)			{}
			public void keyReleased(KeyEvent ke)		{}
			public void keyPressed(KeyEvent ke)		
			{
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) // Validation
				{
					if (_selected == null) return;
					
//					String typename = _selected.getClass().getName() ;
					String value = _textfield.getText();
					Class selectedClass = _selected.getClass();
					
					try
					{
						if 		(selectedClass == Byte.class)			{		setByte(_selected,value);			}
						else if	(selectedClass == Double.class)		{		setDouble(_selected,value);		}
						else if	(selectedClass == Float.class)			{		setFloat(_selected,value);			}
						else if	(selectedClass == Integer.class)		{		setInt(_selected,value);				}
						else if	(selectedClass == Long.class)			{		setLong(_selected,value);			}					
						else if	(selectedClass == Character.class)	{		setChar(_selected,value);			}								
						else if	(selectedClass == Short.class)			{		setShort(_selected,value);			}					
						else if	(selectedClass == Boolean.class)	{		setBoolean(_selected,value);	}							
						else // Autre type ?
						{
							if (selectedClass.isArray()) // TABLEAUX
							{
								value = value.substring(1,value.length()-1);
								String[] valeurs = value.split(";");
								
								Object element = Array.get(_selected,0);
								Class type_composant = element.getClass();
								
								for (int i = 0; i < valeurs.length; i++)
								{
									if 		(type_composant == Byte.class)			{		Array.setByte		(_selected,i,Byte.valueOf(valeurs[i]).byteValue());			}
									else if	(type_composant == Double.class)		{		Array.setDouble	(_selected,i,Double.valueOf(valeurs[i]).doubleValue());	}
									else if	(type_composant == Float.class)			{		Array.setFloat		(_selected,i,Float.valueOf(valeurs[i]).floatValue());			}
									else if	(type_composant == Integer.class)		{		Array.setInt			(_selected,i,Integer.valueOf(valeurs[i]).intValue());			}
									else if	(type_composant == Long.class)			{		Array.setLong		(_selected,i,Long.valueOf(valeurs[i]).longValue());			}					
									else if	(type_composant == Character.class)	{		Array.setChar		(_selected,i,valeurs[i].charAt(0));				}								
									else if	(type_composant == Short.class)			{		Array.setShort	(_selected,i,Short.valueOf(valeurs[i]).shortValue());			}					
									else if	(type_composant == Boolean.class)	{		Array.setBoolean(_selected,i,Boolean.valueOf(valeurs[i]).booleanValue());}							
									else																		{		System.out.println ("Type non gere.");	}
								}
							}
							else			{	System.out.println ("Type non gere.");}
						}
					}
					catch (NoSuchFieldException ie)	{			System.out.println ("Membre inconnu");			}
					catch (IllegalAccessException ie)	{			System.out.println ("Echec acces ecriture");	}
					catch (java.lang.Exception ex)		{			System.out.println("Echec d'ecriture dans un tableau : " + ex);	}
				}
			}
		}
		);	
		
		setBounds(100,100,400,400);
		show();
	}
	
	/** *********************************************
	 * Methode : buildNode
	 * @description 
	 * ----------------------------------------------
	 * @author P01172 --- Nov 19, 2004
	 * ----------------------------------------------
	 * @param instance
	 * @param f
	 * @return
	 ********************************************** */
	protected DefaultMutableTreeNode buildNode(Object instance, Field f)
	{
		DefaultMutableTreeNode retour = null;
		
		try
		{
			// Besoin de lire...
			f.setAccessible(true);
			Object membre = f.get(instance);

			if (f.getType().isPrimitive()) // => feuille
			{
				// On passe le Field comme user object car on conserve deja le lien feuille / variable.
				// Cela permet d'obtenir un affichage plus explicite du nom de la var.
				retour = new DefaultMutableTreeNode(f);					
				_references.put(retour, membre); 	// lien entre feuille et la variable.
				_objets_englobant.put(retour,instance); // lien entre la feuille et l'objet de rattachement
				_fieldBase.put(retour,f);
			}	
			else if (f.getType().isArray()) // => Noeud
			{
				retour = buildArrayNode(instance, f);
			}
			else // Objet Complexe.
			{	
				if (membre != null)
				{
					// De toute facon, on ne peut editer un obj complexe directement => on n'utilise pas le user object
					// On peut donc s'en servir pour le label du noeud.
					retour = new DefaultMutableTreeNode(f);
					Field[] fields = getMembers(membre);
					for (int i = 0; i < fields.length; i++)
					{
						// Construction d'un sous noeud avec comme instance de reference l'objet lié à ce field pour l'instance 'instance'.
						DefaultMutableTreeNode node =  buildNode(membre, fields[i]); 
						if (node != null ) retour.add(node);	// Des fois ca merde (cas des tableaux n dim) => on teste à null.						
					}
				}
				else // Reference non allouee.
				{
					retour = new DefaultMutableTreeNode(f.toString() + " : null");
				}
			}
		}
		catch (IllegalAccessException ie )
		{
			// C'est pas possible on vient de se donner les droits...
			System.out.println("Acces au membre impossible...");
		}
		return retour;	
	}
	
	
	/** *********************************************
	 * Methode : buildArrayNode
	 * @description : construction d'un node a partir d'un tableau N dimensions.
	 * ----------------------------------------------
	 * @author P01172 --- Nov 19, 2004
	 * ----------------------------------------------
	 * @param source 		: instance de reference
	 * @param member		: description du membre. 
	 * @return
	 ********************************************** */
	protected DefaultMutableTreeNode buildArrayNode(Object source, Field member)
	{
		DefaultMutableTreeNode retour = null;
		try
		{
			Object tableau = member.get(source); // valeur du membre dans le contexte d'instanciation 'source'
			String complet = member.toString();
			String varname = member.getName();
			// Suppression du nom de la variable car il sera ajoute ensuite.
			complet = complet.replaceFirst(varname, "");
			retour = buildSubArray(tableau,complet,varname);
		}
		catch (IllegalAccessException iae)
		{
			System.out.println("Echec acces au tableau." + iae);
		}
		catch (java.lang.Exception e)
		{
			System.out.println("Echec lecture." + e);
		}
			
		return retour;
	}
	
	/** *********************************************
	 * Methode : buildSubArray
	 * @description methode re-entrante de construction de noeud lie 
	 * a un tableau (n dim).
	 * ----------------------------------------------
	 * @author P01172 --- Nov 23, 2004
	 * ----------------------------------------------
	 * @param tableau 				instance 
	 * @param nom_noeud			libelle du node
	 * @param nom_var				nom de la variable. (pour distinguer les intitules des tableaux et des sous tableaux.
	 * @return
	 ********************************************** */
	protected DefaultMutableTreeNode buildSubArray(Object tableau, String nom_noeud, String nom_var)
	{
		DefaultMutableTreeNode retour = null;
			
		Class type_tableau = tableau.getClass().getComponentType();		// Byte.TYPE 	etc... 
		String type_name = tableau.getClass().getName();							// [B, [L 			etc...
		int taille = Array.getLength(tableau);
		
		// Est ce un tableau final ( = de dimension 1)
		int dimension = 0;
		while (type_name.startsWith("[", dimension))		{	dimension++;		}
			
		if (dimension == 1) 
		{
			if (type_tableau.isPrimitive()) 
			{
				retour = new DefaultMutableTreeNode(nom_noeud + nom_var);
				_references.put(retour, tableau); 	// lien entre feuille et le tableau. 
			}
				 
			else // Tableau d'objets complexes.
			{
				retour = new DefaultMutableTreeNode(nom_noeud + nom_var);
				for (int i = 0; i < taille; i++) // Chaque element du tableau
				{
					DefaultMutableTreeNode sub = new DefaultMutableTreeNode( nom_var+ "[" + i + "]");
					Object s = Array.get(tableau,i); // Element du tableau.
					if (s != null)
					{
						Field[] fields = getMembers(s);
						for (int j = 0; j < fields.length; j++)			
						{
							sub.add( buildNode(s, fields[j]));		
						}
					}
					else
					{
						sub = new DefaultMutableTreeNode( nom_var+ "[" + i + "]" + " : null");
					}
					retour.add(sub);
				}
			}
		}
		else // Tableaux n dimensions.
		{
			retour = new DefaultMutableTreeNode(nom_noeud);
			for (int i = 0; i < taille; i++) // Chaque element du tableau
			{
				Object subtab = Array.get(tableau,i); // Element du tableau etant un tableau lui meme.
				DefaultMutableTreeNode sub = buildSubArray(subtab,"",nom_var + "[" + i + "]");
				retour.add(sub);
			}
		}
		return retour;
	}
	
	
	/** *********************************************
	 * Methode : getMembers
	 * @description methode de parcour d'arbre de classes
	 * pour recuperation de l'ensemble des membres de la hierarchie.
	 * ----------------------------------------------
	 * @author P01172 --- Nov 19, 2004
	 * ----------------------------------------------
	 * @param o
	 * @return
	 ********************************************** */
	public Field[] getMembers(Object o)
	{
		ArrayList members = new ArrayList();
		
		Class c = o.getClass();
		while (c != Object.class)
		{
			members.addAll(Arrays.asList(c.getDeclaredFields()));
			c = c.getSuperclass();
		}
		
		Field[] retour  = new Field[members.size()];
		Object[] m = members.toArray(); // On essaie de gagner un peu de temps CPU...
		for (int i = 0; i < retour.length; i++)
		{
			retour [i] = (Field) m[i];	
		}
		return retour;
	}
	/** *********************************************
	 * Methode : setField
	 * @description 
	 * ----------------------------------------------
	 * @author P01172 --- Nov 22, 2004
	 * ----------------------------------------------
	 * @param f
	 ********************************************** */
	public void setTextField(Object f)	
	{
		_selected = f;
		
		if (_selected.getClass().isArray()) 	
		{
			int l = Array.getLength(_selected);
			Class type_tableau = _selected.getClass().getComponentType();		// Byte.TYPE 	etc...
			String texte = new String("[");
			
			if 		(type_tableau == Byte.TYPE)					{	for (int i = 0; i < l ; i++) 	{		texte = texte + Byte.toString(Array.getByte(_selected,i)) + ";" ;		}	}
			else if	(type_tableau == Character.TYPE)			{	for (int i = 0; i < l ; i++) 	{		texte = texte + String.valueOf(Array.getChar(_selected,i)) + ";" ;		}	}
			else if	(type_tableau == Integer.TYPE)				{	for (int i = 0; i < l ; i++) 	{		texte = texte + String.valueOf(Array.getInt(_selected,i)) + ";" ;		}	}
			else if	(type_tableau == Float.TYPE)					{	for (int i = 0; i < l ; i++) 	{		texte = texte + String.valueOf(Array.getFloat(_selected,i)) + ";" ;		}	}
			else if	(type_tableau == Boolean.TYPE)				{	for (int i = 0; i < l ; i++) 	{		texte = texte + String.valueOf(Array.getBoolean(_selected,i)) + ";" ;		}	}
			else if	(type_tableau == Short.TYPE)					{	for (int i = 0; i < l ; i++) 	{		texte = texte + String.valueOf(Array.getShort(_selected,i)) + ";" ;		}	}
			else if	(type_tableau == Long.TYPE)					{	for (int i = 0; i < l ; i++) 	{		texte = texte + String.valueOf(Array.getLong(_selected,i)) + ";" ;		}	}
			else if	(type_tableau == Double.TYPE)				{	for (int i = 0; i < l ; i++) 	{		texte = texte + String.valueOf(Array.getDouble(_selected,i)) + ";" ;		}	}
			else																			{	for (int i = 0; i < l ; i++) 	{		texte = texte + Array.get(_selected,i).toString() + ";" ;		}	}
			// Cas du tableau de 0 element oblige a tester ";"
			if (texte.endsWith(";"))					{texte = texte.substring(0,texte.lastIndexOf(";"));} 
			texte = texte + "]";
			_textfield.setText(texte);
		}
		else										_textfield.setText("" + _selected);
		 
	}
	
	
	// SET des types primitifs liés à l'instance '_cible' et ses composants.	
	// On pourrait factoriser un peu de code, mais serait-ce plus lisible ?
	
	protected void setByte(Object obj, String value) throws IllegalAccessException, NoSuchFieldException
	{
		// Ecrasement de la valeur dans l'objet _cible.
		Field f = (Field) _fieldBase.get(_selectedNode);
		Object reference = _objets_englobant.get(_selectedNode);
		f.setByte(reference,Byte.valueOf(value).byteValue());
		
		// Ecrasement de la valeur dans l'objet de type Byte cree par l'introspection
		// On le conserve pour maintenir un affichage correct.
		Byte ref = (Byte) obj;
		Field val = ref.getClass().getDeclaredField("value");
		val.setAccessible(true);
		val.setByte(ref,Byte.valueOf(value).byteValue());
	}
	protected void setDouble(Object obj, String value) throws IllegalAccessException, NoSuchFieldException
	{
		// Ecrasement de la valeur dans l'objet _cible.
		Field f = (Field) _fieldBase.get(_selectedNode);
		Object reference = _objets_englobant.get(_selectedNode);
		f.setDouble(reference,Double.valueOf(value).doubleValue());
		
		// Ecrasement de la valeur dans l'objet de type Byte cree par l'introspection
		// On le conserve pour maintenir un affichage correct.
		Double ref = (Double) obj;
		Field val = ref.getClass().getDeclaredField("value");
		val.setAccessible(true);
		val.setDouble(ref,Double.valueOf(value).doubleValue());
	}
	protected void setFloat(Object obj, String value) throws IllegalAccessException, NoSuchFieldException
	{
		// Ecrasement de la valeur dans l'objet _cible.
		Field f = (Field) _fieldBase.get(_selectedNode);
		Object reference = _objets_englobant.get(_selectedNode);
		f.setFloat(reference,Float.valueOf(value).floatValue());
		
		// Ecrasement de la valeur dans l'objet de type Byte cree par l'introspection
		// On le conserve pour maintenir un affichage correct.
		Float ref = (Float) obj;
		Field val = ref.getClass().getDeclaredField("value");
		val.setAccessible(true);
		val.setFloat(ref,Float.valueOf(value).floatValue());
	}
	protected void setInt(Object obj, String value) throws IllegalAccessException, NoSuchFieldException
	{
		// Ecrasement de la valeur dans l'objet _cible.
		Field f = (Field) _fieldBase.get(_selectedNode);
		Object reference = _objets_englobant.get(_selectedNode);
		f.setInt(reference,Integer.valueOf(value).intValue());
		
		// Ecrasement de la valeur dans l'objet de type Byte cree par l'introspection
		// On le conserve pour maintenir un affichage correct.
		Integer ref = (Integer) obj;
		Field val = ref.getClass().getDeclaredField("value");
		val.setAccessible(true);
		val.setInt(ref,Integer.valueOf(value).intValue());
	}
	protected void setLong(Object obj, String value) throws IllegalAccessException, NoSuchFieldException
	{
		// Ecrasement de la valeur dans l'objet _cible.
		Field f = (Field) _fieldBase.get(_selectedNode);
		Object reference = _objets_englobant.get(_selectedNode);
		f.setLong(reference,Long.valueOf(value).longValue());
		
		// Ecrasement de la valeur dans l'objet de type Byte cree par l'introspection
		// On le conserve pour maintenir un affichage correct.
		Long ref = (Long) obj;
		Field val = ref.getClass().getDeclaredField("value");
		val.setAccessible(true);
		val.setLong(ref,Long.valueOf(value).longValue());
	}
	protected void setChar(Object obj, String value) throws IllegalAccessException, NoSuchFieldException
	{
		// Ecrasement de la valeur dans l'objet _cible.
		Field f = (Field) _fieldBase.get(_selectedNode);
		Object reference = _objets_englobant.get(_selectedNode);
		f.setChar(reference,value.charAt(0));
		
		// Ecrasement de la valeur dans l'objet de type Byte cree par l'introspection
		// On le conserve pour maintenir un affichage correct.
		Character ref = (Character) obj;
		Field val = ref.getClass().getDeclaredField("value");
		val.setAccessible(true);
		val.setChar(ref,value.charAt(0));
	}
	protected void setShort(Object obj, String value) throws IllegalAccessException, NoSuchFieldException
	{
		// Ecrasement de la valeur dans l'objet _cible.
		Field f = (Field) _fieldBase.get(_selectedNode);
		Object reference = _objets_englobant.get(_selectedNode);
		f.setShort(reference,Short.valueOf(value).shortValue());
		
		// Ecrasement de la valeur dans l'objet de type Byte cree par l'introspection
		// On le conserve pour maintenir un affichage correct.
		Short ref = (Short) obj;
		Field val = ref.getClass().getDeclaredField("value");
		val.setAccessible(true);
		val.setShort(ref,Short.valueOf(value).shortValue());
	}					
	protected void setBoolean(Object obj, String value) throws IllegalAccessException, NoSuchFieldException
	{
		// Ecrasement de la valeur dans l'objet _cible.
		Field f = (Field) _fieldBase.get(_selectedNode);
		Object reference = _objets_englobant.get(_selectedNode);
		f.setBoolean(reference,Boolean.valueOf(value).booleanValue());
		
		// Ecrasement de la valeur dans l'objet de type Byte cree par l'introspection
		// On le conserve pour maintenir un affichage correct.
		Boolean ref = (Boolean) _selected;
		Field val = ref.getClass().getDeclaredField("value");
		val.setAccessible(true);
		val.setBoolean(ref,Boolean.valueOf(value).booleanValue());
	}
	
}
