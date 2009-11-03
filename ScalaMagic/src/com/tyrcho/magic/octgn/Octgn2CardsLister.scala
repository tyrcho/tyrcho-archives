package com.tyrcho.magic.octgn

import java.util.zip._
import java.io._
import scala.xml._

  
object Octgn2CardsLister { 
  def main(args : Array[String]) : Unit = {
    var inputFolder=new File(args(0))
    var outputFile=args(1)
    XML.save(outputFile, getAllCards(inputFolder.listFiles))
  }
  
  def getAllCards(files : Seq[File]) : Elem = {
    <sets>
    { files.map (f => getCardsFromSet(getXml(f))) }
    </sets>
  }
  
  def getCardsFromSet(xml:Elem) : Elem = {
    var set:Node = (xml \\ "set") first   
    
    <set 
      name= { set.attribute("name") } 
      id={ set.attribute("id") }>
      {getCards(xml)}
    </set>
  }
  
  def getCards(xml:Elem) : Seq[Elem] = {
    xml \\ "card"   map (e=> 
     <card 
       name={e.attribute("name")}
       id={e.attribute("id")}
     />
      
    )
  }
  
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
