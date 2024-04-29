package in.co.rays.project_3.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.dto.PaymentDTO;
import in.co.rays.project_3.model.BankModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PaymentModelInt;

public class PaymentModelTest {
	public static void main(String[] args) throws Exception {
		//addTest();
		//updateTest();
		//deleteTest();
		// findByPKTest(); 
		 //findByLoginTest();
		 //listTest();
		// searchTest(); 
		addBankTest();
	}
	public static void addTest() throws Exception {
		
		PaymentDTO dto = new PaymentDTO();
		
		dto.setName("Himanshu Jain");
		dto.setAccountNumber("881810100008545");
		dto.setPaymentDate(new Date());
		dto.setPaymentMode("Debit Card");
		dto.setPaymentType("Cash");
	  PaymentModelInt model=ModelFactory.getInstance().getPaymentModel();
	  model.add(dto);
		
		
		 
	}
public static void addBankTest() throws Exception {
		
		BankDTO dto = new BankDTO();
		
		dto.setBankName("HDFC");
		dto.setBankAccountNumber("881810100008545");
		dto.setBankDOB(new Date());
		dto.setBankAddress("Indore");
		dto.setBankIFSC("HDFC005");
	  BankModelInt model=ModelFactory.getInstance().getBankModel();
	  model.add(dto);
		
		
		 
	}
}
