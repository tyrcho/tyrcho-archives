package com.tyrcho.web.glossary.server.data

import scala.collection.mutable.Map;
/**
 * Stores translations from one language to another.
 */
class OneWayGlossary(mainLanguage:String, otherLanguage:String, var translations : List[Translation]=Nil) {
	def addTranslation(main:String, other:String, explaination : String="", tags:List[String]=Nil) {
		translations = new Translation(main, other, explaination, tags) :: translations;
	}
	
	def getTranslations(main : String) : List[Translation] = {
		translations.filter(_.word.equals(main))
	}
	
	def getTranslationsForTag(tag : String): List[Translation] = {
		translations.filter(_.tags.contains(tag))
	}
}
