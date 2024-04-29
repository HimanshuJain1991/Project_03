package in.co.rays.project_3.dto;

import java.util.Date;

public class BankDTO extends BaseDTO {
	String bankName;
	String bankAccountNumber;
	String bankIFSC;
	String bankAddress;
	Date bankDOB;

	

	public Date getBankDOB() {
		return bankDOB;
	}

	public void setBankDOB(Date bankDOB) {
		this.bankDOB = bankDOB;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankIFSC() {
		return bankIFSC;
	}

	public void setBankIFSC(String bankIFSC) {
		this.bankIFSC = bankIFSC;
	}

	public String getBankAddress() {
		return bankAddress;
	}

	public void setBankAddress(String bankAddress) {
		this.bankAddress = bankAddress;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
