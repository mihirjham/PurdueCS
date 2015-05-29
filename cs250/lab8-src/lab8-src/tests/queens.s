	#Creating space for global variables
	.data
queens:
	.quad 0

	#Creating space for global variables
	.data
solid:
	.quad 0

	.text
.globl abs
abs:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument v to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq	0(%rsp),%rbx

	# push 0
	movq $0,%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je else_0

	# push -1
	movq $-1,%r10
	movq	0(%rsp),%r13

	# *
	imulq %r13,%r10
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	jmp endif_0
else_0:
endif_0:
	movq	0(%rsp),%rbx
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
.globl check
check:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument depth to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	#Creating space for local variable i

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
start_loop_1:
	movq	8(%rsp),%rbx
	movq	0(%rsp),%r10

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
	movq	8(%rsp),%rbx
	movq queens, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq (%rax), %rbx
	movq	0(%rsp),%r10
	movq queens, %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10

	#check if equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmove %rdx, %rbx
	movq	8(%rsp),%r10
	movq queens, %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10
	movq	0(%rsp),%r13
	movq queens, %rax
	imulq $8, %r13
	addq %r13, %rax
	movq (%rax), %r13

	# -
	subq %r13, %r10
     # func=abs nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	call abs
	movq %rax, %r10
	movq	0(%rsp),%r13
	movq	8(%rsp),%r14

	# -
	subq %r14, %r13

	#check if equal to
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $1, %rdx
	movq $0, %r10
	cmove %rdx, %r10

	#check ||
	orq %r10, %rbx
	cmpq $0, %rbx
	je else_2

	# push 0
	movq $0,%r10
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	jmp endif_2
else_2:
endif_2:
	jmp for_increment_1
end_loop_1:

	# push 1
	movq $1,%rbx
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
.globl bruteforce
bruteforce:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument depth to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq	0(%rsp),%rbx

	# push 8
	movq $8,%r10

	#check if equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmove %rdx, %rbx
	cmpq $0, %rbx
	je else_3
	#Creating space for local variable i
	#top=1

	# push string "Solution #%2ld = [ " top=1
	movq $string0, %r10
	movq solid,%r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r13, %rsi
	movq %r10, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r10
	movq solid,%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into global variable
	movq	%rbx, queens

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
start_loop_3:
	movq	8(%rsp),%rbx

	# push 8
	movq $8,%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_3
	jmp for_statement_3
for_increment_3:
	movq	8(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 8(%rsp)
	jmp start_loop_3
for_statement_3:
	#top=0

	# push string "%ld " top=0
	movq $string1, %rbx
	movq	8(%rsp),%r10
	movq queens, %rax
	imulq $8, %r10
	addq %r10, %rax
	movq (%rax), %r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	jmp for_increment_3
end_loop_3:
	#top=0

	# push string "]\n" top=0
	movq $string2, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx

	# push 0
	movq $0,%rbx
	movq %rbx, %rax
	addq $256, %rsp
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	ret
	jmp endif_3
else_3:
endif_3:
	#Creating space for local variable i

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
start_loop_5:
	movq	8(%rsp),%rbx

	# push 8
	movq $8,%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_5
	jmp for_statement_5
for_increment_5:
	movq	8(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into local variable
	movq	%r10, 8(%rsp)
	jmp start_loop_5
for_statement_5:
	movq	0(%rsp),%rbx
	movq	8(%rsp),%r10
	movq queens, %rax
	imulq $8, %rbx
	addq %rbx, %rax
	movq %r10, (%rax)
	movq	0(%rsp),%rbx
     # func=check nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call check
	movq %rax, %rbx

	# push 0
	movq $0,%r10

	#check if not equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmovne %rdx, %rbx
	cmpq $0, %rbx
	je else_6
	movq	0(%rsp),%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
     # func=bruteforce nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	call bruteforce
	movq %rax, %r10
	jmp endif_6
else_6:
endif_6:
	jmp for_increment_5
end_loop_5:

	# push 0
	movq $0,%rbx
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

	# push 8
	movq $8,%rbx

	# push 8
	movq $8,%r10

	# *
	imulq %r10,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call malloc
	movq %rax, %rbx
	#Moving data into global variable
	movq	%rbx, queens

	# push 1
	movq $1,%rbx
	#Moving data into global variable
	movq	%rbx, solid

	# push 0
	movq $0,%rbx
     # func=bruteforce nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call bruteforce
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
	.string "Solution #%2ld = [ "

string1:
	.string "%ld "

string2:
	.string "]\n"

