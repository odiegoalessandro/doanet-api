package com.doanet.api.application.dto;

import com.doanet.api.domain.entities.user.Item;

import java.util.List;


public record PageResponse<T> (List<Item> content, long totalElements, int totalPages, int currentPage) {
}
