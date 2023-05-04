package ar.edu.ues21.pricing.services.impl;

import static ar.edu.ues21.pricing.mapper.ResourceMapper.RESOURCE_MAPPER;

import java.util.List;
import java.util.stream.Collectors;

import ar.edu.ues21.pricing.dto.ModalidadResourceDto;
import ar.edu.ues21.pricing.repository.*;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ar.edu.ues21.pricing.dto.Resource;
import ar.edu.ues21.pricing.services.ResouceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResourceServiceImp implements ResouceService {

   private final CgRefCodesRepository cgRefCodesRepository;

   private final CauRepository cauRepository;

   private final CarreraRepository carreraRepository;

   private final ModalidadesRepository modalidadesRepository;

   private final PeriodosRepository periodosRepository;

   private final ArancelesRepository arancelesRepository;

   private final StatusRepository statusRepository;

   @Override
   @Cacheable("resource/carreras")
   public List<Resource> listarCarreras() {
      return RESOURCE_MAPPER.mapCarrerasResource(carreraRepository.findDistinctTopByDescripcion());
   }

   @Override
   @Cacheable("resource/modalidades")
   public List<ModalidadResourceDto> listarModalidades() {
      return RESOURCE_MAPPER.mapModalidadesResource(modalidadesRepository.findDistinctTopByDescripcion());
   }

   @Override
   @Cacheable("resource/cau")
   public List<Resource> listarCau() {
      return RESOURCE_MAPPER.mapCauResource(cauRepository.findDistinctTopByDescripcion());
   }

   @Override
   @Cacheable("resource/ticket")
   public List<Resource> listarTicket() {
      return RESOURCE_MAPPER.mapCodesResource(cgRefCodesRepository.findDistinctTicket());
   }

   @Override
   @Cacheable("resource/turno")
   public List<Resource> listarTurnosCursados() {
      return RESOURCE_MAPPER.mapCodesResource(cgRefCodesRepository.findDistinctTurnosCursados());
   }

   @Override
   @Cacheable("resource/rubros")
   public List<Resource> listarRubros() {
      return RESOURCE_MAPPER.mapCodesResource(cgRefCodesRepository.findDistinctRubros());
   }

   @Override
   @Cacheable("resource/aranceles")
   public List<Resource> listarAranceles() {
      return RESOURCE_MAPPER.mapAranacelesResource(arancelesRepository.findAll());
   }

   @Override
   @Cacheable("resource/periodos")
   public List<Resource> listarPeriodos() {
      return RESOURCE_MAPPER.mapPeriodosResource(periodosRepository.findPeriodos());
   }

   @Override
   @Cacheable("resource/ticket")
   public Resource getTicketByDesc(String desc) {
      return listarTicket().stream().filter(a -> a.getDescripcion().equals(desc)).collect(Collectors.toList()).stream().findFirst().get();
   }

   @Override
   @Cacheable("resource/cau")
   public Resource getCauByDesc(String desc) {
      return listarCau().stream().filter(a -> a.getDescripcion().equals(desc)).collect(Collectors.toList()).stream().findFirst().get();
   }

   @Override
   public List<Resource> findAllStatus() {
      return RESOURCE_MAPPER.mapStatusResource(this.statusRepository.findAll());
   }

   @Cacheable("resource/aranceles")
   public Resource getArancelById(String id) {
      return listarAranceles().stream().filter(a -> a.getId().equals(id)).collect(Collectors.toList()).stream().findFirst().get();
   }

   @Cacheable("resource/carreras")
   public Resource getProgramById(Long id) {
      return listarCarreras().stream().filter(a -> a.getId().equals(id.toString())).collect(Collectors.toList()).stream().findFirst().get();
   }

   @Cacheable("resource/modalidades")
   public ModalidadResourceDto getModalityById(Long id) {
      return listarModalidades().stream().filter(a -> a.getId().equals(id.toString())).collect(Collectors.toList()).stream().findFirst().get();
   }
}
