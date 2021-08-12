package ai.mdp.rl.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a state in the MDP
 * @author kps9907
 *
 */
public class State {
	/*For an action, lists a combination of the probability and destination for the given action*/
	private List<List<Transition>> actions;
	private int reward;
	private int stateNo;
	private boolean isTerminal;

	public State(int stateNo, int reward, boolean isTerminal) {
		this.reward = reward;
		this.stateNo = stateNo;
		this.isTerminal = isTerminal;
		actions = new ArrayList<List<Transition>>();
	}

	/**
	 * Adds an action to the list of actions with the possible transitions and their probabilities.
	 * @param actionNo Inserts the list of transitions with their probabilities for action : actionNo.
	 * @param action List of transitions and their probabilities for the given action.
	 */
	public void addAction(int actionNo, List<Transition> action) {
		actions.add(actionNo, action);
	}

	/**
	 * Returns the list of transitions with each's probabilities.
	 * @return
	 */
	public List<List<Transition>> getActions(){
		return actions;
	}

	/**
	 * Returns the number of this state.
	 * @return
	 */
	public int getStateNo() {
		return stateNo;
	}

	/**
	 * Returns a reward for this state. Makes sense only for terminal nodes.
	 * @return
	 */
	public int getReward() {
		return reward;
	}

	/**
	 * Identifies a state as terminal or non-terminal.
	 * @return
	 */
	public boolean isTerminal() {
		return isTerminal;
	}
}
