package algorithms.array.medium;

import java.util.*;

public class Array27_InsertDeleteGetRandom_bigO_1 {

	public class RandomizedSet {
		HashMap<Integer, Integer> map;
		List<Integer> lstList;
		Random rand;

		/** Initialize your data structure here. */
		public RandomizedSet() {
			map = new HashMap<Integer, Integer>();
			lstList = new ArrayList<>();
			rand = new Random();
		}

		/**
		 * Inserts a value to the set. Returns true if the set did not already contain
		 * the specified element.
		 */
		public boolean insert(int val) {
			if (!map.containsKey(val)) {
				map.put(val, lstList.size());
				lstList.add(val);
				return true;
			} else {
				return false;
			}
		}

		/**
		 * Removes a value from the set. Returns true if the set contained the specified
		 * element.
		 */
		public boolean remove(int val) {
			if (map.containsKey(val)) {

				int indexToDelete = map.get(val);
				int valToReplace = lstList.get(lstList.size() - 1);
				lstList.set(indexToDelete, valToReplace);
				map.put(valToReplace, indexToDelete);

				map.remove(val);
				lstList.remove(lstList.size() - 1);
				return true;

			} else {
				return false;
			}
		}

		/** Get a random element from the set. */
		public int getRandom() {
			return lstList.get(rand.nextInt(lstList.size()));
		}
	}

}
