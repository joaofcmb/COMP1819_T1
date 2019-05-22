.class public Quicksort
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 4
.limit locals 4
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
	new Quicksort
	dup
	invokespecial Quicksort/<init>()V
	astore 3
	aload 3
	aload 1
	invokevirtual Quicksort/quicksort([I)Z
	pop 
	aload 3
	aload 1
	invokevirtual Quicksort/printL([I)Z
	pop 
	return
.end method

.method public quicksort([I)Z
.limit stack 5
.limit locals 2
	aload_0 
	aload 1
	ldc 0
	aload 1
	arraylength 
	ldc 1
	isub 
	invokevirtual Quicksort/quicksort([III)Z
	ireturn 
.end method

.method public quicksort([III)Z
.limit stack 7
.limit locals 5
	iload 2
	iload 3
	if_icmpge L2
	aload_0 
	aload 1
	iload 2
	iload 3
	invokevirtual Quicksort/partition([III)I
	istore 4
	aload_0 
	aload 1
	iload 2
	iload 4
	ldc 1
	isub 
	invokevirtual Quicksort/quicksort([III)Z
	pop 
	aload_0 
	aload 1
	iload 4
	ldc 1
	iadd 
	iload 3
	invokevirtual Quicksort/quicksort([III)Z
	pop 
	goto L1
	L2:
	L1:
	iconst_1 
	ireturn 
.end method

.method public printL([I)Z
.limit stack 2
.limit locals 3
	ldc 0
	istore 2
	goto L1
	L2:
	aload 1
	iload 2
	iaload 
	invokestatic io/println(I)V
	iload 2
	ldc 1
	iadd 
	istore 2
	L1:
	iload 2
	aload 1
	arraylength 
	if_icmplt L2
	iconst_1 
	ireturn 
.end method

.method public partition([III)I
.limit stack 4
.limit locals 8
	aload 1
	iload 3
	iaload 
	istore 4
	iload 2
	istore 5
	iload 2
	istore 6
	goto L1
	L2:
	aload 1
	iload 6
	iaload 
	iload 4
	if_icmpge L4
	aload 1
	iload 5
	iaload 
	istore 7
	aload 1
	iload 5
	aload 1
	iload 6
	iaload 
	iastore 
	aload 1
	iload 6
	iload 7
	iastore 
	iload 5
	ldc 1
	iadd 
	istore 5
	goto L3
	L4:
	L3:
	iload 6
	ldc 1
	iadd 
	istore 6
	L1:
	iload 6
	iload 3
	if_icmplt L2
	aload 1
	iload 5
	iaload 
	istore 7
	aload 1
	iload 5
	aload 1
	iload 3
	iaload 
	iastore 
	aload 1
	iload 3
	iload 7
	iastore 
	iload 5
	ireturn 
.end method
