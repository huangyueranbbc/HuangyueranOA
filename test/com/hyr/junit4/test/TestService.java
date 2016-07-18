package com.hyr.junit4.test;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hyr.oa.model.Department;
import com.hyr.oa.model.Role;
import com.hyr.oa.model.User;


@Component(value="userService")
public class TestService {
	
		@Resource
		private SessionFactory sessionFactory;
	
		@Test
		@Transactional
		public void saveTwoUsers() {
			Session session=sessionFactory.getCurrentSession();
			session.save(new User());
//			int i=1/0;
			session.save(new User());
			session.save(new Department());
			session.save(new Role());
		}
		
}

