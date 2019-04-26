.class public FirstClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object<init>()V
	return
.end method

.method public main([Ljava/lang/String;)V
.limit locals 3
	ldc 4
	istore 1
	iload 1
	ldc 2
	iadd 
	istore 2
	new FirstClass
	iload 1
	iload 2
	invokevirtual FirstClass/calc(II)I
	invokestatic io/println(I)
.end method

.method public FirstClass/calc(II)I
.limit locals 3
	iload 2
	iload 1
	imul 
	ldc 3
	idiv 
	ireturn 
.end method
