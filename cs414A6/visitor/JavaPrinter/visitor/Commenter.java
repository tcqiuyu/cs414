package visitor;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Enumeration;

import syntaxtree.*;

public class Commenter extends TreeDumper {

	protected PrintWriter out;
	private int curLine = 1;
	private int curColumn = 1;
	private boolean startAtNextToken = false;
	private boolean printSpecials = true;

	public Commenter() {
		out = new PrintWriter(System.out, true);
	}

	public Commenter(Writer o) {
		out = new PrintWriter(o, true);
	}

	public Commenter(OutputStream o) {
		out = new PrintWriter(o, true);
	}

	/**
	 * Dumps the current NodeToken to the output stream being used.
	 *
	 * @throws IllegalStateException
	 *             if the token position is invalid relative to the current
	 *             position, i.e. its location places it before the previous
	 *             token.
	 */
	public void visit(NodeToken n) {
		if (n.beginLine == -1 || n.beginColumn == -1) {
			printToken(n.tokenImage);
			return;
		}

		if (startAtNextToken) {
			curLine = n.beginLine;
			curColumn = 1;
			startAtNextToken = false;

			if (n.beginColumn < curColumn)
				out.println();
		}

		//
		// Check for invalid token position relative to current position.
		//
		if (n.beginLine < curLine)
			throw new IllegalStateException("at token \"" + n.tokenImage
					+ "\", n.beginLine = " + Integer.toString(n.beginLine)
					+ ", curLine = " + Integer.toString(curLine));
		else if (n.beginLine == curLine && n.beginColumn < curColumn)
			throw new IllegalStateException("at token \"" + n.tokenImage
					+ "\", n.beginColumn = " + Integer.toString(n.beginColumn)
					+ ", curColumn = " + Integer.toString(curColumn));

		//
		// Handle special tokens
		//
		if (printSpecials && n.numSpecials() > 0)
			for (Enumeration e = n.specialTokens.elements(); e
					.hasMoreElements();)
				visit((NodeToken) e.nextElement());

		//
		// Move output "cursor" to proper location, then print the token
		//
		if (curLine < n.beginLine) {
			curColumn = 1;
			for (; curLine < n.beginLine; ++curLine)
				// out.println("n.beginLine= "+n.beginLine);
				out.println();
		}

		for (; curColumn < n.beginColumn; ++curColumn)
			out.print(" ");

		printToken(n.tokenImage);
	}

	/**@Override
	 * Add comment before class declaration
	 */
	public void visit(ClassDeclaration n) {
		createMultiLineComment("New class ".concat(n.f1.f1.tokenImage));
		n.f0.accept(this);
		n.f1.accept(this);
	}
	
	/**@Override
	 * Add comment before class declaration
	 */
	public void visit(FieldDeclaration n) {
		out.printf("\n// Class variable definition begins");
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		out.printf("\n// Class variable definition ends");
	}

	/**@Override
	 * Add comment before class declaration
	 */
	public void visit(ConstructorDeclaration n) {
		createMultiLineComment("New constructor ".concat(n.f1.tokenImage));
		++curLine;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
	}

	/**@Override
	 * Add comment before class declaration
	 */
	public void visit(MethodDeclaration n) {
		this.createMultiLineComment("New method ".concat(n.f2.f0.tokenImage));
		++curLine;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
	}
	
	/**@Override
	 * Add comment before class declaration
	 */
	public void visit(NestedClassDeclaration n){
		this.createMultiLineComment("New nested class ".concat(n.f1.f1.tokenImage));
		++curLine;
		n.f0.accept(this);
		n.f1.accept(this);
	}
	
	//create multi-line comment
	private void createMultiLineComment(String s) {
		out.println("\n/*************");
		out.println(s);
		out.println("*************/");
		curColumn = 1;//reset curColumn
	}

	private void printToken(String s) {
		for (int i = 0; i < s.length(); ++i) {
			if (s.charAt(i) == '\n') {
				++curLine;
				curColumn = 1;
			} else
				curColumn++;

			out.print(s.charAt(i));
		}

		out.flush();
	}
}
