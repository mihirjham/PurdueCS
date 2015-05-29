	#Creating space for global variables
	.data
i:
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

	# push 0
	movq $0,%rbx
	#Moving data into global variable
	movq	%rbx, i
start_loop_0:
	movq i,%rbx

	# push 15
	movq $15,%r10

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
	movq i,%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into global variable
	movq	%r10, i
	jmp start_loop_0
for_statement_0:
	movq i,%rbx

	# push 5
	movq $5,%r10

	#check if equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmove %rdx, %rbx
	cmpq $0, %rbx
	je else_1
	#break statement
	jmp end_loop_0
	jmp endif_1
else_1:
endif_1:
	#top=1

	# push string "i=%d\n" top=1
	movq $string0, %r10
	movq i,%r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r13, %rsi
	movq %r10, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r10
	jmp for_increment_0
end_loop_0:
	#top=0

	# push string "for i=%d\n" top=0
	movq $string1, %rbx
	movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx

	# push 0
	movq $0,%rbx
	#Moving data into global variable
	movq	%rbx, i
start_loop_2:
	movq i,%rbx

	# push 15
	movq $15,%r10

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $0, %rdx
	movq $1, %rbx
	cmovle %rdx, %rbx
	cmpq $0, %rbx
	je end_loop_2
	#top=0

	# push string "i=%d\n" top=0
	movq $string2, %rbx
	movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	movq i,%rbx

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
	#break statement
	jmp end_loop_2
	jmp endif_3
else_3:
endif_3:
	movq i,%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into global variable
	movq	%r10, i
	jmp start_loop_2
end_loop_2:
	#top=1

	# push string "while i=%d\n" top=1
	movq $string3, %r10
	movq i,%r13
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r13, %rsi
	movq %r10, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r10

	# push 0
	movq $0,%rbx
	#Moving data into global variable
	movq	%rbx, i
start_loop_4:
	#top=0

	# push string "i=%d\n" top=0
	movq $string4, %rbx
	movq i,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	movq i,%rbx

	# push 10
	movq $10,%r10

	#check if equal to
	xorq %rdx, %rdx
	cmpq %rbx, %r10
	movq $1, %rdx
	movq $0, %rbx
	cmove %rdx, %rbx
	cmpq $0, %rbx
	je else_5
	#break statement
	jmp end_loop_4
	jmp endif_5
else_5:
endif_5:
	movq i,%r10

	# push 1
	movq $1,%r13

	# +
	addq %r13,%r10
	#Moving data into global variable
	movq	%r10, i
	movq i,%r10

	# push 15
	movq $15,%r13

	#Check if it is less than
	xorq %rdx, %rdx
	cmpq %r10, %r13
	movq $0, %rdx
	movq $1, %r10
	cmovle %rdx, %r10
	cmpq $0, %r10
	je end_loop_4
	jmp start_loop_4
end_loop_4:
	#top=2

	# push string "do/while i=%d\n" top=2
	movq $string5, %r13
	movq i,%r14
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r14, %rsi
	movq %r13, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r13
	#top=0

	# push string "OK\n" top=0
	movq $string6, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
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
	.string "i=%d\n"

string1:
	.string "for i=%d\n"

string2:
	.string "i=%d\n"

string3:
	.string "while i=%d\n"

string4:
	.string "i=%d\n"

string5:
	.string "do/while i=%d\n"

string6:
	.string "OK\n"

