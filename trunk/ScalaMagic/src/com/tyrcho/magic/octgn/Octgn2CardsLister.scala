package com.tyrcho.magic.octgn

import java.util.zip._
import java.io._
import scala.xml._

/**
 * Extracts matching cards name and id from OCTGN files.
 */  
object Octgn2CardsLister { 
  /**
   * Syntax : arg0=inputFolder containing sets files, arg1 = output XML file (used by deck importer)
   */
  def main(args : Array[String]) : Unit = {
    var inputFolder=new File(args(0))
    var outputFile=args(1)
    XML.saveFull(outputFile, getAllCards(inputFolder.listFiles), true, null)
  }
  
  def getAllCards(files : Seq[File]) : Elem = {
    <sets>
    { files.map (f => getCardsFromSet(getXml(f))) }
    </sets>
  }
  
  /**
   * Converts the octgn xml format for a set.
   */
  def getCardsFromSet(xml:Elem) : Elem = {
    var set:Node = (xml \\ "set") first   
    
    <set 
      name= { set.attribute("name") } 
      id={ set.attribute("id") }>
      {getCards(xml)}
    </set>
  }
  
  /**
   * Converts the octgn xml format for the cards.
   */
  def getCards(xml:Elem) : Seq[Elem] = {
    xml \\ "card"   map (e=> 
     <card 
       name={e.attribute("name")}
       id={e.attribute("id")}
     />
      
    )
  }
  
  /**
   * Reads the xml set file from an OCTGN .o8s file (zip format).
   */
  def getXml(file:File) : Elem = {
    val zip= new ZipFile(file)
    for (entry <- zip.entries) {
    	val name=entry.getName
    	if(name.endsWith("xml") && !name.contains("Content_Types")) {
//    		println(name)
    		return XML.load(zip.getInputStream(entry))
       }
     }
     throw new Exception("No entry found in "+file)
  }
  
  
 case class JavaEnumerationIterable[A](itr : java.util.Enumeration[A] ) extends Iterator[A] {
       def hasNext = itr.hasMoreElements
       def next() = itr.nextElement
    }
  
   implicit def implicitJavaEnumerationToScalaIterable[A]( iterable : java.util.Enumeration[A]) : Iterator[A] =
      JavaEnumerationIterable(iterable)
}
