package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ProductDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.RoleModelInt;
import in.co.rays.project_3.model.ProductModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ProductCtl" })
public class ProductCtl extends BaseCtl {

	protected void preload(HttpServletRequest request) {
		ProductModelInt model = ModelFactory.getInstance().getProductModel();
		try {
			List list = model.list();
			request.setAttribute("ProductList", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	  protected boolean validate(HttpServletRequest request) { boolean pass = true;
	  System.out.println("-------------validate started-------------");
	  
	  if (DataValidator.isNull(request.getParameter("productName"))) {
	  request.setAttribute("productName", PropertyReader.getValue("error.require",
	  "Name")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("productName"))) {
	  request.setAttribute("productName", "Name must contains alphabets only"); pass =
	  false; }
	  
	  if (DataValidator.isNull(request.getParameter("produceNumber"))) {
	  request.setAttribute("produceNumber",
	  PropertyReader.getValue("error.require", " Number")); pass = false; }
		/*
		 * else if
		 * (!DataValidator.isAccountNumber(request.getParameter("produceNumber"))) {
		 * request.setAttribute("produceNumber", " Number contain digits only"); pass =
		 * false; }
		 */
	  
	  if (DataValidator.isNull(request.getParameter("productDOB"))) {
	  request.setAttribute("productDOB", PropertyReader.getValue("error.require",
	  "productDate")); pass = false; } else if
	  (!DataValidator.isDate(request.getParameter("productDOB"))) {
	  request.setAttribute("productDOB", PropertyReader.getValue("error.date",
	  " Product Date")); pass = false; }
	  
	  if (DataValidator.isNull(request.getParameter("productAddress"))) {
	  request.setAttribute("productAddress", PropertyReader.getValue("error.require",
	  "product Address")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("productAddress"))) {
	  request.setAttribute("productAddress", "Product Address  contains alphabets only"); pass
	  = false; } 
	  
	  if (DataValidator.isNull(request.getParameter("productType"))) {
	  request.setAttribute("productType", PropertyReader.getValue("error.require",
	  "Product Type")); pass = false; } else if
	  (!DataValidator.isName(request.getParameter("productType"))) {
	  request.setAttribute("productType", "Product IFSC  contains alphabets only"); pass
	  = false; } return pass;
	  
	  }
	 

	protected BaseDTO populateDTO(HttpServletRequest request) {
		ProductDTO dto = new ProductDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setProductName(DataUtility.getString(request.getParameter("productName")));
		dto.setProduceNumber(DataUtility.getString(request.getParameter("produceNumber")));
		dto.setProductDOB(DataUtility.getDate(request.getParameter("productDOB")));
		dto.setProductAddress(DataUtility.getString(request.getParameter("productAddress")));
		dto.setProductType(DataUtility.getString(request.getParameter("productType")));
		/*
		 * System.out.println("id"+dto.getId());
		 * System.out.println("Product Name: "+dto.getProductName());
		 * System.out.println("ProductAccount:"+dto.getProductAccountNumber());
		 * System.out.println("ProductDOB: "+dto.getProductDOB());
		 * System.out.println("ProductAddress:"+dto.getProductAddress());
		 * System.out.println("Product IFSC:   "+dto.getProductIFSC());
		 */
		return dto;

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		ProductModelInt model = ModelFactory.getInstance().getProductModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			System.out.println("in id > 0  condition");
			ProductDTO dto;
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
		ProductModelInt model = ModelFactory.getInstance().getProductModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			ProductDTO dto = (ProductDTO) populateDTO(request);
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

			ProductDTO dto = (ProductDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {

				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PRODUCT_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.PRODUCT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PRODUCT_VIEW;
	}

}
