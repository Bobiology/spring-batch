package io.mglobe.fileprocessor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.mglobe.fileprocessor.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>{

}
