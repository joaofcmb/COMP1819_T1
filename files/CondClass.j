.class public CondClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 5
.limit locals 3
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
	ldc 0
	istore 1
	goto L14
	L15:
	ldc 8
	iload 1
	iadd 
	invokestatic io/println(I)V
	iload 1
	ldc 1
	iadd 
	istore 1
	L14:
	ldc 4
	ldc 2
	imul 
	ldc 9
	if_icmpge L17
	ldc 2
	ldc 3
	if_icmpge L18
	iconst_1 
	ifeq L18
	goto L17
	L18:
	goto L16
	L17:
	iload 1
	ldc 5
	if_icmpge L16
	iconst_0 
	ifgt L16
	goto L15
	L16:
	ldc 4
	ldc 2
	imul 
	ldc 9
	if_icmpge L21
	ldc 2
	ldc 3
	if_icmpge L22
	iconst_1 
	ifeq L22
	goto L21
	L22:
	goto L20
	L21:
	ldc 4
	ldc 5
	if_icmpge L20
	iconst_0 
	ifgt L20
	iconst_1 
	goto L19
	L20:
	iconst_0 
	L19:
	istore 2
	new CondClass
	dup
	invokespecial CondClass/<init>()V
	iload 2
	ldc 4
	ldc 2
	imul 
	ldc 9
	if_icmpge L25
	ldc 2
	ldc 3
	if_icmpge L26
	iconst_1 
	ifeq L26
	goto L25
	L26:
	goto L24
	L25:
	ldc 4
	ldc 5
	if_icmpge L24
	iconst_0 
	ifgt L24
	iconst_1 
	goto L23
	L24:
	iconst_0 
	L23:
	invokevirtual CondClass/compare(ZZ)Z
	istore 2
	iload 2
	ifeq L28
	ldc 13
	invokestatic io/println(I)V
	goto L27
	L28:
	ldc 0
	ldc 13
	isub 
	invokestatic io/println(I)V
	L27:
	return
.end method

.method public compare(ZZ)Z
.limit stack 2
.limit locals 3
	iload 1
	ifeq L2
	iload 2
	ifeq L2
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	ireturn 
.end method
