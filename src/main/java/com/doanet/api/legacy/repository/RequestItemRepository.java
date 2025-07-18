package com.doanet.api.legacy.repository;

import com.doanet.api.legacy.entity.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestItemRepository extends JpaRepository<RequestItem, Long> {
}
