package in.co.rays.project_3.test;


import java.util.Date;

import in.co.rays.project_3.dto.AttendenceDTO;
import in.co.rays.project_3.model.AttendenceModelInt;
import in.co.rays.project_3.model.ModelFactory;

public class AttendenceModelTest {
	public static void main(String[] args) throws Exception {
		
		addAttendenceTest();
	}
	
public static void addAttendenceTest() throws Exception {
		
		AttendenceDTO dto = new AttendenceDTO();
		
		dto.setAttendenceName("Himanshu Jain");
		dto.setAttendenceStatus("Present");
		dto.setAttendenceAddress("Indore");
		dto.setAttendenceSection("A Section");
		dto.setAttendenceDOB(new Date());
		
		
	  AttendenceModelInt model=ModelFactory.getInstance().getAttendenceModel();
	  model.add(dto);
		
		
		 
	}
}
