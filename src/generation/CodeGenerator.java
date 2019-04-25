package generation;

import semantic.FunctionTable;
import semantic.IntermediateRepresentation;
import semantic.MethodSignature;
import semantic.Type;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class CodeGenerator {
    private final RegisterAllocator registerAllocator = new NaiveRegisterAllocator();
    private final IntermediateRepresentation ir;
    private final Path filePath;

    public CodeGenerator(IntermediateRepresentation ir, String fileName) {
        this.ir = ir;
        this.filePath = Paths.get(fileName + ".j");
    }

    public void generateFile() {
        try (PrintWriter pw = new PrintWriter(Files.newOutputStream(filePath))) {
            final String fileClass = ir.getClassIdentifier();
            final String superClass = ir.getExtendIdentifier() != null ? ir.getExtendIdentifier() : "java/lang/Object";

            pw.println(".class public " + fileClass);
            pw.println(".super " + superClass);

            pw.println(".method public <init>()V");
            pw.println("\taload_0");
            pw.println("\tinvokenonvirtual " + superClass + "<init>()V");
            pw.println("\treturn");
            pw.println(".end method" + System.lineSeparator());

            for (Map.Entry<String, Type> fieldEntry : ir.getAttributes().entrySet()) {
                pw.println(".field public " + fieldEntry.getKey() + " " + fieldEntry.getValue().toDescriptor());
            }

            FunctionTable main = ir.getMain();
            if (main != null) {
                pw.println(System.lineSeparator() + ".method public main([Ljava/lang/String;)V");
                generateMethod(pw, main, 0);
                pw.println(".end method");
            }

            for (Map.Entry<MethodSignature, FunctionTable> methodEntry : ir.getMethods().entrySet()) {
                pw.println(System.lineSeparator() + ".method public " + methodEntry.getKey().toDescriptor(fileClass));
                generateMethod(pw, methodEntry.getValue(), 1);
                pw.println(".end method" + System.lineSeparator());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateMethod(PrintWriter pw, FunctionTable method, int localVarStart) {
        registerAllocator.allocate(method, localVarStart);
        pw.println(registerAllocator); // Print the stack and local limits


    }
}
