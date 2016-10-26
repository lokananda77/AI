import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.HashSet;
import java.util.LinkedHashMap;

class Search {
	public class Pair<K, V> {
	    public final K x;
	    public final V y;

	    public Pair(K x, V y) {
	        this.x = x;
	        this.y = y;
	    }
	    
	    public String toString() {
	    	return "(" + this.x + ", " + this.y + ")";
	    }
	}

	private boolean checkGoalState(Pair<Integer, Integer> state, int d) {
			if (state.x == d && state.y == 0) {
				return true;
			} else {
				return false;
			}
	}
	
	private boolean isStatePresentInClosed(HashSet<Pair<Integer, Integer>> CLOSED, Pair<Integer, Integer> state) {
		if (state != null) {
			for (Pair<Integer, Integer>closedstate: CLOSED) {
				if (closedstate.toString().equals(state.toString())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private LinkedList<Pair<Integer, Integer>> generateSuccessors(Pair<Integer,Integer> state, int m, int n) {
		LinkedList<Pair<Integer, Integer>> succStates = new LinkedList<Pair<Integer, Integer>>();
		
		// m fill
		if (state.x < m) {
			succStates.add(new Pair<Integer,Integer>(m,state.y));
		}
		// n fill
		if (state.y < n) {
			succStates.add(new Pair<Integer,Integer>(state.x,n));
		}
		// m empty
		if (state.x > 0) {
			succStates.add(new Pair<Integer,Integer>(0, state.y));
		}
		// n empty
		if (state.y > 0) {
			succStates.add(new Pair<Integer,Integer>(state.x, 0));
		}
		// m pour
		if (state.x + state.y >= n && state.x > 0 && state.y != n) {
			succStates.add(new Pair<Integer,Integer>(state.x - (n - state.y),n));
		}
		// m pour
		if (state.x + state.y < n && state.x > 0) {
			succStates.add(new Pair<Integer,Integer>(0, state.x + state.y));
		}
		// n pour
		if (state.x + state.y >= m && state.y > 0 && state.x != m) {
			succStates.add(new Pair<Integer,Integer>(m,state.y - (m - state.x)));
		}
		// n pour
		if (state.x + state.y < m && state.y > 0) {
			succStates.add(new Pair<Integer,Integer>(state.x + state.y, 0));
		}
		return succStates;
	}
	
	private void printSolutionPath(Pair<Integer, Integer> state, LinkedHashMap<Pair<Integer, Integer>,Pair<Integer, Integer>> PARENT) {
		if (state != null) {
			printSolutionPath(PARENT.get(state), PARENT);
			System.out.print(state + " ");
		}
	}
	
	public void searchByBFS(int m, int n, int d) {
		System.out.println("BFS");
		System.out.println("Iteration:");
		
		Queue<Pair<Integer, Integer>> OPEN = new LinkedList<Pair<Integer,Integer>>();
		HashSet<Pair<Integer, Integer>> CLOSED = new HashSet<Pair<Integer, Integer>>();
		LinkedHashMap<Pair<Integer, Integer>,Pair<Integer, Integer>> PARENT = new LinkedHashMap<Pair<Integer, Integer>,Pair<Integer, Integer>>();
		Pair<Integer,Integer> initState = new Pair<Integer, Integer>(0, 0);
		PARENT.put(initState, null);
		OPEN.add(initState);
		Pair<Integer, Integer> state = initState;
		int i = 0;
		while(!OPEN.isEmpty()) {
			while(isStatePresentInClosed(CLOSED, state)) {
				state = OPEN.poll();
			}
			if (state == null) {
				break;
			}
			CLOSED.add(state);
			
			if (checkGoalState(state, d)) {
				System.out.println("Result:");
				printSolutionPath(state, PARENT);
				System.out.println();
				return;
			} else {
				System.out.println(i);
				
				//Printing states in closed
				for(Pair<Integer, Integer> closedState: CLOSED) {
					System.out.print(closedState + " ");
				}
				System.out.println();
				
				//Generate and Print successor states
				LinkedList<Pair<Integer, Integer>> succStates = this.generateSuccessors(state,m,n);
				for(Pair<Integer, Integer> succState: succStates) {
					PARENT.put(succState, state);
					OPEN.add(succState);
					System.out.print(succState + " ");	
				}
				System.out.println();
				
				i++;
			}
		}
		System.out.println("Unsolvable");
	}
	
	public void searchByDFS(int m, int n, int d) {
		System.out.println("DFS");
		System.out.println("Iteration:");
		
		Stack<Pair<Integer, Integer>> OPEN = new Stack<Pair<Integer,Integer>>();
		HashSet<Pair<Integer, Integer>> CLOSED = new HashSet<Pair<Integer, Integer>>();
		LinkedHashMap<Pair<Integer, Integer>,Pair<Integer, Integer>> PARENT = new LinkedHashMap<Pair<Integer, Integer>,Pair<Integer, Integer>>();
		Pair<Integer,Integer> initState = new Pair<Integer, Integer>(0, 0);
		OPEN.add(initState);
		Pair<Integer, Integer> state = initState;
		
		int i = 0;
		while(!OPEN.isEmpty()) {
			
			//neglect a state if it is already present in closed
			while(isStatePresentInClosed(CLOSED, state)) {
				if (!OPEN.isEmpty()) {
					state = OPEN.pop();
				} else {
					state = null;
				}
			}
			if (state == null) {
				break;
			}
			CLOSED.add(state);
			if (checkGoalState(state, d)) {
				System.out.println("Result:");
				printSolutionPath(state, PARENT);
				System.out.println();
				return;
			} else {
				
				System.out.println(i);
				
				//Printing states in closed
				for(Pair<Integer, Integer> closedState: CLOSED) {
					System.out.print(closedState + " ");
				}
				System.out.println();
				
				//Generate and Print successor states
				LinkedList<Pair<Integer, Integer>> succStates = this.generateSuccessors(state, m, n);
				for(Pair<Integer, Integer> succState: succStates) {
					PARENT.put(succState, state);
					OPEN.add(succState);
					System.out.print(succState + " ");
				}
				System.out.println();
				
				i++;
			}
		}
		System.out.println("Unsolvable");
	}
	
	public static void main(String[] args) {
		int m, n, d;
		m = Integer.parseInt(args[0], 10);
		n = Integer.parseInt(args[1], 10);
		d = Integer.parseInt(args[2], 10);
		Search s = new Search();
		s.searchByBFS(m,n,d);
		s.searchByDFS(m,n,d);
		return;
	}

}