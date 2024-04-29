package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.AttendenceDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;

public interface AttendenceModelInt {

	public long add(AttendenceDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(AttendenceDTO dto)throws ApplicationException;
	public void update(AttendenceDTO dto)throws ApplicationException,DuplicateRecordException;
	public AttendenceDTO findByPK(long pk)throws ApplicationException;
	public AttendenceDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(AttendenceDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(AttendenceDTO dto)throws ApplicationException;
	public AttendenceDTO authenticate(String login,String password)throws ApplicationException;
    public List getRoles(AttendenceDTO dto)throws ApplicationException;

}
