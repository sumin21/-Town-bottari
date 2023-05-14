package com.backend.townbottari.repository.form;

import com.backend.townbottari.domain.form.Form;
import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.user.Role;
import com.backend.townbottari.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Long> {
    Page<Form> findByUserId(@Param("userId") Long userId, Pageable pageable);

}
