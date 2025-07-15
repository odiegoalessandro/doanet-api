package com.doanet.api.service;

import com.doanet.api.entity.Request;
import com.doanet.api.enums.Status;
import com.doanet.api.repository.RequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DeleteRequestService {
  private static final Set<Status> NOT_ALLOWED_TO_DELETE = Set.of(
    Status.BLOCKED,
    Status.DELIVERED,
    Status.EXPIRED,
    Status.CANCELLED,
    Status.IN_TRANSIT
  );

  private RequestRepository requestRepository;

  public DeleteRequestService(RequestRepository requestRepository) {
    this.requestRepository = requestRepository;
  }

  public Request delete(Long requestId){
    var request = this.requestRepository.findById(requestId)
      .orElseThrow(() -> new EntityNotFoundException("Não foi possivel encontrar uma solicitação com este ID"));

    if(NOT_ALLOWED_TO_DELETE.contains(request.getStatus())){
      throw new IllegalStateException("Não é possivel cancelar uma solicitação que possui movimentações em aberto");
    }

    request.setStatus(Status.CANCELLED);

    return this.requestRepository.save(request);
  }
}
