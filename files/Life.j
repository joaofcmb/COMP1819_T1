.class public Life
.super java/lang/Object

.field public REPRODUCE_NUM I
.field public yMax I
.field public field [I
.field public OVERPOP_LIM I
.field public UNDERPOP_LIM I
.field public xMax I
.field public LOOPS_PER_MS I

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
	astore 1
	aload 1
	invokevirtual Life/init()Z
	pop 
	goto L1
	L2:
	aload 1
	invokevirtual Life/printField()Z
	pop 
	aload 1
	invokevirtual Life/update()Z
	pop 
	invokestatic io/read()I
	istore 2
	L1:
	iconst_1 
	ifgt L2
	return
.end method

.method public cartIdx(I)[I
.limit stack 4
.limit locals 6
	aload_0 
	getfield Life/xMax I
	ldc 1
	iadd 
	istore 2
	iload 1
	iload 2
	idiv 
	istore 3
	iload 1
	iload 3
	iload 2
	imul 
	isub 
	istore 4
	ldc 2
	newarray int 
	astore 5
	aload 5
	ldc 0
	iload 4
	iastore 
	aload 5
	ldc 1
	iload 3
	iastore 
	aload 5
	areturn 
.end method

.method public lt(II)Z
.limit stack 2
.limit locals 3
	iload 1
	iload 2
	if_icmpge L2
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	ireturn 
.end method

.method public field([I)[I
.limit stack 104
.limit locals 2
	aload_0 
	ldc 100
	newarray int 
	putfield Life/field [I
	aload 1
	ldc 0
	ldc 10
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 0
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 1
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 2
	ldc 1
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 3
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 4
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 5
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 6
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 7
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 8
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 9
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 10
	ldc 1
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 11
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 12
	ldc 1
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 13
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 14
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 15
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 16
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 17
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 18
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 19
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 20
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 21
	ldc 1
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 22
	ldc 1
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 23
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 24
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 25
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 26
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 27
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 28
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 29
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 30
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 31
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 32
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 33
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 34
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 35
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 36
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 37
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 38
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 39
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 40
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 41
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 42
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 43
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 44
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 45
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 46
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 47
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 48
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 49
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 50
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 51
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 52
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 53
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 54
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 55
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 56
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 57
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 58
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 59
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 60
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 61
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 62
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 63
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 64
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 65
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 66
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 67
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 68
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 69
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 70
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 71
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 72
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 73
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 74
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 75
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 76
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 77
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 78
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 79
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 80
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 81
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 82
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 83
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 84
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 85
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 86
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 87
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 88
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 89
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 90
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 91
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 92
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 93
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 94
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 95
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 96
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 97
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 98
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	ldc 99
	ldc 0
	iastore 
	aload_0 
	getfield Life/field [I
	areturn 
.end method

.method public gt(II)Z
.limit stack 3
.limit locals 3
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/le(II)Z
	ifgt L2
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	ireturn 
.end method

.method public getNeighborCoords(I)[I
.limit stack 19
.limit locals 10
	aload_0 
	iload 1
	invokevirtual Life/cartIdx(I)[I
	astore 2
	aload 2
	ldc 0
	iaload 
	istore 3
	aload 2
	ldc 1
	iaload 
	istore 4
	iload 3
	aload_0 
	getfield Life/xMax I
	if_icmpge L2
	iload 3
	ldc 1
	iadd 
	istore 5
	aload_0 
	iload 3
	ldc 0
	invokevirtual Life/gt(II)Z
	ifeq L4
	iload 3
	ldc 1
	isub 
	istore 6
	goto L3
	L4:
	aload_0 
	getfield Life/xMax I
	istore 6
	L3:
	goto L1
	L2:
	ldc 0
	istore 5
	iload 3
	ldc 1
	isub 
	istore 6
	L1:
	iload 4
	aload_0 
	getfield Life/yMax I
	if_icmpge L6
	iload 4
	ldc 1
	iadd 
	istore 7
	aload_0 
	iload 4
	ldc 0
	invokevirtual Life/gt(II)Z
	ifeq L8
	iload 4
	ldc 1
	isub 
	istore 8
	goto L7
	L8:
	aload_0 
	getfield Life/yMax I
	istore 8
	L7:
	goto L5
	L6:
	ldc 0
	istore 7
	iload 4
	ldc 1
	isub 
	istore 8
	L5:
	ldc 8
	newarray int 
	astore 9
	aload 9
	ldc 0
	aload_0 
	iload 3
	iload 8
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 9
	ldc 1
	aload_0 
	iload 6
	iload 8
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 9
	ldc 2
	aload_0 
	iload 6
	iload 4
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 9
	ldc 3
	aload_0 
	iload 6
	iload 7
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 9
	ldc 4
	aload_0 
	iload 3
	iload 7
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 9
	ldc 5
	aload_0 
	iload 5
	iload 7
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 9
	ldc 6
	aload_0 
	iload 5
	iload 4
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 9
	ldc 7
	aload_0 
	iload 5
	iload 8
	invokevirtual Life/trIdx(II)I
	iastore 
	aload 9
	areturn 
.end method

.method public busyWait(I)Z
.limit stack 3
.limit locals 4
	iload 1
	aload_0 
	getfield Life/LOOPS_PER_MS I
	imul 
	istore 2
	ldc 0
	istore 3
	goto L1
	L2:
	iload 3
	ldc 1
	iadd 
	istore 3
	L1:
	iload 3
	iload 2
	if_icmplt L2
	iconst_1 
	ireturn 
.end method

.method public getLiveNeighborN(I)I
.limit stack 6
.limit locals 5
	ldc 0
	istore 2
	aload_0 
	iload 1
	invokevirtual Life/getNeighborCoords(I)[I
	astore 3
	ldc 0
	istore 4
	goto L1
	L2:
	aload_0 
	aload_0 
	getfield Life/field [I
	aload 3
	iload 4
	iaload 
	iaload 
	ldc 0
	invokevirtual Life/ne(II)Z
	ifeq L4
	iload 2
	ldc 1
	iadd 
	istore 2
	goto L3
	L4:
	L3:
	iload 4
	ldc 1
	iadd 
	istore 4
	L1:
	iload 4
	aload 3
	arraylength 
	if_icmplt L2
	iload 2
	ireturn 
.end method

.method public ge(II)Z
.limit stack 4
.limit locals 3
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/eq(II)Z
	ifgt L3
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/gt(II)Z
	ifgt L3
	goto L2
	L3:
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	ireturn 
.end method

.method public printField()Z
.limit stack 6
.limit locals 3
	ldc 0
	istore 1
	ldc 0
	istore 2
	goto L1
	L2:
	aload_0 
	iload 2
	aload_0 
	getfield Life/xMax I
	invokevirtual Life/gt(II)Z
	ifeq L4
	invokestatic io/println()V
	ldc 0
	istore 2
	goto L3
	L4:
	L3:
	aload_0 
	getfield Life/field [I
	iload 1
	iaload 
	invokestatic io/print(I)V
	iload 1
	ldc 1
	iadd 
	istore 1
	iload 2
	ldc 1
	iadd 
	istore 2
	L1:
	iload 1
	aload_0 
	getfield Life/field [I
	arraylength 
	if_icmplt L2
	invokestatic io/println()V
	invokestatic io/println()V
	iconst_1 
	ireturn 
.end method

.method public eq(II)Z
.limit stack 4
.limit locals 3
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/lt(II)Z
	ifgt L2
	aload_0 
	iload 2
	iload 1
	invokevirtual Life/lt(II)Z
	ifgt L2
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	ireturn 
.end method

.method public ne(II)Z
.limit stack 3
.limit locals 3
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/eq(II)Z
	ifgt L2
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	ireturn 
.end method

.method public le(II)Z
.limit stack 4
.limit locals 3
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/eq(II)Z
	ifgt L3
	aload_0 
	iload 1
	iload 2
	invokevirtual Life/lt(II)Z
	ifgt L3
	goto L2
	L3:
	iconst_1 
	goto L1
	L2:
	iconst_0 
	L1:
	ireturn 
.end method

.method public trIdx(II)I
.limit stack 4
.limit locals 3
	iload 1
	aload_0 
	getfield Life/xMax I
	ldc 1
	iadd 
	iload 2
	imul 
	iadd 
	ireturn 
.end method

.method public init()Z
.limit stack 11
.limit locals 3
	ldc 1
	newarray int 
	astore 1
	aload_0 
	ldc 2
	putfield Life/UNDERPOP_LIM I
	aload_0 
	ldc 3
	putfield Life/OVERPOP_LIM I
	aload_0 
	ldc 3
	putfield Life/REPRODUCE_NUM I
	aload_0 
	ldc 225000
	putfield Life/LOOPS_PER_MS I
	aload_0 
	aload_0 
	aload 1
	invokevirtual Life/field([I)[I
	putfield Life/field [I
	aload 1
	ldc 0
	iaload 
	istore 2
	aload_0 
	iload 2
	ldc 1
	isub 
	putfield Life/xMax I
	aload_0 
	aload_0 
	getfield Life/field [I
	arraylength 
	iload 2
	idiv 
	ldc 1
	isub 
	putfield Life/yMax I
	iconst_1 
	ireturn 
.end method

.method public update()Z
.limit stack 14
.limit locals 6
	aload_0 
	getfield Life/field [I
	arraylength 
	newarray int 
	astore 1
	ldc 0
	istore 2
	goto L1
	L2:
	aload_0 
	getfield Life/field [I
	iload 2
	iaload 
	istore 3
	aload_0 
	iload 2
	invokevirtual Life/getLiveNeighborN(I)I
	istore 4
	iload 3
	ldc 1
	if_icmplt L4
	aload_0 
	iload 4
	aload_0 
	getfield Life/UNDERPOP_LIM I
	invokevirtual Life/le(II)Z
	ifeq L6
	aload_0 
	iload 4
	aload_0 
	getfield Life/OVERPOP_LIM I
	invokevirtual Life/ge(II)Z
	ifeq L6
	iconst_1 
	goto L5
	L6:
	iconst_0 
	L5:
	istore 5
	iload 5
	ifgt L8
	aload 1
	iload 2
	ldc 0
	iastore 
	goto L7
	L8:
	aload 1
	iload 2
	aload_0 
	getfield Life/field [I
	iload 2
	iaload 
	iastore 
	L7:
	goto L3
	L4:
	aload_0 
	iload 4
	aload_0 
	getfield Life/REPRODUCE_NUM I
	invokevirtual Life/eq(II)Z
	ifeq L10
	aload 1
	iload 2
	ldc 1
	iastore 
	goto L9
	L10:
	aload 1
	iload 2
	aload_0 
	getfield Life/field [I
	iload 2
	iaload 
	iastore 
	L9:
	L3:
	iload 2
	ldc 1
	iadd 
	istore 2
	L1:
	iload 2
	aload_0 
	getfield Life/field [I
	arraylength 
	if_icmplt L2
	aload_0 
	aload 1
	putfield Life/field [I
	iconst_1 
	ireturn 
.end method
