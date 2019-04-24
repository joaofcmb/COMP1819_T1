package generation;

import semantic.FunctionTable;
import semantic.IntermediateRepresentation;
import semantic.MethodSignature;

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
            pw.println("aload_0");
            pw.println("invokenonvirtual " + superClass + "<init>()V");
            pw.println("return");

            FunctionTable main = ir.getMain();
            if (main != null) {
                pw.println(".method public main([Ljava/lang/String;)V");
                generateMethod(main, 0);
                pw.println(".end method");
            }

            for (Map.Entry<MethodSignature, FunctionTable> methodEntry : ir.getMethods().entrySet()) {
                pw.println(".method public " + methodEntry.getKey().toDescriptor(fileClass));
                generateMethod(methodEntry.getValue(), 1);
                pw.println(".end method");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateMethod(FunctionTable method, int localVarStart) {

    }
}
