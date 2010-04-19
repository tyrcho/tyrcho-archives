package test.com.tyrcho.web.glossary.server.data

import com.tyrcho.web.glossary.server.data.Translation
import com.tyrcho.web.glossary.server.data.Glossary
import org.junit._
import org.junit.Assert._

class GlossaryTest {
	var g : Glossary = null
	
	@Before
	def setUp() {
		g=new Glossary("fr", "en")
	}
	
	def contains(translations : List[Translation], word : String) : Boolean = {
		translations.findIndexOf(_.translation == word)>=0
	}

	@Test
	def testSimpleAdd() {
		g.addTranslations("bonjour", List("hello", "hi"))
		var t=g.getTranslations("bonjour")
		assertTrue(contains(t, "hello"))
		assertTrue(contains(t, "hi"))
		assertTrue(contains(g.getTranslations("hi", false), "bonjour"))
		assertTrue(contains(g.getTranslations("hello", false), "bonjour"))
	}
	
	@Test
	def testOtherAdd() {
		g.addTranslation(List("hello", "hi"), "bonjour")
		var t=g.getTranslations("bonjour", false)
		assertTrue(contains(t, "hello"))
		assertTrue(contains(t, "hi"))
		assertTrue(contains(g.getTranslations("hi"), "bonjour"))
		assertTrue(contains(g.getTranslations("hello"), "bonjour"))
	}
	
	@Test
	def testChainedAdd() {
		g.addTranslations("bonjour", List("hello", "hi"))
		g.addTranslations("salut", List("hello", "hi"))
		var t=g.getTranslations("bonjour")
		assertTrue(contains(t, "hello"))
		assertTrue(contains(t, "hi"))
		t=g.getTranslations("salut")
		assertTrue(contains(t, "hello"))
		assertTrue(contains(t, "hi"))
		assertTrue(contains(g.getTranslations("hi", false),"salut"))
		assertTrue(contains(g.getTranslations("hello", false),"salut"))
		assertFalse(contains(g.getTranslations("hello", false),"toto")) // sanity check for contains
	}
	 
	@Test
	def testTags() {
		g.addTranslations("bonjour", List("hello", "hi"))
		g.addTranslations("cher", List("dear", "expensive"), " ", List("adjectif"))
		g.addTranslation("je", "I", "I am", List("pronom", "sujet"))
		g.addTranslation("tu", "you", "", List("pronom", "sujet"))
		g.addTranslation("mon", "my", "", List("pronom"))
		assertEquals(3, g.getTranslationsForTag("pronom").size)
		assertEquals(2, g.getTranslationsForTag("sujet").size)
		assertEquals(2, g.getTranslationsForTag("adjectif").size)
		assertEquals(0, g.getTranslationsForTag(" ").size)
	}
	
}
