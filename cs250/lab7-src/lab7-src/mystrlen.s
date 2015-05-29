	.file   "mystrlen.c"
	.text
.globl	mystrlen
	.type	mystrlen, @function
mystrlen:
	movq	$0, %rdx	#len: %rdx, len = 0

while:
	movb	(%rdi), %sil	#if *s == NULL
	cmpb	$0, %sil
	je	end

	addq	$1, %rdx	#len++
	add	$1, %rdi	#s++
	jmp	while
end:
	movq	%rdx, %rax	#return len
	ret
