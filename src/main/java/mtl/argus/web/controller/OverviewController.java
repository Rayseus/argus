package mtl.argus.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mtl.argus.dao.UmlDao;
/**
 * 
 * @author Guo
 *
 */
@RestController
@CrossOrigin
@RequestMapping("overview")
public class OverviewController {
	@Autowired
	UmlDao dao;

	@GetMapping
	public Map<String, Object> get() {

		Map<String, Object> result = new HashMap<String, Object>();

		result.put("project", dao.countByProject());
		result.put("team", dao.countByteam());
		result.put("projects", dao.findAll());
		return result;
	}

}
