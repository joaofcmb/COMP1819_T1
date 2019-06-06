.class public CondClass
.super java/lang/Object


.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 5
.limit locals 2
	bipush 1
	invokestatic io/println(I)V
	bipush 2
	invokestatic io/println(I)V
	bipush 3
	invokestatic io/println(I)V
	bipush 4
	invokestatic io/println(I)V
	bipush 5
	invokestatic io/println(I)V
	bipush 6
	invokestatic io/println(I)V
	bipush 7
	invokestatic io/println(I)V
	bipush 0
	istore 1
	goto L13
	L14:
	bipush 8
	iload 1
	iadd 
	invokestatic io/println(I)V
	iinc 1 1
	L13:
	bipush 4
	bipush 2
	imul 
	bipush 9
	if_icmpge L16
	bipush 2
	bipush 3
	if_icmpge L17
	iconst_1 
	ifeq L17
	goto L16
	L17:
	goto L15
	L16:
	iload 1
	bipush 5
	if_icmpge L15
	iconst_0 
	ifgt L15
	goto L14
	L15:
	bipush 4
	bipush 2
	imul 
	bipush 9
	if_icmpge L20
	bipush 2
	bipush 3
	if_icmpge L21
	iconst_1 
	ifeq L21
	goto L20
	L21:
	goto L19
	L20:
	bipush 4
	bipush 5
	if_icmpge L19
	iconst_0 
	ifgt L19
	iconst_1 
	goto L18
	L19:
	iconst_0 
	L18:
	istore 1
	new CondClass
	dup
	invokespecial CondClass/<init>()V
	iload 1
	bipush 4
	bipush 2
	imul 
	bipush 9
	if_icmpge L24
	bipush 2
	bipush 3
	if_icmpge L25
	iconst_1 
	ifeq L25
	goto L24
	L25:
	goto L23
	L24:
	bipush 4
	bipush 5
	if_icmpge L23
	iconst_0 
	ifgt L23
	iconst_1 
	goto L22
	L23:
	iconst_0 
	L22:
	invokevirtual CondClass/compare(ZZ)Z
	istore 1
	iload 1
	ifeq L27
	bipush 13
	invokestatic io/println(I)V
	goto L26
	L27:
	bipush -13
	invokestatic io/println(I)V
	L26:
	return
.end method

.method public compare(ZZ)Z
.limit stack 2
.limit locals 4
	iload 1
	ifeq L1
	iload 2
	ifeq L1
	iconst_1 
	ireturn 
	L1:
	iconst_0 
	L0:
	ireturn 
.end method
