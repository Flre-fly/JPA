package jpaStudy.ex;

import jpaStudy.ex.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ExApplicationTests {

	@Autowired
	private EntityManager em;
	@Autowired
	private MyRepository repository;
	@Test
	@Transactional
	@Commit
	public void 값타입컬렉션의insert쿼리확인(){
		Address address1 = new Address(null, "2");
		Address address2 = new Address("3", "4");
		Address address3 = new Address("12", "123");
		List<Address> addressList = new ArrayList<>();
		addressList.add(address1);
		addressList.add(address2);
		addressList.add(address3);
		Member member = new Member();
		member.setAddressList(null);
		em.persist(member);
		//변경
		em.flush();
		address1 = new Address("213", "!23");
		addressList.remove(0);
		addressList.add(address1);
		/*
		Hibernate: insert into member (member_id, name) values (default, ?)
		Hibernate: insert into address (member_id, code, zip_code) values (?, ?, ?)
		Hibernate: insert into address (member_id, code, zip_code) values (?, ?, ?)
		Hibernate: insert into address (member_id, code, zip_code) values (?, ?, ?)
		Hibernate: delete from address where member_id=?
		Hibernate: insert into address (member_id, code, zip_code) values (?, ?, ?)
		Hibernate: insert into address (member_id, code, zip_code) values (?, ?, ?)
		Hibernate: insert into address (member_id, code, zip_code) values (?, ?, ?)*/
	}


}
