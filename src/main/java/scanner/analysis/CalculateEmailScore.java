package scanner.analysis;

import scanner.Doublet;

import java.util.ArrayList;

/**
 * Calculates, using Bayes Theorem, the score of the email based on its confidential terms.
 */
public class CalculateEmailScore
{
    /**
     * Bayes Theorem of all confidential terms is multiplied together to return a total score of the email.
     * @param termProbabilityList ArrayList of Doublets that store each confidential term's usages of each type.
     * @return emailScore score of the email.
     */
	public static double calculate(ArrayList<Doublet> termProbabilityList)
	{
		double greatestConfProb = 0;
		double emailScoreX = 1;
		double emailScoreY = 1;

		if (termProbabilityList.size() == 0)
			return 0.0;

		for (Doublet term : termProbabilityList)
		{
			int totalConfNorm = term.getNumConf() + term.getNumNorm();
			double wordConfProb;
			double wordNormProb;

			if (totalConfNorm == 0)
				wordConfProb = 0.0;
			else
				wordConfProb = term.getNumConf() / (double) totalConfNorm;

			if (greatestConfProb < wordConfProb)
				greatestConfProb = wordConfProb;

			if (totalConfNorm == 0)
				wordNormProb = 0.0;
			else
				wordNormProb = term.getNumNorm() / (double) totalConfNorm;

			if(wordConfProb > 1.0){
				wordConfProb = 1.0;
			}

			if(wordNormProb > 1.0){
				wordNormProb = 1.0;
			}

			double wordProb;
			double totalProb;
			if ((totalProb = wordConfProb + wordNormProb) == 0)
				wordProb = 0;
			else
				wordProb = wordConfProb / totalProb;
			emailScoreX *= wordProb;
			emailScoreY *= 1 - wordProb;
		}

		double result = emailScoreX / (emailScoreX + emailScoreY);

		if (greatestConfProb > result)
			return greatestConfProb;
		return result;
	}
}
