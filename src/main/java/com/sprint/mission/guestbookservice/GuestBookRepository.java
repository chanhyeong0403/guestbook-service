package com.sprint.mission.guestbookservice;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestBookRepository extends JpaRepository<GuestBook, Long> {
}
