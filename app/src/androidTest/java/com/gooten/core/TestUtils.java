package com.gooten.core;

import java.util.ArrayList;
import java.util.Arrays;

import com.braintreegateway.encryption.Braintree;
import com.braintreegateway.encryption.BraintreeEncryptionException;
import com.gooten.core.model.Address;
import com.gooten.core.model.Image;
import com.gooten.core.model.Order;
import com.gooten.core.model.OrderItem;
import com.gooten.core.model.Payment;
import com.gooten.core.model.ShippingPricesRequest;
import com.gooten.core.model.SkuQuantityPair;
import com.gooten.core.types.Environment;

public class TestUtils {

    private static final String BRAINTREE_ENCRYPTION_KEY_LIVE = "MIIBCgKCAQEAvSJ/AjTz8ybKlkbboXBVbHHW9smRoVjcIgJmNdYcrFg+8aW41DEtYJKEENvlA2UvHDMTA7VG8L41bjjFyGrq6OSudzx/gDGsogOb/oMt+OZukkoKy47AYyVDVRc9+k4lH8POg7YDsgTwZC2RVIJ+z4gvc8juF39GOMuPnhLvbPjj1a3ns+UmGF16SnIOSSlZkSNgQumbWa5+Vw/ewVH4jud8xGcmy4G0EyRZzrw5rO4CtkOOFX/TPBCCAU/ANhY0cEbJelr0fJJhOvgp5FC+udqF4PkOHJ5GHhXUjijcfOOjLtMc7dG3QkXx5AcwtDP4wnKFqRDTyjg+KZXpwH7i2QIDAQAB";

    public static GTNConfig createConfig() {
        return createConfig(Environment.LIVE);
    }

    public static GTNConfig createConfig(Environment environment) {
        GTNConfig config = new GTNConfig();
        config.setEnvironment(environment);
        if (environment == Environment.LIVE) {
            config.setRecipeId("1AB4E1F8-DBCB-4D6C-829F-EE0B2A60C0B3");
        } else if (environment == Environment.STAGING) {
            config.setRecipeId("00000000-0000-0000-0000-000000000000");
        }
        config.setCountryCode("US");
        config.setCurrencyCode("USD");
        config.setLanguageCode("en");
        return config;
    }

    public static Address createValidAddress() {
        Address address = new Address();
        address.setFirstName("Gooten");
        address.setLastName("Test");
        address.setEmail("Test@gooten.com");
        address.setPhone("13123123123");
        address.setLine1("222 broadway");
        address.setCity("New York");
        address.setPostalCode("10038");
        address.setState("NY");
        address.setCountryCode("US");
        return address;
    }

    public static Address createValidAddressTax() {
        Address address = new Address();
        address.setFirstName("Gooten");
        address.setLastName("Test");
        address.setEmail("Test@gooten.com");
        address.setPhone("13123123123");
        address.setLine1("2601 West Marina Place");
        address.setCity("Washington");
        address.setPostalCode("15301");
        address.setState("PA");
        address.setCountryCode("US");
        return address;
    }

    public static Order createValidPriceEstimateOrder() {
        Order order = new Order();
        OrderItem orderItem1 = new OrderItem(1, "PhoneCase-GalaxyNote2-Matte", 1);
        OrderItem orderItem2 = new OrderItem(2, "PhoneCase-Glossy-GalaxyNote3", 1);
        order.setShipToAddress(createValidAddress());
        order.setItems(Arrays.asList(orderItem1, orderItem2));
        return order;
    }

    public static Order createValidPostOrdersOrder() {
        Image image = new Image();
        image.setUrl("https://printio-widget-live.s3.amazonaws.com/200E4604-4CD5-4E0C-A131-9F5AF25006E6.jpg");
        image.setThumbnailUrl("https://printio-widget-live.s3.amazonaws.com/200E4604-4CD5-4E0C-A131-9F5AF25006E6.jpg");
        image.setIndex(0);
        image.setManipCommand("");
        image.setSpaceId("0");

        Order order = new Order();
        OrderItem orderItem1 = new OrderItem(1, "PhoneCase-GalaxyNote2-Matte", 1);
        orderItem1.setImages(Arrays.asList(image));
        OrderItem orderItem2 = new OrderItem(2, "PhoneCase-Glossy-GalaxyNote3", 1);
        orderItem2.setImages(Arrays.asList(image));
        order.setShipToAddress(createValidAddress());
        order.setBillingAddress(createValidAddress());
        order.setItems(Arrays.asList(orderItem1, orderItem2));

        return order;
    }

    public static Payment createTestCCPayment(double total, String currencyCode) {
        return createCCPayment("4111111111111111", "12/19", "123", total, currencyCode);
    }

    public static Payment createCCPayment(String ccNumber, String expDate, String cvvNumber, double total, String currencyCode) {
        Braintree braintree = new Braintree(BRAINTREE_ENCRYPTION_KEY_LIVE);

        Payment payment = new Payment();
        payment.setTotal(total);
        payment.setCurrencyCode(currencyCode);
        try {
            payment.setBraintreeEnryptedCCNumber(braintree.encrypt(ccNumber));
            payment.setBraintreeEnryptedCCExpDate(braintree.encrypt(expDate));
            payment.setBraintreeEnryptedCVV(braintree.encrypt(cvvNumber));
        } catch (BraintreeEncryptionException e) {
            e.printStackTrace();
        }
        return payment;
    }

    public static Payment createPaymentWithCurrencyCode(String currencyCode) {
        Payment payment = new Payment();
        payment.setCurrencyCode(currencyCode);
        return payment;
    }

    public static ShippingPricesRequest createValidShippingPricesRequest(String currencyCode) {
        ShippingPricesRequest request = new ShippingPricesRequest();
        request.setCurrencyCode(currencyCode);
        request.setShipToCountry("US");
        request.setShipToState("PA");
        request.setShipToPostalCode("115301");

        ArrayList<SkuQuantityPair> items = new ArrayList<>();
        items.add(new SkuQuantityPair("PhoneCase-GalaxyNote2-Matte", 1));
        items.add(new SkuQuantityPair("PhoneCase-Glossy-GalaxyNote3", 2));
        request.setItems(items);

        return  request;
    }
}
