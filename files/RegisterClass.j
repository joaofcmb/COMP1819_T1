.class public RegisterClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 3
.limit locals 4
	bipush 10
	istore 3
	bipush 30
	istore 2
	iload 2
	iload 3
	iadd 
	bipush 10
	iadd 
	istore 2
	iload 2
	iload 3
	iadd 
	bipush 10
	iadd 
	istore 2
	iload 2
	iload 3
	iadd 
	bipush 10
	iadd 
	istore 2
	iload 2
	iload 3
	iadd 
	bipush 10
	iadd 
	istore 2
	iload 2
	iload 3
	iadd 
	bipush 10
	iadd 
	istore 2
	new RegisterClass
	dup
	invokespecial RegisterClass/<init>()V
	bipush 10
	bipush 10
	invokevirtual RegisterClass/test(II)I
	istore 3
	iload 2
	invokestatic io/println(I)V
	return
.end method

.method public test(II)I
.limit stack 2
.limit locals 4
	iload 1
	iload 2
	imul 
	ireturn 
.end method
