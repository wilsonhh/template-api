package br.com.firstdatacorp.template.util;

import org.springframework.util.StringUtils;

import br.com.firstdatacorp.template.domain.EUser;
import br.com.firstdatacorp.template.exception.MandatoryFieldException;

public class UserUtil {
	
	public static void validateMandatoryFields(EUser user) {
		StringBuilder mandatoryFields = new StringBuilder();
		if (StringUtils.isEmpty(user.getEmail())) {
			mandatoryFields.append("|email|");
		}
		if (StringUtils.isEmpty(user.getNome())) {
			mandatoryFields.append("|name|");
		}
		if (StringUtils.isEmpty(user.getSenha())) {
			mandatoryFields.append("|password|");
		}
		if (StringUtils.isEmpty(user.getGroupId())) {
			mandatoryFields.append("|groupId|");
		}
		if (mandatoryFields.length() > 0) {
			throw new MandatoryFieldException(mandatoryFields.toString());
		}

	}

}
