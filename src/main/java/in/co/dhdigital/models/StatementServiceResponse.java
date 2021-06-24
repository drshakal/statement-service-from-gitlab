package in.co.dhdigital.models;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("StatementServiceResponse")
public class StatementServiceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private AccountDetails accountDetails;
	
	private Restart restart;

	
	private List<TransactionDetail> transactionDetails;
	private boolean faultOccurred;
	private FaultType fault;

	public AccountDetails getAccountDetails() {
		return accountDetails;
	}
	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}
	public Restart getRestart() {
		return restart;
	}
	public void setRestart(Restart restart) {
		this.restart = restart;
	}

	public FaultType getFault() {
		return fault;
	}
	public void setFault(FaultType fault) {
		this.fault = fault;
	}
	

	public List<TransactionDetail> getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	public boolean isFaultOccurred() {
		return faultOccurred;
	}
	public void setFaultOccurred(boolean faultOccurred) {
		this.faultOccurred = faultOccurred;
	}

	
	
}
