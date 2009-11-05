package com.tyrcho.magic.octgn

import java.io._
import scala.xml._
import java.util.regex.Pattern
import scala.collection.mutable.HashMap

/**
 * Converts all decks in a folder (including subfolders from MWS .mwdeck format to OCTGN .o8d format).
 */
object Otcgn2DeckImporter {
  /**
   * Syntax : arg0=folder containing deck files, arg1=xml reference file containing OCTGN ids (generated by CardsLister).
   */
  def main(args : Array[String]) : Unit = {
    val deckFile=new File(args(0))    
    val cardsReference = buildDeckMap(XML.loadFile(args(1)))
    foreach(deckFile, convertDeck(cardsReference), f=>f.getName.endsWith(".mwDeck"))
  }
  
  
  /**
   * Builds a map with card names as keys, ids as values, for faster access.
   */
  def buildDeckMap(reference : Elem) : Map[String, String]= {    
    var map = Map.empty[String, String] 
    for (card <- reference \\ "card") {
      def name=card.attribute("name").get.text
      def id=card.attribute("id").get.text
      map += name -> id
    }
    map
  }
  
  /**
   * Converts a deck file with regards to the XML reference.
   */
  def convertDeck(cardsReference : Map[String, String])(deckFile : File) : Unit = {
    println(deckFile.getAbsolutePath)
    val reader = new BufferedReader(new FileReader(deckFile))
    val lines=getLines(reader)
    val deck= buildDeck(getCards(lines,mainPattern), getCards(lines,sidePattern), cardsReference)
    XML.saveFull(deckFile.getAbsolutePath+".o8d", deck, true, null)
  }
  
  /**
   * Applies a function recursively to all files in a directory which match the filter.
   */
  def foreach(file : File, action : File=>Unit, filter : File => boolean) : Unit = {
    if(file.isDirectory) {
      for(f <- file.listFiles) { foreach(f, action, filter)};
    } else {
      if (filter(file)) { action(file)}
    }
  }
  
  //Main deck regexp pattern
  val mainPattern = Pattern.compile("\\s*(\\d) \\[(\\S+)\\] (.*)")
  //Sideboard regexp pattern
  val sidePattern = Pattern.compile("\\SB: (\\d) \\[(\\S+)\\] (.*)")
  
  /**
   * For each line which match the pattern, convert it to a card XML element.
   */
  def getCards(lines : Seq[String], pattern:Pattern) : Seq[(String, Int)] = {
    lines.filter(l=>pattern.matcher(l).matches).map(parseLine(pattern))
  }
  
  /**
   * Extracts from a line the card name and count.
   */
  def parseLine(pattern:Pattern) (line : String) : (String, Int) = {
    val matcher=pattern.matcher(line)
    assert(matcher.matches)
    val c=matcher.group(3)
    val card=if(c.contains("(")) c.split("\\(")(0).trim else c 
    (card, Integer.parseInt(matcher.group(1)))
  }
  
  /**
   * Reads all lines as a Seq from the reader.
   */
  def getLines(r:BufferedReader) : Seq[String] = {
    val line=r.readLine 
    line match {
      case null => Seq.empty
      case line : String =>  Seq(line) ++ getLines(r)
    }    
  }
  
  /**
   * Builds the card XML element from the card name.
   */
  def getCard(card:String, count: Int, cardsList:Map[String, String]) : Elem = {
   <card 
     qty={count.toString} 
     id={cardsList.get(card).orElse({println(card+" not found"); Some("")}).get}>{card}</card>
  }
  
  /**
   * Builds the deck XML from the main and sideboard cards names and counts.
   */
  def buildDeck(main:Seq[(String, Int)], side :Seq[(String, Int)], cardsList : Map[String, String]) : Elem = {
    <deck game="a6c8d2e8-7cd8-11dd-8f94-e62b56d89593">
    	<section name="Main">
    	{
    		main.map(entry => getCard(entry._1, entry._2, cardsList))
    	}
    	</section>
    	<section name="Sideboard">
    	{
    		side.map(entry => getCard(entry._1, entry._2, cardsList))
    	}
    	</section>
    </deck>
  }
}
