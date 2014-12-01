//
// A modified version of the main() method found in the Java 1.1 grammar.
// To add your visitors to the program, go to near the end, and copy or
// replace the line "root.accept(new Visitor());" with your own visitor
//

import syntaxtree.*;
import visitor.*;

public class Main {
   public static void main(String args[]) {
      JavaParser parser;
      if (args.length == 0) {
         System.err.println("JTB Pretty Printer 1.1:  Reading from standard " +
                            "input . . .");
         parser = new JavaParser(System.in);
      }
      else if (args.length == 1) {
         System.err.println("JTB Pretty Printer 1.1:  Reading from file " +
                            args[0] + " . . .");
         try { parser = new JavaParser(new java.io.FileInputStream(args[0])); }
         catch (java.io.FileNotFoundException e) {
            System.err.println("JTB Pretty Printer 1.1:  File " + args[0] +
                               " not found.");
            return;
         }
      }
      else {
         System.err.println("JTB Pretty Printer 1.1:  Usage is one of:");
         System.err.println("         java JavaParser < inputfile");
         System.err.println("OR");
         System.err.println("         java JavaParser inputfile");
         return;
      }

      try {
         //
         // Here's where the AST actions and visiting take place.
         //
         Node root = parser.CompilationUnit();
         System.err.println("JTB Pretty Printer 1.1:  Java program parsed " +
                            "successfully.");

/**** Memory tests--ignore this section ****
         Runtime rt = Runtime.getRuntime();
         rt.gc();
         System.out.println("Total Memory in VM: " +
                            Long.toString(rt.totalMemory()));
         System.out.println("Total Free Memory:  " +
                            Long.toString(rt.freeMemory()));
         try { System.in.read(); }
         catch (Exception e) { }
 **** Memory tests--ignore this section ****/

         //
         // Dump the source code exactly as it was read in
         //
         final TreeDumper dumper = new TreeDumper();

         System.out.println("******************");
         System.out.println("Before formatting");
         System.out.println("******************");
         root.accept(dumper);

         //
         // Format the source code in the syntax tree, then dump it
         //
         System.out.println("******************");
         System.out.println("After formatting");
         System.out.println("******************");
         root.accept(new TreeFormatter(3, 80));
         dumper.resetPosition();
         root.accept(dumper);

         //
         // An example use of TreeFormatter and TreeDumper:
         // Print only method declarations (without method bodies)
         //
         System.out.println("******************");
         System.out.println("Print Only Methods");
         System.out.println("******************");
         dumper.printSpecials(false);
         root.accept(new DepthFirstVisitor() {
            public void visit(MethodDeclaration n) {
               dumper.startAtNextToken();
               n.f0.accept(dumper);
               n.f1.accept(dumper);
               n.f2.accept(dumper);
               n.f3.accept(dumper);             // skip n.f4, the method body
               System.out.println();
            }
         });
      }
      catch (ParseException e) {
         System.err.println(e.getMessage());
         System.err.println("JTB Pretty Printer 1.1:  Encountered errors " +
                            "during parse.");
      }
   }
}
