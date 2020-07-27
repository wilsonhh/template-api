package br.com.firstdatacorp.template.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import br.com.firstdatacorp.template.domain.EUser;
import br.com.firstdatacorp.template.dto.DGitResp;
import br.com.firstdatacorp.template.dto.DUserInsertReq;
import br.com.firstdatacorp.template.dto.DUserResp;
import br.com.firstdatacorp.template.dto.DUserUpdateReq;
import br.com.firstdatacorp.template.model.DPage;
import br.com.firstdatacorp.template.model.DResponse;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

	DUserResp userToUserDTO(EUser user);
	
	
	EUser dUserUpdateReqToEUser(DUserUpdateReq req);
	
	EUser dUserInsertReqToEUser(DUserInsertReq req);
	
    List<DUserResp> userListToUserDTOList(List<EUser> users);
    
    default DPage<DUserResp> pageUserToDPageUser(Page<EUser> users) {
        return PageMapper.toDPage(users.map(this::userToUserDTO));
    }
    
    default DResponse<List<String>> transactionToDResponseTransaction(List<String> merchants) {
        return DResponse.ok(merchants, "Sucesso.");
    }
    
    default DResponse<DUserResp> userToDResponse(DUserResp user) {
        return DResponse.ok(user, "Sucesso.");
    }

    default DResponse<DGitResp> gitToDResponse(DGitResp git) {
        return DResponse.ok(git, "Sucesso.");
    }
    
}
