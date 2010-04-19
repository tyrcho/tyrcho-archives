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
	 
	 def addTranslations(native : String, other : List[String], explaination : String="", tags : List[String]=Nil) {
		 other.foreach(s => {
			 addTranslation(native, s, explaination, tags)
		 })
	 }
	 
	 def addTranslation(native : String, other : String, explaination : String="", tags : List[String]=Nil) {
			 nativeGlossary.addTranslation(native, other, explaination, tags)
			 otherGlossary.addTranslation(other, native, explaination, tags)
	 }
	 
	 def addTranslation(native : List[String], other : String) {
		 native.foreach(s => {
			 addTranslation(s, other)
		 })
	 }
	 
	 def getTranslations(word : String, native : Boolean=true) : List[Translation] = {
		 var glossary = if(native) nativeGlossary else otherGlossary
		 glossary.getTranslations(word)
	 }
	 
	 def getTranslationsForTag(tag : String) : List[Translation] = {
			 nativeGlossary.getTranslationsForTag(tag) 
//			 ++
//			 otherGlossary.getTranslationsForTag(tag).map(_.reverse)			 
	 }
}
