package coreJava.collections.set;

import java.util.HashSet;

import org.testng.annotations.Test;

/********
***********************
java.util 
Interface Set<E>
************************
A collection that contains no duplicate elements. More formally, sets contain no pair of elements e1 and e2 such that e1.equals(e2), 
and at most one null element. As implied by its name, this interface models the mathematical set abstraction.
The Set interface places additional stipulations, beyond those inherited from the Collection interface, 
on the contracts of all constructors and on the contracts of the add, equals and hashCode methods. 
Declarations for other inherited methods are also included here for convenience. 
(The specifications accompanying these declarations have been tailored to the Set interface, but they do not contain any additional stipulations.)

The additional stipulation on constructors is, not surprisingly, that all constructors must create a set that contains no duplicate elements (as defined above).

Note: Great care must be exercised if mutable objects are used as set elements. 
The behavior of a set is not specified if the value of an object is changed in a manner that affects equals comparisons while the object is an element 
in the set. A special case of this prohibition is that it is not permissible for a set to contain itself as an element.

Some set implementations have restrictions on the elements that they may contain. For example, some implementations prohibit null elements, 
and some have restrictions on the types of their elements. Attempting to add an ineligible element throws an unchecked exception, 
typically NullPointerException or ClassCastException. Attempting to query the presence of an ineligible element may throw an exception, 
or it may simply return false; some implementations will exhibit the former behavior and some will exhibit the latter. More generally, 
attempting an operation on an ineligible element whose completion would not result in the insertion of an ineligible element into the set may throw 
an exception or it may succeed, at the option of the implementation. Such exceptions are marked as "optional" in the specification for this interface.
*
******************************

public class HashSet<E> extends AbstractSet<E> implements Set<E>, Cloneable, Serializable

**************************************
 This class implements the Set interface, backed by a hash table (actually a HashMap instance). It makes no guarantees as to the iteration order of the set; in particular, it does not guarantee that the order will remain constant over time. This class permits the null element.
This class offers constant time performance for the basic operations (add, remove, contains and size), assuming the hash function disperses the elements properly among the buckets. Iterating over this set requires time proportional to the sum of the HashSet instance's size (the number of elements) plus the "capacity" of the backing HashMap instance (the number of buckets). Thus, it's very important not to set the initial capacity too high (or the load factor too low) if iteration performance is important.

Note that this implementation is not synchronized. If multiple threads access a hash set concurrently, and at least one of the threads modifies the set, it must be synchronized externally. This is typically accomplished by synchronizing on some object that naturally encapsulates the set. If no such object exists, the set should be "wrapped" using the Collections.synchronizedSet method. This is best done at creation time, to prevent accidental unsynchronized access to the set:

   Set s = Collections.synchronizedSet(new HashSet(...));
The iterators returned by this class's iterator method are fail-fast: if the set is modified at any time after the iterator is created, in any way except through the iterator's own remove method, the Iterator throws a ConcurrentModificationException. Thus, in the face of concurrent modification, the iterator fails quickly and cleanly, rather than risking arbitrary, non-deterministic behavior at an undetermined time in the future.

Note that the fail-fast behavior of an iterator cannot be guaranteed as it is, generally speaking, impossible to make any hard guarantees in the presence of unsynchronized concurrent modification. Fail-fast iterators throw ConcurrentModificationException on a best-effort basis. Therefore, it would be wrong to write a program that depended on this exception for its correctness: the fail-fast behavior of iterators should be used only to detect bugs.

This class is a member of the Java Collections Framework.
 */

public class Set01_HashSet {

	@Test
	public void hasSetSample() {
		HashSet<Object> hashSet = new HashSet<Object>();
		hashSet.add(45);
	      hashSet.add(15);
	      hashSet.add(99);
	      hashSet.add(70);
	      hashSet.add(65);
	      hashSet.add(30);
	      hashSet.add(10);
	      hashSet.add(60);
	      hashSet.add(80);
	      System.out.println("HashSet\n" + hashSet); /***** Will not maintain the insertion order ****/
	}
}
