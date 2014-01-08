Checkout Kata:
--------------

  Implement the code for a supermarket checkout that calculates the total price of a number of
 items. In a normal supermarket, things are identified using Stock Keeping Units, or SKUs. In our
 store, we’ll use individual letters of the alphabet (A, B, C, and so on as the SKUs). Our goods are
 priced individually. In addition, some items are multi­priced: buy n of them, and they’ll cost you y.
 For example, item ‘A’ might cost 50 pence individually, but this week we have a special offer: buy
 three ‘A’s and they’ll cost you £1.30. In fact this week’s prices are:
   Item Unit Price  Special Price
    A         50    3 for 130
    B         30    2 for 45
    C         20
    D         15
 Our checkout accepts items in any order, so that if we scan a B, an A, and another B, we’ll
 recognise the two B’s and price them at 45 (for a total price so far of 95).
 Extra points: Because the pricing changes frequently, we need to be able to pass in a set of
 pricing rules each time we start handling a checkout transaction

Solution Approach   :
---------------------

  The idea is to implement the checkout functionality in totally functional fashion,
  all the methods defined do not have any side effects and truly functional which do not modify and data strucutre
  
  Idea is to use Map-Reduce technique.
     1) Map the correct price to the item based on the discount rules
     2) Reduce/ Accumulate the correct prices to derive the total price
	 
  Checkout is defined as a scala object, which provides a singleton implementation of Checkout with functions
  
  The map and reduce functions which in this case mapCorrectPrice and cumulativePrice uses tail-recursion for efficiency
  
  Note : More sophisticated implementation is also possible, but for the given use case the implementation appears to be the most simple and efficent.
         Also in some places Scala's predefined library could be used for ex: foldL in mapCorrectPrice and cumulativePrice functions but I have chosen 
		 to use a custom implementation

Build project :
---------------

  sbt clean
  sbt compile

Run Tests :
-----------

  sbt test
  
RuntimeEnvironment :
--------------------
 Java       1.6
 Scala      2.10.0
 Scalatest  scalatest_2.10 2.0
 Scala IDE IntelliJ


