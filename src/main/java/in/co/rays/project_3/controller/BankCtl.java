package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.BankDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.model.BankModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/BankCtl" })
public class BankCtl extends BaseCtl {

	protected void preload(HttpServletRequest request) {
		BankModelInt model = ModelFactory.getInstance().getBankModel();
		try {
			List list = model.list();
			request.setAttribute("BankList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	  protected boolean validate(HttpServletRequest request) { boolean pass = true;
	  System.out.println("-------------validate started-------------");
	  
	  if (DataValidator.isNull(request.getParameter("bankName"))) {
	  request.setAttribute("bankName", PropertyReader.getValue("error.require",
	  "Name")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("bankName"))) {
	  request.setAttribute("bankName", "Name must contains alphabets only"); pass =
	  false; }
	  
	  if (DataValidator.isNull(request.getParameter("bankAccountNumber"))) {
	  request.setAttribute("bankAccountNumber",
	  PropertyReader.getValue("error.require", "Account Number")); pass = false; }
	  else if
	  (!DataValidator.isAccountNumber(request.getParameter("bankAccountNumber"))) {
	  request.setAttribute("bankAccountNumber", "Account Number contain digits only");
	  pass = false; }
	  
	  if (DataValidator.isNull(request.getParameter("bankDOB"))) {
	  request.setAttribute("bankDOB", PropertyReader.getValue("error.require",
	  "BankDate")); pass = false; } else if
	  (!DataValidator.isDate(request.getParameter("bankDOB"))) {
	  request.setAttribute("bankDOB", PropertyReader.getValue("error.date",
	  " Bank Date")); pass = false; }
	  
	  if (DataValidator.isNull(request.getParameter("bankAddress"))) {
	  request.setAttribute("bankAddress", PropertyReader.getValue("error.require",
	  "Bank Address")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("bankAddress"))) {
	  request.setAttribute("bankAddress", "Bank Address  contains alphabets only"); pass
	  = false; } 
	  
	  if (DataValidator.isNull(request.getParameter("bankIFSC"))) {
	  request.setAttribute("bankIFSC", PropertyReader.getValue("error.require",
	  "Bank IFSC")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("BankType"))) {
	  request.setAttribute("bankIFSC", "Bank IFSC  contains alphabets only"); pass
	  = false; } return pass;
	  
	  }
	 

	protected BaseDTO populateDTO(HttpServletRequest request) {
		BankDTO dto = new BankDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setBankName(DataUtility.getString(request.getParameter("bankName")));
		dto.setBankAccountNumber(DataUtility.getString(request.getParameter("bankAccountNumber")));
		dto.setBankDOB(DataUtility.getDate(request.getParameter("bankDOB")));
		dto.setBankAddress(DataUtility.getString(request.getParameter("bankAddress")));
		dto.setBankIFSC(DataUtility.getString(request.getParameter("bankIFSC")));
		/*
		 * System.out.println("id"+dto.getId());
		 * System.out.println("Bank Name: "+dto.getBankName());
		 * System.out.println("BankAccount:"+dto.getBankAccountNumber());
		 * System.out.println("bankDOB: "+dto.getBankDOB());
		 * System.out.println("BankAddress:"+dto.getBankAddress());
		 * System.out.println("Bank IFSC:   "+dto.getBankIFSC());
		 */
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		BankModelInt model = ModelFactory.getInstance().getBankModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			BankDTO dto;
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
		BankModelInt model = ModelFactory.getInstance().getBankModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			BankDTO dto = (BankDTO) populateDTO(request);
			System.out.println(" in do post method jkjjkjk++++++++" + dto.getId());
			try {
				if (id > 0) {
					System.out.println("update Calling------>>>>");
					model.update(dto);
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
				} else {

					try {
						model.add(dto);
						 ServletUtility.setDto(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
					} catch (ApplicationException e) {

						ServletUtility.handleException(e, request, response);
						return;
					} catch (DuplicateRecordException e) {
						ServletUtility.setDto(dto, request);
						ServletUtility.setErrorMessage("Login id already exists", request);
					}

				}
				// ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			BankDTO dto = (BankDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANK_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.BANK_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.BANK_VIEW;
	}

}
