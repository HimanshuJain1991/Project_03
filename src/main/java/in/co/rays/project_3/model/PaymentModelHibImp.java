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

import in.co.rays.project_3.dto.PaymentDTO;
import in.co.rays.project_3.dto.PaymentDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.EmailBuilder;
import in.co.rays.project_3.util.EmailMessage;
import in.co.rays.project_3.util.EmailUtility;
import in.co.rays.project_3.util.HibDataSource;

public class PaymentModelHibImp implements PaymentModelInt {
	public long add(PaymentDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("in addddddddddddd");

//		PaymentDTO existDto = null;
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
			throw new ApplicationException("Exception in Payment Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();

	}

	public void delete(PaymentDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Payment Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(PaymentDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
//		PaymentDTO exesistDto = findByLogin(dto.getLogin());
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
			throw new ApplicationException("Exception in Payment update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public PaymentDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		PaymentDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (PaymentDTO) session.get(PaymentDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Payment by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public PaymentDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		PaymentDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PaymentDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (PaymentDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Payment by Login " + e.getMessage());

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
			Criteria criteria = session.createCriteria(PaymentDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Payments list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(PaymentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(PaymentDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		Session session = null;
		ArrayList<PaymentDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(PaymentDTO.class);
			if (dto != null) {
				System.out.println("search method" + dto.getName());
				
				 if (dto.getId() != null && dto.getId()>0) { 
					 criteria.add(Restrictions.eq("id", dto.getId())); 
				 }
				 
				 if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}

				if (dto.getAccountNumber() != null && dto.getAccountNumber().length() > 0) {
					criteria.add(Restrictions.like("accountNumber", dto.getAccountNumber() + "%"));
				}
				if (dto.getPaymentMode() != null && dto.getPaymentMode().length() > 0) {
					criteria.add(Restrictions.eq("paymentMode", dto.getPaymentMode()));
				}
				System.out.println("Model ---->>>paymentDate:" + dto.getPaymentDate());
				if (dto.getPaymentDate() != null && dto.getPaymentDate().getTime() > 0) {
					criteria.add(Restrictions.eq("paymentDate", dto.getPaymentDate()));
				}
				 if (dto.getPaymentType() != null && dto.getPaymentType().length() > 0) {
						criteria.add(Restrictions.like("paymentType", dto.getPaymentType() + "%"));
					}
				/*
				 * if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				 * criteria.add(Restrictions.like("lastName", dto.getLastName() + "%")); } if
				 * (dto.getLogin() != null && dto.getLogin().length() > 0) {
				 * criteria.add(Restrictions.like("login", dto.getLogin() + "%")); } if
				 * (dto.getPassword() != null && dto.getPassword().length() > 0) {
				 * criteria.add(Restrictions.like("password", dto.getPassword() + "%")); } if
				 * (dto.getGender() != null && dto.getGender().length() > 0) {
				 * criteria.add(Restrictions.like("gender", dto.getGender() + "%")); } if if
				 * (dto.getLastLogin() != null && dto.getLastLogin().getTime() > 0) {
				 * criteria.add(Restrictions.eq("lastLogin", dto.getLastLogin())); } if
				 * (dto.getRoleId() > 0) { criteria.add(Restrictions.eq("roleId",
				 * dto.getRoleId())); } if (dto.getUnSuccessfullLogin() > 0) {
				 * criteria.add(Restrictions.eq("unSuccessfulLogin",
				 * dto.getUnSuccessfullLogin())); }
				 */
			}
			// if pageSize is greater than 0
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<PaymentDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Payment search");
		} finally {
			session.close();
		}

		return list;
	}

	public PaymentDTO authenticate(String login, String password) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println(login + "kkkkk" + password);
		Session session = null;
		PaymentDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from PaymentDTO where login=? and password=?");

		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (PaymentDTO) list.get(0);
		} else {
			dto = null;

		}
		return dto;
	}

	public List getRoles(PaymentDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
