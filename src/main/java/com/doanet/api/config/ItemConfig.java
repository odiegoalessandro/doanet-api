package com.doanet.api.config;


import com.doanet.api.application.gateways.ItemRepository;
import com.doanet.api.application.usecases.item.CreateItemUseCase;
import com.doanet.api.application.usecases.item.DeleteItemByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByIdUseCase;
import com.doanet.api.application.usecases.item.FindItemByNameUseCase;
import com.doanet.api.infra.gateways.ItemEntityMapper;
import com.doanet.api.infra.gateways.ItemRepositoryImpl;
import com.doanet.api.infra.persistence.JpaItemRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemConfig {
  @Bean
  public CreateItemUseCase createItemUseCase(ItemRepository itemRepository){
    return new CreateItemUseCase(itemRepository);
  }

  @Bean
  public FindItemByIdUseCase findItemByIdUseCase(ItemRepository itemRepository) {
    return new FindItemByIdUseCase(itemRepository);
  }

  @Bean
  public FindItemByNameUseCase findItemByNameUseCase(ItemRepository itemRepository){
    return new FindItemByNameUseCase(itemRepository);
  }

  @Bean
  public DeleteItemByIdUseCase deleteItemByIdUseCase(ItemRepository itemRepository) {
    return new DeleteItemByIdUseCase(itemRepository);
  }

  @Bean
  public ItemRepository itemRepository(JpaItemRepository jpaItemRepository, ItemEntityMapper itemEntityMapper) {
    return new ItemRepositoryImpl(jpaItemRepository, itemEntityMapper);
  }
}
