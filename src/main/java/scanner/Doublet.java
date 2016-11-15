package scanner;

/**
 * Storage of a single set of information on a confidential term in an email.
 */
public class Doublet
{
	private int numConf = 0;
	private int numNorm = 0;

	/**
	 * Creates a Doublet and stores the provided number given to each variable.
	 * @param conf Number of times the word has been used confidentially.
	 * @param norm Number of times the word has been used normally.
	 */
	public Doublet(int conf, int norm) {
		this.numConf = conf;
		this.numNorm = norm;
	}

	/**
	 * Returns the number of confidential uses of the word.
	 * @return numConf
	 */
	public int getNumConf()
	{
		return numConf;
	}

	/**
	 * Returns the number of normal uses of the word.
	 * @return numNorm
	 */
	public int getNumNorm()
	{
		return numNorm;
	}
}