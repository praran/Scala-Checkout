Solution Approach   :
---------------------

  The idea is to implement the checkout functionality in totally functional fashion,
  all the methods defined do not have any side effects and truly functional which do not modify and data strucutre
  
  Idea is to use Map-Reduce technique.
     1) Map the correct price to the item based on the discount rules
     2) Reduce/ Accumulate the correct prices to derive the total price
	 
  Checkout is defined as a scala object, which provides a singleton implementation of Checkout with functions
  
  The map and reduce functions which in this case mapCorrectPrice and cumulativePrice uses tail-recursion for efficiency


 RuntimeEnvironment :
 --------------------
 Java       1.6
 Scala      2.10.0
 Scalatest  scalatest_2.10 2.0
 Graph Core 1.6.1
 Maven      3.0.4
 Scala IDE IntelliJ


