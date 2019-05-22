.class public MonteCarloPi
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 2
.limit locals 3
	invokestatic ioPlus/requestNumber()I
	istore 1
	new MonteCarloPi
	dup
	invokespecial MonteCarloPi/<init>()V
	iload 1
	invokevirtual MonteCarloPi/estimatePi100(I)I
	istore 2
	iload 2
	invokestatic ioPlus/printResult(I)V
	return
.end method

.method public performSingleEstimate()Z
.limit stack 3
.limit locals 5
	ldc 0
	ldc 100
	isub 
	ldc 100
	invokestatic MathUtils/random(II)I
	istore 1
	ldc 0
	ldc 100
	isub 
	ldc 100
	invokestatic MathUtils/random(II)I
	istore 2
	iload 1
	iload 1
	imul 
	iload 2
	iload 2
	imul 
	iadd 
	ldc 100
	idiv 
	istore 3
	iload 3
	ldc 100
	if_icmpge L2
	iconst_1 
	istore 4
	goto L1
	L2:
	iconst_0 
	istore 4
	L1:
	iload 4
	ireturn 
.end method

.method public estimatePi100(I)I
.limit stack 6
.limit locals 5
	ldc 0
	istore 2
	ldc 0
	istore 3
	goto L1
	L2:
	aload_0 
	invokevirtual MonteCarloPi/performSingleEstimate()Z
	ifeq L4
	iload 3
	ldc 1
	iadd 
	istore 3
	goto L3
	L4:
	L3:
	iload 2
	ldc 1
	iadd 
	istore 2
	L1:
	iload 2
	iload 1
	if_icmplt L2
	ldc 400
	iload 3
	imul 
	iload 1
	idiv 
	istore 4
	iload 4
	ireturn 
.end method
