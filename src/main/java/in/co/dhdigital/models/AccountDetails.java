package in.co.dhdigital.models;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class AccountDetails   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String accountNumber;

	private String accountTypeDescription;
	
	private String name1;
	private String name2;
	
	private String addressFormat;
	
	private String address1;
	
	private String address2;
	
	private String address3;
	
	private String address4;
	
	private String address5;

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountTypeDescription() {
		return accountTypeDescription;
	}

	public void setAccountTypeDescription(String accountTypeDescription) {
		this.accountTypeDescription = accountTypeDescription;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getAddressFormat() {
		return addressFormat;
	}

	public void setAddressFormat(String addressFormat) {
		this.addressFormat = addressFormat;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getAddress5() {
		return address5;
	}

	public void setAddress5(String address5) {
		this.address5 = address5;
	}
	
	
}
