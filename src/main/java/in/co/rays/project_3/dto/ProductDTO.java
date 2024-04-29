package in.co.rays.project_3.dto;

import java.util.Date;

public class ProductDTO extends BaseDTO {
private String productName;
private String produceNumber;
private Date productDOB;
private String productAddress;
private String productType;
public String getProductName() {
	return productName;
}
public void setProductName(String productName) {
	this.productName = productName;
}
public String getProduceNumber() {
	return produceNumber;
}
public void setProduceNumber(String produceNumber) {
	this.produceNumber = produceNumber;
}
public Date getProductDOB() {
	return productDOB;
}
public void setProductDOB(Date productDOB) {
	this.productDOB = productDOB;
}
public String getProductAddress() {
	return productAddress;
}
public void setProductAddress(String productAddress) {
	this.productAddress = productAddress;
}
public String getProductType() {
	return productType;
}
public void setProductType(String productType) {
	this.productType = productType;
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
