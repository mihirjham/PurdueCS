	#Creating space for global variables
	.data
g:
	.quad 0

	.text
.globl compute
compute:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument a to the stack
	#Adding argument b to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
	#Creating space for local variable j
	#Creating space for local variable h
	movq g,%rbx

	# push 9
	movq $9,%r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 16(%rsp)
	movq	0(%rsp),%rbx
	movq	8(%rsp),%r10

	# +
	addq %r10,%rbx
	movq	16(%rsp),%r10

	# *
	imulq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)
	movq	24(%rsp),%rbx
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	addq	$256, %rsp
# Restore registers
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	#Restoring space for local variables
	ret
	.text
.globl main
main:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Save Arguments
	#Creating space for local variable j

	# push 9
	movq $9,%rbx
	#Moving data into global variable
	movq	%rbx, g

	# push 2
	movq $2,%rbx

	# push 5
	movq $5,%r10
     # func=compute nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	call compute
	movq %rax, %rbx
	#Moving data into local variable
	movq	%rbx, 0(%rsp)
	#top=0

	# push string "j=%d g=%d\n" top=0
	movq $string0, %rbx
	movq	0(%rsp),%r10
	movq g,%r13
     # func=printf nargs=3
     # Move values from reg stack to reg args
	movq %r13, %rdx
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	addq	$256, %rsp
# Restore registers
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	#Restoring space for local variables
	ret
string0:
	.string "j=%d g=%d\n"

