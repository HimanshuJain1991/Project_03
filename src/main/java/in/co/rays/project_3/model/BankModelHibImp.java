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

import in.co.rays.project_3.dto.BankDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.EmailBuilder;
import in.co.rays.project_3.util.EmailMessage;
import in.co.rays.project_3.util.EmailUtility;
import in.co.rays.project_3.util.HibDataSource;

public class BankModelHibImp implements BankModelInt {
	public long add(BankDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("in addddddddddddd");

//		BankDTO existDto = null;
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
			throw new ApplicationException("Exception in Bank Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();

	}

	public void delete(BankDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Bank Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(BankDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
//		BankDTO exesistDto = findByLogin(dto.getLogin());
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
			throw new ApplicationException("Exception in Bank update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public BankDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		BankDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (BankDTO) session.get(BankDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Bank by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public BankDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		BankDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BankDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (BankDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Bank by Login " + e.getMessage());

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
			Criteria criteria = session.createCriteria(BankDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Banks list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(BankDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(BankDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		Session session = null;
		ArrayList<BankDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(BankDTO.class);
			if (dto != null) {
				
				
				 if (dto.getId() != null && dto.getId()>0) { 
					 criteria.add(Restrictions.eq("id", dto.getId())); 
				 }
				 
				 if (dto.getBankName() != null && dto.getBankName().length() > 0) {
					criteria.add(Restrictions.like("bankName", dto.getBankName() + "%"));
				}

				if (dto.getBankAccountNumber() != null && dto.getBankAccountNumber().length() > 0) {
					criteria.add(Restrictions.like("bankAccountNumber", dto.getBankAccountNumber() + "%"));
				}
				if (dto.getBankIFSC() != null && dto.getBankIFSC().length() > 0) {
					criteria.add(Restrictions.eq("bankIFSC", dto.getBankIFSC()));
				}
		
				if (dto.getBankDOB() != null && dto.getBankDOB().getTime() > 0) {
					criteria.add(Restrictions.eq("bankDOB", dto.getBankDOB()));
				}
				 if (dto.getBankAddress() != null && dto.getBankAddress().length() > 0) {
						criteria.add(Restrictions.like("bankAddress", dto.getBankAddress() + "%"));
					}
				
			}
			
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<BankDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Bank search");
		} finally {
			session.close();
		}

		return list;
	}

	public BankDTO authenticate(String login, String password) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println(login + "kkkkk" + password);
		Session session = null;
		BankDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from BankDTO where login=? and password=?");

		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (BankDTO) list.get(0);
		} else {
			dto = null;

		}
		return dto;
	}

	public List getRoles(BankDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
