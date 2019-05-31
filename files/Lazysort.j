.class public Lazysort
.super Quicksort


.method public <init>()V
	aload_0
	invokenonvirtual Quicksort/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 4
.limit locals 5
	ldc 10
	newarray int 
	astore 1
	ldc 0
	istore 2
	goto L1
	L2:
	aload 1
	iload 2
	aload 1
	arraylength 
	iload 2
	isub 
	iastore 
	iload 2
	ldc 1
	iadd 
	istore 2
	L1:
	iload 2
	aload 1
	arraylength 
	if_icmplt L2
	new Lazysort
	dup
	invokespecial Lazysort/<init>()V
	astore 3
	aload 3
	aload 1
	invokevirtual Lazysort/quicksort([I)Z
	pop 
	aload 3
	aload 1
	invokevirtual Quicksort/printL([I)Z
	istore 4
	return
.end method

.method public quicksort([I)Z
.limit stack 8
.limit locals 3
	ldc 0
	ldc 5
	invokestatic MathUtils/random(II)I
	ldc 4
	if_icmpge L2
	aload_0 
	aload 1
	invokevirtual Lazysort/beLazy([I)Z
	pop 
	iconst_1 
	istore 2
	goto L1
	L2:
	iconst_0 
	istore 2
	L1:
	iload 2
	ifeq L4
	iload 2
	ifgt L6
	iconst_1 
	goto L5
	L6:
	iconst_0 
	L5:
	istore 2
	goto L3
	L4:
	aload_0 
	aload 1
	ldc 0
	aload 1
	arraylength 
	ldc 1
	isub 
	invokevirtual Quicksort/quicksort([III)Z
	istore 2
	L3:
	iload 2
	ireturn 
.end method

.method public beLazy([I)Z
.limit stack 4
.limit locals 4
	aload 1
	arraylength 
	istore 2
	ldc 0
	istore 3
	goto L1
	L2:
	aload 1
	iload 3
	ldc 0
	ldc 10
	invokestatic MathUtils/random(II)I
	iastore 
	iload 3
	ldc 1
	iadd 
	istore 3
	L1:
	iload 3
	iload 2
	ldc 2
	idiv 
	if_icmplt L2
	goto L3
	L4:
	aload 1
	iload 3
	ldc 0
	ldc 10
	invokestatic MathUtils/random(II)I
	ldc 1
	iadd 
	iastore 
	iload 3
	ldc 1
	iadd 
	istore 3
	L3:
	iload 3
	iload 2
	if_icmplt L4
	iconst_1 
	ireturn 
.end method
