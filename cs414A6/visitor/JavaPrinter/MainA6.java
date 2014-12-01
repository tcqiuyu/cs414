//
// CS414: Use this class as the driver class for your Commenter visitor. 
// Note that this class creates an instance of Commenter and passes it to
// the root of the parse tree.
//

import syntaxtree.*;
import visitor.*;

public class MainA6 {
	public static void main(String args[]) {
		JavaParser parser;

		if (args.length == 1)
		{
			System.err.println("JTB Pretty Printer 1.1:  Reading from file " +
					args[0] + " . . .");
			try 
			{ 
				parser = new JavaParser(new java.io.FileInputStream(args[0])); 
			}
			catch (java.io.FileNotFoundException e)
			{
				System.err.println("JTB Pretty Printer 1.1:  File " + args[0] +
						" not found."+e.getMessage());
				return;
			}
		}
		else {
			System.err.println("JTB Pretty Printer 1.1:  Cannot find file");
			return;
		}

		try {
			// Here's where the AST actions and visiting take place.
			Node root = parser.CompilationUnit();
			System.err.println("JTB Pretty Printer 1.1:  Java program parsed successfully.");
			Commenter commenter = new Commenter();

			// Insert comments in  the source code, then dump it
			root.accept(commenter);
		}
		catch (ParseException e) 
		{
			System.err.println(e.getMessage());
			System.err.println("JTB Pretty Printer 1.1:  Encountered errors during parse.");
		}
	}
}
