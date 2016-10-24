package scanner;

import java.util.ArrayList;

public class CalculateEmailScore
{
	static ArrayList<Doublet> termProbabilityList = new ArrayList<>();
	
	public static double calculate()
	{
		double emailScoreX = 1;
		double emailScoreY = 1;
		
		for (Doublet term : termProbabilityList)
		{
			double wordConfProb = term.getNumConf() / term.getNumNorm();
			double wordNormProb = term.getNumNorm() / term.getNumConf();
			double wordProb = wordConfProb / (wordConfProb * wordNormProb);
			emailScoreX *= wordProb;
			emailScoreY *= 1 - wordProb;
		}
		
		return emailScoreX / (emailScoreX + emailScoreY);
	}
}
