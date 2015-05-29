	.file	"counter.c"
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
	subq	$16, %rsp
	movl	$0, -4(%rbp)
.L3:
	call	getchar
	movb	%al, -5(%rbp)
	addl	$1, -4(%rbp)
	cmpb	$-1, -5(%rbp)
	je	.L4
	cmpl	$140, -4(%rbp)
	jg	.L4
	movsbl	-5(%rbp), %eax
	movl	%eax, %edi
	call	putchar
	jmp	.L3
.L4:
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.ident	"GCC: (Gentoo 4.6.3 p1.13, pie-0.5.2) 4.6.3"
	.section	.note.GNU-stack,"",@progbits
