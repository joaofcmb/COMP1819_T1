package generation;

import semantic.FunctionTable;
import semantic.IntermediateInstruction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

class GraphColoringAllocator implements RegisterAllocator {
    private final int maxRegisters;

    GraphColoringAllocator(int maxRegisters) {
        this.maxRegisters = maxRegisters;
    }

    @Override
    public int allocate(FunctionTable method, int paramStart) throws AllocationException {
        // Initialize Data
        final ArrayList<IntermediateInstruction> instructionList = new ArrayList<>(method.getIntermediateInstructions());
        final int instructionSize = instructionList.size();

        final Map<String, Integer> labelIndexes = labelIndexes(instructionList);

        final ArrayList<String> variableList = new ArrayList<>();
        final ArrayList<String> parameterList = new ArrayList<>(method.getParameters().keySet());

        final ArrayList<int[]> successorsList = new ArrayList<>(instructionSize);

        for (int i = 0; i < instructionSize; i++) {
            final IntermediateInstruction instruction = instructionList.get(i);

            if (instruction.isGoto()) {
                successorsList.add(i, new int[]{labelIndexes.get(instruction.getValue())});
            } else if (instruction.isBranch()) {
                successorsList.add(i, new int[]{i + 1, labelIndexes.get(instruction.getValue())});
            }
            else {
                successorsList.add(i, new int[]{i + 1});

                if (instruction.isLocal()) {
                    String varIdentifier = instruction.getValue();

                    if (!variableList.contains(varIdentifier) && !parameterList.contains(varIdentifier)) {
                        variableList.add(varIdentifier);
                    }
                }
            }
        }

        final int variableSize = variableList.size();

        // Calculate use and def sets
        final BitSet[] use = new BitSet[instructionSize];
        final BitSet[] def = new BitSet[instructionSize];

        for (int i = 0; i < instructionSize; i++) {
            use[i] = new BitSet(variableSize);
            def[i] = new BitSet(variableSize);

            final IntermediateInstruction instruction = instructionList.get(i);
            if (!variableList.contains(instruction.getValue())) continue;

            if (instruction.isStore()) {
                def[i].set(variableList.indexOf(instruction.getValue()));
            }
            else if (instruction.isLoad()) {
                use[i].set(variableList.indexOf(instruction.getValue()));
            }
        }

        //System.out.println(Arrays.toString(use));
        //System.out.println(Arrays.toString(def));

        // Calculate in and out sets
        final BitSet[] out = new BitSet[instructionSize];
        final BitSet[] in = new BitSet[instructionSize];

        for (int i = 0; i < instructionSize; i++) {
            out[i] = new BitSet(variableSize);
            in[i] = new BitSet(variableSize);
        }

        boolean notFinalIteration = true;
        while (notFinalIteration) {
            notFinalIteration = false;

            for (int i = instructionSize - 1; i >= 0; i--) {
                final BitSet currOut = new BitSet(variableSize);
                final BitSet currIn = (BitSet) use[i].clone();

                for (int successor : successorsList.get(i)) {
                    if (successor < instructionSize) {
                        currOut.or(in[successor]);
                    }
                }

                if (!currOut.equals(out[i]))    notFinalIteration = true;
                out[i] = (BitSet) currOut.clone();

                currOut.andNot(def[i]);
                currIn.or(currOut);

                if (!currIn.equals(in[i]))      notFinalIteration = true;
                in[i] = currIn;
            }
        }

        //System.out.println(Arrays.toString(out));
        //System.out.println(Arrays.toString(in));

        // Calculate interference graph
        final BitSet[] graph = new BitSet[variableSize];
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new BitSet(variableSize);
        }

        for (int i = 0; i < instructionSize; i++) {
            addToGraph(graph, out[i].stream().toArray());
            addToGraph(graph, in[i].stream().toArray());
        }

        //System.out.println(Arrays.toString(graph));

        // Register Allocation
        final ArrayDeque<Integer> stack = new ArrayDeque<>(variableSize);

        final BitSet[] tempGraph = Arrays.stream(graph).map(bitSet -> (BitSet) bitSet.clone()).toArray(BitSet[]::new);
        for (int i = 0; i < variableSize; i++) {
            final int candidateNode = IntStream.range(0, variableSize).boxed()
                    .min(Comparator.comparingInt(a -> tempGraph[a].cardinality())).get();

            stack.push(candidateNode);
            removeFromGraph(tempGraph, candidateNode);
        }

        // Allocate variables
        final int[] allocation = new int[variableSize];
        for (int i = 0; i < allocation.length; i++) {
            allocation[i] = -1;
        }

        //System.out.println(stack);
        while (!stack.isEmpty()) {
            final int node = stack.pop();

            allocation[node] = IntStream.range(0, variableSize)
                    .filter(value -> graph[node].stream().map(i1 -> allocation[i1]).noneMatch(i -> i == value))
                    .min().getAsInt();
        }


        System.out.println("Register Allocation");

        int n = 0;
        for (int i = 0; i < allocation.length; i++) {
            if (allocation[i] > n)  n = allocation[i];

            System.out.println("\t" + variableList.get(i) + " -> " + allocation[i]);
        }

        System.out.println("Number of Registers: " + (++n > 1 ? n : 0));

        if (n > maxRegisters)
            throw new AllocationException("ABORT: The input source code requires at least " + n + " local variables.");

        for (IntermediateInstruction instruction : instructionList) {
            if (instruction.isLocal()) {
                if (variableList.contains(instruction.getValue())) {
                    instruction.setVarNum(allocation[variableList.indexOf(instruction.getValue())]
                            + paramStart + parameterList.size());
                }
                else {
                    instruction.setVarNum(parameterList.indexOf(instruction.getValue()) + paramStart);
                }
            }
        }

        return paramStart + n + parameterList.size();
    }

    private void addToGraph(BitSet[] graph, int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                graph[array[i]].set(array[j]);
                graph[array[j]].set(array[i]);
            }
        }
    }

    private void removeFromGraph(BitSet[] graph, int candidateNode) {
        graph[candidateNode].stream().forEach(i -> {
            graph[i].set(candidateNode, false);
        });

        // Fill this graph node with all connections so the heuristic never chooses it again (Since at least this node
        // is removed, other nodes cannot have as many connections as this node).
        graph[candidateNode].set(0, graph[candidateNode].size());
    }

    private Map<String, Integer> labelIndexes(ArrayList<IntermediateInstruction> intermediateList) {
        final Map<String, Integer> labelIndexes = new HashMap<>();

        for (int i = 0; i < intermediateList.size(); i++) {
            final String label = intermediateList.get(i).getLabel();
            if (label != null) labelIndexes.put(label, i);
        }

        return labelIndexes;
    }
}
