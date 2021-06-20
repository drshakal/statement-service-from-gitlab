package in.co.dhdigital.models;

public class RestartResponse {

	private Restart restart;
	private boolean faultOccurred;
	
	
	public Restart getRestart() {
		return restart;
	}
	public void setRestart(Restart restart) {
		this.restart = restart;
	}
	public boolean isFaultOccurred() {
		return faultOccurred;
	}
	public void setFaultOccurred(boolean faultOccurred) {
		this.faultOccurred = faultOccurred;
	}

	
	
}
