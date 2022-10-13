package com.example.demo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.logstash.logback.composite.loggingevent.MdcJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static net.logstash.logback.argument.StructuredArguments.kv;

@RestController
public class Controller {

    private CustomerClient customerClient;

    private AddressClient addressClient;

    private Logger logger = LoggerFactory.getLogger("kafkaLogger");

    @Autowired
    public Controller(CustomerClient customerClient, AddressClient addressClient) {
        this.customerClient = customerClient;
        this.addressClient = addressClient;
    }

    @GetMapping(path = "customers/{id}")
    public CustomerAndAddress getCustomerWithAddress(@PathVariable("id") long customerId) {
        logger.info("Testing KV with json",
                kv("customerID", customerId), kv("username", "Saba"), kv("LogType", "Audit"), kv("info", "HelloFriends"),
                kv("employee", new Employee("Mamad", 5000000, new Point(2.5, 1.5))));

        Customer customer = customerClient.getCustomer(customerId);
        Address address = addressClient.getAddressForCustomerId(customerId);
        return new CustomerAndAddress(customer, address);
    }

}

@Getter @AllArgsConstructor(access = AccessLevel.PUBLIC)
class Employee {
    String name;
    long salary;
    Point location;
}
@Getter @AllArgsConstructor(access = AccessLevel.PUBLIC)
class Point {
    double x;
    double y;
}
