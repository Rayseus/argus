package mtl.argus.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mtl.argus.dao.UmlDao;
import mtl.argus.pojo.Uml;

@RestController
@RequestMapping("s")
@CrossOrigin
@Slf4j
public class SearchController {

	@Autowired
	UmlDao umlDao;
	@GetMapping()
	public List<Uml> all() {
		log.info("coming query all");
		return umlDao.findAll();
	}
}
