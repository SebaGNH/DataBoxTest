package ar.edu.ues21.pricing.services;

import ar.edu.ues21.pricing.dto.ModalidadResourceDto;
import ar.edu.ues21.pricing.dto.Resource;
import ar.edu.ues21.pricing.model.Status;

import java.util.List;

public interface ResouceService {

   List<Resource> listarCarreras();

   List<ModalidadResourceDto> listarModalidades();

   List<Resource> listarCau();

   List<Resource> listarTicket();

   List<Resource> listarTurnosCursados();

   List<Resource> listarRubros();

   List<Resource> listarPeriodos();

   List<Resource> listarAranceles();

   Resource getTicketByDesc(String desc);

   Resource getCauByDesc(String desc);

   List<Resource> findAllStatus();
}
