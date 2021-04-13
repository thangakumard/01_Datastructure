package algorithms.graph;

import java.util.*;

import org.testng.annotations.Test;

public class Graph03_AccountsMerge {

	@Test
	private void test() {
		List<List<String>> accounts = new ArrayList<>();
		List<String> lstAccount = new ArrayList<>();
		lstAccount.add("John");
		lstAccount.add("johnsmith@mail.com");
		lstAccount.add("john_newyork@mail.com");
		accounts.add(lstAccount);
		
		lstAccount = new ArrayList<>();
		lstAccount.add("John");
		lstAccount.add("johnsmith@mail.com");
		lstAccount.add("john00@mail.com");
		accounts.add(lstAccount);
		
		lstAccount = new ArrayList<>();
		lstAccount.add("Mary");
		lstAccount.add("mary@mail.com");
		accounts.add(lstAccount);
		
		lstAccount = new ArrayList<>();
		lstAccount.add("John");
		lstAccount.add("johnnybravo@mail.com");
		accounts.add(lstAccount);
		
		List<List<String>> result = accountsMerge(accounts);
		for(List<String> r: result) {
			for(String s: r) {
				System.out.print(s +",");
			}
			System.out.println("");
		}
	}

	public List<List<String>> accountsMerge(List<List<String>> accounts) {
		Map<String, Set<String>> graph = new HashMap<>(); // <email node, neighbor nodes>
		Map<String, String> name = new HashMap<>(); // <email, username>
		// Build the graph;
		for (List<String> account : accounts) {
			String userName = account.get(0);
			for (int i = 1; i < account.size(); i++) {
				if (!graph.containsKey(account.get(i))) {
					graph.put(account.get(i), new HashSet<>());
				}
				name.put(account.get(i), userName);

				if (i == 1)
					continue;
				graph.get(account.get(i)).add(account.get(i - 1));
				graph.get(account.get(i - 1)).add(account.get(i));
			}
		}
		//graph
		/*
		 {
			 johnnybravo@mail.com=[], 
			 johnsmith@mail.com=[john00@mail.com, john_newyork@mail.com], 
			 john00@mail.com=[johnsmith@mail.com], 
			 john_newyork@mail.com=[johnsmith@mail.com], 
			 mary@mail.com=[]
		 }
		 */
		//name
		/*
		 {
			 johnnybravo@mail.com=John, 
			 johnsmith@mail.com=John, 
			 john00@mail.com=John, 
			 john_newyork@mail.com=John, 
			 mary@mail.com=Mary
		 }
		 */

		Set<String> visited = new HashSet<>();
		List<List<String>> res = new LinkedList<>();
		// DFS search the graph;
		for (String email : name.keySet()) {
			List<String> list = new LinkedList<>();
			if (visited.add(email)) {
				dfs(graph, email, visited, list);
				Collections.sort(list);
				list.add(0, name.get(email));
				res.add(list);
			}
		}

		return res;
	}

	public void dfs(Map<String, Set<String>> graph, String email, Set<String> visited, List<String> list) {
		list.add(email);
		for (String next : graph.get(email)) {
			if (visited.add(next)) {
				dfs(graph, next, visited, list);
			}
		}
	}

}
