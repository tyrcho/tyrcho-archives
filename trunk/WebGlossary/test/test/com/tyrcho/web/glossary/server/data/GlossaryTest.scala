package test.com.tyrcho.web.glossary.server.data

import com.tyrcho.web.glossary.server.data.Glossary
import org.junit._
import org.junit.Assert._

class GlossaryTest {
	var g : Glossary = null
	
	@Before
	def setUp() {
		g=new Glossary("fr", "en")
		
	}

	@Test
	def testSimpleAdd() {
		g.addTranslation("bonjour", List("hello", "hi"))
		var t=g.getTranslation("bonjour", true)
		assertTrue(t.translations.contains("hello"))
		assertTrue(t.translations.contains("hi"))
		assertTrue(g.getTranslation("hi", false).translations.contains("bonjour"))
		assertTrue(g.getTranslation("hello", false).translations.contains("bonjour"))
	}
	
	@Test
	def testOtherAdd() {
		g.addTranslation(List("hello", "hi"), "bonjour")
		var t=g.getTranslation("bonjour", false)
		assertTrue(t.translations.contains("hello"))
		assertTrue(t.translations.contains("hi"))
		assertTrue(g.getTranslation("hi", true).translations.contains("bonjour"))
		assertTrue(g.getTranslation("hello", true).translations.contains("bonjour"))
	}
	
	@Test
	def testChainedAdd() {
		g.addTranslation("bonjour", List("hello", "hi"))
		g.addTranslation("salut", List("hello", "hi"))
		var t=g.getTranslation("bonjour", true)
		assertTrue(t.translations.contains("hello"))
		assertTrue(t.translations.contains("hi"))
		t=g.getTranslation("salut", true)
		assertTrue(t.translations.contains("hello"))
		assertTrue(t.translations.contains("hi"))
		assertTrue(g.getTranslation("hi", false).translations.contains("salut"))
		assertTrue(g.getTranslation("hello", false).translations.contains("salut"))
	}
	
}
