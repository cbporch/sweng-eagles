package scanner;

public class Doublet
{
	private int numConf = 0;
	private int numNorm = 0;

	public Doublet(int conf, int norm) {
		this.numConf = conf;
		this.numNorm = norm;
	}

	public int getNumConf()
	{
		return numConf;
	}

	public int getNumNorm()
	{
		return numNorm;
	}
}