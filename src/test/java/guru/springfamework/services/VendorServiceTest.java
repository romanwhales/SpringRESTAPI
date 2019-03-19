package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;



import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class VendorServiceTest {
    public static final Long ID = 1L;
    public static final String NAME = "Bydata Nigeria";
    VendorService vendorService;

    @Mock
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(VendorMapper.INSTANCE,
                vendorRepository);
    }

    @Test
    public void getAllVendors() throws Exception{
        //given
        List<Vendor> vendors = Arrays.asList(new Vendor(),new Vendor(),
                new Vendor());
        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> vendorDTOS = vendorService.getAllVendors();

        //then
        assertEquals(3,vendorDTOS.size());
    }

    @Test
    public void createNewVendor() throws Exception{
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1l);

        //when
        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //then
        assertEquals(vendorDTO.getName(),savedVendor.getName());
    }

    @Test
    public void saveVendorByDTO() throws Exception{
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        Vendor savedVendor = new Vendor();
        savedVendor.setName(vendorDTO.getName());
        savedVendor.setId(1L);

        when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

        //when
        VendorDTO savedvendorDTO = vendorService.saveVendorByDTO(1L,vendorDTO);

        //then
        assertEquals(vendorDTO.getName(),savedvendorDTO.getName());

    }

    @Test
    public void getVendorById() throws Exception{
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(NAME);

        when(vendorRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID);

        //then
        assertEquals(ID,vendorDTO.getId());
        assertEquals(NAME,vendorDTO.getName());
    }

    @Test
    public void deleteVendorById() throws Exception{
        Long id = 1L;
        vendorRepository.deleteById(id);
        verify(vendorRepository,times(1)).deleteById(anyLong());
    }
}
