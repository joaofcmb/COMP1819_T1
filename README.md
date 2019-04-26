**Instructions**

1. (Optional) Use the `compile.bat` script to compile the JMM Compiler (The repo already has a build with Java 11)
2. Use the `jmm.bat` script to run the JMM Compiler and generate a file with JVM instructions (`./jmm <input_file>`)
3. Assemble the generated file using jasmin (`java -jar jasmin.jar <gen_file>`)
4. Run the class file in the JVM (`java <class_file>`)
