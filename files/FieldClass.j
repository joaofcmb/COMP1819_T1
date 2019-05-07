.class public FieldClass
.super java/lang/Object

.field public x I
.field public y I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 3
.limit locals 3
	new FieldClass
	dup
	invokespecial FieldClass/<init>()V
	astore 1
	aload 1
	ldc 4
	ldc 6
	invokevirtual FieldClass/set(II)I
	istore 2
	aload 1
	invokevirtual FieldClass/calc()I
	invokestatic io/println(I)V
	return
.end method

.method public set(II)I
.limit stack 3
.limit locals 3
	aload_0 
	iload 1
	putfield FieldClass/x I
	aload_0 
	iload 2
	putfield FieldClass/y I
	ldc 1
	ireturn 
.end method

.method public calc()I
.limit stack 4
.limit locals 1
	aload_0 
	getfield FieldClass/y I
	aload_0 
	getfield FieldClass/x I
	imul 
	ldc 3
	idiv 
	ireturn 
.end method
