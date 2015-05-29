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

	# push 1
	movq $1,%rbx
	cmpq $0, %rbx
	je else_0
	#top=1

	# push string "OK1\n" top=1
	movq $string0, %r10
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r10
	jmp endif_0
else_0:
endif_0:

	# push 0
	movq $0,%rbx
	cmpq $0, %rbx
	je else_1
	#top=1

	# push string "OK2\n" top=1
	movq $string1, %r10
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r10
	jmp endif_1
else_1:
endif_1:

	# push 1
	movq $1,%rbx
	cmpq $0, %rbx
	je else_2
	#top=1

	# push string "OK3\n" top=1
	movq $string2, %r10
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r10
	jmp endif_2
else_2:
	#top=0

	# push string "OK4\n" top=0
	movq $string3, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	jmp endif_2
endif_2:

	# push 0
	movq $0,%rbx
	cmpq $0, %rbx
	je else_3
	#top=1

	# push string "OK5\n" top=1
	movq $string4, %r10
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %r10, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %r10
	jmp endif_3
else_3:
	#top=0

	# push string "OK6\n" top=0
	movq $string5, %rbx
     # func=printf nargs=1
     # Move values from reg stack to reg args
	movq %rbx, %rdi
	movl    $0, %eax
	call printf
	movq %rax, %rbx
	jmp endif_3
endif_3:
	#top=0

	# push string "OK7\n" top=0
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
	.string "OK1\n"

string1:
	.string "OK2\n"

string2:
	.string "OK3\n"

string3:
	.string "OK4\n"

string4:
	.string "OK5\n"

string5:
	.string "OK6\n"

string6:
	.string "OK7\n"

