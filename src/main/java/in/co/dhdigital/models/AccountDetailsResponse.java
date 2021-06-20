package in.co.dhdigital.models;

import java.util.List;

public class AccountDetailsResponse {

	private AccountDetails accountDetails;
	private FaultType fault;
	private boolean faultOccurred;
	
	private List<TransactionDetail> transactionDetail;
	
	
	// Getters and Setters.
	public AccountDetails getAccountDetails() {
		return accountDetails;
	}



	public List<TransactionDetail> getTransactionDetail() {
		return transactionDetail;
	}



	public void setTransactionDetail(List<TransactionDetail> transactionDetail) {
		this.transactionDetail = transactionDetail;
	}



	public void setAccountDetails(AccountDetails accountDetails) {
		this.accountDetails = accountDetails;
	}
	public FaultType getFault() {
		return fault;
	}
	public void setFault(FaultType fault) {
		this.fault = fault;
	}
	public boolean isFaultOccurred() {
		return faultOccurred;
	}
	public void setFaultOccurred(boolean faultOccurred) {
		this.faultOccurred = faultOccurred;
	}
	
	
}
