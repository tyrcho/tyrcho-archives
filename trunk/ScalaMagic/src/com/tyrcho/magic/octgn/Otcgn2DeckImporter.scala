package com.tyrcho.magic.octgn

import java.io._
import scala.xml._
import java.util.regex.Pattern

object Otcgn2DeckImporter {
  def main(args : Array[String]) : Unit = {
    val deckFile=new File(args(0))    
    val cardsReference = XML.loadFile(args(1))
    foreach(deckFile, convertDeck(cardsReference), f=>f.getName.endsWith(".mwDeck"))
  }
  
  def convertDeck(cardsReference : Elem)(deckFile : File) : Unit = {
    println(deckFile.getAbsolutePath)
    val reader = new BufferedReader(new FileReader(deckFile))
    val lines=getLines(reader)
    val deck= buildDeck(getCards(lines,mainPattern), getCards(lines,sidePattern), cardsReference)
    XML.saveFull(deckFile.getAbsolutePath+".o8d", deck, true, null)
  }
  
  def foreach(file : File, action : File=>Unit, filter : File => boolean) : Unit = {
    if(file.isDirectory) {
      for(f <- file.listFiles) { foreach(f, action, filter)};
    } else {
      if (filter(file)) { action(file)}
    }
  }
  
  val mainPattern = Pattern.compile("\\s*(\\d) \\[(\\S+)\\] (.*)")
  val sidePattern = Pattern.compile("\\SB: (\\d) \\[(\\S+)\\] (.*)")
  
  def getCards(lines : Seq[String], pattern:Pattern) : Seq[(String, Int)] = {
    lines.filter(l=>pattern.matcher(l).matches).map(parseLine(pattern))
  }
  
  def parseLine(pattern:Pattern) (line : String) : (String, Int) = {
    val matcher=pattern.matcher(line)
    assert(matcher.matches)
    val c=matcher.group(3)
    val card=if(c.contains("(")) c.split("\\(")(0).trim else c 
    (card, Integer.parseInt(matcher.group(1)))
  }
  
  def getLines(r:BufferedReader) : Seq[String] = {
    val line=r.readLine 
    line match {
      case null => Seq.empty
      case line : String =>  Seq(line) ++ getLines(r)
    }    
  }
  
  def getId(card : String, cardsList : Elem) : Option[String] = {
    val cards=cardsList \\ "card"
    val firstCardWithSameName=cards.filter(c => c.attribute("name").get.text.equals(card)).firstOption
    if(firstCardWithSameName.isDefined) {
    	return Some(firstCardWithSameName.get.attribute("id").get.text)
    } else {
      println(card+" not found")
      return None
    }
  }
  
  def getCard(card:String, count: Int, cardsList:Elem) : Elem = {
   <card 
     qty={count.toString} 
     id={getId(card, cardsList).orElse(Some("")).get}>{card}</card>
  }
  
  def buildDeck(main:Seq[(String, Int)], side :Seq[(String, Int)], cardsList : Elem) : Elem = {
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
