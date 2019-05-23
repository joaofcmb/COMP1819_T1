.class public CondChild
.super CondClass


.method public <init>()V
	aload_0
	invokenonvirtual CondClass/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 6
.limit locals 5
	new CondChild
	dup
	invokespecial CondChild/<init>()V
	astore 1
	iconst_1 
	istore 2
	iconst_1 
	istore 3
	aload 1
	iload 2
	iload 3
	invokevirtual CondClass/compare(ZZ)Z
	ifgt L2
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	istore 4
	aload 1
	iload 2
	iload 3
	iload 4
	invokevirtual CondChild/epicCompare(ZZZ)Z
	ifeq L4
	ldc 1
	invokestatic io/println(I)V
	goto L3
	L4:
	aload 1
	iload 2
	iload 3
	invokevirtual CondClass/compare(ZZ)Z
	ifeq L6
	ldc 2
	invokestatic io/println(I)V
	goto L5
	L6:
	L5:
	L3:
	return
.end method

.method public epicCompare(ZZZ)Z
.limit stack 4
.limit locals 4
	aload_0 
	iload 1
	iload 2
	invokevirtual CondClass/compare(ZZ)Z
	ifeq L2
	aload_0 
	iload 2
	iload 3
	invokevirtual CondClass/compare(ZZ)Z
	ifeq L2
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	ireturn 
.end method
