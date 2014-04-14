somecompiler
============

Compiles &lt;some_name> high level code into &lt;some_other_name> bytecode.

Examples:

Code:
> var a ;
> a = 5 ;
> var b ;
> b = 3 ;
> var c ;
> c = a + b ;
> c + = 2 ; 
> print! c ;";
Results in :
> MOV[AL] : addr(0), literal(5)
> MOV[AL] : addr(1), literal(3)
> MOV[AA] : addr(2), addr(0)
> ADD[AA] : addr(2), addr(1)
> ADD[AL] : addr(2), literal(2)
> CALL[AA] : addr(100), addr(2)


And code:
> var x ; 
> x = 9 ; 
> x = x ; 
> x + = 10 ; 
> x + = x ; 
> print! x ;
Results in:
> MOV[AL] : addr(3), literal(9)
> MOV[AA] : addr(3), addr(3)
> ADD[AL] : addr(3), literal(10)
> ADD[AA] : addr(3), addr(3)
> CALL[AA] : addr(100), addr(3)