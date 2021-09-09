package org.excavator.boot.experiment;

public class HelpfulNpes {

    public static void main(String[] args) {

        var countryName = new Customer().address.country.name; // cannot read field "country" because "address" is null
                                                                       // 无法读取字段country, 因为address 是空
        System.out.println(countryName);
    }

    public static class Customer{
        public Address address;
    }

    public static class Address{
        public Country country;
    }

    public static class Country{
        public String name;
    }
}
