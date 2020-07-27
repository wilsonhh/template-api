package br.com.firstdatacorp.template.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.firstdatacorp.template.domain.EUser;
import br.com.firstdatacorp.template.exception.DuplicatedException;
import br.com.firstdatacorp.template.exception.ItemNotFoundException;
import br.com.firstdatacorp.template.repository.TemplateRepository;
import br.com.firstdatacorp.template.util.AuditUtil;

@Service
public class TemplateService {

	@Autowired
	TemplateRepository repository;
	
	AuditUtil<EUser> audit;

	public TemplateService() {
		audit = new AuditUtil<EUser>();
	}
	

	public Page<EUser> findByName(final String name, Pageable pageable) {
		Page<EUser> findByName = null;
		findByName = repository.findByName(name, pageable);
		if (findByName.getTotalElements() == 0) {
			throw new ItemNotFoundException();
		}

		return findByName;

	}

	public Page<EUser> findAll(Pageable pageable) {
		Page<EUser> findByName = repository.findAllActive(pageable);
		if (findByName.getTotalElements() == 0) {
			throw new ItemNotFoundException();
		}

		return findByName;
	}

	public EUser findByEmail(final String email) {
		EUser findByEmail = null;
		findByEmail = repository.findByEmail(email);
		if (findByEmail == null) {
			
			throw new ItemNotFoundException();
		}

		return findByEmail;
	}

	@Transactional
	public void update(Integer id, String name) {
		Optional<EUser> user = repository.findById(id);
		if (user.isPresent())
			throw new ItemNotFoundException();

		user.get().setNome(name);
	}

	@Transactional
	public void save(EUser user) {
		EUser userByEmail = repository.findByEmail(user.getEmail());
		if (userByEmail != null)
			throw new DuplicatedException();
		audit.setFields(user, AuditUtil.INSERT.toString());
		
		repository.save(user);

	}
	
	@Transactional
	public void remove(Integer id) {
		Optional<EUser> user = repository.findById(id);
		if (user.isPresent())
			throw new ItemNotFoundException();

		 audit.setFields(user.get(), AuditUtil.DELETE.toString());
	}


}
