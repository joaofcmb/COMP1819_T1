.class public CondClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 16
.limit locals 1
	iconst_0 
	ifgt L2
	ldc 1
	invokestatic io/println(I)V
	ldc 2
	ldc 3
	iadd 
	ldc 5
	if_icmpge L4
	ldc 0
	ldc 2
	isub 
	invokestatic io/println(I)V
	goto L3
	L4:
	ldc 2
	invokestatic io/println(I)V
	iconst_1 
	ifeq L6
	ldc 6
	ldc 2
	idiv 
	ldc 4
	if_icmpge L6
	ldc 3
	invokestatic io/println(I)V
	ldc 4
	ldc 2
	imul 
	ldc 9
	if_icmpge L9
	ldc 2
	ldc 3
	if_icmpge L9
	iconst_0 
	ifeq L9
	goto L8
	L9:
	ldc 4
	invokestatic io/println(I)V
	ldc 4
	ldc 2
	imul 
	ldc 9
	if_icmpge L12
	ldc 2
	ldc 3
	if_icmpge L13
	iconst_1 
	ifeq L13
	goto L12
	L13:
	goto L11
	L12:
	ldc 4
	ldc 5
	if_icmpge L11
	iconst_0 
	ifgt L11
	ldc 5
	invokestatic io/println(I)V
	goto L10
	L11:
	ldc 0
	ldc 5
	isub 
	invokestatic io/println(I)V
	L10:
	goto L7
	L8:
	ldc 0
	ldc 4
	isub 
	invokestatic io/println(I)V
	L7:
	goto L5
	L6:
	ldc 0
	ldc 3
	isub 
	invokestatic io/println(I)V
	L5:
	L3:
	ldc 6
	invokestatic io/println(I)V
	goto L1
	L2:
	ldc 0
	ldc 1
	isub 
	invokestatic io/println(I)V
	L1:
	ldc 7
	invokestatic io/println(I)V
	return
.end method
