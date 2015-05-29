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
	#Creating space for local variable i

	# push 8
	movq $8,%rbx

	# push 20
	movq $20,%r10

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
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
start_loop_0:
	movq	8(%rsp),%rbx

	# push 20
	movq $20,%r10

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
	movq	8(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 8(%rsp)
	jmp start_loop_0
for_statement_0:
	movq	8(%rsp),%rbx

	# push 3
	movq $3,%r10
	movq	8(%rsp),%r13

	# *
	imulq %r13,%r10
	movq 0(%rsp), %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)
	jmp for_increment_0
end_loop_0:
	#top=0

	# push string "Ok so far\n" top=0
	movq $string0, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
start_loop_1:
	movq	8(%rsp),%rbx

	# push 20
	movq $20,%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_1
	jmp for_statement_1
for_increment_1:
	movq	8(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 8(%rsp)
	jmp start_loop_1
for_statement_1:
	#top=0

	# push string "%d: %d\n" top=0
	movq $string1, %rbx
	movq	8(%rsp),%r10
	movq	8(%rsp),%r13
	movq 0(%rsp), %rax
	imulq $8, %r13
	addq %r13, %rax
	movq (%rax), %r13
     # func=printf nargs=3
     # Move values from reg stack to reg args
	movq %r13, %rdx
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	jmp for_increment_1
end_loop_1:
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
	.string "Ok so far\n"

string1:
	.string "%d: %d\n"

