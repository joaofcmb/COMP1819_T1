.class public FindMaximum
.super java/lang/Object

.field public test_arr [I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 6
.limit locals 2
	new FindMaximum
	dup
	invokespecial FindMaximum/<init>()V
	astore 1
	aload 1
	invokevirtual FindMaximum/build_test_arr()I
	aload 1
	aload 1
	invokevirtual FindMaximum/get_array()[I
	invokevirtual FindMaximum/find_maximum([I)I
	invokestatic ioPlus/printResult(I)V
	return
.end method

.method public find_maximum([I)I
.limit stack 5
.limit locals 5
	ldc 1
	istore 2
	aload 1
	ldc 0
	iaload 
	istore 3
	goto L1
	L2:
	aload 1
	iload 2
	iaload 
	istore 4
	iload 3
	iload 4
	if_icmpge L4
	iload 4
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
	aload 1
	arraylength 
	if_icmplt L2
	iload 3
	ireturn 
.end method

.method public get_array()[I
.limit stack 2
.limit locals 1
	aload_0 
	getfield FindMaximum/test_arr [I
	areturn 
.end method

.method public build_test_arr()I
.limit stack 9
.limit locals 1
	aload_0 
	ldc 5
	newarray int 
	putfield FindMaximum/test_arr [I
	aload_0 
	getfield FindMaximum/test_arr [I
	ldc 0
	ldc 14
	iastore 
	aload_0 
	getfield FindMaximum/test_arr [I
	ldc 1
	ldc 28
	iastore 
	aload_0 
	getfield FindMaximum/test_arr [I
	ldc 2
	ldc 0
	iastore 
	aload_0 
	getfield FindMaximum/test_arr [I
	ldc 3
	ldc 0
	ldc 5
	isub 
	iastore 
	aload_0 
	getfield FindMaximum/test_arr [I
	ldc 4
	ldc 12
	iastore 
	ldc 0
	ireturn 
.end method
