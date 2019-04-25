package generation;

import semantic.FunctionTable;

abstract class RegisterAllocator {
    private int stackLimit, localLimit;

    abstract void allocate(FunctionTable method, int localVarStart);

    @Override
    public String toString() {
        return ".limit stack " + stackLimit + System.lineSeparator()
                + ".limit locals " + localLimit + System.lineSeparator();
    }
}
