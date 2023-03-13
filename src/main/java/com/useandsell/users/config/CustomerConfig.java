//package com.useandsell.users.config;
//
//import com.useandsell.users.dto.Customer;
//import com.useandsell.users.repository.CustomerRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.List;
//@Configuration
//public class CustomerConfig {
//    @Bean
//    CommandLineRunner commandLineRunner(CustomerRepository repository){
//        return args -> {
//            Customer Ram = new Customer(
//                    "Ram",
//                    "ram@gmail.com",
//                    LocalDate.of(2000, Month.JANUARY, 5)
//
//
//
//            );
//            Customer Mohan = new Customer(
//                    "Mohan",
//                    "mohan@gmail.com",
//                    LocalDate.of(1999, Month.JUNE, 3)
//
//
//
//            );
//            repository.saveAll(List.of(Ram,Mohan));
//        };
//    }
//}
