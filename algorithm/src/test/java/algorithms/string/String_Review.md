##Limit result to within Integer MAX_VALUE

if (reversed > Integer.MAX_VALUE/10 || 
   (reversed == Integer.MAX_VALUE/10 && digit > 7)) // overflow

Integer.MAX_VALUE = 2147483647

Split that into its last digit and everything before it:

2147483647
└───┬────┘└┬┘
 214748364  7

So Integer.MAX_VALUE / 10 = 214748364 (integer division truncates), and the leftover last digit is 7.
