package br.com.firstdatacorp.template.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.firstdatacorp.template.domain.EUser;

@Repository
public interface TemplateRepository extends JpaRepository<EUser, Integer> {

	@Query(value = " SELECT  u from User u WHERE u.nome = :nome and u.status = 1")
	@Transactional(readOnly = true)
	public Page<EUser> findByName(@Param("nome") String nome, Pageable pageable);

	@Query(value = " SELECT  u from User u WHERE u.email = :email and u.status = 1")
	@Transactional(readOnly = true)
	public EUser findByEmail(@Param("email") String email);

	@Query(value = "select u from User u where u.status = 1", countQuery = "select count(1) from User u where u.status = 1")
	Page<EUser> findAllActive(Pageable pageable);

}
