package com.tyrcho.web.glossary.server.data

import scala.collection.mutable.Map;
/**
 * Stores translations from one language to another.
 */
class OneWayGlossary(mainLanguage:String, otherLanguage:String, var translations : Map[String,Translation]=Map.empty) {
	def addTranslation(main:String, other:String) {
		var t=translations.getOrElse(main, new Translation(main))
		t.add(other)
		translations.put(main, t)
	}
}
