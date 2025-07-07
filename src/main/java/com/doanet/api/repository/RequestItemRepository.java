package com.doanet.api.repository;

import com.doanet.api.entity.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestItemRepository extends JpaRepository<RequestItem, Long> {
}
