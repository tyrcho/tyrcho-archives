package com.tyrcho.web.glossary.server.data

/**
 * Keeps the translations for one word, with explaination and tags.
 */
class Translation(var word:String, var translation : String, var explaination:String="", var tags:List[String] = Nil) {
	def reverse() : Translation = new Translation(translation, word, explaination, tags)
}
