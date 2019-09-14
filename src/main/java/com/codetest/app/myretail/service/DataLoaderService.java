/**
 *
 */
package com.codetest.app.myretail.service;

import com.codetest.app.myretail.entity.CurrentPrice;
import com.codetest.app.myretail.entity.Product;
import com.codetest.app.myretail.repository.ProductRepository;
import com.codetest.app.myretail.repository.RoleRepository;
import com.codetest.app.myretail.repository.UsersRepository;
import com.codetest.app.myretail.response.Role;
import com.codetest.app.myretail.response.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author nrajuri
 *
 */
@Service
public class DataLoaderService {

    private static final Role roleUser = new Role("1", "USER");
    private static final Role roleAdmin = new Role("2", "ADMIN");
    private static final List<Role> rolesList1 = Arrays.asList(new Role[]{roleAdmin, roleUser});
    private static final List<Role> rolesList2 = Arrays.asList(new Role[]{roleUser});
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
        loadProductPriceInDB();
        loadUsersInDB();
    }

    //load users in DB
    private void loadUsersInDB() {
        if (usersRepository != null) {
            this.roleRepository.deleteAll();
            roleRepository.save(roleUser);
            roleRepository.save(roleAdmin);

            List<Users> usersList = new ArrayList<Users>();
            Users users1 = new Users();
            users1.setUsername("admin");
            users1.setPassword(passwordEncoder.encode("admin"));
            users1.setRoles(rolesList1);
            usersList.add(users1);

            Users users2 = new Users();
            users2.setUsername("user");
            users2.setPassword(passwordEncoder.encode("user"));
            users2.setRoles(rolesList2);
            usersList.add(users2);

            Users users3 = new Users();
            users3.setUsername("dbuser");
            users3.setPassword(passwordEncoder.encode("dbuser"));
            users3.setRoles(rolesList1);
            usersList.add(users3);

            //Deleting any data before load
            usersRepository.deleteAll();
            usersRepository.saveAll(usersList);
        }
    }

    //Load the products in DB
    private void loadProductPriceInDB() {
        if (productRepository != null) {
            List<Product> prodList = new ArrayList<Product>();
            CurrentPrice currentPriceObj1 = new CurrentPrice();
            currentPriceObj1.setCurrencyCode("USD");
            currentPriceObj1.setValue(BigDecimal.valueOf(13.49));
            Product product1 = new Product("13860428", currentPriceObj1);
            prodList.add(product1);

            CurrentPrice currentPriceObj2 = new CurrentPrice();
            currentPriceObj2.setCurrencyCode("USD");
            currentPriceObj2.setValue(BigDecimal.valueOf(18.99));
            Product product2 = new Product("16752456", currentPriceObj2);
            prodList.add(product2);

            CurrentPrice currentPriceObj3 = new CurrentPrice();
            currentPriceObj3.setCurrencyCode("USD");
            currentPriceObj3.setValue(BigDecimal.valueOf(29.99));
            Product product3 = new Product("16752457", currentPriceObj2);
            prodList.add(product3);

            //Deleting any data before load
            productRepository.deleteAll();
            productRepository.saveAll(prodList);
        }
    }
}