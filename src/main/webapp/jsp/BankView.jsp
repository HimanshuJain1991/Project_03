<%@page import="java.util.List"%>
<%@page import="in.co.rays.project_3.controller.BankCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bank View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.input-group-addon {
	/* box-shadow: 9px 8px 7px #001a33; */
	background-image: linear-gradient(to bottom right, Blue);
	background-repeat: no repeat;
	background-size: 100%;
	padding-bottom: 11px;
}

.hm {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/unsplash.jpg');
	background-size: cover;
	padding-top: 6%;
}
</style>

</head>
<body class="hm">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>
	</div>
	<div>

		<main>
		<form action="<%=ORSView.BANK_CTL%>" method="post">
			<jsp:useBean id="dto" class="in.co.rays.project_3.dto.BankDTO"
				scope="request"></jsp:useBean>
			<div class="row pt-3">
				<!-- Grid column -->
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card input-group-addon">
						<div class="card-body">

							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId() != null && id > 0) {
							%>
							<h3 class="text-center default-text text-primary">Update
								Bank</h3>
							<%
								} else {
							%>
							<h3 class="text-center default-text text-primary">Add
								Bank</h3>
							<%
								}
							%>
							<!--Body-->
							<div>
								<%
									List list = (List) request.getAttribute("BankList");
								%>

								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>

								<input type="hidden" name="id" value="<%=dto.getId()%>">

							</div>

							<div class="md-form">

								<span class="pl-sm-5"><b> Bank Name</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-alt grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="bankName"
											placeholder=" Enter Name"
											value="<%=DataUtility.getStringData(dto.getBankName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("bankName", request)%></font></br>

								<span class="pl-sm-5"><b>Bank Account Number</b> <span
									style="color: red;">*</span></span></br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-user-circle grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" name="bankAccountNumber"
											placeholder=" Bank Account Number"
											value="<%=DataUtility.getStringData(dto.getBankAccountNumber())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("bankAccountNumber", request)%></font></br>

								<%-- 	<%
								if ( dto.getId()==null || id <=0   ) {
								
								%>

                               <span class="pl-sm-5"><b>Password</b>
	<span style="color: red;">*</span></span> </br>
    <div class="col-sm-12">
      <div class="input-group">
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-key grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="password" class="form-control" name="password" placeholder="password" value="<%=DataUtility.getStringData(dto.getPassword())%>">
      </div>
    </div>
	<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("password", request)%></font></br>
	
	<span class="pl-sm-5"><b>Confirm Password</b>
	<span style="color: red;">*</span></span> </br>							
	<div class="col-sm-12">
      <div class="input-group">
        <div class="input-group-prepend">
          <div class="input-group-text"><i class="fa fa-key grey-text" style="font-size: 1rem;"></i> </div>
        </div>
        <input type="password" class="form-control" name="confirmPassword" placeholder="confirmPassword" value="<%=DataUtility.getStringData(dto.getConfirmPassword())%>">
      </div>
    </div>
	<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></br>
							<%
									}
								%> --%>
								<span class="pl-sm-5"><b>Bank Date</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-envelope grey-text" style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" id="defaultForm-email"
											name="bankDOB" placeholder=" Enter Bank DATE"
											value="<%=DataUtility.getDateString(dto.getBankDOB())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("bankDOB", request)%></font></br>

								<span class="pl-sm-5"><b>Bank IFSC</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-phone-square grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" id="defaultForm-email"
											maxlength="10" name="bankIFSC"
											placeholder="Enter Bank IFSC"
											value="<%=DataUtility.getStringData(dto.getBankIFSC())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("bankIFSC", request)%></font></br>
								<span class="pl-sm-5"><b>Bank Address</b> <span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-phone-square grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" class="form-control" 
											name="bankAddress" placeholder="Enter Bank Address"
											value="<%=DataUtility.getStringData(dto.getBankAddress())%>">
									</div>
									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("bankAddress", request)%></font></br>
								</div>

								<%
									
									if (id > 0 && dto.getId() != null) {
								%>

								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=BankCtl.OP_UPDATE%>"> <input
										type="submit" name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=BankCtl.OP_CANCEL%>">

								</div>
								<%
									} else {
								%>
								<div class="text-center">

									<input type="submit" name="operation"
										class="btn btn-success btn-md" style="font-size: 17px"
										value="<%=BankCtl.OP_SAVE%>"> <input type="submit"
										name="operation" class="btn btn-warning btn-md"
										style="font-size: 17px" value="<%=BankCtl.OP_RESET%>">
								</div>

							</div>
							<%
								}
							%>
						</div>
					</div>
		</form>
		</main>
		<div class="col-md-4 mb-4"></div>

	</div>

</body>
<%@include file="FooterView.jsp"%>

</body>
</html>