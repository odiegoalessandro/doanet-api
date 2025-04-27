package com.doanet.api.controller;

import com.doanet.api.dto.CreateOngDto;
import com.doanet.api.entity.Ong;
import com.doanet.api.service.CreateOngService;
import com.doanet.api.service.FindOngService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ong")
public class OngController {
  private final CreateOngService createOngService;
  private final FindOngService findOngService;

  public OngController(CreateOngService createOngService, FindOngService findOngService){
    this.createOngService = createOngService;
    this.findOngService = findOngService;
  }

  @Transactional
  @PostMapping
  public Ong create(@RequestBody @Valid CreateOngDto ong){
    return this.createOngService.create(ong.user(), ong.cnpj());
  }

  @GetMapping("/{id}")
  public Ong findById(@PathVariable("id") Long id){
    return this.findOngService.findById(id);
  }

  @GetMapping("/cnpj/{cnpj}")
  public Ong findByCnpj(@PathVariable("cnpj") String cnpj){
    return this.findOngService.findByCnpj(cnpj);
  }

  @GetMapping
  public Page<Ong> findAll(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
    return this.findOngService.findAll(pageNumber, pageSize);
  }
}
