package com.tyrcho.web.glossary.server.data

/**
 * Keeps the translations for one word, with explaination and tags.
 */
class Translation(word:String, var translations:List[String]=Nil, explaination:String="", tags:List[String] = Nil) {
	def add(translation : String) {
		translations = translation :: translations
	}
}
