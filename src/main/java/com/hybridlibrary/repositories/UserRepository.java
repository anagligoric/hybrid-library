package com.hybridlibrary.repositories;


import com.hybridlibrary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    Collection<User> findByUsernameContainingIgnoreCase(String username);

    @Transactional
    @Modifying
    @Query(value = "update user set password = ?1 where id = ?2", nativeQuery = true)
    void executeUpdate(String password, Long id);
}
