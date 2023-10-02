package com.Yushan.votesystem.repositories;

import com.Yushan.votesystem.models.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {
}

