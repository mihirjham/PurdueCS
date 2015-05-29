	.data
	
	.comm	array 800,4	#Initialize array

	.text			#Addresses of strings that are used by printf and scanf
printSort:
	.string	"sort\n"
readInt:
	.string	"%d"
printSorted:
	.string "Sorted:\n"
printInt:
	.string "%d\n"

	.globl	main			#main() {
main:
	mov	$printSort, %edi	#printf("sort:\n");
	mov	$0, %eax
	call	printf

	mov	$0, %r13d		#len: %r13d, len = 0;
	mov	$0, %r14d
whileRead:
	mov	$array, %edx		#read integers line by line until EOF
	mov	%r13d, %ecx
	imul	$4, %ecx
	add	%ecx, %edx

	mov	$readInt, %edi
	mov	%edx, %esi
	mov	$0, %eax
	call	scanf
	
	cmp	$-1, %eax		#if input is EOF
	je	sortArray	

	add	$1, %r13d		#len++
	jmp	whileRead


sortArray:
	mov	$0, %r14d		#c=0
	mov	%r13d, %r15d
	sub	$1, %r15d		#n-1
	
whileLoopOne:
	cmp	%r14d, %r15d		#for(c=0;c<n-1;c++)
	je	printArray

	mov	$0, %r8d		#d=0
	mov	%r13d, %r9d	
	sub	%r14d, %r9d
	sub	$1, %r9d		#n-c-1
whileLoopTwo:
	cmp	%r8d, %r9d		#for(d=0;d<n-c-1;d++)
	je	incSwap
	
	mov	$array, %edx
	mov	%r8d, %ecx
	imul	$4, %ecx
	add	%ecx, %edx

	mov	%edx, %esi
	add	$4, %esi

	mov	(%edx), %eax
	mov	(%esi), %ebx

	cmp	%eax, %ebx		#if(array[i] > array[i+1]
	jge	incSwapTwo
	
	xchg	%edx, %esi		#swap
	mov	(%edx), %edx
	mov	(%esi), %esi
	
	mov	%edx, array+(%ecx)
	add	$4, %ecx
	mov	%esi, array+(%ecx)

incSwapTwo:
	add	$1, %r8d		#d++
	jmp	whileLoopTwo
incSwap:
	add	$1, %r14d		#c++
	jmp	whileLoopOne

printArray:				#print the sorted array
	mov	$0, %r14d
	mov	$printSorted, %edi
	mov	$0, %eax
	call	printf
	jmp	whilePrint

whilePrint:	
	cmp	%r14d, %r13d
	je	end

	mov	$array, %edx
	mov	%r14d, %ecx
	imul	$4, %ecx
	add	%ecx, %edx

	mov	$printInt, %edi
	mov	%edx, %esi
	mov	(%esi), %esi
	mov	$0, %eax
	call	printf

	add	$1, %r14d
	jmp	whilePrint

end:
	ret				#return

	
