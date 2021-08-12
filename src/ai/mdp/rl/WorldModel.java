package ai.mdp.rl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ai.mdp.rl.model.State;
import ai.mdp.rl.util.RandomUtil;

/**
 * WorldModel representation of the given MDP.
 * @author kps9907
 *
 */
public class WorldModel {
	private List<State> stateList;
	private int numberOfNonTerminals;
	private State currentState;

	public WorldModel(List<State> stateList, int numberOfNonTerminals) {
		this.stateList = stateList;
		this.numberOfNonTerminals = numberOfNonTerminals;
		this.currentState = null;
	}

	/**
	 * Returns a random initial state to begin any round. 
	 * Sets the currentState of the world model to this initial state
	 * @return State the initial state
	 */
	public State getInitialState() {
		Random random = new Random();
		int initialState = random.nextInt(numberOfNonTerminals);
		this.currentState = stateList.get(initialState);
		return currentState;
	}

	/**
	 * Given an action, randomly chooses from a distribution, the next state the world model must go to
	 * based on the probabilities of the next state for the given action.
	 * @param action
	 * @return State the next state given the action.
	 */
	public State getNextState(int action) {
		List<Double> nextStateProbDist = new ArrayList<Double>();
		for(int i = 0; i < currentState.getActions().get(action).size(); i++) {
			nextStateProbDist.add(currentState.getActions().get(action).get(i).getActionProbability());
		}
		currentState = stateList.get(currentState.getActions().get(action).get(RandomUtil.choices(nextStateProbDist)).getDestinationState());
		return currentState;
	}
}
