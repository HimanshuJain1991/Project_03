
<%@page import="in.co.rays.project_3.controller.ProductListCtl"%>
<%@page import="in.co.rays.project_3.dto.ProductDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.model.ModelFactory"%>
<%@page import="in.co.rays.project_3.model.RoleModelInt"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.controller.ProductListCtl"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Product List</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script type="text/javascript"
	src="<%=ORSView.APP_CONTEXT%>/js/CheckBox11.js"></script>
<style>
.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/Stone.jpeg');
	background-size: cover;
	background-repeat: no-repeate;
	padding-top: 6%;
}

.p1 {
	padding: 4px;
	width: 200px;
	font-size: bold;
}

.text {
	text-align: center;
}
</style>
</head>

<body class="hm">
	<%@include file="Header.jsp"%>
	<%@include file="calendar.jsp"%>
	<div></div>
	<div>
		<form class="pb-5" action="<%=ORSView.PRODUCT_LIST_CTL%>"
			method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ProductDTO"
				scope="request"></jsp:useBean>
			<%
				List list1 = (List) request.getAttribute("ProductList");
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextPageSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());
				RoleDTO rbean1 = new RoleDTO();
				RoleModelInt rmodel = ModelFactory.getInstance().getRoleModel();

				List list = ServletUtility.getList(request);

				Iterator<ProductDTO> it = list.iterator();
				if (list.size() != 0) {
			%>
			<center>
				<h1 class="text-primary font-weight-bold pt-3">
					<u>Product List</u>
				</h1>
			</center>
			<div class="row">
				<div class="col-md-4"></div>
				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>
				<div class="col-md-4"></div>
			</div>

			<div class="row" style="background:gray; ">


				<!-- <div class="col-sm-2"></div> -->
				<div class="col-sm-2">
					<input type="text" name="productName"
						placeholder="Enter Product Name" class="form-control "
						value="<%=ServletUtility.getParameter("productName", request)%>">
				</div>

				&emsp;
				<div class="col-sm-2">
					<input type="text" name="produceNumber"
						placeholder="Product  Number" class="form-control"
						value="<%=ServletUtility.getParameter("produceNumber", request)%>">
				</div>
				&emsp;
				<%-- <div class="col-sm-2"><%=HTMLUtility.getList("ProductAddress", String.valueOf(dto.), list1)%></div> --%>
				&emsp;

				<div class="col-sm-2">
					<input type="text" name="productDOB" class="form-control"
						placeholder="Enter DOB"
						value="<%=DataUtility.getDateString(dto.getProductDOB())%>">
				</div>

				<%-- 	<div class="col-sm-2">
					<input type="submit" class="btn btn-primary btn-md"
						style="font-size: 15px" name="operation"
						value="<%=ProductListCtl.OP_SEARCH%>"> &emsp; <input
						type="submit" class="btn btn-dark btn-md" style="font-size: 15px"
						name="operation" value="<%=ProductListCtl.OP_RESET%>">
				</div> --%>

				<div class="col-sm-2">
					<input type="text" name="productType" placeholder="Product Type"
						class="form-control "
						value="<%=ServletUtility.getParameter("ProductType", request)%>">
				</div>
				<%-- <div class="col-sm-2">
					<input type="text" name="ProductMode" placeholder="Search Product Mode"
						class="form-control "
						value="<%=ServletUtility.getParameter("ProductMode", request)%>">
				</div> --%>
				&emsp;&emsp;
				<div class="col-sm-2">
					<input type="text" name="productAddress"
						placeholder="Product Address" class="form-control "
						value="<%=ServletUtility.getParameter("productAddress", request)%>">
				</div>

				<!-- <div class="col-sm-2"></div> -->
			</div>
			&emsp;
			<div class="row">
				<%-- <div class="col-sm-2">
					<input type="text" name="productType" placeholder="Product Type"
						class="form-control "
						value="<%=ServletUtility.getParameter("ProductType", request)%>">
				</div>
				<div class="col-sm-2">
					<input type="text" name="ProductMode" placeholder="Search Product Mode"
						class="form-control "
						value="<%=ServletUtility.getParameter("ProductMode", request)%>">
				</div>&emsp;&emsp;
				<div class="col-sm-2">
					<input type="text" name="productAddress" placeholder="Product Address"
						class="form-control "
						value="<%=ServletUtility.getParameter("productAddress", request)%>">
				</div> --%>
				<!-- <div class="col-sm-2"> -->
				
			<!-- </div> -->
	</div>
<div align="center" >
				<input type="submit" class="btn btn-primary btn-lg "
					style="font-size: 15px" name="operation"
					value="<%=ProductListCtl.OP_SEARCH%>"> &emsp; <input
					type="submit" class="btn btn-dark btn-lg" style="font-size: 15px"
					name="operation" value="<%=ProductListCtl.OP_RESET%>">
					</div>

	</br>
			<div style="margin-bottom: 20px;" class="table-responsive">
				<table class="table table-bordered table-primary table-hover">
					<thead>
						<tr style="background-color: aqua;">

							<th width="10%"><input type="checkbox" id="select_all"
								name="Select" class="text"> Select All</th>
							<th width="5%" class="text">S.NO</th>
							<th width="15%" class="text">Product Name</th>
							<th width="10%" class="text">Product  Number</th>
							<th width="10%" class="text">Product Type</th>
							<th width="10%" class="text">Product Address</th>
							<th width="10%" class="text">DOB</th>
							<th width="5%" class="text">Edit</th>
						</tr>
					</thead>
					<%
						while (it.hasNext()) {
								dto = it.next();

								/* RoleDTO rbean = rmodel.findByPK(dto.getRoleId()); */
					%>
					<tbody>
						<tr>
							<td align="center"><input type="checkbox" class="checkbox"
								name="ids" value="<%=dto.getId()%>"></td>
							<td class="text"><%=index++%></td>.
							<td class="text"><%=dto.getProductName()%></td>
							<td class="text"><%=dto.getProduceNumber()%></td>
							<td class="text"><%=dto.getProductType()%></td>
							<td class="text"><%=dto.getProductAddress()%></td>
							<td class="text"><%=DataUtility.getDateString(dto.getProductDOB())%></td>
							<td class="text"><a href="ProductCtl?id=<%=dto.getId()%>">Edit</a></td>
						</tr>
					</tbody>
					<%
						}
					%>
				</table>
			</div>
			<table width="100%">
				<tr>
					<td><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						value="<%=ProductListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td><input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=ProductListCtl.OP_NEW%>"></td>

					<td><input type="submit" name="operation"
						class="btn btn-danger btn-md" style="font-size: 17px"
						value="<%=ProductListCtl.OP_DELETE%>"></td>

					<td align="right"><input type="submit" name="operation"
						class="btn btn-warning btn-md" style="font-size: 17px"
						style="padding: 5px;" value="<%=ProductListCtl.OP_NEXT%>"
						<%=(nextPageSize != 0) ? "" : "disabled"%>></td>
				</tr>
				<tr></tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>
			<center>
				<h1 style="font-size: 40px; color: #162390;">Product List</h1>
			</center>
			</br>
			<div class="row">
				<div class="col-md-4"></div>

				<%
					if (!ServletUtility.getErrorMessage(request).equals("")) {
				%>
				<div class=" col-md-4 alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>




				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) {
				%>

				<div class="col-md-4 alert alert-success alert-dismissible"
					style="background-color: #80ff80">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<h4>
						<font color="#008000"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h4>
				</div>
				<%
					}
				%>

				<div style="padding-left: 48%;">
					<input type="submit" name="operation"
						class="btn btn-primary btn-md" style="font-size: 17px"
						value="<%=ProductListCtl.OP_BACK%>">
				</div>

				<div class="col-md-4"></div>
			</div>

			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>


	</div>


</body>
<%@include file="FooterView.jsp"%>
</html>