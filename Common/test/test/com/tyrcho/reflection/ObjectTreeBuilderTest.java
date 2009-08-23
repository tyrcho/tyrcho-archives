package test.com.tyrcho.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.tyrcho.reflection.ObjectTreeBuilder;
import com.tyrcho.structure.ITree;

public class ObjectTreeBuilderTest {

	@Test
	public void simpleClass() {
		SimplePerson p = new SimplePerson();
		p.name = "toto";
		ObjectTreeBuilder builder = new ObjectTreeBuilder(p);
		ITree<Object> tree = builder.buildTree();
		assertSame(p, tree.getValue());
		assertNull(tree.getSubtrees().get("firstName").getValue());
		assertEquals("toto", tree.getSubtrees().get("name").getValue());
	}

	

	class SimplePerson {
		String name;
		String firstName;
	}
	
}
