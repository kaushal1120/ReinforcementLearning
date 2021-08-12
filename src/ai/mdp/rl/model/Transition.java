package ai.mdp.rl.model;

/**
 * Represents a transition with its probability and its destination for a given action.
 * @author kps9907
 *
 */
public class Transition {
	private int destinationState;
	private double actionProbability;

	public Transition(int destinationState, double actionProbability) {
		this.destinationState = destinationState;
		this.actionProbability = actionProbability;
	}

	/**
	 * Returns the destination for the given transition.
	 * @return
	 */
	public int getDestinationState() {
		return destinationState;
	}

	/**
	 * Returns the probability with which a particular action reaches the destination
	 * associated with this transition.
	 * @return
	 */
	public double getActionProbability() {
		return actionProbability;
	}
}
