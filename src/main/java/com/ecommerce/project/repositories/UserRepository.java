package com.ecommerce.project.repositories;

import com.ecommerce.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long > {

}
