package org.develop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.develop.dto.FunkoCreateDto;
import org.develop.dto.FunkoUpdateDto;
import org.develop.exceptions.FunkoNotFound;
import org.develop.models.Funko;
import org.develop.services.FunkoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ExtendWith(MockitoExtension.class)
class FunkoRestControllerTest {
    private final String myEndPoint = "/funkos";
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private FunkoService funkoService;
    @Autowired
    private JacksonTester<FunkoCreateDto> jsonFunkoCreateDto;
    @Autowired
    private JacksonTester<FunkoUpdateDto> jsonFunkoUpdateDto;
    private  final Funko funko1 = Funko.builder()
            .id(1L)
            .nombre("Test1")
            .precio(15.99)
            .cantidad(33)
            .imagen("test1")
            .categoria("ANIME")
            .fechaDeCreacion(LocalDate.now())
            .fechaDeActualizacion(LocalDateTime.now())
            .build();
    private  final Funko funko2 = Funko.builder()
            .id(2L)
            .nombre("Test2")
            .precio(49.99)
            .cantidad(22)
            .imagen("test2")
            .categoria("OTROS")
            .fechaDeCreacion(LocalDate.now())
            .fechaDeActualizacion(LocalDateTime.now())
            .build();

    @Autowired
    public  FunkoRestControllerTest(FunkoService funkoService){
        this.funkoService = funkoService;
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getAllFunkos() throws Exception {
        List<Funko> funkos = List.of(funko1, funko2);

        when(funkoService.findAll(null)).thenReturn(funkos);

        MockHttpServletResponse response = mockMvc.perform(
                get(myEndPoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        List<Funko> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, Funko.class));

        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertEquals(2, res.size()),
                () -> assertEquals(funko1, res.get(0)),
                () -> assertEquals(funko2, res.get(1))
        );

        verify(funkoService, times(1)).findAll(null);
    }

    @Test
    void getAllFunkosCategoria() throws Exception {
        List<Funko> funkos = List.of(funko1);
        String categoria = "ANIME";
        String myLocalEndPoint = myEndPoint + "?categoria=ANIME";

        when(funkoService.findAll(categoria)).thenReturn(funkos);

        MockHttpServletResponse response = mockMvc.perform(
             get(myLocalEndPoint)
                     .accept(MediaType.APPLICATION_JSON))
                     .andReturn().getResponse();

        List<Funko> res = mapper.readValue(response.getContentAsString(),
                mapper.getTypeFactory().constructCollectionType(List.class, Funko.class));

        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertEquals(1, res.size()),
                () -> assertEquals(funko1, res.get(0))
        );

        verify(funkoService, times(1)).findAll(categoria);
    }

    @Test
    void getFunkoById() throws Exception{
        String localEndPoint = myEndPoint + "/1";

        when(funkoService.findById(1L)).thenReturn(funko1);

        MockHttpServletResponse response = mockMvc.perform(
                get(localEndPoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Funko res = mapper.readValue(response.getContentAsString(), Funko.class);

        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () -> assertEquals(funko1, res)
        );
    }

    @Test
    void getFunkoByIdNotExist() throws Exception{
        String localEndPoint = myEndPoint + "/2";

        when(funkoService.findById(2L)).thenThrow(new FunkoNotFound(2L));

        MockHttpServletResponse response = mockMvc.perform(
                get(localEndPoint)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(404, response.getStatus());

        verify(funkoService, times(1)).findById(2L);
    }

    @Test
    void createFunko() throws Exception{
        FunkoCreateDto funkoCreDto = FunkoCreateDto.builder()
                .nombre("funko3")
                .precio(11.99)
                .cantidad(3)
                .imagen("holamundo")
                .categoria("MARVEL")
                .build();

        when(funkoService.save(funkoCreDto)).thenReturn(funko1);

        MockHttpServletResponse response = mockMvc.perform(
                post(myEndPoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonFunkoCreateDto.write(funkoCreDto).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Funko res = mapper.readValue(response.getContentAsString(), Funko.class);

        assertAll(
                () -> assertEquals(201, response.getStatus()),
                () -> assertEquals(funko1, res)
        );
        verify(funkoService, times(1)).save(funkoCreDto);
    }

    @Test
    void createFunkoBadNombre() throws Exception{
        FunkoCreateDto funkoCreDto = FunkoCreateDto.builder()
                .nombre("f")
                .precio(11.99)
                .cantidad(3)
                .imagen("holamundo")
                .categoria("MARVEL")
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndPoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFunkoCreateDto.write(funkoCreDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(400, response.getStatus()),
                () -> assertTrue(response.getContentAsString().contains("El nombre debe tener al menos 3 caracteres"))
        );
    }

    @Test
    void createFunkBadPrecio() throws Exception{
        FunkoCreateDto funkoCreDto = FunkoCreateDto.builder()
                .nombre("funko3")
                .precio(-1)
                .cantidad(3)
                .imagen("holamundo")
                .categoria("MARVEL")
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndPoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFunkoCreateDto.write(funkoCreDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(400, response.getStatus()),
                () -> assertTrue(response.getContentAsString().contains("El precio no puede ser negativo"))
        );
    }

    @Test
    void createFunkoBadCantidad() throws Exception{
        FunkoCreateDto funkoCreDto = FunkoCreateDto.builder()
                .nombre("funko3")
                .precio(11.99)
                .cantidad(-1)
                .imagen("holamundo")
                .categoria("MARVEL")
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndPoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFunkoCreateDto.write(funkoCreDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(400, response.getStatus()),
                () -> assertTrue(response.getContentAsString().contains("La cantidad no puede ser menor a 0"))
        );
    }

    @Test
    void createFunkoBadCategoria() throws Exception{
        FunkoCreateDto funkoCreDto = FunkoCreateDto.builder()
                .nombre("funko3")
                .precio(11.99)
                .cantidad(1)
                .imagen("holamundo")
                .categoria("")
                .build();

        MockHttpServletResponse response = mockMvc.perform(
                        post(myEndPoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFunkoCreateDto.write(funkoCreDto).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertAll(
                () -> assertEquals(400, response.getStatus()),
                () -> assertTrue(response.getContentAsString().contains("La categoria no puede estar vacia"))
        );
    }

    @Test
    void updateFunko() throws Exception{
        String myLocalEndPoint = myEndPoint + "/1";
        FunkoUpdateDto funkoUpdate = FunkoUpdateDto.builder()
                .nombre("funko3")
                .precio(11.99)
                .cantidad(1)
                .imagen("holamundo")
                .categoria("MARVEL")
                .build();
        when(funkoService.update(anyLong(),any(FunkoUpdateDto.class))).thenReturn(funko1);

        MockHttpServletResponse response = mockMvc.perform(
                        put(myLocalEndPoint)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonFunkoUpdateDto.write(funkoUpdate).getJson())
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        Funko res = mapper.readValue(response.getContentAsString(), Funko.class);

        assertAll(
                () -> assertEquals(200, response.getStatus()),
                () ->assertEquals(funko1, res)
        );

        verify(funkoService, times(1)).update(anyLong(), any(FunkoUpdateDto.class));

    }

}