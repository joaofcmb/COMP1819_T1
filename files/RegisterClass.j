.class public RegisterClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 2
.limit locals 5
	ldc 10
	istore 4
	ldc 20
	istore 1
	iload 4
	iload 1
	iadd 
	istore 3
	ldc 0
	istore 2
	goto L0
	L1:
	ldc 10
	istore 1
	iload 3
	iload 1
	iadd 
	iload 4
	iadd 
	istore 3
	iload 2
	ldc 1
	iadd 
	istore 2
	L0:
	iload 2
	ldc 5
	if_icmplt L1
	iload 3
	invokestatic io/println(I)V
	return
.end method
