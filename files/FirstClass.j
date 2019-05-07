.class public FirstClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 3
.limit locals 3
	ldc 4
	istore 1
	iload 1
	ldc 2
	iadd 
	istore 2
	new FirstClass
	dup
	invokespecial FirstClass/<init>()V
	iload 1
	iload 2
	invokevirtual FirstClass/calc(II)I
	invokestatic io/println(I)V
	return
.end method

.method public calc(II)I
.limit stack 2
.limit locals 3
	iload 2
	iload 1
	imul 
	ldc 3
	idiv 
	ireturn 
.end method
