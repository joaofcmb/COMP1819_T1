package generation;

import semantic.FunctionTable;

interface RegisterAllocator {
    int allocate(FunctionTable method, int localVarStart) throws AllocationException;
}
