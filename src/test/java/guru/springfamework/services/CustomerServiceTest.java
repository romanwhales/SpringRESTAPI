package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {
    public static final Long ID = 1L;
    public static final String firstname = "OLAWALE";
    public static final String lastname = "ADEDEJI";
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE,
                customerRepository);
    }

    @Test
    public void getAllCustomers() throws Exception{
        //given
        List<Customer> customers = Arrays.asList(new Customer(),
                new Customer(),new Customer());
        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

        //then
        assertEquals(3,customerDTOS.size());
    }

    @Test
    public void getCustomerId() throws Exception{
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setLastname(lastname);
        customer.setFirstname(firstname);

        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        //then
        assertEquals(ID,customerDTO.getId());
        assertEquals(lastname,customerDTO.getLastname());
        assertEquals(firstname,customerDTO.getFirstname());

    }

    @Test
    public void createNewCustomer() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Olawale");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1l);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(),savedDTO.getFirstname());
    }

    @Test
    public void saveCustomerByDTO() throws Exception{
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        savedCustomer.setId(1l);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L,
                customerDTO);

        //then
        assertEquals(customerDTO.getFirstname(),savedDTO.getFirstname());

    }

    @Test
    public void deleteCustomerById() throws Exception{
        Long id = 1L;

        customerRepository.deleteById(id);

        verify(customerRepository,times(1)).deleteById(anyLong());
    }
}