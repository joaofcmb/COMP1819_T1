.class public Test
.super Base

.field public x I
.field public check Z

.method public <init>()V
	aload_0
	invokenonvirtual Base<init>()V
	return
.end method

.method public main([Ljava/lang/String;)V
.limit locals 2
	new Test
	iload 1
	invokevirtual Test/computeFac(I)I
.end method

.method public Test/computeFac(I[I)I
.limit locals 5
	aload_0 
	aload_0 
	invokevirtual Test/gimmeFive()I
	new Text
	ldc 1
	invokevirtual Text/f(I)I
	newarray int 
	invokevirtual Test/computeFac(I[I)I
	newarray int 
	arraylength 
	istore 3
	iload 4
	new Text
	invokevirtual Text/gimmeFive()I
	iadd 
	istore 3
	iload 3
	new Test
	invokevirtual Test/gimmeFive()I
	imul 
	aload_0 
	iload 3
	invokevirtual Test/computeFac(I)I
	iadd 
	istore 4
	iload 4
	ldc 3
	iadd 
	istore 4
	aload_0 
	aload 2
	aload_0 
	invokevirtual Test/gimmeFive()I
	ARRAYLOAD 
	invokevirtual Test/computeFac(I)I
	iload 4
	ldc 2
	ldc 6
	imul 
	iadd 
	astore 3
	iload 4
	ldc 5
	ldc 4
	imul 
	ldc 2
	aload 2
	ldc 3
	ARRAYLOAD 
	ldc 4
	idiv 
	iadd 
	idiv 
	iadd 
	ireturn 
.end method

.method public Test/gimmeFive()I
.limit locals 1
	ldc 5
	ireturn 
.end method

.method public Test/computeFac(I)I
.limit locals 2
	aload_0 
	iload 1
	ldc 3
	newarray int 
	invokevirtual Test/computeFac(I[I)I
	ireturn 
.end method
