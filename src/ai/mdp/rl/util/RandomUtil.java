package ai.mdp.rl.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Random Utility class to provide a method for choosing randomly from a distribution.
 * @author kps9907
 *
 */
public class RandomUtil {
	/**
	 * Choose randomly from a distribution.
	 * @param probabilities of picking an action.
	 * @return index of the chosen action
	 */
	public static int choices(List<Double> p) {
		List<Double> u = new ArrayList<Double>();
		u.add(p.get(0));
		for(int i = 1; i < p.size(); i++)
			u.add(u.get(i-1) + p.get(i));
		double x = Math.random();
		for(int i = 0; i < p.size() - 1; i++) {
			if(x < u.get(i))
				return i;
		}
		return p.size() - 1;
	}
}
