##Limit result to within Integer MAX_VALUE
==========================================
if (reversed > Integer.MAX_VALUE/10 || 
   (reversed == Integer.MAX_VALUE/10 && digit > 7)) // overflow

Integer.MAX_VALUE = 2147483647

Split that into its last digit and everything before it:

2147483647
└───┬────┘└┬┘
 214748364  7

So Integer.MAX_VALUE / 10 = 214748364 (integer division truncates), and the leftover last digit is 7.

##Limit result to within Integer MIN_VALUE
==========================================
if (reversed > Integer.MIN_VALUE/10 || 
   (reversed == Integer.MIN_VALUE/10 && digit > 8)) // overflow
   
Integer.MIN_VALUE = -2147483648

Split it the same way as before:

-2147483648
└───┬─────┘└┬┘
-214748364   -8
