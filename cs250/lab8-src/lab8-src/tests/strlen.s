	.text
.globl mystrlen
mystrlen:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument s to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	#Creating space for local variable l

	# push 0
	movq $0,%rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
start_loop_0:
	movq	8(%rsp),%rbx
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb (%rax), %bl
	cmpq $0, %rbx
	je end_loop_0
	movq	8(%rsp),%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
	jmp start_loop_0
end_loop_0:
	movq	8(%rsp),%rbx
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
.globl mystrcpy
mystrcpy:
# Save registers
	pushq %rbx
	pushq %r10
	pushq %r13
	pushq %r14
	pushq %r15
	#Saving space for 32 local variables
	subq	$256, %rsp
	#Adding argument s1 to the stack
	#Adding argument s2 to the stack
	#Save Arguments
	movq %rdi, 0(%rsp)
	movq %rsi, 8(%rsp)
start_loop_1:

	# push 0
	movq $0,%rbx
	movq 8(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb (%rax), %bl
	cmpq $0, %rbx
	je end_loop_1

	# push 0
	movq $0,%rbx

	# push 0
	movq $0,%r10
	movq 8(%rsp), %rax
	imulq $1, %r10
	addq %r10, %rax
	movb (%rax), %r10b
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)
	movq	0(%rsp),%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 0(%rsp)
	movq	8(%rsp),%rbx

	# push 1
	movq $1,%r10

	# +
	addq %r10,%rbx
	#Moving data into local variable
	movq	%rbx, 8(%rsp)
	jmp start_loop_1
end_loop_1:

	# push 0
	movq $0,%rbx

	# push 0
	movq $0,%r10
	movq 0(%rsp), %rax
	imulq $1, %rbx
	addq %rbx, %rax
	movb %r10b, (%rax)
	addq	$256, %rsp
# Restore registers
	popq %r15
	popq %r14
	popq %r13
	popq %r10
	popq %rbx
	#Restoring space for local variables
	ret
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

	# push 30
	movq $30,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call malloc
	movq %rax, %rbx
	#Moving data into local variable
	movq	%rbx, 0(%rsp)
	movq	0(%rsp),%rbx
	#top=1

	# push string "Hello world" top=1
	movq $string0, %r10
     # func=mystrcpy nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	call mystrcpy
	movq %rax, %rbx
	#top=0

	# push string "h=%s\n" top=0
	movq $string1, %rbx
	movq	0(%rsp),%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	#top=0

	# push string "l=%d\n" top=0
	movq $string2, %rbx
	movq	0(%rsp),%r10
     # func=mystrlen nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	call mystrlen
	movq %rax, %r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx

	# push 30
	movq $30,%rbx
     # func=malloc nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	call malloc
	movq %rax, %rbx
	#Moving data into global variable
	movq	%rbx, g
	movq g,%rbx
	#top=1

	# push string "This is a great course" top=1
	movq $string3, %r10
     # func=strcpy nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	call strcpy
	movq %rax, %rbx
	#top=0

	# push string "g=%s\n" top=0
	movq $string4, %rbx
	movq g,%r10
     # func=printf nargs=2
     # Move values from reg stack to reg args
	movq %r10, %rsi
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	#top=0

	# push string "l=%d\n" top=0
	movq $string5, %rbx
	movq g,%r10
     # func=mystrlen nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	call mystrlen
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
	.string "Hello world"

string1:
	.string "h=%s\n"

string2:
	.string "l=%d\n"

string3:
	.string "This is a great course"

string4:
	.string "g=%s\n"

string5:
	.string "l=%d\n"

