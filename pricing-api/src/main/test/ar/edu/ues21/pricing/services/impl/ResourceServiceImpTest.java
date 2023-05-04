package ar.edu.ues21.pricing.services.impl;

import ar.edu.ues21.pricing.dto.Resource;
import ar.edu.ues21.pricing.mapper.ResourceMapper;
import ar.edu.ues21.pricing.model.*;
import ar.edu.ues21.pricing.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ResourceServiceImpTest {

    @InjectMocks
    ResourceServiceImp resourceServiceImp;

    @Mock
    ResourceMapper resourceMapper;

    @Mock
    CarreraRepository carreraRepository;

    @Mock
    CgRefCodesRepository cgRefCodesRepository;

    @Mock
    CauRepository cauRepository;

    @Mock
    ModalidadesRepository modalidadesRepository;

    @Mock
    PeriodosRepository periodosRepository;

    @Mock
    ArancelesRepository arancelesRepository;


    private CarrerasResource carrerasResource;
    private CarrerasResource carrerasResource1;
    private Resource resource;
    private Resource resource1;
    private ModalidadesResource modalidadesResource;
    private ModalidadesResource modalidadesResource1;
    private CauResource cauResource;
    private CauResource cauResource1;
    private CgRefCodes cgRefCodes;
    private CgRefCodes cgRefCodes1;
    private PeriodosResource periodosResource;
    private PeriodosResource periodosResource1;
    private ArancelesResource arancelesResource;
    private ArancelesResource arancelesResource1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        carrerasResource = CarrerasResource.builder().id(1L).build();
        carrerasResource1 = CarrerasResource.builder().id(2L).build();
        resource = Resource.builder().id("1").build();
        resource1 = Resource.builder().id("2").build();
        modalidadesResource = ModalidadesResource.builder().id(1L).build();
        modalidadesResource1 = ModalidadesResource.builder().id(2L).build();
        cauResource = CauResource.builder().id(2L).build();
        cauResource = CauResource.builder().id(1L).descripcion("description cau 1").build();
        cauResource1 = CauResource.builder().id(2L).descripcion("description cau 2").build();
        cgRefCodes = CgRefCodes.builder().id("1").descripcion("description1").build();
        cgRefCodes1 = CgRefCodes.builder().id("2").descripcion("description2").build();
        periodosResource = PeriodosResource.builder().id(1L).build();
        periodosResource1 = PeriodosResource.builder().id(2L).build();
        arancelesResource = ArancelesResource.builder().id(1L).idArancel("1").build();
        arancelesResource1 = ArancelesResource.builder().id(2L).idArancel("2").build();
    }

    @Test
    void listarCarreras() {
        when(carreraRepository.findDistinctTopByDescripcion()).thenReturn(List.of(carrerasResource, carrerasResource1));
        when(resourceMapper.mapCarrerasResource(List.of(carrerasResource, carrerasResource1))).thenReturn(List.of(resource, resource1));

        List<Resource> list = resourceServiceImp.listarCarreras();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void listarModalidades() {
        when(modalidadesRepository.findDistinctTopByDescripcion()).thenReturn(List.of(modalidadesResource, modalidadesResource1));
        when(resourceMapper.mapModalidadesResource(List.of(modalidadesResource, modalidadesResource1))).thenReturn(List.of(resource, resource1));

        List<Resource> list = resourceServiceImp.listarModalidades();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void listarCau() {
        when(cauRepository.findDistinctTopByDescripcion()).thenReturn(List.of(cauResource, cauResource1));
        when(resourceMapper.mapCauResource(List.of(cauResource, cauResource1))).thenReturn(List.of(resource, resource1));

        List<Resource> list = resourceServiceImp.listarCau();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void listarTicket() {
        when(cgRefCodesRepository.findDistinctTicket()).thenReturn(List.of(cgRefCodes, cgRefCodes1));
        when(resourceMapper.mapCodesResource(List.of(cgRefCodes, cgRefCodes1))).thenReturn(List.of(resource, resource1));

        List<Resource> list = resourceServiceImp.listarTicket();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void listarTurnosCursados() {
        when(cgRefCodesRepository.findDistinctTurnosCursados()).thenReturn(List.of(cgRefCodes, cgRefCodes1));
        when(resourceMapper.mapCodesResource(List.of(cgRefCodes, cgRefCodes1))).thenReturn(List.of(resource, resource1));

        List<Resource> list = resourceServiceImp.listarTurnosCursados();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void listarRubros() {
        when(cgRefCodesRepository.findDistinctRubros()).thenReturn(List.of(cgRefCodes, cgRefCodes1));
        when(resourceMapper.mapCodesResource(List.of(cgRefCodes, cgRefCodes1))).thenReturn(List.of(resource, resource1));

        List<Resource> list = resourceServiceImp.listarRubros();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void listarPeriodos() {
        when(periodosRepository.findPeriodos()).thenReturn(List.of(periodosResource, periodosResource1));
        when(resourceMapper.mapPeriodosResource(List.of(periodosResource, periodosResource1))).thenReturn(List.of(resource, resource1));

        List<Resource> list = resourceServiceImp.listarPeriodos();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void listarAranceles() {
        when(arancelesRepository.findAll()).thenReturn(List.of(arancelesResource, arancelesResource1));
        when(resourceMapper.mapAranacelesResource(List.of(arancelesResource, arancelesResource1))).thenReturn(List.of(resource, resource1));

        List<Resource> list = resourceServiceImp.listarAranceles();
        assertNotNull(list);
        assertEquals(2, list.size());
    }

    @Test
    void getTicketByDesc() {
        when(cgRefCodesRepository.findDistinctTicket()).thenReturn(List.of(cgRefCodes, cgRefCodes1));
        when(resourceMapper.mapCodesResource(List.of(cgRefCodes, cgRefCodes1))).thenReturn(List.of(resource, resource1));
        List<Resource> list = resourceServiceImp.listarTicket();
        Resource resource1 = resourceServiceImp.getTicketByDesc("description1");
        Resource resource2 = resourceServiceImp.getTicketByDesc("description2");
        assertEquals(resource1,list.get(0));
        assertEquals(resource2,list.get(1));
    }

    @Test
    void getCauByDesc() {
        when(cauRepository.findDistinctTopByDescripcion()).thenReturn(List.of(cauResource, cauResource1));
        when(resourceMapper.mapCauResource(List.of(cauResource, cauResource1))).thenReturn(List.of(resource, resource1));
        List<Resource> list = resourceServiceImp.listarCau();

        Resource resource1 = resourceServiceImp.getCauByDesc("description cau 1");
        Resource resource2 = resourceServiceImp.getCauByDesc("description cau 2");
        assertEquals(resource1,list.get(0));
        assertEquals(resource2,list.get(1));
    }

    @Test
    void getArancelById() {
        when(arancelesRepository.findAll()).thenReturn(List.of(arancelesResource, arancelesResource1));
        List<Resource> list = resourceServiceImp.listarAranceles();

        Resource resource1 = resourceServiceImp.getArancelById(String.valueOf(1));
        Resource resource2 = resourceServiceImp.getArancelById(String.valueOf(2));
        assertEquals(resource1,list.get(0));
        assertEquals(resource2,list.get(1));
    }

    @Test
    void getProgramById() {
        when(carreraRepository.findDistinctTopByDescripcion()).thenReturn(List.of(carrerasResource, carrerasResource1));
        when(resourceMapper.mapCarrerasResource(List.of(carrerasResource, carrerasResource1))).thenReturn(List.of(resource, resource1));
        List<Resource> list = resourceServiceImp.listarCarreras();

        Resource resource1 = resourceServiceImp.getProgramById(1L);
        Resource resource2 = resourceServiceImp.getProgramById(2L);
        assertEquals(resource1,list.get(0));
        assertEquals(resource2,list.get(1));
    }

    @Test
    void getModalityById() {
        when(modalidadesRepository.findDistinctTopByDescripcion()).thenReturn(List.of(modalidadesResource, modalidadesResource1));
        when(resourceMapper.mapModalidadesResource(List.of(modalidadesResource, modalidadesResource1))).thenReturn(List.of(resource, resource1));
        List<Resource> list = resourceServiceImp.listarModalidades();

        Resource resource1 = resourceServiceImp.getModalityById(1L);
        Resource resource2 = resourceServiceImp.getModalityById(2L);
        assertEquals(resource1,list.get(0));
        assertEquals(resource2,list.get(1));
    }
}