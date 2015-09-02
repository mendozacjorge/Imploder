package org.imploder.test;

import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.imploder.Operations;

/**
 *
 * @author Jorge
 */
public class Test {

	public static void main(String... args) {
	
		try {
			Operations.explodeFolder(Paths.get("/wip/hidden/container.cst"), null, true);
			System.out.println("Exploded");
			
//				Operations.implodeFolder(Paths.get("/wip/hidden/exploded/"), Paths.get("/wip/hidden/container.cst"), true);
//				System.out.println("Imploded");
		} catch (Exception ex) {
			Logger.getLogger(Test.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
		
	}
	
}
