.class public Life
.super java/lang/Object

.field public 'REPRODUCE_NUM' I
.field public 'yMax' I
.field public 'field' [I
.field public 'OVERPOP_LIM' I
.field public 'UNDERPOP_LIM' I
.field public 'xMax' I
.field public 'LOOPS_PER_MS' I

.method public <init>()V
	aload_0
	invokenonvirtual java/lang/Object/<init>()V
	return
.end method

.method public static main([Ljava/lang/String;)V
.limit stack 7
.limit locals 3
	new Life
	dup
	invokespecial Life/<init>()V
	astore 2
	aload 2
	invokevirtual Life/init()Z
	pop 
	L1:
	aload 2
	invokevirtual Life/printField()Z
	pop 
	aload 2
	invokevirtual Life/update()Z
	pop 
	invokestatic io/read()I
	istore 1
	L0:
	iconst_1 
	ifgt L1
	return
.end method

.method public cartIdx(I)[I
.limit stack 4
.limit locals 5
	aload_0 
	getfield Life/xMax I
	bipush 1
	iadd 
	istore 3
	iload 1
	iload 3
	idiv 
	istore 4
	iload 1
	iload 4
	iload 3
	imul 
	isub 
	istore 3
	bipush 2
	newarray int 
	astore 2
	aload 2
	bipush 0
	iload 3
	iastore 
	aload 2
	bipush 1
	iload 4
	iastore 
	aload 2
	areturn 
.end method

.method public lt(II)Z
.limit stack 2
.limit locals 4
	iload 1
	iload 2
	if_icmpge L1
	iconst_1 
	ireturn 
	L1:
	iconst_0 
	L0:
	ireturn 
.end method

.method public field([I)[I
.limit stack 104
.limit locals 3
	aload_0 
	bipush 100
	newarray int 
	putfield Life/field [I
	aload 1
	bipush 0
	bipush 10
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 0
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 1
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 2
	bipush 1
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 3
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 4
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 5
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 6
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 7
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 8
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 9
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 10
	bipush 1
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 11
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 12
	bipush 1
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 13
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 14
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 15
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 16
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 17
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 18
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 19
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 20
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 21
	bipush 1
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 22
	bipush 1
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 23
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 24
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 25
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 26
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 27
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 28
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 29
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 30
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 31
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 32
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 33
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 34
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 35
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 36
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 37
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 38
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 39
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 40
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 41
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 42
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 43
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 44
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 45
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 46
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 47
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 48
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 49
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 50
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 51
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 52
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 53
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 54
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 55
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 56
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 57
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 58
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 59
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 60
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 61
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 62
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 63
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 64
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 65
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 66
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 67
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 68
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 69
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 70
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 71
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 72
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 73
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 74
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 75
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 76
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 77
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 78
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 79
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 80
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 81
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 82
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 83
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 84
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 85
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 86
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 87
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 88
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 89
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 90
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 91
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 92
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 93
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 94
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 95
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 96
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 97
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 98
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	bipush 99
	bipush 0
	iastore 
	aload_0 
	getfield Life/field [I
	areturn 
.end method

.method public gt(II)Z
.limit stack 3
.limit locals 4
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/le(II)Z
	ifgt L1
	iconst_1 
	ireturn 
	L1:
	iconst_0 
	L0:
	ireturn 
.end method

.method public getNeighborCoords(I)[I
.limit stack 19
.limit locals 7
	aload_0 
	iload 1
	invokevirtual Life/cartIdx(I)[I
	astore 2
	aload 2
	bipush 0
	iaload 
	istore 6
	aload 2
	bipush 1
	iaload 
	istore 5
	iload 6
	aload_0 
	getfield Life/xMax I
	if_icmpge L1
	aload_0 
	iload 6
	bipush 0
	invokevirtual Life/gt(II)Z
	ifeq L3
	iload 6
	bipush 1
	isub 
	istore 4
	goto L0
	L3:
	aload_0 
	getfield Life/xMax I
	istore 4
	L2:
	goto L0
	L1:
	iload 6
	bipush 1
	isub 
	istore 4
	L0:
	iload 5
	aload_0 
	getfield Life/yMax I
	if_icmpge L5
	aload_0 
	iload 5
	bipush 0
	invokevirtual Life/gt(II)Z
	ifeq L7
	iload 5
	bipush 1
	isub 
	istore 3
	goto L4
	L7:
	aload_0 
	getfield Life/yMax I
	istore 3
	L6:
	goto L4
	L5:
	iload 5
	bipush 1
	isub 
	istore 3
	L4:
	bipush 8
	newarray int 
	astore 2
	aload 2
	bipush 0
	aload_0 
	iload 6
	iload 3
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 2
	bipush 1
	aload_0 
	iload 4
	iload 3
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 2
	bipush 2
	aload_0 
	iload 4
	iload 5
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 2
	bipush 3
	aload_0 
	iload 4
	bipush 0
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 2
	bipush 4
	aload_0 
	iload 6
	bipush 0
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 2
	bipush 5
	aload_0 
	bipush 0
	bipush 0
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 2
	bipush 6
	aload_0 
	bipush 0
	iload 5
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 2
	bipush 7
	aload_0 
	bipush 0
	iload 3
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 2
	areturn 
.end method

.method public busyWait(I)Z
.limit stack 3
.limit locals 4
	iload 1
	aload_0 
	getfield Life/LOOPS_PER_MS I
	imul 
	istore 3
	bipush 0
	istore 2
	goto L0
	L1:
	iinc 2 1
	L0:
	iload 2
	iload 3
	if_icmplt L1
	iconst_1 
	ireturn 
.end method

.method public getLiveNeighborN(I)I
.limit stack 6
.limit locals 5
	bipush 0
	istore 4
	aload_0 
	iload 1
	invokevirtual Life/getNeighborCoords(I)[I
	astore 3
	bipush 0
	istore 2
	goto L0
	L1:
	aload_0 
	aload_0 
	getfield Life/field [I
	aload 3
	iload 2
	iaload 
	iaload 
	bipush 0
	invokevirtual Life/ne(II)Z
	ifeq L3
	iinc 4 1
	goto L2
	L3:
	L2:
	iinc 2 1
	L0:
	iload 2
	aload 3
	arraylength 
	if_icmplt L1
	iload 4
	ireturn 
.end method

.method public ge(II)Z
.limit stack 4
.limit locals 4
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/eq(II)Z
	ifgt L2
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/gt(II)Z
	ifgt L2
	goto L1
	L2:
	iconst_1 
	ireturn 
	L1:
	iconst_0 
	L0:
	ireturn 
.end method

.method public printField()Z
.limit stack 6
.limit locals 3
	bipush 0
	istore 2
	bipush 0
	istore 1
	goto L0
	L1:
	aload_0 
	iload 1
	aload_0 
	getfield Life/xMax I
	invokevirtual Life/gt(II)Z
	ifeq L3
	invokestatic io/println()V
	bipush 0
	istore 1
	goto L2
	L3:
	L2:
	aload_0 
	getfield Life/field [I
	iload 2
	iaload 
	invokestatic io/print(I)V
	iinc 2 1
	iinc 1 1
	L0:
	iload 2
	aload_0 
	getfield Life/field [I
	arraylength 
	if_icmplt L1
	invokestatic io/println()V
	invokestatic io/println()V
	iconst_1 
	ireturn 
.end method

.method public eq(II)Z
.limit stack 4
.limit locals 4
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/lt(II)Z
	ifgt L1
	aload_0 
	iload 2
	iload 1
	invokevirtual Life/lt(II)Z
	ifgt L1
	iconst_1 
	ireturn 
	L1:
	iconst_0 
	L0:
	ireturn 
.end method

.method public ne(II)Z
.limit stack 3
.limit locals 4
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/eq(II)Z
	ifgt L1
	iconst_1 
	ireturn 
	L1:
	iconst_0 
	L0:
	ireturn 
.end method

.method public le(II)Z
.limit stack 4
.limit locals 4
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/eq(II)Z
	ifgt L2
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/lt(II)Z
	ifgt L2
	goto L1
	L2:
	iconst_1 
	ireturn 
	L1:
	iconst_0 
	L0:
	ireturn 
.end method

.method public trIdx(II)I
.limit stack 4
.limit locals 4
	iload 1
	aload_0 
	getfield Life/xMax I
	bipush 1
	iadd 
	iload 2
	imul 
	iadd 
	ireturn 
.end method

.method public init()Z
.limit stack 11
.limit locals 3
	bipush 1
	newarray int 
	astore 2
	aload_0 
	bipush 2
	putfield Life/UNDERPOP_LIM I
	aload_0 
	bipush 3
	putfield Life/OVERPOP_LIM I
	aload_0 
	bipush 3
	putfield Life/REPRODUCE_NUM I
	aload_0 
	ldc 225000
	putfield Life/LOOPS_PER_MS I
	aload_0 
	aload_0 
	aload 2
	invokevirtual Life/field([I)[I
	putfield Life/field [I
	aload 2
	bipush 0
	iaload 
	istore 1
	aload_0 
	iload 1
	bipush 1
	isub 
	putfield Life/xMax I
	aload_0 
	aload_0 
	getfield Life/field [I
	arraylength 
	iload 1
	idiv 
	bipush 1
	isub 
	putfield Life/yMax I
	iconst_1 
	ireturn 
.end method

.method public update()Z
.limit stack 16
.limit locals 5
	aload_0 
	getfield Life/field [I
	arraylength 
	newarray int 
	astore 4
	bipush 0
	istore 3
	goto L0
	L1:
	aload_0 
	getfield Life/field [I
	iload 3
	iaload 
	istore 2
	aload_0 
	iload 3
	invokevirtual Life/getLiveNeighborN(I)I
	istore 1
	iload 2
	bipush 1
	if_icmplt L3
	aload_0 
	iload 1
	aload_0 
	getfield Life/UNDERPOP_LIM I
	invokevirtual Life/le(II)Z
	ifeq L5
	aload_0 
	iload 1
	aload_0 
	getfield Life/OVERPOP_LIM I
	invokevirtual Life/ge(II)Z
	ifeq L5
	iconst_1 
	goto L4
	L5:
	iconst_0 
	L4:
	istore 1
	iload 1
	ifgt L7
	aload 4
	iload 3
	bipush 0
	iastore 
	goto L2
	L7:
	aload 4
	iload 3
	aload_0 
	getfield Life/field [I
	iload 3
	iaload 
	iastore 
	L6:
	goto L2
	L3:
	aload_0 
	iload 1
	aload_0 
	getfield Life/REPRODUCE_NUM I
	invokevirtual Life/eq(II)Z
	ifeq L9
	aload 4
	iload 3
	bipush 1
	iastore 
	goto L8
	L9:
	aload 4
	iload 3
	aload_0 
	getfield Life/field [I
	iload 3
	iaload 
	iastore 
	L8:
	L2:
	iinc 3 1
	L0:
	iload 3
	aload_0 
	getfield Life/field [I
	arraylength 
	if_icmplt L1
	aload_0 
	aload 4
	putfield Life/field [I
	iconst_1 
	ireturn 
.end method
