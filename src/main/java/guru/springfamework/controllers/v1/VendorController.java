package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.Vendor;
import guru.springfamework.services.VendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(description = "This is my Vendor Controller")
@RequestMapping(VendorController.BASE_URL)
@RestController
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors/";
    private final VendorService vendorService;

    public VendorController(VendorService vendorService){
        this.vendorService = vendorService;
    }

    @ApiOperation(value="This will fetch the list of all vendors", notes=
            "These are some notes about fetching the vendor api")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors(){
        return new VendorListDTO(vendorService.getAllVendors());
    }
    @ApiOperation(value="This will fetch an individual vendor",notes="This will fetch an individual vendor")
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id){
        return vendorService.getVendorById(id);
    }
    @ApiOperation(value="This will create a new vendor",notes="This will " +
            "create a new vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDto){
        return vendorService.createNewVendor(vendorDto);
    }
    @ApiOperation(value="This will delete a vendor based on the passed id",
            notes="This will " +
            "delete a vendor based on the id passed")
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id){
        vendorService.deleteVendorById(id);
    }
    @ApiOperation(value="This will update a vendor with the put method " +
            "passing the id and the payload of an object ",notes = "This will update a vendor with the put method \" +\n" +
            "            \"passing the id and the payload of an object")
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id,
                                  @RequestBody VendorDTO vendorDTO){
        return vendorService.saveVendorByDTO(id,vendorDTO);
    }
    @ApiOperation(value="This will update a vendor with the patch method " +
            "passing the id and the payload of an object ",notes = "This will" +
            " update a vendor with the patch method \" +\n" +
            "            \"passing the id and the payload of an object")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id,
                                 @RequestBody VendorDTO vendorDTO){
        return vendorService.patchVendor(id,vendorDTO);
    }
}
