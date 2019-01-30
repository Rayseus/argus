package mtl.argus.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mtl.argus.pojo.Uml;

public interface UmlDao extends JpaRepository<Uml, Long>{

	@Query(value = "select * from uml where saml like %?1%", nativeQuery = true)
	List<Uml> findBySaml(String code);
	@Query(value="select count(project) from uml",nativeQuery=true)
	Long  countByProject();
	@Query(value="select count(distinct team) from uml",nativeQuery=true)
	Long  countByteam();
	
	@Query(value="select saml from uml where id=?1",nativeQuery=true)
	String findSamlByid(Long id);
	
	Uml  findByTeamAndProject(String team,String project);
	
}
