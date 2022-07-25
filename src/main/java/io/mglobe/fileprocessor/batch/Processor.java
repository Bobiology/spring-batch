package io.mglobe.fileprocessor.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import io.mglobe.fileprocessor.model.Users;

@Component
public class Processor implements ItemProcessor<Users, Users> {
	private static final Map<String, String> DEPT_NAMES = new HashMap<>();

	public Processor() {
		DEPT_NAMES.put("001", "Technology");
		DEPT_NAMES.put("002", "Operations");
		DEPT_NAMES.put("003", "Accounts");
	}

	@Override
	public Users process(Users users) throws Exception {
		String deptCode = users.getDept();
		String dept = DEPT_NAMES.get(deptCode);
		users.setDept(dept);
		users.setTime(new Date());
		System.out.println(String.format("Converted from [%s] to [%s]", deptCode, dept));
		return users;
	}

}
