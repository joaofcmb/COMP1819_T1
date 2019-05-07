.class public ArrayClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 5
.limit locals 3
	ldc 1
	newarray int 
	astore 1
	aload 1
	arraylength 
	ldc 1
	iadd 
	newarray int 
	astore 2
	aload 1
	ldc 0
	ldc 4
	iastore 
	aload 2
	ldc 0
	ldc 0
	ldc 3
	isub 
	aload 1
	ldc 0
	iaload 
	iadd 
	iastore 
	aload 2
	aload 2
	ldc 0
	iaload 
	aload 1
	ldc 0
	iaload 
	aload 2
	ldc 0
	iaload 
	iadd 
	ldc 2
	imul 
	iastore 
	new ArrayClass
	dup
	invokespecial ArrayClass/<init>()V
	aload 1
	aload 2
	invokevirtual ArrayClass/calc([I[I)I
	invokestatic io/println(I)V
	aload 0
	ldc 0
	aaload 
	invokestatic io/println(Ljava/lang/String;)V
	return
.end method

.method public calc([I[I)I
.limit stack 3
.limit locals 3
	aload 2
	arraylength 
	aload 2
	ldc 1
	iaload 
	imul 
	aload 1
	ldc 0
	iaload 
	idiv 
	ireturn 
.end method
