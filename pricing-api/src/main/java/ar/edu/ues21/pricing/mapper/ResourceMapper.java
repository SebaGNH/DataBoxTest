package ar.edu.ues21.pricing.mapper;

import java.util.List;

import ar.edu.ues21.pricing.dto.ModalidadResourceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ar.edu.ues21.pricing.dto.Resource;
import ar.edu.ues21.pricing.model.ArancelesResource;
import ar.edu.ues21.pricing.model.CarrerasResource;
import ar.edu.ues21.pricing.model.CauResource;
import ar.edu.ues21.pricing.model.CgRefCodes;
import ar.edu.ues21.pricing.model.ModalidadesResource;
import ar.edu.ues21.pricing.model.PeriodosResource;
import ar.edu.ues21.pricing.model.Status;

@Mapper
public interface ResourceMapper {

   ResourceMapper RESOURCE_MAPPER = Mappers.getMapper(ResourceMapper.class);

   List<Resource> mapCodesResource(List<CgRefCodes> entity);

   List<Resource> mapCarrerasResource(List<CarrerasResource> entity);

   List<Resource> mapCauResource(List<CauResource> entity);

   List<ModalidadResourceDto> mapModalidadesResource(List<ModalidadesResource> entity);

   List<Resource> mapPeriodosResource(List<PeriodosResource> periodos);

   List<Resource> mapAranacelesResource(List<ArancelesResource> all);

   List<Resource> mapStatusResource(List<Status> statuses);

   @Mappings({ @Mapping(target = "id", source = "idArancel") })
   Resource mapArancel(ArancelesResource entity);
}
