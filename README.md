# Java-- Language to Java Bytecodes Compiler

## Group 16

- **César Alexandre da Costa Pinho** - up201604039 - Grade **20** - **33%** Contribution
- **João Fernando da Costa Meireles Barbosa** - up201604156 - Grade **20** - **33%** Contribution
- **Rui Jorge Leão Guedes** - up201603854** - Grade **20** - **33%** Contribution

Global Grade of the Project: **20**

## Summary

This tool translates a single source file in **Java--** (A Java Language subset) into **JVM Bytecodes**. When the input source file has external dependencies, the tool infers related information using the source file itself, assuming that information to be, therefore, correct.

The generated Bytecodes are presented in a syntax readable by [Jasmin](http://jasmin.sourceforge.net/), an assembler for the JVM which converts these Bytecodes into binary Java class files, executable by a **Java Runtime Environment**.

This repository included a directory `files`, with multiple test files, for evaluating the Compiler's functionality.

### Features

* Translation of a Java-- source file to JVM Bytecodes.
* Graph Coloring Register Allocation (option `-r=n`)
* Optimizations
    * Optimized JVM opcodes for Constant Loading
    * Usage of `iinc` instruction and Branch Elimination (Code Cleanup)
    * **option `-o`:** Static evaluation of integer expressions and conditions
        * Constant Propagation and Folding
        * Optimized `if` constructs (No branching)
        * Optimized `while` constructs (Skip first `goto` to the condition, going straight to loop body or to the next statement depending on result)
    * **option `-o`:** Loop Unrolling and Instruction Combining

## Execute

### Requirements

- JDK 9+ **OR** JRE 11+ (Pre-compiled build)
- [Jasmin Assembler](http://jasmin.sourceforge.net/)

### Compiling and Executing a Java-- program

1. (Optional) Use the `compile.bat` script to compile the JMM Compiler
2. Run the JMM Compiler and generate a file with JVM instructions using the `jmm.bat` script to run the build (`./jmm [-r=<num>] [-o] <input_file>`) or using the provided jarfile (`java -jar jmm.jar [-r=<num>] [-o] <input_file>`)
3. Assemble the generated file using jasmin (`java -jar jasmin.jar <gen_file>`)
4. Run the class file in the JRE (`java <class_file>`)

## Syntactic Errors

The compiler is capable of recovering from syntactic errors on `while` statements. The test file `WhileTest.jmm` showcases this behavior, consisting of nested `while` loops. The compiler is capable of successfully recovering from the first 5 `while` loops

## Semantic Analysis

Before fully generating an **Intermediate Representation** of the program, the Compiler performs a series of semantic checks:

* Existence and non duplication of the referenced Variable and Parameter Identifiers within that scope
* Non duplicate definition of Variable and Parameter Identifiers within a scope
* Type checking in Operations, Assignments and Conditions
* Existence of the methods being called, belonging to that class
* Non duplicate definition of methods (Like in Java, a method's signature is characterized by its identifier and the type of its parameters)

## Intermediate Representation

The generated Intermediate Representation, representing the Class in the input source file, is consisted of a **Symbol Table** for that class' fields, and a **Function Table** for each method.

A **Function Table** contains an ordered **Symbol Table** for its parameters, A **Symbol Table** for its local variables, its return **Type** and an Ordered List of **Intermediate Instructions**

The Intermediate Representation uses a **stack-based representation** for the Intermediate Code, similar to the final output for the JVM. Therefore, the **Intermediate Code** contains low-level instructions for control flow, despite the variables still being referred by its **Identifier**.

## Code Generation

The Compiler, using the generated **Intermediate Representation**, is capable of generating the code straightforwardly. Since the **Intermediate Code** is in a similar structure as the targeted output structure, its conversion corresponds to a simple association of each **Intermediate Instruction** to its JVM counterpart (In fact, this association is already defined at the creation of that **Intermediate Instruction**).

The only tasks left to be done at this stage are the **allocation** of JVM local variables (Using a naive or graph-coloring algorithm) and the calculation of the maximum stack size for each method (maximum of a cumulative sum).

## Overview

The Compiler's source code is divided in three packages:

### parser

Contains the **Lexical and Semantic Analysis** of the source code, mostly constituted of generated code by [JJTree](https://javacc.org/jjtree) and [JavaCC](https://javacc.org/).

**Output:** Abstract Syntax Tree

### semantic

Creates an **Intermediate Representation** of the AST, performing a series of **semantic checks** before its generation.

During the generation of the IR, the optimizations regarding the usage of optimized constant opcodes and the static evaluation
of expressions and conditions (**Constant propagation** and **folding**) are performed.

**Output:** Stack-based Intermediate Representation

### generation

Generates bytecode from the IR, to be used as input by the [Jasmin assembler](http://jasmin.sourceforge.net/).

Performs **register allocation** (naive or graph-coloring), precise maximum stack size calculation and the Code Cleanup (`iinc` + **Branch Elimination**), **Loop Unrolling** and **Instruction Combining** optimizations.  

**Output:** JVM bytecode (Jasmin syntax)

## Task Distribution

- **Lexical and Syntactic Analysis -** César Pinho and Rui Guedes
- **Semantic Analysis and Intermediate Code -** João Barbosa and Rui Guedes
- **Code Generation and Optimizations -** César Pinho and João Barbosa

## Pros

- Usage of a single, stack-based IR extremely similar to the output, resulting in an efficient compiler

- Usage of Java's [Method Signature](https://docs.oracle.com/javase/specs/jls/se12/html/jls-8.html#jls-8.4.2) to identify methods, therefore allowing for **Method Overloading**

- Usage of `iinc`, `bipush` and `sipush` opcodes wherever appropriate and **Branch Elimination**

- `-r=n` option, enabling and enhanced register allocation using **Dataflow Analysis** and a simplified **Graph-Coloring Algorithm** (Simplify + Select)

- `-o` optimization suite, enabling static evaluation of conditions and scalar expressions, resulting in **Constant Propagation**, **Constant Folding** and optimized `if` and `while` templates

- `-o` optimization suite, performing **Loop Unrolling** and **Instruction Combining** in iterations

## Cons

- Due to the usage of a single IR, extremely close to the final output, a lot of compilation tasks are concentrated on this stage, reducing the modularity of the compiler, making its extension more difficult.  

- Semantic Analysis does not include variable initialization checks before its usage

- Due to lack of **Dataflow Analysis** at the early compilation stage of static evaluation of conditions and expressions, it was not possible to implement a sophisticated **Constant Propagation** optimization to make better use of the capabilities of this static evaluation. 

- **Loop Unrolling** and **Instruction Combining** are performed after **Register Allocation** and the **Static Evaluation**. Since whole loops can be erased from both these optimizations, some variables may no longer be used and it may be possible to extract more information for the **Static Evaluation** 