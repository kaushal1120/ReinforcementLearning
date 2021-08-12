package ai.mdp.rl;
import java.util.ArrayList;
import java.util.List;

import ai.mdp.rl.model.State;
import ai.mdp.rl.util.RandomUtil;

/**
 * Agent that makes a choice of action to put the environment from one state into a new state.
 * @author kps9907
 *
 */
public class Agent {
	/**
	 * List of attained rewards (count) and actions (total) taken to help make choices for further actions.
	 */
	private List<List<Integer>> count;
	private List<List<Integer>> total;

	public Agent(List<List<Integer>> total, List<List<Integer>> count){
		this.total = total;
		this.count = count;
	}

	/**
	 * Updates the values of count and total for the agent after a round is played.
	 * @param count
	 * @param total
	 */
	public void updateValues(List<List<Integer>> roundCount, List<List<Integer>> roundTotal) {
		for(int i = 0; i < this.count.size(); i++) {
			for(int j = 0; j < this.count.get(i).size(); j++) {
				this.count.get(i).set(j, this.count.get(i).get(j) + roundCount.get(i).get(j));
				this.total.get(i).set(j, this.total.get(i).get(j) + roundTotal.get(i).get(j));
			}
		}
	}

	/**
	 * Make a choice of action to put the environment into a new state.
	 * @param s
	 * @param count
	 * @param total
	 * @param M
	 * @param terminalStates
	 * @return
	 */
	public int chooseAction(State s, int M, List<State> terminalStates) {
		int n = s.getActions().size();
		for(int i = 0; i < n; i++)
			if(count.get(s.getStateNo()).get(i)==0)
				return i;
		List<Double> avg = new ArrayList<Double>();
		for(int i = 0; i < n; i++) {
			avg.add((double)total.get(s.getStateNo()).get(i)/(double)count.get(s.getStateNo()).get(i));			
		}
		int bottom = Integer.MAX_VALUE;
		int top = Integer.MIN_VALUE;
		for(State state : terminalStates) {
			if(bottom > state.getReward())
				bottom = state.getReward();
			if(top < state.getReward())
				top = state.getReward();
		}
		List<Double> savg = new ArrayList<>();
		for(int i = 0; i < avg.size(); i++)
			savg.add(0.25 + 0.75*(avg.get(i) - (double)bottom)/((double)top - (double)bottom));
		int c = 0;
		for(int i = 0; i < count.get(s.getStateNo()).size(); i++) {
			c += count.get(s.getStateNo()).get(i);
		}
		List<Double> up = new ArrayList<Double>();
		List<Double> p = new ArrayList<Double>();
		double norm = 0;
		for(int i = 0; i < n; i++) {
			up.add(Math.pow(savg.get(i), (double)c/(double)M));
			norm += up.get(i);
		}
		for(int i = 0; i < n; i++) {
			p.add(up.get(i)/norm);
		}
		return RandomUtil.choices(p);
	}

	/**
	 * Outputs the count of each possible action taken, their associated rewards and the best action from each state
	 * based on these values.
	 */
	public void output() {
		System.out.println("Count:");
		for(int i = 0; i < count.size(); i++) {
			for(int j = 0; j < count.get(i).size(); j++) {
				System.out.print("[" + i + "," + j + "]=" + count.get(i).get(j) + " ");
			}
			System.out.println();
		}
		System.out.println("Total:");
		List<String> bestActions = new ArrayList<String>();
		double bestActionAvg = 0.0;
		for(int i = 0; i < total.size(); i++) {
			bestActions.add("0");
			bestActionAvg = 0.0;
			for(int j = 0; j < total.get(i).size(); j++) {
				System.out.print("[" + i + "," + j + "]=" + total.get(i).get(j) + " ");
				if(count.get(i).get(j) == 0) {
					bestActions.set(i, "U");
					break;
				}
				else{
					if((double) total.get(i).get(j)/count.get(i).get(j) > bestActionAvg) {
						bestActionAvg = (double) total.get(i).get(j)/count.get(i).get(j);
						bestActions.set(i, String.valueOf(j));
					}
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.print("Best action: ");
		for(int i = 0; i < bestActions.size(); i++) {
			System.out.print(i + ":" + bestActions.get(i) + ". ");
		}
		System.out.println();
	}
}
