package com.doanet.api.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = """
   - CREATED: Solicitação ou doação registrada no sistema, aguardando ação.
   - ACCEPTED: Solicitação ou doação foi aceita por outra parte.
   - REJECTED: Solicitação ou doação foi recusada.
   - IN_TRANSIT: Item está em transporte para o destino.
   - DELIVERED: Item foi entregue com sucesso.
   - CANCELLED: Ação foi cancelada por quem criou ou aceitou.
   - EXPIRED: Tempo limite da solicitação/doação foi atingido sem conclusão.
   - FAILED: Tentativa de entrega ou coleta falhou.
   - RETURNED: Item foi devolvido ao ponto de origem.
   - BLOCKED: Solicitação ou doação foi marcada como irregular ou indevida.
  """)
public enum Status {
  CREATED,
  ACCEPTED,
  REJECTED,
  IN_TRANSIT,
  DELIVERED,
  CANCELLED,
  EXPIRED,
  FAILED,
  RETURNED,
  BLOCKED
}

