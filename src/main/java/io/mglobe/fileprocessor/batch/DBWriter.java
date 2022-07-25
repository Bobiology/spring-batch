package io.mglobe.fileprocessor.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.mglobe.fileprocessor.model.Users;
import io.mglobe.fileprocessor.repo.UserRepo;

@Component
public class DBWriter implements ItemWriter<Users> {

	private UserRepo userRepo;

	@Autowired
	public DBWriter(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public void write(List<? extends Users> users) throws Exception {
		System.out.println("Data Saved for Users: " + users);
		userRepo.saveAll(users);

	}

}
