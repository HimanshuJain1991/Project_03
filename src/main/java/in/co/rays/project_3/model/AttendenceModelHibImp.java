package in.co.rays.project_3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import in.co.rays.project_3.dto.AttendenceDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.EmailBuilder;
import in.co.rays.project_3.util.EmailMessage;
import in.co.rays.project_3.util.EmailUtility;
import in.co.rays.project_3.util.HibDataSource;

public class AttendenceModelHibImp implements AttendenceModelInt {
	public long add(AttendenceDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("in addddddddddddd");

//		AttendenceDTO existDto = null;
//		existDto = findByLogin(dto.getLogin());
//		if (existDto != null) {
//			throw new DuplicateRecordException("login id already exist");
//		}
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();

			session.save(dto);

			dto.getId();
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			// TODO: handle exception
			if (tx != null) {
				tx.rollback();

			}
			throw new ApplicationException("Exception in Attendence Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();

	}

	public void delete(AttendenceDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Attendence Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(AttendenceDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
//		AttendenceDTO exesistDto = findByLogin(dto.getLogin());
//
//		if (exesistDto != null && exesistDto.getId() != dto.getId()) {
//			throw new DuplicateRecordException("Login id already exist");
//		}

		try {
			session = HibDataSource.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(dto);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ApplicationException("Exception in Attendence update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public AttendenceDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		AttendenceDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (AttendenceDTO) session.get(AttendenceDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Attendence by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public AttendenceDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		AttendenceDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AttendenceDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (AttendenceDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Attendence by Login " + e.getMessage());

		} finally {
			session.close();
		}

		return dto;
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AttendenceDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Attendences list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(AttendenceDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(AttendenceDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		Session session = null;
		ArrayList<AttendenceDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(AttendenceDTO.class);
			if (dto != null) {
				
				
				 if (dto.getId() != null && dto.getId()>0) { 
					 criteria.add(Restrictions.eq("id", dto.getId())); 
				 }
				 
				 if (dto.getAttendenceName() != null && dto.getAttendenceName().length() > 0) {
					criteria.add(Restrictions.like("attendenceName", dto.getAttendenceName() + "%"));
				}

				if (dto.getAttendenceStatus() != null && dto.getAttendenceStatus().length() > 0) {
					criteria.add(Restrictions.like("attendenceStatus", dto.getAttendenceStatus() + "%"));
				}
				
				if (dto.getAttendenceAddress() != null && dto.getAttendenceAddress().length() > 0) {
					criteria.add(Restrictions.like("attendenceAddress", dto.getAttendenceAddress() + "%"));
				}
				if (dto.getAttendenceSection() != null && dto.getAttendenceSection().length() > 0) {
					criteria.add(Restrictions.eq("attendenceSection", dto.getAttendenceSection()));
				}
		
				if (dto.getAttendenceDOB() != null && dto.getAttendenceDOB().getTime() > 0) {
					criteria.add(Restrictions.eq("attendenceDOB", dto.getAttendenceDOB()));
				}
				 
				
			}
			
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<AttendenceDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Attendence search");
		} finally {
			session.close();
		}

		return list;
	}

	public AttendenceDTO authenticate(String login, String password) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println(login + "kkkkk" + password);
		Session session = null;
		AttendenceDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from AttendenceDTO where login=? and password=?");

		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (AttendenceDTO) list.get(0);
		} else {
			dto = null;

		}
		return dto;
	}

	public List getRoles(AttendenceDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
