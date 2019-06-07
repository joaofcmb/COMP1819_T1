.class public RegisterClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 3
.limit locals 3
	bipush 10
	istore 1
	sipush 430
	istore 2
	new RegisterClass
	dup
	invokespecial RegisterClass/<init>()V
	bipush 10
	bipush 10
	invokevirtual RegisterClass/test(II)I
	istore 1
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
