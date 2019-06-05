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
	ldc 30
	istore 1
	ldc 0
	istore 2
	goto L0
	L1:
	ldc 10
	istore 3
	iload 1
	iload 3
	iadd 
	ldc 10
	iadd 
	istore 1
	iload 2
	ldc 1
	iadd 
	istore 2
	L0:
	iload 2
	ldc 5
	if_icmplt L1
	new RegisterClass
	dup
	invokespecial RegisterClass/<init>()V
	ldc 10
	ldc 10
	invokevirtual RegisterClass/test(II)I
	istore 3
	iload 1
	invokestatic io/println(I)V
	return
.end method

.method public test(II)I
.limit stack 2
.limit locals 3
	iload 1
	iload 2
	imul 
	ireturn 
.end method
