package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.AttendenceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.model.AttendenceModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "AttendenceListCtl", urlPatterns = { "/ctl/AttendenceListCtl" })
public class AttendenceListCtl extends BaseCtl {

	protected void preload(HttpServletRequest request) {
		AttendenceModelInt model = ModelFactory.getInstance().getAttendenceModel();
		try {
			List list = model.list();
			request.setAttribute("AttendenceList", list);
		} catch (Exception e) {

		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		AttendenceDTO dto = new AttendenceDTO();

		dto.setAttendenceName(DataUtility.getString(request.getParameter("attendenceName")));
		dto.setAttendenceStatus(DataUtility.getString(request.getParameter("attendenceStatus")));
		dto.setAttendenceAddress(DataUtility.getString(request.getParameter("attendenceAddress")));
		dto.setAttendenceSection(DataUtility.getString(request.getParameter("attendenceSection")));
		dto.setAttendenceDOB(DataUtility.getDate(request.getParameter("attendenceDOB")));
		
		
		populateBean(dto, request);
		// dto.setId(DataUtility.getLong(request.getParameter("AttendenceDate")));
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list;
		List next;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		AttendenceDTO dto = (AttendenceDTO) populateDTO(request);

		AttendenceModelInt model = ModelFactory.getInstance().getAttendenceModel();
		try {
			list = model.search(dto, pageNo, pageSize);

			ArrayList<AttendenceDTO> a = (ArrayList<AttendenceDTO>) list;

			next = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {

			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list = null;
		List next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		AttendenceDTO dto = (AttendenceDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		AttendenceModelInt model = ModelFactory.getInstance().getAttendenceModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ATTENDENCE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.ATTENDENCE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					AttendenceDTO deletedto = new AttendenceDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.ATTENDENCE_LIST_CTL, request, response);
				return;
			}
			dto = (AttendenceDTO) populateDTO(request);

			list = model.search(dto, pageNo, pageSize);

			ServletUtility.setDto(dto, request);
			next = model.search(dto, pageNo + 1, pageSize);

			ServletUtility.setList(list, request);
			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				if (!OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
			}
			if (next == null || next.size() == 0) {
				request.setAttribute("nextListSize", 0);

			} else {
				request.setAttribute("nextListSize", next.size());
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {

			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected String getView() {
		return ORSView.ATTENDENCE_LIST_VIEW;
	}

}