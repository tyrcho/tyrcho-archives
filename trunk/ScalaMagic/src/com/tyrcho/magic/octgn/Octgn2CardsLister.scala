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
    var sets=getAllCards(inputFolder.listFiles)
    XML.saveFull(outputFile, sets, true, null)
    zip(outputFile)
  }
  
  def copyStream(istream : InputStream, ostream : OutputStream) : Unit = {
    var bytes =  new Array[Byte](1024)
    var len = -1
    while({ len = istream.read(bytes, 0, 1024); len != -1 })
      ostream.write(bytes, 0, len)
  }
  
  
  def zip(file : String) : Unit = {
    val f = new File(file).getCanonicalPath
    val localFileName=if(f.contains(File.separator)) f.substring(f.lastIndexOf(File.separator)).replace(File.separator, "") else f
    val zos:ZipOutputStream = new ZipOutputStream(new BufferedOutputStream( new FileOutputStream(file+".zip")));
    def fis:FileInputStream = new FileInputStream(file);
    zos.putNextEntry(new ZipEntry(localFileName));
	copyStream(fis, zos)
    zos.closeEntry
    fis.close
    zos.close
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
