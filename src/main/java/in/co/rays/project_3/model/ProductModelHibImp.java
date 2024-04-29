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

import in.co.rays.project_3.dto.ProductDTO;

import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;
import in.co.rays.project_3.util.EmailBuilder;
import in.co.rays.project_3.util.EmailMessage;
import in.co.rays.project_3.util.EmailUtility;
import in.co.rays.project_3.util.HibDataSource;

public class ProductModelHibImp implements ProductModelInt {
	public long add(ProductDTO dto) throws ApplicationException, DuplicateRecordException {

		System.out.println("in addddddddddddd");

//		ProductDTO existDto = null;
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
			throw new ApplicationException("Exception in Product Add " + e.getMessage());
		} finally {
			session.close();
		}
		/* log.debug("Model add End"); */
		return dto.getId();

	}

	public void delete(ProductDTO dto) throws ApplicationException {
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
			throw new ApplicationException("Exception in Product Delete" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public void update(ProductDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Session session = null;
		Transaction tx = null;
//		ProductDTO exesistDto = findByLogin(dto.getLogin());
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
			throw new ApplicationException("Exception in Product update" + e.getMessage());
		} finally {
			session.close();
		}
	}

	public ProductDTO findByPK(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		ProductDTO dto = null;
		try {
			session = HibDataSource.getSession();
			dto = (ProductDTO) session.get(ProductDTO.class, pk);

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in getting Product by pk");
		} finally {
			session.close();
		}

		return dto;
	}

	public ProductDTO findByLogin(String login) throws ApplicationException {
		// TODO Auto-generated method stub
		Session session = null;
		ProductDTO dto = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProductDTO.class);
			criteria.add(Restrictions.eq("login", login));
			List list = criteria.list();
			if (list.size() == 1) {
				dto = (ProductDTO) list.get(0);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Product by Login " + e.getMessage());

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
			Criteria criteria = session.createCriteria(ProductDTO.class);
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);

			}
			list = criteria.list();

		} catch (HibernateException e) {
			throw new ApplicationException("Exception : Exception in  Products list");
		} finally {
			session.close();
		}

		return list;
	}

	public List search(ProductDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return search(dto, 0, 0);
	}

	public List search(ProductDTO dto, int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub

		Session session = null;
		ArrayList<ProductDTO> list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(ProductDTO.class);
			if (dto != null) {
				
				
				 if (dto.getId() != null && dto.getId()>0) { 
					 criteria.add(Restrictions.eq("id", dto.getId())); 
				 }
				 
				 if (dto.getProductName() != null && dto.getProductName().length() > 0) {
					criteria.add(Restrictions.like("productName", dto.getProductName() + "%"));
				}

				if (dto.getProduceNumber() != null && dto.getProduceNumber().length() > 0) {
					criteria.add(Restrictions.like("produceNumber", dto.getProduceNumber() + "%"));
				}
				if (dto.getProductType() != null && dto.getProductType().length() > 0) {
					criteria.add(Restrictions.eq("productType", dto.getProductType()));
				}
		
				if (dto.getProductDOB() != null && dto.getProductDOB().getTime() > 0) {
					criteria.add(Restrictions.eq("productDOB", dto.getProductDOB()));
				}
				 if (dto.getProductAddress() != null && dto.getProductAddress().length() > 0) {
						criteria.add(Restrictions.like("productAddress", dto.getProductAddress() + "%"));
					}
				
			}
			
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = (ArrayList<ProductDTO>) criteria.list();
		} catch (HibernateException e) {
			throw new ApplicationException("Exception in Product search");
		} finally {
			session.close();
		}

		return list;
	}

	public ProductDTO authenticate(String login, String password) throws ApplicationException {
		// TODO Auto-generated method stub
		System.out.println(login + "kkkkk" + password);
		Session session = null;
		ProductDTO dto = null;
		session = HibDataSource.getSession();
		Query q = session.createQuery("from ProductDTO where login=? and password=?");

		q.setString(0, login);
		q.setString(1, password);
		List list = q.list();
		if (list.size() > 0) {
			dto = (ProductDTO) list.get(0);
		} else {
			dto = null;

		}
		return dto;
	}

	public List getRoles(ProductDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		return null;
	}

}
