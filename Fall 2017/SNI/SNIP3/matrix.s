	.file	"matrix.c"
	.comm	array,83886080,32
	.section	.rodata
.LC0:
	.string	"%ld\n"
	.text
	.globl	main
	.type	main, @function
main:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$48, %rsp
	movl	%edi, -36(%rbp)
	movq	%rsi, -48(%rbp)
	call	clock
	movq	%rax, -16(%rbp)
	movl	$0, -20(%rbp)
	jmp	.L2
.L3:
	movl	-20(%rbp), %eax
	cltq
	salq	$12, %rax
	addq	$array, %rax
	movb	$97, (%rax)
	addl	$1, -20(%rbp)
.L2:
	cmpl	$20479, -20(%rbp)
	jle	.L3
	call	clock
	movq	%rax, -8(%rbp)
	movq	-8(%rbp), %rax
	subq	-16(%rbp), %rax
	movq	%rax, %rcx
	movabsq	$4835703278458516699, %rdx
	movq	%rcx, %rax
	imulq	%rdx
	sarq	$18, %rdx
	movq	%rcx, %rax
	sarq	$63, %rax
	subq	%rax, %rdx
	movq	%rdx, %rax
	imulq	$1000, %rax, %rax
	movq	%rax, %rsi
	movl	$.LC0, %edi
	movl	$0, %eax
	call	printf
	call	clock
	movq	%rax, -16(%rbp)
	movl	$0, -20(%rbp)
	jmp	.L4
.L5:
	movl	-20(%rbp), %eax
	cltq
	salq	$12, %rax
	addq	$array, %rax
	movzbl	(%rax), %eax
	movb	%al, -21(%rbp)
	addl	$1, -20(%rbp)
.L4:
	cmpl	$20479, -20(%rbp)
	jle	.L5
	call	clock
	movq	%rax, -8(%rbp)
	movq	-8(%rbp), %rax
	subq	-16(%rbp), %rax
	movq	%rax, %rcx
	movabsq	$4835703278458516699, %rdx
	movq	%rcx, %rax
	imulq	%rdx
	sarq	$18, %rdx
	movq	%rcx, %rax
	sarq	$63, %rax
	subq	%rax, %rdx
	movq	%rdx, %rax
	imulq	$1000, %rax, %rax
	movq	%rax, %rsi
	movl	$.LC0, %edi
	movl	$0, %eax
	call	printf
	movl	$0, %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.ident	"GCC: (Ubuntu 5.4.0-6ubuntu1~16.04.4) 5.4.0 20160609"
	.section	.note.GNU-stack,"",@progbits
