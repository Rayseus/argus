package mtl.argus.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import mtl.argus.dao.UmlDao;
import mtl.argus.pojo.Uml;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UmlDaoTest {

	@Autowired
	UmlDao dao;

	@Test
	public void testQuery() {

		List<Uml> resultList = dao.findBySaml("mysql");
		log.info("one [{}]", resultList);
		log.info("another [{}]", dao.findAll());
	}

	@Test
	public void testFind() {
		Uml resultUml = dao.findByTeamAndProject("mtl", "Demo 1");
		log.info("{}",resultUml);
	}

}
