package in.co.rays.project_3.dto;

import java.util.Date;

import in.co.rays.project_3.util.DataUtility;

public class PaymentDTO  extends BaseDTO{
	private String name;
	private String accountNumber;
	private String paymentType;
	private String paymentMode;
	private Date paymentDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
	return paymentMode;
		//return id+"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return paymentMode;
		//return DataUtility.getDateString(paymentDate);
	}

}
