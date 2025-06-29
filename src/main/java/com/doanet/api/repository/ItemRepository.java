package com.doanet.api.repository;

import com.doanet.api.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
  Page<Item> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
