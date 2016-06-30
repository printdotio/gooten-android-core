package com.gooten.core.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.gooten.core.GTN;
import com.gooten.core.GTNConfig;
import com.gooten.core.GTNException;
import com.gooten.core.TestUtils;
import com.gooten.core.model.Address;
import com.gooten.core.model.AddressValidationResponse;
import com.gooten.core.model.Country;
import com.gooten.core.model.Currency;
import com.gooten.core.model.Order;
import com.gooten.core.model.OrderItem;
import com.gooten.core.model.OrderPriceResult;
import com.gooten.core.model.OrderResult;
import com.gooten.core.model.Payment;
import com.gooten.core.model.PaymentValidationResponse;
import com.gooten.core.model.Product;
import com.gooten.core.model.ProductBuildOption;
import com.gooten.core.model.ProductVariantsResponse;
import com.gooten.core.model.ShipPriceEstimate;
import com.gooten.core.model.ShippingPricesRequest;
import com.gooten.core.model.ShippingPricesResponse;
import com.gooten.core.model.SkuQuantityPair;
import com.gooten.core.model.SubmittedOrder;
import com.gooten.core.model.UserInfo;
import com.gooten.core.types.Environment;
import com.gooten.core.types.Local;
import com.gooten.core.utils.StringUtils;

/**
 * Base abstract class holding tests for {@link API} class.
 *
 * @author Vlado
 */
public abstract class APITests extends ApplicationTestCase<Application> {

    private final GTNConfig config;
    private final int PHONE_CASE_ID = 57; // PhoneCase has same ID on live and staging (+)
    private final int INVALID_PRODUCT_ID = 1313131;
    private final List<String> PHONE_CASE_SKUS = Arrays.asList("PhoneCase-GalaxyNote2-Matte", "PhoneCase-Glossy-GalaxyNote3", "PhoneCase-Nexus5-Matte");
    private final String COUPON1 = "ANDROIDTEST1"; // 5USD off
    private final String COUPON2 = "ANDROIDTEST2"; // 20USD off

    public APITests(Environment environment) {
        super(Application.class);
        config = TestUtils.createConfig(environment);
    }

    private void setValidConfig() {
        try {
            GTN.setConfig(getContext(), config);
        } catch (GTNException e) {
            e.printStackTrace();
        }
    }

    private void setNullConfig() {
        GTN.setConfigNoValidation(getContext(), null);
    }

    private void setConfigNoCountryCode() {
        GTNConfig cfg = new GTNConfig();
        cfg.setEnvironment(config.getEnvironment());
        cfg.setRecipeId(config.getRecipeId());
        GTN.setConfigNoValidation(getContext(), cfg);
    }

    // -------------------------- getUserLocation ----------------------------------------- //

    public void testGetUserLocation() {
        final Local<String> result = new Local<>();
        API.createGetUserLocationRequest(new APIRequestCompleteListener<String>() {
            @Override
            public void onRequestComplete(APIResultType resultType, String response) {
                result.val = response;
            }
        }).executeAndWait();

        assertTrue(StringUtils.isNotEmpty(result.val));
    }

    // -------------------------- getUserInfo ----------------------------------------- //

    private UserInfo getUserInfo() {
        final Local<UserInfo> result = new Local<>();
        API.createGetUserInfoRequest(getContext(), new APIRequestCompleteListener<UserInfo>() {
            @Override
            public void onRequestComplete(APIResultType resultType, UserInfo response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    @SmallTest
    public void testGetUserInfo_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            UserInfo result = getUserInfo();
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @LargeTest
    public void testGetUserInfo() {
        setValidConfig();

        UserInfo result = getUserInfo();

        assertNotNull(result);
        assertNotNull(result.getCountryCode());
        assertNotNull(result.getCurrencyCode());
    }

    // -------------------------- getCountries ----------------------------------------- //

    private List<Country> getCountries() {
        final Local<List<Country>> result = new Local<>();
        API.createGetCountriesRequest(getContext(), new APIRequestCompleteListener<List<Country>>() {
            @Override
            public void onRequestComplete(APIResultType resultType, List<Country> response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    @SmallTest
    public void testGetCountries_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            getCountries();
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @MediumTest
    public void testGetCountries() {
        setValidConfig();

        List<Country> result = getCountries();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // -------------------------- getCurrencies ----------------------------------------- //

    private List<Currency> getCurrencies() {
        final Local<List<Currency>> result = new Local<>();
        API.createGetCurrenciesRequest(getContext(), new APIRequestCompleteListener<List<Currency>>() {
            @Override
            public void onRequestComplete(APIResultType resultType, List<Currency> response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    @SmallTest
    public void testGetCurrencies_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            getCurrencies();
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @MediumTest
    public void testGetCurrencies() {
        setValidConfig();

        List<Currency> result = getCurrencies();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    // -------------------------- getProducts ----------------------------------------- //

    @SmallTest
    public void testGetProducts_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            API.createGetProductsRequest(getContext(), null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testGetProducts_InvalidConfig() {
        setConfigNoCountryCode();

        Exception exception = null;
        try {
            API.createGetProductsRequest(getContext(), null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @MediumTest
    public void testGetProducts_WithArgs() {
        setValidConfig();

        final Local<List<Product>> result = new Local<>();
        API.createGetProductsRequest(getContext(), config.getCountryCode(), config.getCurrencyCode(), config.isAllProductsAndVariants(), new APIRequestCompleteListener<List<Product>>() {
            @Override
            public void onRequestComplete(APIResultType resultType, List<Product> response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
        assertFalse(result.val.isEmpty());
        assertEquals(config.getCurrencyCode(), result.val.get(0).getPriceInfo().getCurrencyCode());
    }

    @MediumTest
    public void testGetProducts_WithArgsInvalid() {
        setValidConfig();

        final Local<List<Product>> result = new Local<>();
        API.createGetProductsRequest(getContext(), "INVALID", config.getCurrencyCode(), config.isAllProductsAndVariants(), new APIRequestCompleteListener<List<Product>>() {
            @Override
            public void onRequestComplete(APIResultType resultType, List<Product> response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
        assertTrue(result.val.isEmpty());
    }

    @MediumTest
    public void testGetProducts_CurrencyRSD() {
        setValidConfig();

        final Local<List<Product>> result = new Local<>();
        API.createGetProductsRequest(getContext(), config.getCountryCode(), "RSD", config.isAllProductsAndVariants(), new APIRequestCompleteListener<List<Product>>() {
            @Override
            public void onRequestComplete(APIResultType resultType, List<Product> response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
        assertFalse(result.val.isEmpty());
        assertEquals("RSD", result.val.get(0).getPriceInfo().getCurrencyCode());
    }

    @MediumTest
    public void testGetProducts_WithConfig() {
        setValidConfig();

        final Local<List<Product>> result = new Local<>();
        API.createGetProductsRequest(getContext(), new APIRequestCompleteListener<List<Product>>() {
            @Override
            public void onRequestComplete(APIResultType resultType, List<Product> response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
    }

    // -------------------------- getProductVariants ----------------------------------------- //

    @SmallTest
    public void testGetProductVariants_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            API.createGetProductVariantsRequest(getContext(), PHONE_CASE_ID, null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testGetProductVariants_InvalidConfig() {
        setConfigNoCountryCode();

        Exception exception = null;
        try {
            API.createGetProductVariantsRequest(getContext(), PHONE_CASE_ID, null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @MediumTest
    public void testGetProductVariants_WithArgs() {
        setValidConfig();

        final Local<ProductVariantsResponse> result = new Local<>();
        API.createGetProductVariantsRequest(getContext(), PHONE_CASE_ID, config.getCountryCode(), config.getCurrencyCode(), config.isAllProductsAndVariants(), new APIRequestCompleteListener<ProductVariantsResponse>() {
            @Override
            public void onRequestComplete(APIResultType resultType, ProductVariantsResponse response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
    }

    @MediumTest
    public void testGetProductVariants_WithConfig() {
        setValidConfig();

        final Local<ProductVariantsResponse> result = new Local<>();
        API.createGetProductVariantsRequest(getContext(), PHONE_CASE_ID, new APIRequestCompleteListener<ProductVariantsResponse>() {
            @Override
            public void onRequestComplete(APIResultType resultType, ProductVariantsResponse response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
        assertFalse(result.val.getProductOptions().isEmpty());
        assertFalse(result.val.getProductVariants().isEmpty());
    }

    @MediumTest
    public void testGetProductVariants_InvalidId() {
        setValidConfig();

        final Local<ProductVariantsResponse> result = new Local<>();
        API.createGetProductVariantsRequest(getContext(), INVALID_PRODUCT_ID, new APIRequestCompleteListener<ProductVariantsResponse>() {
            @Override
            public void onRequestComplete(APIResultType resultType, ProductVariantsResponse response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
        assertTrue(result.val.getProductOptions().isEmpty());
        assertTrue(result.val.getProductVariants().isEmpty());
    }

    // -------------------------- getProductTemplates ----------------------------------------- //

    @SmallTest
    public void testGetProductTemplates_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            API.createGetProductTemplatesRequest(getContext(), "INVALID", null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testGetProductTemplates_EmptySKU() {
        setValidConfig();

        Exception exception = null;
        try {
            API.createGetProductTemplatesRequest(getContext(), "", null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testGetProductTemplates_NullSKU() {
        setValidConfig();

        Exception exception = null;
        try {
            API.createGetProductTemplatesRequest(getContext(), null, null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @MediumTest
    public void tesGetProductTemplates_ValidSKU() {
        setValidConfig();

        for (String sku : PHONE_CASE_SKUS) {
            final Local<List<ProductBuildOption>> result = new Local<>();
            API.createGetProductTemplatesRequest(getContext(), sku, new APIRequestCompleteListener<List<ProductBuildOption>>() {
                @Override
                public void onRequestComplete(APIResultType resultType, List<ProductBuildOption> response) {
                    result.val = response;
                }
            }).executeAndWait();

            assertNotNull(result.val);
            assertFalse(result.val.isEmpty());
        }
    }

    @MediumTest
    public void tesGetProductTemplates_InvalidSKU() {
        setValidConfig();

        final Local<List<ProductBuildOption>> result = new Local<>();
        API.createGetProductTemplatesRequest(getContext(), "INVALID", new APIRequestCompleteListener<List<ProductBuildOption>>() {
            @Override
            public void onRequestComplete(APIResultType resultType, List<ProductBuildOption> response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
        assertTrue(result.val.isEmpty());
    }

    // -------------------------- getShipPriceEstimate ----------------------------------------- //

    @SmallTest
    public void testGetShipPriceEstimate_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            API.createGetShipPriceEstimateRequest(getContext(), PHONE_CASE_ID, null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testGetShipPriceEstimate_InvalidConfig() {
        setConfigNoCountryCode();

        Exception exception = null;
        try {
            API.createGetShipPriceEstimateRequest(getContext(), PHONE_CASE_ID, null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @MediumTest
    public void testGetShipPriceEstimate_WithArgs() {
        setValidConfig();

        final Local<ShipPriceEstimate> result = new Local<>();
        API.createGetShipPriceEstimateRequest(getContext(), PHONE_CASE_ID, config.getCountryCode(), config.getCurrencyCode(), new APIRequestCompleteListener<ShipPriceEstimate>() {
            @Override
            public void onRequestComplete(APIResultType resultType, ShipPriceEstimate response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
    }

    @MediumTest
    public void testGetShipPriceEstimate_WithConfig() {
        setValidConfig();

        final Local<ShipPriceEstimate> result = new Local<>();
        API.createGetShipPriceEstimateRequest(getContext(), PHONE_CASE_ID, new APIRequestCompleteListener<ShipPriceEstimate>() {
            @Override
            public void onRequestComplete(APIResultType resultType, ShipPriceEstimate response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
    }

    @MediumTest
    public void testGetShipPriceEstimate_InvalidId() {
        setValidConfig();

        final Local<ShipPriceEstimate> result = new Local<>();
        API.createGetShipPriceEstimateRequest(getContext(), INVALID_PRODUCT_ID, new APIRequestCompleteListener<ShipPriceEstimate>() {
            @Override
            public void onRequestComplete(APIResultType resultType, ShipPriceEstimate response) {
                result.val = response;
            }
        }).executeAndWait();

        assertNotNull(result.val);
        assertNull(result.val.getMaxPrice());
        assertNull(result.val.getMinPrice());
    }

    // -------------------------- postShippingPrices ----------------------------------------- //

    private ShippingPricesResponse postShippingPrices(ShippingPricesRequest shipRequest) {
        final Local<ShippingPricesResponse> result = new Local<>();
        API.createPostShippingPricesRequest(getContext(), shipRequest, new APIRequestCompleteListener<ShippingPricesResponse>() {
            @Override
            public void onRequestComplete(APIResultType resultType, ShippingPricesResponse response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    @SmallTest
    public void testPostShippingPrices_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            postShippingPrices(new ShippingPricesRequest());
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostShippingPrices_NullRequest() {
        setValidConfig();

        Exception exception = null;
        try {
            postShippingPrices(null);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostShippingPrices_EmptyRequest() {
        setValidConfig();

        Exception exception = null;
        try {
            postShippingPrices(new ShippingPricesRequest());
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostShippingPrices_EmptyItems() {
        setValidConfig();

        Exception exception = null;
        try {
            ShippingPricesRequest request = TestUtils.createValidShippingPricesRequest("USD");
            request.setItems(new ArrayList<SkuQuantityPair>());
            postShippingPrices(request);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostShippingPrices_InvalidItem() {
        setValidConfig();

        Exception exception = null;
        try {
            ShippingPricesRequest request = TestUtils.createValidShippingPricesRequest("USD");
            request.getItems().add(new SkuQuantityPair("", 1));
            postShippingPrices(request);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @LargeTest
    public void testPostShippingPrices_validUSDCurr() {
        setValidConfig();

        ShippingPricesRequest request = TestUtils.createValidShippingPricesRequest("USD");
        ShippingPricesResponse result = postShippingPrices(request);

        assertNotNull(result);
        assertNotNull(result.getResult());
        assertFalse(result.getResult().isEmpty());
        assertEquals("USD", result.getResult().get(0).getShipOptions().get(0).getPrice().getCurrencyCode());
    }

    @LargeTest
    public void testPostShippingPrices_validRSDCurr() {
        setValidConfig();

        ShippingPricesRequest request = TestUtils.createValidShippingPricesRequest("RSD");
        ShippingPricesResponse result = postShippingPrices(request);

        assertNotNull(result);
        assertNotNull(result.getResult());
        assertFalse(result.getResult().isEmpty());
        assertEquals("RSD", result.getResult().get(0).getShipOptions().get(0).getPrice().getCurrencyCode());
    }

    // -------------------------- postPriceEstimate ----------------------------------------- //

    private OrderPriceResult postPriceEstimate(Order order) {
        final Local<OrderPriceResult> result = new Local<>();
        API.createPostPriceEstimateRequest(getContext(), order, new APIRequestCompleteListener<OrderPriceResult>() {
            @Override
            public void onRequestComplete(APIResultType resultType, OrderPriceResult response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    @SmallTest
    public void testPostPriceEstimate_NullOrder() {
        setValidConfig();

        Exception exception = null;
        try {
            postPriceEstimate(null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testPostPriceEstimate_NullItems() {
        setValidConfig();

        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setItems(null);

        Exception exception = null;
        try {
            postPriceEstimate(order);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testPostPriceEstimate_InvalidItem() {
        setValidConfig();

        Order order = TestUtils.createValidPriceEstimateOrder();
        order.getItems().get(1).setSku(null);

        Exception exception = null;
        try {
            postPriceEstimate(order);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testPostPriceEstimate_NullShipToAddress() {
        setValidConfig();

        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setShipToAddress(null);

        Exception exception = null;
        try {
            postPriceEstimate(order);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testPostPriceEstimate_ShipToAddressNoCountryCode() {
        setValidConfig();

        Order order = TestUtils.createValidPriceEstimateOrder();
        order.getShipToAddress().setCountryCode("");

        Exception exception = null;
        try {
            postPriceEstimate(order);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @MediumTest
    public void testPostPriceEstimate_ValidOrder() {
        setValidConfig();

        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setPayment(TestUtils.createPaymentWithCurrencyCode("USD"));

        OrderPriceResult result = postPriceEstimate(order);

        assertNotNull(result);
        assertFalse(result.isHadError());
        assertNotNull(result.getItems());
        assertNotNull(result.getShipping());
    }

    @LargeTest
    public void testPostPriceEstimate_InvalidOrder() {
        setValidConfig();

        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setItems(Arrays.asList(new OrderItem(1, "INVALID", 1)));
        order.setPayment(TestUtils.createPaymentWithCurrencyCode("USD"));

        OrderPriceResult result = postPriceEstimate(order);

        assertNull(result);
    }

    @MediumTest
    public void testPostPriceEstimate_NoCurrency() {
        setValidConfig();

        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setPayment(null);

        OrderPriceResult result = postPriceEstimate(order);

        assertNotNull(result);
    }

    @MediumTest
    public void testPostPriceEstimate_CurrencyRSD() {
        setValidConfig();

        Payment payment = new Payment();
        payment.setCurrencyCode("RSD");
        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setPayment(payment);

        OrderPriceResult result = postPriceEstimate(order);

        assertNotNull(result);
        assertEquals("RSD", result.getItems().getCurrencyCode());
    }

    @MediumTest
    public void testPostPriceEstimate_Tax() {
        setValidConfig();

        // Tax is only available for Staging & QA env.
        if (config.getEnvironment() != Environment.LIVE) {
            Order order = TestUtils.createValidPriceEstimateOrder();
            order.setPayment(TestUtils.createPaymentWithCurrencyCode("USD"));
            order.setShipToAddress(TestUtils.createValidAddressTax());

            OrderPriceResult result = postPriceEstimate(order);

            assertNotNull(result);
            assertFalse(result.isHadError());
            assertNotNull(result.getItems());
            assertNotNull(result.getShipping());
            assertNotNull(result.getTax());
            assertTrue(result.getTax().getPrice() > 0);
        }
    }

    @MediumTest
    public void testPostPriceEstimate_SingleCoupon() {
        setValidConfig();

        String couponCode = COUPON1;
        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setPayment(TestUtils.createPaymentWithCurrencyCode("USD"));
        order.setCouponCodes(Arrays.asList(couponCode));

        OrderPriceResult result = postPriceEstimate(order);

        assertNotNull(result);
        assertFalse(result.isHadError());
        assertNotNull(result.getItems());
        assertNotNull(result.getShipping());
        assertTrue(result.isHadCouponApply());
        assertNotNull(result.getCoupons());

        OrderPriceResult.CouponData couponData = OrderPriceResult.CouponData.findByCouponCode(result.getCoupons(), couponCode);
        assertNotNull(couponData);
        assertTrue(couponData.isApplied());
        assertEquals(5.0, couponData.getCouponSavings().getPrice());
    }

    @MediumTest
    public void testPostPriceEstimate_SingleCouponInvalid() {
        setValidConfig();

        String couponCode = "INVALID";
        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setPayment(TestUtils.createPaymentWithCurrencyCode("USD"));
        order.setCouponCodes(Arrays.asList(couponCode));

        OrderPriceResult result = postPriceEstimate(order);

        assertNotNull(result);
        assertFalse(result.isHadError());
        assertNotNull(result.getItems());
        assertNotNull(result.getShipping());
        assertFalse(result.isHadCouponApply());
        assertNotNull(result.getCoupons());

        OrderPriceResult.CouponData couponData = OrderPriceResult.CouponData.findByCouponCode(result.getCoupons(), couponCode);
        assertNotNull(couponData);
        assertFalse(couponData.isApplied());
    }

    @MediumTest
    public void testPostPriceEstimate_MultiCoupons() {
        setValidConfig();

        String[] coupons = new String[]{
                COUPON1, COUPON2
        };
        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setPayment(TestUtils.createPaymentWithCurrencyCode("USD"));
        order.setCouponCodes(Arrays.asList(coupons));

        OrderPriceResult result = postPriceEstimate(order);

        assertNotNull(result);
        assertFalse(result.isHadError());
        assertNotNull(result.getItems());
        assertNotNull(result.getShipping());
        assertTrue(result.isHadCouponApply());
        assertNotNull(result.getCoupons());

        OrderPriceResult.CouponData couponData = OrderPriceResult.CouponData.findByCouponCode(result.getCoupons(), coupons[1]);
        assertNotNull(couponData);
        assertTrue(couponData.isApplied());
        assertEquals(20.0, couponData.getCouponSavings().getPrice());
    }

    @MediumTest
    public void testPostPriceEstimate_MultiCouponsInvalid() {
        setValidConfig();

        String[] coupons = new String[]{
                "INVALID1", "INVALID2"
        };
        Order order = TestUtils.createValidPriceEstimateOrder();
        order.setPayment(TestUtils.createPaymentWithCurrencyCode("USD"));
        order.setCouponCodes(Arrays.asList(coupons));

        OrderPriceResult result = postPriceEstimate(order);

        assertNotNull(result);
        assertFalse(result.isHadError());
        assertNotNull(result.getItems());
        assertNotNull(result.getShipping());
        assertFalse(result.isHadCouponApply());
        assertNotNull(result.getCoupons());
        assertNotNull(OrderPriceResult.CouponData.findByCouponCode(result.getCoupons(), coupons[0]));
        assertNotNull(OrderPriceResult.CouponData.findByCouponCode(result.getCoupons(), coupons[1]));
    }

    // -------------------------- postOrders ----------------------------------------- //

    private OrderResult postOrders(Order order) {
        final Local<OrderResult> result = new Local<>();
        API.createPostOrderRequest(getContext(), order, new APIRequestCompleteListener<OrderResult>() {
            @Override
            public void onRequestComplete(APIResultType resultType, OrderResult response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    private Order createValidOrder(String currencyCode, boolean mockPrice) {
        Order order = TestUtils.createValidPostOrdersOrder();

        double total = 123.12;
        if (!mockPrice) {
            order.setPayment(TestUtils.createPaymentWithCurrencyCode(currencyCode));
            OrderPriceResult result = postPriceEstimate(order);

            total = 0;
            if (result.getTax() != null) {
                total += result.getTax().getPrice();
            }
            total += result.getShipping().getPrice();
            total += result.getItems().getPrice();
        }
        order.setPayment(TestUtils.createTestCCPayment(total, currencyCode));

        return order;
    }


    @SmallTest
    public void testPostOrders_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            postOrders(new Order());
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostOrders_NullOrder() {
        setValidConfig();

        Exception exception = null;
        try {
            postOrders(null);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostOrders_NullShipToAddress() {
        setValidConfig();

        Order order = createValidOrder("USD", true);
        order.setShipToAddress(null);

        Exception exception = null;
        try {
            postOrders(order);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostOrders_EmptyShipToAddress() {
        setValidConfig();

        Order order = createValidOrder("USD", true);
        order.setShipToAddress(new Address());

        Exception exception = null;
        try {
            postOrders(order);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostOrders_NullBillingAddress() {
        setValidConfig();

        Order order = createValidOrder("USD", true);
        order.setBillingAddress(null);

        Exception exception = null;
        try {
            postOrders(order);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostOrders_EmptyBillingAddress() {
        setValidConfig();

        Order order = createValidOrder("USD", true);
        order.setBillingAddress(new Address());

        Exception exception = null;
        try {
            postOrders(order);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testPostOrders_NullPayment() {
        setValidConfig();

        Order order = createValidOrder("USD", true);
        order.setPayment(null);

        Exception exception = null;
        try {
            postOrders(order);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @LargeTest
    public void testPostOrders_IncorrectTotalCost() {
        setValidConfig();

        // TODO
    }

    @SmallTest
    public void testPostOrders_NoItems() {
        setValidConfig();

        {
            Order order = createValidOrder("USD", true);
            order.setItems(null);

            Exception exception = null;
            try {
                postOrders(order);
            } catch (Exception e) {
                exception = e;
            }

            assertNotNull(exception);
        }

        {
            Order order = createValidOrder("USD", true);
            order.setItems(new ArrayList<OrderItem>());

            Exception exception = null;
            try {
                postOrders(order);
            } catch (Exception e) {
                exception = e;
            }

            assertNotNull(exception);
        }
    }

    @SmallTest
    public void testPostOrders_EmptyItem() {
        setValidConfig();

        Order order = createValidOrder("USD", true);
        order.setItems(new ArrayList(order.getItems()));
        order.getItems().add(new OrderItem());

        Exception exception = null;
        try {
            postOrders(order);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @LargeTest
    public void testPostOrders_ValidOrder_CCPaym() {
        setValidConfig();

        Order order = createValidOrder("USD", false);
        order.setTestingOrder(true);
        OrderResult result = postOrders(order);

        assertNotNull(result);
        assertFalse(result.isHadError());
        assertNotNull(result.getId());
    }

    @LargeTest
    public void testPostOrders_ValidOrder_CCPaymRSD() {
        setValidConfig();

        Order order = createValidOrder("RSD", false);
        order.setTestingOrder(true);
        OrderResult result = postOrders(order);

        assertNull(result);
    }

    @LargeTest
    public void testPostOrders_ValidOrder_PayPalPaym() {
        setValidConfig();

        Order order = createValidOrder("USD", false);
        Payment ccPayment = order.getPayment();
        Payment ppPayment = new Payment();
        ppPayment.setCurrencyCode(ccPayment.getCurrencyCode());
        ppPayment.setTotal(ccPayment.getTotal());
        order.setPayment(ppPayment);
        order.setTestingOrder(true);
        order.setPreSubmit(true);
        OrderResult result = postOrders(order);

        assertNotNull(result);
        assertFalse(result.isHadError());
        assertNotNull(result.getId());
    }

    @LargeTest
    public void testPostOrders_ValidOrder_InvalidPayPalPaym() {
        setValidConfig();

        Order order = createValidOrder("USD", false);
        Payment ccPayment = order.getPayment();
        Payment ppPayment = new Payment();
        ppPayment.setCurrencyCode("INVALID");
        ppPayment.setTotal(ccPayment.getTotal());
        order.setPayment(ppPayment);
        order.setTestingOrder(true);
        order.setPreSubmit(true);
        OrderResult result = postOrders(order);

        assertNull(result);
    }

    // -------------------------- getOrders ----------------------------------------- //

    private SubmittedOrder getOrders(String orderId) {
        final Local<SubmittedOrder> result = new Local<>();
        API.createGetOrdersRequest(getContext(), orderId, new APIRequestCompleteListener<SubmittedOrder>() {
            @Override
            public void onRequestComplete(APIResultType resultType, SubmittedOrder response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    @SmallTest
    public void testGetOrders_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            getOrders("");
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testGetOrders_NullOrderId() {
        setValidConfig();

        Exception exception = null;
        try {
            getOrders(null);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testGetOrders_EmptyOrderId() {
        setValidConfig();

        Exception exception = null;
        try {
            getOrders("");
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @MediumTest
    public void testGetOrders_InvalidOrderId() {
        setValidConfig();

        SubmittedOrder result = getOrders("INVALID");

        assertNotNull(result);
        assertFalse(result.isValidOrderId());
    }

    @MediumTest
    public void testGetOrders_ValidOrderId() {
        setValidConfig();

        String orderId;
        if (config.getEnvironment() == Environment.LIVE) {
            orderId = "7-95f6fb16-304c-4cd9-8240-9aa17ef66237";
        } else {
            orderId = "7-4bf5200a-f9c0-458f-bf33-59dc17ec1d8a";
        }
        SubmittedOrder result = getOrders(orderId);

        assertNotNull(result);
        assertTrue(result.isValidOrderId());
    }

    // -------------------------- getPaymentValidation --------------------------------------- //

    private PaymentValidationResponse getPaymentValidation(String orderId, String payPalKey) {
        final Local<PaymentValidationResponse> result = new Local<>();
        API.createGetPaymentValidationRequest(getContext(), orderId, payPalKey, new APIRequestCompleteListener<PaymentValidationResponse>() {
            @Override
            public void onRequestComplete(APIResultType resultType, PaymentValidationResponse response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    @SmallTest
    public void testGetPaymentValidation_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            getPaymentValidation("", "");
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testGetPaymentValidation_NullArgs1() {
        setValidConfig();

        Exception exception = null;
        try {
            getPaymentValidation(null, null);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @SmallTest
    public void testGetPaymentValidation_NullArgs2() {
        setValidConfig();

        Exception exception = null;
        try {
            getPaymentValidation("INVALID", null);
        } catch (Exception e) {
            exception = e;
        }

        assertNotNull(exception);
    }

    @MediumTest
    public void testGetPaymentValidation_InvalidArgs() {
        setValidConfig();

        PaymentValidationResponse result = getPaymentValidation("INVALID", "INVALID");

        assertNull(result);
    }

    // TODO add more tests (valid result, etc...)

    // -------------------------- getAddressValidation ----------------------------------------- //

    private AddressValidationResponse getAddressValidation(Address address) {
        final Local<AddressValidationResponse> result = new Local<>();
        API.createGetAddressValidationRequest(getContext(), address, new APIRequestCompleteListener<AddressValidationResponse>() {
            @Override
            public void onRequestComplete(APIResultType resultType, AddressValidationResponse response) {
                result.val = response;
            }
        }).executeAndWait();
        return result.val;
    }

    @SmallTest
    public void testGetAddressValidation_NullConfig() {
        setNullConfig();

        Exception exception = null;
        try {
            getAddressValidation(new Address());
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @SmallTest
    public void testAddressValidation_NullAddress() {
        setValidConfig();

        Exception exception = null;
        try {
            getAddressValidation(null);
        } catch (Exception e) {
            exception = e;
        }
        assertNotNull(exception);
    }

    @MediumTest
    public void testAddressValidation_EmptyAddress() {
        setValidConfig();

        AddressValidationResponse result = getAddressValidation(new Address());

        assertNotNull(result);
        assertTrue(result.isValid());
    }

    @MediumTest
    public void testAddressValidation_InvalidAddress() {
        setValidConfig();

        Address address = new Address();
        address.setLine1("Line1");
        address.setLine2("Line2");
        address.setCity("City");
        address.setCountryCode("US");

        AddressValidationResponse result = getAddressValidation(address);

        assertNotNull(result);
        assertFalse(result.isValid());
    }

    @MediumTest
    public void testAddressValidation_ValidAddress() {
        setValidConfig();

        Address address = TestUtils.createValidAddress();

        AddressValidationResponse result = getAddressValidation(address);

        assertNotNull(result);
        assertTrue(result.isValid());
    }

    @MediumTest
    public void testAddressValidation_ProposedAddress() {
        setValidConfig();

        Address address = TestUtils.createValidAddress();
        address.setPostalCode(null);

        AddressValidationResponse result = getAddressValidation(address);

        assertNotNull(result);
        assertNotNull(result.getProposedAddress());
    }

}
