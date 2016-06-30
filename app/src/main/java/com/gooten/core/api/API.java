package com.gooten.core.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;

import com.gooten.core.GTN;
import com.gooten.core.GTNConfig;
import com.gooten.core.Version;
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
import com.gooten.core.utils.JsonUtils;
import com.gooten.core.utils.StringUtils;

import static com.gooten.core.utils.Validate.hasGTNConfig;
import static com.gooten.core.utils.Validate.notEmpty;
import static com.gooten.core.utils.Validate.notNull;
import static com.gooten.core.utils.Validate.notNullOrEmpty;

/**
 * Holds factory methods for creating Gooten API requests.
 *
 * @author Vlado
 */
public class API {

    private static final String PIO_API_URL_BASE = "%sv/" + Version.API_VERSION + "/source/api/%s/?recipeid=%s&languageCode=%s";
    private static final long DEFAULT_CACHE_VALIDITY_TIME = TimeUnit.MINUTES.toMillis(5);

    private static String createGootenApiUrl(GTNConfig config, String type) {
        String languageCode = config.getLanguageCode();
        if (StringUtils.isBlank(languageCode)) {
            languageCode = "en";
        }
        return String.format(Locale.US, PIO_API_URL_BASE, config.getEnvironment().getAPIUrl(), type, config.getRecipeId(), languageCode);
    }

    private static Bundle createJSONHeaderProps() {
        Bundle headerProperties = new Bundle(1);
        headerProperties.putString("Content-Type", "application/json");
        return headerProperties;
    }

    private static String urlEncode(String str) {
        if (str != null) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return StringUtils.EMPTY;
    }

    public static APIRequest<String> createGetUserLocationRequest(APIRequestCompleteListener<String> onComplete) {
        String url = "https://printio-geo.appspot.com/ip";
        return APIRequest.<String>createGET(0, url, ResponseFactories.getUserLocation, onComplete);
    }

    public static APIRequest<UserInfo> createGetUserInfoRequest(Context context, APIRequestCompleteListener<UserInfo> onComplete) {
        hasGTNConfig(context);

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "userinfo");

        return APIRequest.<UserInfo>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getUserInfo, onComplete);
    }

    public static APIRequest<List<Country>> createGetCountriesRequest(Context context, APIRequestCompleteListener<List<Country>> onComplete) {
        hasGTNConfig(context);

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "countries");

        return APIRequest.<List<Country>>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getCountries, onComplete);
    }

    public static APIRequest<List<Currency>> createGetCurrenciesRequest(Context context, APIRequestCompleteListener<List<Currency>> onComplete) {
        hasGTNConfig(context);

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "currencies");

        return APIRequest.<List<Currency>>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getCurrencies, onComplete);
    }

    public static APIRequest<List<Product>> createGetProductsRequest(Context context, APIRequestCompleteListener<List<Product>> onComplete) {
        hasGTNConfig(context);
        notEmpty(GTN.getRestoredConfig(context).getCountryCode(), "GTNConfig.countryCode");

        GTNConfig cfg = GTN.getRestoredConfig(context);
        return createGetProductsRequest(context, cfg.getCountryCode(), cfg.getCurrencyCode(), cfg.isAllProductsAndVariants(), onComplete);
    }

    public static APIRequest<List<Product>> createGetProductsRequest(Context context, String countryCode, String currencyCode, boolean isAll, APIRequestCompleteListener<List<Product>> onComplete) {
        hasGTNConfig(context);
        notEmpty(countryCode, "countryCode");

        if (StringUtils.isEmpty(currencyCode)) {
            currencyCode = "USD";
        }
        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "products")
                + "&countryCode=" + countryCode
                + "&currencyCode=" + currencyCode
                + "&all=" + (isAll ? "true" : "false");

        return APIRequest.<List<Product>>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getProducts, onComplete);
    }

    public static APIRequest<ProductVariantsResponse> createGetProductVariantsRequest(Context context, int productId, APIRequestCompleteListener<ProductVariantsResponse> onComplete) {
        hasGTNConfig(context);
        notEmpty(GTN.getRestoredConfig(context).getCountryCode(), "GTNConfig.countryCode");

        GTNConfig cfg = GTN.getRestoredConfig(context);
        return createGetProductVariantsRequest(context, productId, cfg.getCountryCode(), cfg.getCurrencyCode(), cfg.isAllProductsAndVariants(), onComplete);
    }

    public static APIRequest<ProductVariantsResponse> createGetProductVariantsRequest(Context context, int productId, String countryCode, String currencyCode, boolean isAll, APIRequestCompleteListener<ProductVariantsResponse> onComplete) {
        hasGTNConfig(context);
        notEmpty(countryCode, "countryCode");

        if (StringUtils.isEmpty(currencyCode)) {
            currencyCode = "USD";
        }
        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "productvariants")
                + "&productId=" + productId
                + "&countryCode=" + countryCode
                + "&currencyCode=" + currencyCode
                + "&all=" + (isAll ? "true" : "false");

        return APIRequest.<ProductVariantsResponse>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getProductVariants, onComplete);
    }

    public static APIRequest<List<ProductBuildOption>> createGetProductTemplatesRequest(Context context, String sku, APIRequestCompleteListener<List<ProductBuildOption>> onComplete) {
        hasGTNConfig(context);
        notEmpty(sku, "sku");

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "producttemplates")
                + "&sku=" + sku;

        return APIRequest.<List<ProductBuildOption>>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getProductTemplates, onComplete);
    }

    public static APIRequest<ShipPriceEstimate> createGetShipPriceEstimateRequest(Context context, int productId, APIRequestCompleteListener<ShipPriceEstimate> onComplete) {
        hasGTNConfig(context);
        notEmpty(GTN.getRestoredConfig(context).getCountryCode(), "GTNConfig.countryCode");

        GTNConfig cfg = GTN.getRestoredConfig(context);
        return createGetShipPriceEstimateRequest(context, productId, cfg.getCountryCode(), cfg.getCurrencyCode(), onComplete);
    }

    public static APIRequest<ShipPriceEstimate> createGetShipPriceEstimateRequest(Context context, int productId, String countryCode, String currencyCode, APIRequestCompleteListener<ShipPriceEstimate> onComplete) {
        hasGTNConfig(context);
        notEmpty(countryCode, "countryCode");

        if (StringUtils.isEmpty(currencyCode)) {
            currencyCode = "USD";
        }
        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "shippriceestimate")
                + "&productId=" + productId
                + "&countryCode=" + countryCode
                + "&currencyCode=" + currencyCode;

        return APIRequest.<ShipPriceEstimate>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getShipPriceEstimate, onComplete);
    }

    public static APIRequest<ShippingPricesResponse> createPostShippingPricesRequest(Context context, ShippingPricesRequest request, APIRequestCompleteListener<ShippingPricesResponse> onComplete) {
        hasGTNConfig(context);
        notNull(request, "request");
        notEmpty(request.getShipToCountry(), "request.shipToCountry");
        notEmpty(request.getShipToPostalCode(), "request.shipToPostalCode");
        notEmpty(request.getCurrencyCode(), "request.currencyCode");
        notNullOrEmpty(request.getItems(), "request.items");
        { // Validate items
            List<SkuQuantityPair> items = request.getItems();
            notNullOrEmpty(items, "request.items");
            for (int i = 0; i < items.size(); i++) {
                String paramNamePrefix = "request.items[" + i + "]";
                notNull(items.get(i), paramNamePrefix);
                notEmpty(items.get(i).getSKU(), paramNamePrefix + ".sku");
            }
        }
        String languageCode = request.getLanguageCode();
        if(StringUtils.isEmpty(languageCode)){
            languageCode = GTN.getRestoredConfig(context).getLanguageCode();
            if(StringUtils.isEmpty(languageCode)){
                languageCode = "en";
            }
        }
        request.setLanguageCode(languageCode);

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "shippingprices");
        byte[] data = null;
        try {
            data = request.toJSON().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return APIRequest.<ShippingPricesResponse>createPOST(0, url, data, createJSONHeaderProps(), ResponseFactories.postShippingPrices, onComplete);
    }

    public static APIRequest<OrderPriceResult> createPostPriceEstimateRequest(Context context, Order order, APIRequestCompleteListener<OrderPriceResult> onComplete) {
        hasGTNConfig(context);
        notNull(order, "order");
        { // Validate ship to address
            Address address = order.getShipToAddress();
            notNull(address, "order.shipToAddress");
            notEmpty(address.getCountryCode(), "order.shipToAddress.countryCode");
        }
        { // Validate items
            List<OrderItem> items = order.getItems();
            notNullOrEmpty(items, "order.items");
            for (int i = 0; i < items.size(); i++) {
                String paramNamePrefix = "order.payment.items[" + i + "]";
                notNull(items.get(i), paramNamePrefix);
                notNull(items.get(i).getSku(), paramNamePrefix + ".sku");
                notNull(items.get(i).getShipCarrierMethodId(), paramNamePrefix + ".shipCarrierMethodId");
                notNull(items.get(i).getQuantity(), paramNamePrefix + ".quantity");
            }
        }

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "priceestimate");
        byte[] data = null;
        try {
            data = order.toJson().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return APIRequest.<OrderPriceResult>createPOST(0, url, data, createJSONHeaderProps(), ResponseFactories.postPriceEstimate, onComplete);
    }

    public static APIRequest<OrderResult> createPostOrderRequest(Context context, Order order, APIRequestCompleteListener<OrderResult> onComplete) {
        hasGTNConfig(context);
        notNull(order, "order");
        { // Validate ship to address
            Address address = order.getShipToAddress();
            notNull(address, "order.shipToAddress");
            notEmpty(address.getFirstName(), "order.shipToAddress.firstName");
            notEmpty(address.getLastName(), "order.shipToAddress.lastName");
            notEmpty(address.getLine1(), "order.shipToAddress.line1");
            notEmpty(address.getCity(), "order.shipToAddress.city");
            notEmpty(address.getPostalCode(), "order.shipToAddress.postalCode");
            notEmpty(address.getCountryCode(), "order.shipToAddress.countryCode");
            notEmpty(address.getEmail(), "order.shipToAddress.email");
            notEmpty(address.getPhone(), "order.shipToAddress.phone");
        }
        { // Validate billing address
            Address address = order.getBillingAddress();
            notNull(address, "order.shipToAddress");
            notEmpty(address.getFirstName(), "order.shipToAddress.firstName");
            notEmpty(address.getLastName(), "order.shipToAddress.lastName");
            notEmpty(address.getPostalCode(), "order.shipToAddress.postalCode");
            notEmpty(address.getCountryCode(), "order.shipToAddress.countryCode");
            notEmpty(address.getEmail(), "order.shipToAddress.email");
        }
        { // Validate payment
            Payment payment = order.getPayment();
            notNull(payment, "order.payment");
            notEmpty(payment.getCurrencyCode(), "order.payment.currencyCode");
            notNull(payment.getTotal(), "order.payment.total");
            if (order.isPreSubmit()) {
                // NOP
            } else {
                notEmpty(payment.getBraintreeEnryptedCCExpDate(), "order.payment.braintreeEncryptedCCExpDate");
                notEmpty(payment.getBraintreeEnryptedCCNumber(), "order.payment.braintreeEncryptedCCNumber");
                notEmpty(payment.getBraintreeEnryptedCVV(), "order.payment.braintreeEncryptedCVV");
            }
        }
        { // Validate items
            List<OrderItem> items = order.getItems();
            notNullOrEmpty(items, "order.items");
            for (int i = 0; i < items.size(); i++) {
                String paramNamePrefix = "order.payment.items[" + i + "]";
                notNull(items.get(i).getSku(), paramNamePrefix + ".sku");
                notNull(items.get(i).getShipCarrierMethodId(), paramNamePrefix + ".shipCarrierMethodId");
                notNull(items.get(i).getQuantity(), paramNamePrefix + ".quantity");
            }
        }

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "orders");
        byte[] data = null;
        try {
            data = order.toJson().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        order.addMetaData(Order.META_SOURCE, "android-core");
        order.addMetaData(Order.META_VERSION, Version.BUILD_VERSION);
        return APIRequest.<OrderResult>createPOST(0, url, data, createJSONHeaderProps(), ResponseFactories.postOrders, onComplete);
    }

    public static APIRequest<SubmittedOrder> createGetOrdersRequest(Context context, String orderId, APIRequestCompleteListener<SubmittedOrder> onComplete) {
        hasGTNConfig(context);
        notEmpty(orderId, "orderId");

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "orders")
                + "&id=" + orderId;

        return APIRequest.<SubmittedOrder>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getOrders, onComplete);
    }

    public static APIRequest<PaymentValidationResponse> createGetPaymentValidationRequest(Context context, String orderId, String payPalKey, APIRequestCompleteListener<PaymentValidationResponse> onComplete) {
        hasGTNConfig(context);
        notEmpty(orderId, "orderId");
        notEmpty(payPalKey, "payPalKey");

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "paymentvalidation")
                + "&OrderId=" + orderId
                + "&PayPalKey=" + payPalKey;

        return APIRequest.<PaymentValidationResponse>createGET(0, url, ResponseFactories.getPaymentValidation, onComplete);
    }

    public static APIRequest<AddressValidationResponse> createGetAddressValidationRequest(Context context, Address address, APIRequestCompleteListener<AddressValidationResponse> onComplete) {
        hasGTNConfig(context);
        notNull(address, "address");

        String url = createGootenApiUrl(GTN.getRestoredConfig(context), "addressvalidation")
                + "&line1=" + urlEncode(address.getLine1())
                + "&line2=" + urlEncode(address.getLine2())
                + "&city=" + urlEncode(address.getCity())
                + "&state=" + urlEncode(address.getState())
                + "&postalCode=" + urlEncode(address.getPostalCode())
                + "&countryCode=" + urlEncode(address.getCountryCode());

        return APIRequest.<AddressValidationResponse>createGET(DEFAULT_CACHE_VALIDITY_TIME, url, ResponseFactories.getAddressValidation, onComplete);
    }


    /**
     * Holds all {@link ResponseFactory} interface implementations.
     */
    static class ResponseFactories {

        static final ResponseFactory<String> getUserLocation = new ResponseFactory<String>() {
            @Override
            public String deserializeResponse(String response) throws Exception {
                return response;
            }
        };

        static final ResponseFactory<UserInfo> getUserInfo = new ResponseFactory<UserInfo>() {
            @Override
            public UserInfo deserializeResponse(String response) throws JSONException {
                return JsonUtils.fromJsonString(UserInfo.class, response);
            }
        };

        static final ResponseFactory<List<Country>> getCountries = new ResponseFactory<List<Country>>() {
            @Override
            public List<Country> deserializeResponse(String response) throws Exception {
                List<Country> result = null;
                if (StringUtils.isNotBlank(response)) {
                    try {
                        result = JsonUtils.fromJsonArray(Country.class, new JSONObject(response).optJSONArray("Countries"));
                    } catch (Exception e) {
                        // NOP
                    }
                }
                if (result == null) {
                    result = new ArrayList<Country>(0);
                }
                return result;
            }
        };

        static final ResponseFactory<List<Currency>> getCurrencies = new ResponseFactory<List<Currency>>() {
            @Override
            public List<Currency> deserializeResponse(String response) throws Exception {
                List<Currency> result = null;
                if (StringUtils.isNotBlank(response)) {
                    try {
                        result = JsonUtils.fromJsonArray(Currency.class, new JSONObject(response).optJSONArray("Currencies"));
                    } catch (Exception e) {
                        // NOP
                    }
                }
                if (result == null) {
                    result = new ArrayList<Currency>(0);
                }
                return result;
            }
        };

        static final ResponseFactory<List<Product>> getProducts = new ResponseFactory<List<Product>>() {
            @Override
            public List<Product> deserializeResponse(String response) throws Exception {
                List<Product> result = null;
                if (StringUtils.isNotBlank(response)) {
                    try {
                        result = JsonUtils.fromJsonArray(Product.class, new JSONObject(response).optJSONArray("Products"));
                    } catch (Exception e) {
                        // NOP
                    }
                }
                if (result == null) {
                    result = new ArrayList<Product>(0);
                }
                return result;
            }
        };

        static final ResponseFactory<ProductVariantsResponse> getProductVariants = new ResponseFactory<ProductVariantsResponse>() {
            @Override
            public ProductVariantsResponse deserializeResponse(String response) throws Exception {
                return ProductVariantsResponse.fromJson(response);
            }
        };

        static final ResponseFactory<List<ProductBuildOption>> getProductTemplates = new ResponseFactory<List<ProductBuildOption>>() {
            @Override
            public List<ProductBuildOption> deserializeResponse(String response) throws Exception {
                List<ProductBuildOption> result = null;
                if (StringUtils.isNotBlank(response)) {
                    try {
                        result = JsonUtils.fromJsonArray(ProductBuildOption.class, new JSONObject(response).optJSONArray("Options"));
                    } catch (Exception e) {
                        // NOP
                    }
                }
                if (result == null) {
                    result = new ArrayList<ProductBuildOption>(0);
                }
                return result;
            }
        };

        static final ResponseFactory<ShipPriceEstimate> getShipPriceEstimate = new ResponseFactory<ShipPriceEstimate>() {
            @Override
            public ShipPriceEstimate deserializeResponse(String response) throws Exception {
                return JsonUtils.fromJsonString(ShipPriceEstimate.class, response);
            }
        };

        static final ResponseFactory<OrderResult> postOrders = new ResponseFactory<OrderResult>() {
            @Override
            public OrderResult deserializeResponse(String response) throws Exception {
                return OrderResult.fromJson(response);
            }
        };

        static final ResponseFactory<OrderPriceResult> postPriceEstimate = new ResponseFactory<OrderPriceResult>() {
            @Override
            public OrderPriceResult deserializeResponse(String response) throws Exception {
                return OrderPriceResult.fromJson(response);
            }
        };

        static final ResponseFactory<SubmittedOrder> getOrders = new ResponseFactory<SubmittedOrder>() {
            @Override
            public SubmittedOrder deserializeResponse(String response) throws Exception {
                return SubmittedOrder.fromJson(response);
            }
        };

        static final ResponseFactory<PaymentValidationResponse> getPaymentValidation = new ResponseFactory<PaymentValidationResponse>() {
            @Override
            public PaymentValidationResponse deserializeResponse(String response) throws Exception {
                return PaymentValidationResponse.fromJson(response);
            }
        };

        static final ResponseFactory<AddressValidationResponse> getAddressValidation = new ResponseFactory<AddressValidationResponse>() {
            @Override
            public AddressValidationResponse deserializeResponse(String response) throws Exception {
                return AddressValidationResponse.fromJson(response);
            }
        };

        static final ResponseFactory<ShippingPricesResponse> postShippingPrices = new ResponseFactory<ShippingPricesResponse>() {
            @Override
            public ShippingPricesResponse deserializeResponse(String response) throws Exception {
                return ShippingPricesResponse.fromJson(response);
            }
        };

    }

}
