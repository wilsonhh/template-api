package br.com.firstdatacorp.template.util;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.firstdatacorp.template.enums.ApplicationEnum;

public class AuditUtil<T> {

	private static final Logger LOGGER =  LoggerFactory.getLogger(AuditUtil.class);

	public static final Object UPDATE = "U";
	public static final Object INSERT = "I";
	public static final Object DELETE = "D";

	public static final Integer ACTIVE = 1;
	public static final Integer INACTIVE = 0;

	public void setFields(T t, final String type) {
		@SuppressWarnings("serial")
		final Map<String, Object[]> defaultValues = new HashMap<String, Object[]>() {
			{
				if (type.equals(INSERT)) {
					put("CreationDate", new Object[] { Calendar.class, Calendar.getInstance() });
				}
				put("UpdateDate", new Object[] { Calendar.class, Calendar.getInstance() });
				if (type.equals(INSERT)) {
					put("Status", new Object[] { Integer.class, ACTIVE });
				} else if (type.equals(DELETE)) {
					put("Status", new Object[] { Integer.class, INACTIVE });
				}
			}
		};

		defaultValues.forEach((k, v) -> {

			try {

				Method method = t.getClass().getMethod("set" + k, new Class[] { (Class<?>) v[0] });
				method.invoke(t, new Object[] { v[1] });

			} catch (final Exception e) {
				LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA,  "Set audit filed failed."));
			}

		});


	}

}
