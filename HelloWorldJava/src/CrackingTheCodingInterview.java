
public class CrackingTheCodingInterview {

	static int[] stairCombos = new int[100];
	
	public static void main(String[] args) {		
		System.out.println(stairs9_1Rec(9));
		System.out.println(stairs9_1Dyn(36));
		
		System.out.println(robotMoveIncorrect(0, 0, 3, 3	));
		int[] sortedInts = new int[] {-3, -2, 2, 4, 10, 20};
		System.out.println(findMagicIndex(sortedInts, 0, sortedInts.length - 1));
		
		allPermutations("", "abcd");
	}

	//Chapter 9: recursion/dynamic programming
	static void allPermutations(String pre, String s) {
		if (s.length() == 1) System.out.println(pre + s);
		else {
			for (int i = 0; i < s.length(); i++) {
				allPermutations(pre + s.charAt(i), s.substring(0, i) + s.substring(i + 1, s.length()));
			}
		}
	}
	
	static int findMagicIndex(int[] a, int start, int end) {
		if (start > end || end < start) return -1;
		int mid = (end - start / 2) + start;
		if (a[mid] == mid) return mid;
		
		if (a[mid] < mid) return findMagicIndex(a, mid + 1, end);
		else if (a[mid] > mid) return findMagicIndex(a, start, mid - 1);
		else return -1;
	}
	
	static int robotMoveIncorrect(int rX, int rY, int x, int y) {
		if (rX < x - 1)
			return 1 + robotMoveIncorrect(rX + 1, rY, x, y);
		else if (rY < y - 1)
			return 1 + robotMoveIncorrect(rX, rY + 1, x, y);
		else
			return 0;
	}
	

	static int stairs9_1Rec(int steps) {
		if (steps < 0) {
			return 0;
		}
		else if (steps == 0) {
			return 1;
		}
		else {
			return stairs9_1Rec(steps - 1) + stairs9_1Rec(steps - 2) + stairs9_1Rec(steps - 3);
		}
	}
	
	static int stairs9_1Dyn(int steps) {
		if (steps < 0) {
			return 0;
		}
		else if (steps == 0) {
			return 1;
		}
		else if (stairCombos[steps] != 0) {
			return stairCombos[steps];
		}
		else {
			stairCombos[steps] = stairs9_1Dyn(steps - 1) + stairs9_1Dyn(steps - 2) + stairs9_1Dyn(steps - 3);
			return stairCombos[steps];
		}
	}
	
}
