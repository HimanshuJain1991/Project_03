package in.co.rays.project_3.dto;

import java.util.Date;

public class AttendenceDTO extends BaseDTO{

	private String attendenceName = null;
	private String attendenceStatus = null;
	private String attendenceAddress = null;
	private String attendenceSection = null;
	private Date attendenceDOB;

	public String getAttendenceName() {
		return attendenceName;
	}

	public void setAttendenceName(String attendenceName) {
		this.attendenceName = attendenceName;
	}

	public String getAttendenceStatus() {
		return attendenceStatus;
	}

	public void setAttendenceStatus(String attendenceStatus) {
		this.attendenceStatus = attendenceStatus;
	}

	public String getAttendenceAddress() {
		return attendenceAddress;
	}

	public void setAttendenceAddress(String attendenceAddress) {
		this.attendenceAddress = attendenceAddress;
	}

	public String getAttendenceSection() {
		return attendenceSection;
	}

	public void setAttendenceSection(String attendenceSection) {
		this.attendenceSection = attendenceSection;
	}

	public Date getAttendenceDOB() {
		return attendenceDOB;
	}

	public void setAttendenceDOB(Date attendenceDOB) {
		this.attendenceDOB = attendenceDOB;
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
