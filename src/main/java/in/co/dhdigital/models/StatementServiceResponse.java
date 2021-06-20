package in.co.dhdigital.models;

import java.util.List;

public class StatementServiceResponse {

	private AccountDetails accountDetails;
	
	private Restart restart;
	private FaultType fault;
	private boolean faultOccurred;
	
	private List<TransactionDetail> transactionDetail;

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
	
	public List<TransactionDetail> getTransactionDetail() {
		return transactionDetail;
	}
	public void setTransactionDetail(List<TransactionDetail> transactionDetail) {
		this.transactionDetail = transactionDetail;
	}
	public boolean isFaultOccurred() {
		return faultOccurred;
	}
	public void setFaultOccurred(boolean faultOccurred) {
		this.faultOccurred = faultOccurred;
	}

	
	
}
