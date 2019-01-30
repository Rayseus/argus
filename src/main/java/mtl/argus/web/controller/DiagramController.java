package mtl.argus.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import mtl.argus.common.Constants;
import mtl.argus.dao.UmlDao;
import mtl.argus.pojo.Uml;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;
import net.sourceforge.plantuml.code.Base64Coder;
import net.sourceforge.plantuml.core.DiagramDescription;
/**
 * 
 * @author Guo
 *
 */
@Controller
@RequestMapping("draw")
@Slf4j
@CrossOrigin
public class DiagramController {

	@Autowired
	UmlDao umlDao;

	/**
	 * only when user not get image we call this method, most time no need
	 * 
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("png/{id}")
	public void getPng(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		StringBuilder sBuilder = new StringBuilder();
		boolean needHeader = false;
		String script = umlDao.findSamlByid(Long.valueOf(id));
		if (script == null) {
			script = "[Error] --> Not_Find_id\\r\\n";
		}
		if (!script.startsWith(Constants.START_UML)) {
			needHeader = true;
			sBuilder.append(Constants.START_UML).append(Constants.END);
		}
		log.info("Generate UML for: " + script);
		sBuilder.append(script).append(Constants.END);
		if (needHeader) {
			sBuilder.append(Constants.END_UML);
		}
		log.info("now draw [{}]", sBuilder);
		drawImage(sBuilder, response);
	}

	/**
	 * write Base64 image to the client
	 * 
	 * @param sBuilder
	 * @param response
	 * @throws IOException
	 */
	public String drawImage(StringBuilder sBuilder, HttpServletResponse response) throws IOException {
		SourceStringReader reader = new SourceStringReader(sBuilder.toString());
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final DiagramDescription result = reader.outputImage(baos, 0, new FileFormatOption(FileFormat.PNG));
		baos.close();
		final String encodedBytes = "data:image/png;base64,"
				+ Base64Coder.encodeLines(baos.toByteArray()).replaceAll("\\s", "");

		response.getOutputStream().write(encodedBytes.getBytes());
		log.info("---------write end--------[{}]",result.getDescription());
		return encodedBytes;
	}

	@PostMapping("png")
	public void create(@RequestBody Uml uml, HttpServletResponse response) throws IOException {
		StringBuilder sBuilder = new StringBuilder();
		boolean needHeader = false;
		String script = StringUtils.trimToNull(uml.getSaml());
		if (script == null) {
			script = "[Error] --> CondNotInput\r\n";
		}
		if (!script.startsWith(Constants.START_UML)) {
			needHeader = true;
			sBuilder.append(Constants.START_UML).append(Constants.END);
		}
		log.info("Generate UML for: " + script);
		sBuilder.append(script).append(Constants.END);
		if (needHeader) {
			sBuilder.append(Constants.END_UML);
		}
		log.info("now draw [{}]", sBuilder);
		String result = drawImage(sBuilder, response);
		uml.setPath(result);
		Uml db = umlDao.findByTeamAndProject(uml.getTeam(), uml.getProject());
		if (db == null) {
			// means it not exists
			db = new Uml();
			db.setProject(uml.getProject());
			db.setTeam(uml.getTeam());
		}
		db.setPath(result);
		db.setSaml(uml.getSaml());
		umlDao.save(db);
		log.info("-------------end");
	}

}
