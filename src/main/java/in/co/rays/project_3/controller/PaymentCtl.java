package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.PaymentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.model.PaymentModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/PaymentCtl" })
public class PaymentCtl extends BaseCtl {

	protected void preload(HttpServletRequest request) {
		PaymentModelInt model = ModelFactory.getInstance().getPaymentModel();
		try {
			List list = model.list();
			request.setAttribute("paymentList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	  protected boolean validate(HttpServletRequest request) {
		  boolean pass = true;
	  System.out.println("-------------validate started-------------");
	  
	  
	  if (DataValidator.isNull(request.getParameter("name"))) {
		  request.setAttribute("name", PropertyReader.getValue("error.require",
		  "Name")); pass = false; } else if
		  (!DataValidator.isName(request.getParameter("name"))) {
		  request.setAttribute("name", "Name must contains alphabets only");
		  pass = false;}
		  
		   if (DataValidator.isNull(request.getParameter("accountNumber"))) {
			  request.setAttribute("accountNumber", PropertyReader.getValue("error.require",
			  "Account Number")); pass = false; }  else if
			  (!DataValidator.isAccountNumber(request.getParameter("accountNumber"))) {
				  request.setAttribute("accountNumber", "Account number contain digits only");
				  pass = false;}
		   
		   
		  if (DataValidator.isNull(request.getParameter("paymentDate"))) {
			  request.setAttribute("paymentDate", PropertyReader.getValue("error.require", "paymentDate"));
			  pass = false; }else if (!DataValidator.isDate(request.getParameter("paymentDate"))) {
			  request.setAttribute("paymentDate", PropertyReader.getValue("error.date",
			  " Payment Date")); pass = false; }
		  
		  if (DataValidator.isNull(request.getParameter("paymentMode"))) {
			  request.setAttribute("paymentMode", PropertyReader.getValue("error.require",
			  "Payment Mode")); pass = false; } else if
			  (!DataValidator.isName(request.getParameter("paymentMode"))) {
			  request.setAttribute("paymentMode", "Payment Mode  contains alphabets only");
			  pass = false;
		  }
		  if (DataValidator.isNull(request.getParameter("paymentType"))) {
			  request.setAttribute("paymentType", PropertyReader.getValue("error.require",
			  "Payment Type")); pass = false; } else if
			  (!DataValidator.isName(request.getParameter("paymentType"))) {
			  request.setAttribute("paymentType", "Payment Name  contains alphabets only");
			  pass = false;	  
		  }
	 return pass;
	  
	  }
	 

	protected BaseDTO populateDTO(HttpServletRequest request) {
		PaymentDTO dto = new PaymentDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		  dto.setName(DataUtility.getString(request.getParameter("name")));
		  
		  dto.setAccountNumber(DataUtility.getString(request.getParameter("accountNumber")));
		  dto.setPaymentDate(DataUtility.getDate(request.getParameter("paymentDate")));

	     dto.setPaymentMode(DataUtility.getString(request.getParameter("paymentMode")));
	     dto.setPaymentType(DataUtility.getString(request.getParameter("paymentType")));
		  
		 

		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		PaymentModelInt model = ModelFactory.getInstance().getPaymentModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			PaymentDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDto(dto, request);
			} catch (Exception e) {
				e.printStackTrace();

				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out
				.println("-------------------------------------------------------------------------dopost run-------");
		// get model
		PaymentModelInt model = ModelFactory.getInstance().getPaymentModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			PaymentDTO dto = (PaymentDTO) populateDTO(request);
			System.out.println(" in do post method jkjjkjk++++++++" + dto.getId());
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						//ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {

						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
			//	ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			PaymentDTO dto = (PaymentDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.PAYMENT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PAYMENT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PAYMENT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PAYMENT_VIEW;
	}

}
