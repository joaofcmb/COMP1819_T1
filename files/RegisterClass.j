.class public RegisterClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 2
.limit locals 3
	ldc 10
	ldc 20
	iadd 
	istore 2
	ldc 0
	istore 1
	goto L0
	L1:
	iload 2
	ldc 10
	iadd 
	ldc 10
	iadd 
	istore 2
	iload 1
	ldc 1
	iadd 
	istore 1
	L0:
	iload 1
	ldc 5
	if_icmplt L1
	iload 2
	invokestatic io/println(I)V
	return
.end method
