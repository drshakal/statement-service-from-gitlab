package in.co.dhdigital.models;

import java.io.Serializable;

public class Restart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String restartRecNo;
	private double restartRecAmount;
	
	
	public String getRestartRecNo() {
		return restartRecNo;
	}
	public void setRestartRecNo(String restartRecNo) {
		this.restartRecNo = restartRecNo;
	}
	public double getRestartRecAmount() {
		return restartRecAmount;
	}
	public void setRestartRecAmount(double restartRecAmount) {
		this.restartRecAmount = restartRecAmount;
	}
	
	
}
