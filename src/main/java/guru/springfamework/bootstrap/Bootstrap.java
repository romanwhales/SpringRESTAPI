package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.domain.Vendor;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category fruits = new Category();
        fruits.setName("Fruits");

        Category dried = new Category();
        dried.setName("Dried");

        Category fresh = new Category();
        fresh.setName("Fresh");

        Category exotic = new Category();
        exotic.setName("Exotic");

        Category nuts = new Category();
        nuts.setName("Nuts");


        categoryRepository.save(fruits);
        categoryRepository.save(dried);
        categoryRepository.save(fresh);
        categoryRepository.save(exotic);
        categoryRepository.save(nuts);

        Customer joe = new Customer();
        joe.setFirstname("Joe");
        joe.setLastname("Newman");

        Customer micheal = new Customer();
        micheal.setFirstname("Micheal");
        micheal.setLastname("Lachappele");

        Customer david = new Customer();
        david.setFirstname("David");
        david.setLastname("Winter");

        Customer anne = new Customer();
        anne.setFirstname("Anne");
        anne.setLastname("Hine");

        Customer alice = new Customer();
        alice.setFirstname("Alice");
        alice.setLastname("Eastman");

        customerRepository.save(joe);
        customerRepository.save(micheal);
        customerRepository.save(david);
        customerRepository.save(anne);
        customerRepository.save(alice);


        Vendor western = new Vendor();
        western.setName("Western Tasty Fruits Ltd.");

        Vendor exotic2 = new Vendor();
        exotic2.setName("Exotic Fruits Company");

        Vendor homeF = new Vendor();
        homeF.setName("Home Fruits");

        Vendor funfresh = new Vendor();
        funfresh.setName("Fun Fresh Fruits Ltd.");

        Vendor nuts2 = new Vendor();
        nuts2.setName("Nuts for Nuts Company");

        vendorRepository.save(western);
        vendorRepository.save(exotic2);
        vendorRepository.save(homeF);
        vendorRepository.save(funfresh);
        vendorRepository.save(nuts2);




        System.out.println("Data Loaded = "+categoryRepository.count());
        System.out.println("Data Loaded = "+customerRepository.count());
        System.out.println("Data Loaded = "+vendorRepository.count());
    }
}
