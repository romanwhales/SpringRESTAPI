package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.controllers.RestResponseEntityExceptionHandler;
import guru.springfamework.services.ResourceNotFoundException;
import guru.springfamework.services.VendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VendorControllerTest {
    @Mock
    VendorService vendorService;

    @InjectMocks
    VendorController vendorController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        mockMvc =
                MockMvcBuilders.standaloneSetup(vendorController).setControllerAdvice(new RestResponseEntityExceptionHandler()).build();
    }

    @Test
    public void createNewVendor() throws Exception{
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Olawale");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());


        when(vendorService.createNewVendor(vendor)).thenReturn(returnDTO);
        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            .content(asJsonString(vendor)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name",equalTo("Olawale")));

    }

    @Test
    public void testUpdateVendor() throws Exception{
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Olawale");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());

        when(vendorService.saveVendorByDTO(anyLong(),any(VendorDTO.class))).thenReturn(returnDTO);

        //when/then
        mockMvc.perform(put(VendorController.BASE_URL+"1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Olawale")));
    }

    @Test
    public void testPatchVendor() throws Exception{
        //given
        VendorDTO vendor = new VendorDTO();
        vendor.setName("Olawale");

        VendorDTO returnDTO = new VendorDTO();
        returnDTO.setName(vendor.getName());

        when(vendorService.patchVendor(anyLong(),any(VendorDTO.class))).thenReturn(returnDTO);
        mockMvc.perform(patch(VendorController.BASE_URL+"1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("Olawale")));
    }

    @Test
    public void testDeleteVendor() throws Exception{
        mockMvc.perform(delete(VendorController.BASE_URL+"1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(vendorService).deleteVendorById(anyLong());
    }

    @Test
    public void testNotFoundException() throws Exception{
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(VendorController.BASE_URL+"222").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
