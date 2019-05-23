package generation;

import semantic.FunctionTable;
import semantic.IntermediateInstruction;
import semantic.IntermediateRepresentation;
import semantic.MethodSignature;
import semantic.Type;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class CodeGenerator {
    private final RegisterAllocator registerAllocator = new NaiveRegisterAllocator();
    private final IntermediateRepresentation ir;
    private final Path filePath;

    public CodeGenerator(IntermediateRepresentation ir, Path outputPath) {
        this.ir = ir;
        this.filePath = outputPath.resolve(ir.getClassIdentifier() + ".j");
    }

    public void generateFile() {
        try (PrintWriter pw = new PrintWriter(Files.newOutputStream(filePath))) {
            final String fileClass = ir.getClassIdentifier();
            final String superClass = ir.getExtendIdentifier() != null ? ir.getExtendIdentifier() : "java/lang/Object";

            pw.println(".class public " + fileClass);
            pw.println(".super " + superClass + System.lineSeparator());

            for (Map.Entry<String, Type> fieldEntry : ir.getAttributes().entrySet()) {
                pw.println(".field public '" + fieldEntry.getKey() + "' " + fieldEntry.getValue().toDescriptor());
            }

            pw.println(System.lineSeparator() + ".method public <init>()V");
            pw.println("\taload_0");
            pw.println("\tinvokenonvirtual " + superClass + "/<init>()V");
            pw.println("\treturn");
            pw.println(".end method");

            FunctionTable main = ir.getMain();
            if (main != null) {
                pw.println(System.lineSeparator() + ".method public static main([Ljava/lang/String;)V");
                generateMethod(pw, main, 0);
                pw.println("\treturn");
                pw.println(".end method");
            }

            for (Map.Entry<MethodSignature, FunctionTable> methodEntry : ir.getMethods().entrySet()) {
                pw.println(System.lineSeparator() + ".method public " + methodEntry.getKey().toDescriptor(null));
                generateMethod(pw, methodEntry.getValue(), 1);
                pw.println(".end method");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateMethod(PrintWriter pw, FunctionTable method, int paramStart) {
        pw.println(".limit stack " + stackSlots(method));
        pw.println(".limit locals " +  registerAllocator.allocate(method, paramStart));

        pw.print(method.methodCode());
    }

    private int stackSlots(FunctionTable method) {
        int stackSlots = 0, maxStackSlots = 0;

        for (IntermediateInstruction instruction : method.getIntermediateInstructions()) {
            stackSlots += instruction.stackSlots();

            if (stackSlots > maxStackSlots)
                maxStackSlots = stackSlots;
        }

        return maxStackSlots;
    }
}
