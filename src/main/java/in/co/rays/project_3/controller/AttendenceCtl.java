package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AttendenceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.model.AttendenceModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/AttendenceCtl" })
public class AttendenceCtl extends BaseCtl {

	protected void preload(HttpServletRequest request) {
		AttendenceModelInt model = ModelFactory.getInstance().getAttendenceModel();
		try {
			List list = model.list();
			request.setAttribute("AttendenceList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	  protected boolean validate(HttpServletRequest request) { boolean pass = true;
	  System.out.println("-------------validate started-------------");
	  
	  if (DataValidator.isNull(request.getParameter("attendenceName"))) {
	  request.setAttribute("attendenceName", PropertyReader.getValue("error.require",
	  "Name")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("attendenceName"))) {
	  request.setAttribute("attendenceName", "Name must contains alphabets only"); pass =
	  false; }
	  
	  if (DataValidator.isNull(request.getParameter("attendenceStatus"))) {
		  request.setAttribute("attendenceStatus", PropertyReader.getValue("error.require",
		  "Status")); pass = false; } else if
		  (!DataValidator.isName(request.getParameter("attendenceName"))) {
		  request.setAttribute("attendenceStatus", "Status must contains alphabets only"); pass =
		  false; }
	  
		
	  
	  if (DataValidator.isNull(request.getParameter("attendenceDOB"))) {
	  request.setAttribute("attendenceDOB", PropertyReader.getValue("error.require",
	  "AttendenceDate")); pass = false; } else if
	  (!DataValidator.isDate(request.getParameter("attendenceDOB"))) {
	  request.setAttribute("attendenceDOB", PropertyReader.getValue("error.date",
	  " Attendence Date")); pass = false; }
	  
	  if (DataValidator.isNull(request.getParameter("attendenceAddress"))) {
	  request.setAttribute("attendenceAddress", PropertyReader.getValue("error.require",
	  "Attendence Address")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("attendenceAddress"))) {
	  request.setAttribute("attendenceAddress", "Attendence Address  contains alphabets only"); pass
	  = false; } 
	  
	  if (DataValidator.isNull(request.getParameter("attendenceSection"))) {
	  request.setAttribute("attendenceSection", PropertyReader.getValue("error.require",
	  " Section")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("attendenceSection"))) {
	  request.setAttribute("attendenceSection", "Section  contains alphabets only"); pass
	  = false; } return pass;
	  
	  
	  
	  }
	  
	 

	protected BaseDTO populateDTO(HttpServletRequest request) {
		AttendenceDTO dto = new AttendenceDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setAttendenceName(DataUtility.getString(request.getParameter("attendenceName")));
		dto.setAttendenceStatus(DataUtility.getString(request.getParameter("attendenceStatus")));
		dto.setAttendenceAddress(DataUtility.getString(request.getParameter("attendenceAddress")));
		dto.setAttendenceSection(DataUtility.getString(request.getParameter("attendenceSection")));
		dto.setAttendenceDOB(DataUtility.getDate(request.getParameter("attendenceDOB")));
		
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		AttendenceModelInt model = ModelFactory.getInstance().getAttendenceModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			AttendenceDTO dto;
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
		AttendenceModelInt model = ModelFactory.getInstance().getAttendenceModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			AttendenceDTO dto = (AttendenceDTO) populateDTO(request);
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

			AttendenceDTO dto = (AttendenceDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ATTENDENCE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ATTENDENCE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ATTENDENCE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ATTENDENCE_VIEW;
	}

}
