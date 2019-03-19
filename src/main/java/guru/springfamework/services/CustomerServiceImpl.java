package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.v1.CustomerController;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id){

        return customerRepository.findById(id)
                .map(customerMapper::customerToCustomerDTO)
                .orElseThrow(ResourceNotFoundException::new);

//        Optional<Customer> customerOptional = customerRepository.findById(id);
//        Customer customer = null;
//        if(customerOptional.isPresent()){
//            customer = customerOptional.get();
//        }
//        return customerMapper.customerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO){
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        return saveAndReturnDTO(customer);
//        Customer savedCustomer = customerRepository.save(customer);
//        CustomerDTO returnDto =
//                customerMapper.customerToCustomerDTO(savedCustomer);
//        return returnDto;
    }

    private CustomerDTO saveAndReturnDTO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDTO =
                customerMapper.customerToCustomerDTO(savedCustomer);
        return returnDTO;
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }
    @Override
    public CustomerDTO patchCustomer(Long id,CustomerDTO customerDTO){
        return customerRepository.findById(id).map(customer -> {
            if(customerDTO.getFirstname() != null){
                customer.setFirstname(customerDTO.getFirstname());
            }
            if(customerDTO.getLastname() != null){
                customer.setLastname(customerDTO.getLastname());
            }
            return customerMapper.customerToCustomerDTO(customerRepository.save(customer));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    private String getCustomerUrl(Long id){
        return CustomerController.BASE_URL+id;
    }
}
