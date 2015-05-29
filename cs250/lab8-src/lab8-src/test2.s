	#Creating space for global variables
	.data
h:
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
	#Adding argument c to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
	movq %rdx, 16(%rsp)
	#Creating space for local variable d

	# push 10
	movq $10,%rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)

	# push 11
	movq $11,%rbx
	#Moving data into global variable
	movq	%rbx, h

	# push 2
	movq $2,%rbx
	movq	0(%rsp),%r10

	# *
	imulq %r10,%rbx
	movq	0(%rsp),%r10

	# *
	imulq %r10,%rbx

	# push 4
	movq $4,%r10
	movq	8(%rsp),%r13
	movq	16(%rsp),%r14

	# -
	subq %r14, %r13

	# *
	imulq %r13,%r10
	movq	24(%rsp),%r13

	# *
	imulq %r13,%r10

	# -
	subq %r10, %rbx
	movq h,%r10

	# -
	subq %r10, %rbx
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
	#top=0

	# push string "compute=%d\n" top=0
	movq $string0, %rbx

	# push 1
	movq $1,%r10

	# push 2
	movq $2,%r13

	# push 3
	movq $3,%r14
     # func=compute nargs=3
     # Move values from reg stack to reg args
	movq %r14, %rdx
	movq %r13, %rsi
	movq %r10, %rdi
	call compute
	movq %rax, %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
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
	.string "compute=%d\n"

