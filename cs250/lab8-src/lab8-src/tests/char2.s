	#Creating space for global variables
	.data
g:
	.quad 0

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
	#Creating space for local variable h

	# push 20
	movq $20,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call malloc
	movq %rax, %rbx
	#Moving data into local variable
	movq	%rbx, 0(%rsp)

	# push 0
	movq $0,%rbx

	# push 65
	movq $65,%r10
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)

	# push 1
	movq $1,%rbx

	# push 66
	movq $66,%r10
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)

	# push 2
	movq $2,%rbx

	# push 67
	movq $67,%r10
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)

	# push 3
	movq $3,%rbx

	# push 0
	movq $0,%r10
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)
	#top=0

	# push string "h=%s\n" top=0
	movq $string0, %rbx
	movq	0(%rsp),%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx

	# push 20
	movq $20,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call malloc
	movq %rax, %rbx
	#Moving data into global variable
	movq	%rbx, g

	# push 0
	movq $0,%rbx

	# push 68
	movq $68,%r10
	movq g, %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)

	# push 1
	movq $1,%rbx

	# push 69
	movq $69,%r10
	movq g, %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)

	# push 2
	movq $2,%rbx

	# push 70
	movq $70,%r10
	movq g, %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)

	# push 3
	movq $3,%rbx

	# push 0
	movq $0,%r10
	movq g, %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)
	#top=0

	# push string "g=%s\n" top=0
	movq $string1, %rbx
	movq g,%r10
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
	.string "h=%s\n"

string1:
	.string "g=%s\n"

