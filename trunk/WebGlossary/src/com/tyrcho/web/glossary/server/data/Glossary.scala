package com.tyrcho.web.glossary.server.data

/**
 * Stores the translations for both languages.
 */
class Glossary(
		nativeLanguage:String, 
		otherLanguage:String)
{	
	 var nativeGlossary = new OneWayGlossary(this.nativeLanguage, otherLanguage)
	 var otherGlossary = new OneWayGlossary(otherLanguage, nativeLanguage)
	 
	 def addTranslation(native : String, other : List[String]) {
		 other.foreach(s => {
			 addTranslation(native, s)
		 })
	 }
	 
	 def addTranslation(native : String, other : String) {
			 nativeGlossary.addTranslation(native, other)
			 otherGlossary.addTranslation(other, native)
	 }
	 
	 def addTranslation(native : List[String], other : String) {
		 native.foreach(s => {
			 addTranslation(s, other)
		 })
	 }
	 
	 def getTranslation(word : String, native : Boolean) : Translation = {
		 var glossary = if(native) nativeGlossary else otherGlossary
		 glossary.translations(word)
	 }
}
