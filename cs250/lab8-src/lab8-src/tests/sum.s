	.text
.globl sum
sum:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument n to the stack
	#Adding argument a to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
	#Creating space for local variable i

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 16(%rsp)
start_loop_0:
	movq	16(%rsp),%rbx
	movq	0(%rsp),%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_0
	jmp for_statement_0
for_increment_0:
	movq	16(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 16(%rsp)
	jmp start_loop_0
for_statement_0:
	movq	24(%rsp),%rbx
	movq	16(%rsp),%r10
	movq 8(%rsp), %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 24(%rsp)
	jmp for_increment_0
end_loop_0:
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
	#Creating space for local variable a

	# push 5
	movq $5,%rbx

	# push 8
	movq $8,%r10

	# *
	imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call malloc
	movq %rax, %rbx
	#Moving data into local variable
	movq	%rbx, 0(%rsp)

	# push 0
	movq $0,%rbx

	# push 4
	movq $4,%r10
	movq 0(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 1
	movq $1,%rbx

	# push 3
	movq $3,%r10
	movq 0(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 2
	movq $2,%rbx

	# push 1
	movq $1,%r10
	movq 0(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 3
	movq $3,%rbx

	# push 7
	movq $7,%r10
	movq 0(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)

	# push 4
	movq $4,%rbx

	# push 6
	movq $6,%r10
	movq 0(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)
	#Creating space for local variable s

	# push 5
	movq $5,%rbx
	movq	0(%rsp),%r10
     # func=sum nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	call sum
	movq %rax, %rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
	#top=0

	# push string "sum=%d\n" top=0
	movq $string0, %rbx
	movq	8(%rsp),%r10
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
	.string "sum=%d\n"

