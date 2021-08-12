package ai.mdp.rl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import ai.mdp.rl.model.State;
import ai.mdp.rl.model.Transition;

/**
 * Executes the Reinforcement Learning Process.
 * @author kps9907
 *
 */
public class Executor {
	private List<State> stateList;
	private int numberOfNonTerminals;
	private int numberOfTerminals;
	private int roundsToPlay;
	private int opFrequency;
	private int controlParam;

	/**
	 * Reads the input for the given MDP.
	 */
	public void getInput() {
		File inputFile = new File(System.getProperty("user.dir") + "/src/input_files/mdp_input.txt");
		stateList = new ArrayList<State>();
		Scanner scanner;
		try {			
			scanner = new Scanner(inputFile);
			List<String> actionInfo;
			numberOfNonTerminals = scanner.nextInt();
			numberOfTerminals = scanner.nextInt();
			roundsToPlay = scanner.nextInt();
			opFrequency = scanner.nextInt();
			controlParam = scanner.nextInt();
			for(int i = 0; i < numberOfNonTerminals; i++)
				stateList.add(new State(i,0,false));
			for(int i = 0; i < numberOfTerminals; i++)
				stateList.add(new State(scanner.nextInt(),scanner.nextInt(), true));
			scanner.nextLine();
			while(scanner.hasNextLine()) {		
				actionInfo = Arrays.asList(scanner.nextLine().split("\\s+"));
				List<Transition> transitionProbabilityList = new ArrayList<Transition>();
				for(int j = 1; j < actionInfo.size();j += 2) {
					Transition action = new Transition(Integer.valueOf(actionInfo.get(j)), Double.valueOf(actionInfo.get(j+1)));
					transitionProbabilityList.add(action);
				}
				stateList.get(Integer.valueOf(actionInfo.get(0).substring(0, 1))).addAction(
						Integer.valueOf(actionInfo.get(0).substring(2, 3)), transitionProbabilityList);
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initiates the learning through experimentation process
	 * @param agent
	 * @param worldModel
	 */
	public void initiate(Agent agent, WorldModel worldModel) {
		for(int i = 1; i <= roundsToPlay; i++) {
			executeRound(agent, worldModel);
			if(opFrequency != 0 && i%opFrequency == 0) {
				System.out.println("After " + i + " rounds");
				agent.output();
				System.out.println();
			}
		}
		if(opFrequency == 0)
			agent.output();
	}

	/**
	 * Executes a round of the MDP Process.
	 * @param agent
	 * @param worldModel
	 */
	public void executeRound(Agent agent, WorldModel worldModel) {
		//WorldModel selects a random initial state.
		State startState = worldModel.getInitialState();
		List<List<Integer>> count = new ArrayList<List<Integer>>();
		List<List<Integer>> total = new ArrayList<List<Integer>>();
		for(int i = 0; i < numberOfNonTerminals; i++) {
			count.add(new ArrayList<Integer>());
			total.add(new ArrayList<Integer>());
			for(int j = 0; j < stateList.get(i).getActions().size(); j++) {
				count.get(i).add(0);
				total.get(i).add(0);
			}
		}

		//Executes the stochastic MDP Process until a terminal is reached.
		int action = -1;
		while(!startState.isTerminal()) {
			//An action is chosen by the agent.
			action = agent.chooseAction(startState, controlParam, stateList.subList(numberOfNonTerminals, numberOfNonTerminals + numberOfTerminals));
			if(count.get(startState.getStateNo()).get(action) == 0)
				count.get(startState.getStateNo()).set(action, 1);
			//The world model is updated to do the action and the next state the world model moves to is returned.
			startState = worldModel.getNextState(action);
		}

		//For all the actions that led to this reward, update the state and that action with the reward.
		for(int i = 0; i < count.size(); i++) {
			for(int j = 0; j < count.get(i).size(); j++) {
				if(count.get(i).get(j)==1)
					total.get(i).set(j, startState.getReward());
			}
		}
		//Update these values with the agent.
		agent.updateValues(count, total);
	}

	/**
	 * Creates a world model and and agent for the mdp.
	 * @param args
	 */
	public static void main(String[] args) {
		Executor executor = new Executor();
		executor.getInput();

		//Create a world MDP model for the given input
		WorldModel worldModel = new WorldModel(executor.stateList, executor.numberOfNonTerminals);
		
		//Initialize and prepare the count and total arrays for the agent.
		List<List<Integer>> count = new ArrayList<List<Integer>>();
		List<List<Integer>> total = new ArrayList<List<Integer>>();
		for(int i = 0; i < executor.numberOfNonTerminals; i++) {
			count.add(new ArrayList<Integer>());
			total.add(new ArrayList<Integer>());
			for(int j = 0; j < executor.stateList.get(i).getActions().size(); j++) {
				count.get(i).add(0);
				total.get(i).add(0);
			}
		}
		Agent agent = new Agent(total,count);

		//Begin learning through experimentation process.
		executor.initiate(agent, worldModel);
	}
}
