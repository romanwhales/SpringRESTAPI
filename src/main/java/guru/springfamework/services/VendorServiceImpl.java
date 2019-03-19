package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.VendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class VendorServiceImpl implements VendorService {
    private final VendorMapper vendorMapper;
    private final VendorRepository vendorRepository;

    public VendorServiceImpl(VendorMapper vendorMapper,
                             VendorRepository vendorRepository){
        this.vendorMapper = vendorMapper;
        this.vendorRepository = vendorRepository;
    }
    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::vendorToVendorDTO)
                .collect(Collectors.toList());
    }


    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {
        Vendor mappedVendor = vendorMapper.vendorDtoToVendor(vendorDTO);
        return saveAndReturnDto(mappedVendor);

    }

    @Override
    public VendorDTO saveVendorByDTO(Long id,VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);

        vendor.setId(id);
        return saveAndReturnDto(vendor);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id).map(vendor -> {
            if(vendorDTO.getName() != null){
                vendor.setName(vendorDTO.getName());
            }
            return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        vendorRepository.deleteById(id);
    }

    private VendorDTO saveAndReturnDto(Vendor vendor){
        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.vendorToVendorDTO(savedVendor);
    }
}
