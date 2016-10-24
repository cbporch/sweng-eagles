package scanner.analysis;

import scanner.Doublet;

import java.util.ArrayList;

public class CalculateEmailScore
{
//	static ArrayList<Doublet> termProbabilityList = new ArrayList<>();
	
	public static double calculate(ArrayList<Doublet> termProbabilityList)
	{
		double emailScoreX = 1;
		double emailScoreY = 1;
		
		for (Doublet term : termProbabilityList)
		{
			double wordConfProb = term.getNumConf() / (double) term.getNumNorm();
			double wordNormProb = term.getNumNorm() / (double) term.getNumConf();

			if(wordConfProb > 1.0){
				wordConfProb = 1.0;
			}

			if(wordNormProb > 1.0){
				wordNormProb = 1.0;
			}

			double wordProb = wordConfProb / (wordConfProb + wordNormProb);
			emailScoreX *= wordProb;
			emailScoreY *= 1 - wordProb;
		}
		
		return (emailScoreX / (emailScoreX + emailScoreY));
	}
}
