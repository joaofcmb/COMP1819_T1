package generation;

import semantic.FunctionTable;
import semantic.IntermediateInstruction;

import java.util.ArrayList;

public class NaiveRegisterAllocator implements RegisterAllocator {
    @Override
    public int allocate(FunctionTable method, int paramStart) {
        ArrayList<String> parameterVar = new ArrayList<>(method.getParameters().keySet());

        for (IntermediateInstruction instruction : method.getIntermediateInstructions()) {
            if (instruction.isLocal()) {
                String varIdentifier = instruction.getValue();

                if (!parameterVar.contains(varIdentifier))
                    parameterVar.add(varIdentifier);

                instruction.setVarNum(parameterVar.indexOf(varIdentifier) + paramStart);
            }
        }

        return parameterVar.size() + paramStart;
    }
}
