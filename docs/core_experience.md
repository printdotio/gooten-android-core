# Gooten Android Core SDK Experience

## Gooten Core SDK Configuration

The very first step is to create and set configuration for Gooten Core SDK.  
Gooten Core SDK configuration is held by `GTNConfig` class. There is only one mandatory property that needs to be set - `Recipe ID`.  

Below is an example of creating and setting configuration for Gooten Core SDK.
```java
GNTConfig config = new GTNConfig();
config.setRecipeId("<INSERT YOUR RECIPE ID HERE>");
GTN.setConfig(getContext(), config);
``` 
After the configuration has been set, all API requests will use the specified Recipe ID.

## Gooten API

The main class for dealing with Gooten API is `com.gooten.core.api.API`. This class contains factory methods for creating Gooten API requests.

### Getting User's Location

Getting user's home/ship-to country is important for the following:

 - determining which products are available to the user
 - determining which vendors will produce the products, to optimize delivery time
 - determining product and shipping prices

If you have a two character country code (ISO 3166-1 alpha-2) already, (for example, "US"), then feel free to skip this section.  

Below is an example for getting user's country code using Gooten API.
```java
APIRequest<String> request = API.createGetUserLocationRequest(new APIRequestCompleteListener<String>() {

	@Override
	public void onRequestComplete(APIResultType resultType, String countryCode) {      
       // Let's set user's country code to GTNConfig for future API requests
       GTNConfig config = GTN.getRestoredConfig(getContext());
       config.setCountryCode(countryCode);
       GTN.setConfig(getContext(), config);
	}

});

// Execute API request synchronously
// executeAndWait() method will block until API request and APIRequestCompleteListener are executed:
request.executeAndWait();

// To execute API request asynchronously, simply execute API request like so:
//request.execute();
```

### Getting a List of Products

Now that we have the user's country, we can call out to the API for a list of available products.  

Each product belongs to some `Product Category`.  
Products can have multiple `Product Variants`, and Product Variants can have multiple `Product Templates`.  
For example, "Canvas Wraps" is a product that has multiple `Product Variants` (square, rectangle, black, white, 6x6 inch, etc...) and each of them has multiple `Product Templates` (with a single photo, 3 photos, 6 photos, etc...). It belongs to "Home Decor" `Product Category`.  
`SKU` (Stock Keeping Unit) is a string that uniquely identifies a single `Product Variant`. For example, `CanvsWrp-BlkWrp-6x6` is a `SKU` for a black, square 6x6 inch Canvas Wrap.  

The `createGetProductsRequest()` method creates a request that gives you the data necessary to:

 - see which products are available in your region
 - see the starting prices for the products
 - get marketing-worthy content and images for the products

Example code for getting list of products:
```java
APIRequest<List<Product>> request = API.createGetProductsRequest(getContext(), new APIRequestCompleteListener<List<Product>>() {

	@Override
	public void onRequestComplete(APIResultType resultType, List<Product> response) {
		// response - list of products
	}

});
request.executeAndWait();
```

This request will use following properties set in `GTNConfig`:

 - `countryCode` - required - the 2 character (ISO 3166-1 alpha-2) country code that the user is interested in shipping to
 - `currencyCode` - optional, defaults to "USD" - the currency in which the prices will be represented
 - `languageCode` - optional, defaults to "en" - the language to have product data returned in
 - `allProductsAndVariants` - optional, defaults to `false` - whether to return all the products that are orderable in the user's region, or to only return products the user has set up in the [product settings page](https://www.gooten.com/admin#/products).

Or, alternatively, you can manually specify these values using the method with the following signature:
```java
public static APIRequest<List<Product>> createGetProductsRequest(Context context, String countryCode, String currencyCode, boolean isAll, APIRequestCompleteListener<List<Product>> onComplete);
```

So now we have a list of products. You may want to only display products which have `Product.isComingSoon()` property set to `false`. This is the indication that a new product is getting ready to be launched, but is not yet available for ordering.

Now that we have a list of products that are available, we ping the API to see which `Product Variants` underneath them are available.

### Getting a List of Product Variants (SKUs) for a Product

It's easy to get a list of available `Product Variants` (`SKUs`) for a product, after you have obtained the product's ID from our `products` endpoint (explained above).

#### Selection of a SKU

Different SKUs *can be* available for different regions. For instance, in US, our canvas wrap SKUs are based on inch formats, while in Europe, those SKUs are based on centimeter formats. This results in different SKUs.  

The important point here is that you want to show the US SKU to someone from US, and the European SKU for the person in Europe. This is why getting (and setting) the user's country is so crucial.  

Here is an example of getting a list of SKUs for a Product:
```java
int productId = <SOME VALID PRODUCT ID>; // e.g. Product.getId();
APIRequest<ProductVariantsResponse> request = API.createGetProductVariantsRequest(getContext(), productId, new APIRequestCompleteListener<ProductVariantsResponse>() {

	@Override
	public void onRequestComplete(APIResultType resultType, ProductVariantsResponse response) {
		// response.getProductVariants() - List<ProductVariant>
	}

});
request.executeAndWait();
```

This request will use following properties set in `GTNConfig`:

 - `countryCode` - required - the 2 character (ISO 3166-1 alpha-2) country code that the user is interested in shipping to
 - `currencyCode` - optional, defaults to "USD" - the currency in which the prices will be represented
 - `languageCode` - optional, defaults to "en" - the language to have product data returned in
 - `allProductsAndVariants` - optional, defaults to `false` - whether to return all the products that are orderable in the user's region, or to only return products the user has set up in the [product settings page](https://www.gooten.com/admin#/products).

Or, alternatively, you can manually specify these values using the method with the following signature:
```java
public static APIRequest<ProductVariantsResponse> createGetProductVariantsRequest(Context context, int productId, String countryCode, String currencyCode, boolean isAll, APIRequestCompleteListener<ProductVariantsResponse> onComplete);
```

### Getting a List of Product Templates For a SKU

`Product Template` is the data that describes how to build the UI for a SKU. For example, a template contains information such as:

 - how many images does one need to supply for a SKU
 - what are the required sizes for the supplied images
 - coordinates where to place the images
 - assets (product renderings) used to draw a realistic preview of what the final product would look like, with their respective coordinates

The `createGetProductTemplatesRequest()` method takes a single argument:

- `sku` - required - the SKU for which you are requesting the templates

Here is an example for getting list of templates for a given SKU:
```java
String sku = "<SOME VALID SKU>"; // e.g. ProductVariant.getSku();
APIRequest<List<ProductBuildOption>> request = API.createGetProductTemplatesRequest(getContext(), sku, new APIRequestCompleteListener<List<ProductBuildOption>>() {

	@Override
	public void onRequestComplete(APIResultType resultType, List<ProductBuildOption> response) {
		// response - list of templates
	}

});
request.executeAndWait();
```

### Getting a Order Total for Items in the Cart

You can get items', shipping, and tax prices via a single API call. 

The `createPostPriceEstimateRequest()` method takes a single argument of type `Order`. The following properties must be set:

 - `order.shipToAddress.countryCode` - required - shipping country code (2-letter)
 - `order.items` - required - list of items for which the prices are to be calculated. Each item must have the following properties defined:
	 - `sku` - required - SKU of product variant
	 - `shipCarrierMethodId` - required - ID of the shipping method
	 - `quantity` - required - quantity of items with this SKU

It is important to note that the shipping prices can change, based on how much data you give to us.  
For example, if you give us a full address that is in the US, you may get a different price than if you only passed in the `countryCode`. Thus, **wherever possible, pass in the entire address**.

Example:
```java
// Create an order
Order order = new Order();

OrderItem orderItem1 = new OrderItem();
orderItem1.setSku("PhoneCase-GalaxyNote2-Matte");
orderItem1.setQuantity(1);
orderItem1.setShipCarrierMethodId(1); // 1 is ID of standard shipping method
OrderItem orderItem2 = new OrderItem();
orderItem1.setSku("PhoneCase-Glossy-GalaxyNote3");
orderItem1.setQuantity(2);
orderItem1.setShipCarrierMethodId(1); // 1 is ID of standard shipping method
order.setItems(Arrays.asList(orderItem1, orderItem2));

Address address = new Address();
address.setCountryCode("US");
order.setShipToAddress(address);

// Create and execute API request
APIRequest<OrderPriceResult> request = API.createPostPriceEstimateRequest(getContext(), order, new APIRequestCompleteListener<OrderPriceResult>() {
	@Override
	public void onRequestComplete(APIResultType resultType, OrderPriceResult response) {
		// response - holds price information about all items, shipping and tax
	}
});
request.executeAndWait();
```

### Getting Shipping Options

The `createPostShippingPricesRequest()` method takes a single argument of type `ShippingPricesRequest`. The following properties must be set:

 - `shipToPostalCode` - required - postal code
 - `shipToCountry` - required - 2-letter country code
 - `shipToState` - optional - state code if exists
 - `currencyCode` - required - currency code
 - `languageCode` - optional - 2-letter language code (fallbacks to current language code in `GTNConfig`, or to 'en' if language code is not set)
 - `items` - required - list of SKU & quantity pairs. Each item must have the following properties defined:
	 - `sku` - required - SKU of product variant
	 - `quantity` - required - quantity of items with this SKU

```java
// Create ShippingPricesRequest
ShippingPricesRequest request = new ShippingPricesRequest();
request.setCurrencyCode("USD");
request.setShipToCountry("US");
request.setShipToState("PA");
request.setShipToPostalCode("115301");
ArrayList<SkuQuantityPair> items = new ArrayList<>(2);
items.add(new SkuQuantityPair("PhoneCase-GalaxyNote2-Matte", 1));
items.add(new SkuQuantityPair("PhoneCase-Glossy-GalaxyNote3", 2));
request.setItems(items);

// Create and execute API request
APIRequest<ShippingPricesResponse> request = API.createPostShippingPricesRequest(getContext(), request, new APIRequestCompleteListener<ShippingPricesResponse>() {
	@Override
	public void onRequestComplete(APIResultType resultType, ShippingPricesResponse response) {
		// response - holds available shipping options and prices
	}
});
request.executeAndWait();
```

### Submitting an Order

To submit an order, you need to execute the API request created by `createPostOrderRequest()` method. This method takes a single argument of type `Order`.

There are two possible payment methods that can be used to pay for order. Depending on the payment method, there are two ways to create an order:

- order with Credit Card details (encrypted using Braintree)
- pre-submitted order (for PayPal payments)

#### Submitting an Order via Credit Card (Braintree)
The following properties must be defined:

- `shipToAddress` object, with following properties:
	- `firstName` - required - first name of the user
	- `lastName` - required - last name of the user
	- `line1` - required - shipping address line 1
	- `line2` - optional - shipping address line 2
	- `city` - required - shipping city
	- `state` - optional - shipping state (if applicable)
	- `postalCode` - required - shipping postal code
	- `countryCode` - required - shipping 2-letter country code
	- `email` - required - user's email
	- `phone` - required - user's phone
- `billingAddress` object, with following properties:
	- `firstName` - required - first name of the user
	- `lastName` - required - last name of the user
	- `postalCode` - required - shipping postal code
	- `countryCode` - required - shipping 2-letter country code
- `items` list:
	- `sku` - required - SKU of product variant
	- `shipCarrierMethodId` - required - ID of shipping carrier method
	- `quantity` - required - quantity of items with this SKU
	- `images` - required - list of images, each containing `Index` of image and `ManipCommand`
- `Payment` object, with following properties:
	- `braintreeEncryptedCCNumber` - required - encrypted credit card number
	- `braintreeEncryptedCCExpDate` - required - encrypted expiry date of card (month/year)
	- `braintreeEncryptedCVV` - required - encrypted CVV number
	- `currencyCode` - required - currency code
	- `total` - required - order total


```java
// Address used for both shipping and billing
Address address = new Address();
address.setFirstName("Gooten");
address.setLastName("Apiexample");
address.setEmail("api-example@gooten.com");
address.setPhone("13123123123");
address.setLine1("222 broadway");
address.setCity("New York");
address.setPostalCode("10038");
address.setState("NY");
address.setCountryCode("US");

// Create payment
Braintree braintree = new Braintree("<INSERT BRAINTREE KEY HERE>"); // import com.braintreegateway.encryption.Braintree
Payment payment = new Payment();
payment.setTotal(total);
payment.setCurrencyCode(currencyCode);
payment.setBraintreeEnryptedCCNumber(braintree.encrypt(ccNumber));
payment.setBraintreeEnryptedCCV(braintree.encrypt(csvNumber));
payment.setBraintreeEnryptedCCExpDate(braintree.encrypt(expDate));

// Sample image applied to products
Image image = new Image();
image.setUrl("https://printio-widget-live.s3.amazonaws.com/200E4604-4CD5-4E0C-A131-9F5AF25006E6.jpg");
image.setThumbnailUrl("https://printio-widget-live.s3.amazonaws.com/200E4604-4CD5-4E0C-A131-9F5AF25006E6.jpg");
image.setIndex(0);
image.setManipCommand("");
image.setSpaceId("0");

// Order items
OrderItem orderItem1 = new OrderItem();
orderItem1.setSku("PhoneCase-GalaxyNote2-Matte");
orderItem1.setQuantity(1);
orderItem1.setShipCarrierMethodId(1);
orderItem1.setImages(Arrays.asList(image));

OrderItem orderItem2 = new OrderItem();
orderItem2.setSku("PhoneCase-Glossy-GalaxyNote3");
orderItem2.setQuantity(2);
orderItem2.setShipCarrierMethodId(1);
orderItem2.setImages(Arrays.asList(image));

// Order
Order order = new Order();
order.setShipToAddress(address);
order.setBillingAddress(address);
order.setItems(Arrays.asList(orderItem1, orderItem2));
order.setPayment(payment);
// IMPORTANT: Testing orders are not sent to production and you can use dummy CC numbers
order.setTestingOrder(true);

		
// Call API to place order
APIRequest<OrderResult> request =  API.createPostOrderRequest(getContext(), order, new APIRequestCompleteListener<OrderResult>() {
            @Override
            public void onRequestComplete(APIResultType resultType, OrderResult response) {
                // response.isHadError() - tells if the order has been placed successfully
                // response.getId() - ID of successfully placed order
            }
});
request.executeAndWait();
```

Note: To use Braintree library you will need to add following dependency `'com.braintreepayments:encryption:2.1.0'`.

#### Submitting an Order via PayPal

Creating order is the same as in previous example, except that encrypted Credit Card information is not needed. Additionally, pre-submitted flag needs to be set to true, like so:

`order.setPreSubmit(true);`

Submitting the order is done via the same method API method.

After the user has paid for the order, you need to verify the payment.  
This is done by sending ID of `proof of payment` to the Gooten API. This is easily done by executing request created by `createGetPaymentValidationRequest()` method.

The `createGetPaymentValidationRequest()` method takes two arguments:
- `orderId` - required - ID of the order that is being verified
- `proofOfPaymentId` - required - ID of `proof of payment` returned by PayPal upon successful payment

```java
APIRequest<PaymentValidationResponse> request = API.createGetPaymentValidationRequest(getContext(), orderId, proofOfPaymentId, new APIRequestCompleteListener<PaymentValidationResponse>() {
            @Override
            public void onRequestComplete(APIResultType resultType, PaymentValidationResponse response) {
                // Response - tells if the validation was successful on not
            }
});
request.executeAndWait();
```

### Getting Order Info

After an order has successfully been placed, you can retrieve order info using order ID.

```java
String orderId = "<INSERT ID HERE>"; // Could be OrderResult.getId()
APIRequest<SubmittedOrder> request = API.createGetOrdersRequest(getContext(), orderId, new APIRequestCompleteListener<SubmittedOrder>() {
	@Override
	public void onRequestComplete(APIResultType resultType, SubmittedOrder response) {
		// response.getItems() - information about each shipped item
	}
});
request.executeAndWait();
```

## Appendix

### Getting supported currencies

You can retrieve a list of supported currencies from the Gooten API like so:
```java
APIRequest<List<Currency>> request = API.createGetCurrenciesRequest(getContext(), new APIRequestCompleteListener<List<Currency>>() {
	@Override
	public void onRequestComplete(APIResultType resultType, List<Currency> response) {
		// response - list of supported curriencies
	}
});
request.executeAndWait();
```

### Getting supported shipping-to countries

You can retrieve a list of supported countries from the Gooten API like so:
```java
APIRequest<List<Country>> request = API.createGetCountriesRequest(getContext(), new APIRequestCompleteListener<List<Country>>() {
	@Override
	public void onRequestComplete(APIResultType resultType, List<Country> response) {
		// response - list of supported countries
	}
});
request.executeAndWait();
```

### Validating addresses

To validate user-entered address information, you can query the Gooten API. API will return a score of the entered address, and a suggested address - a valid address that most closely matches the user-entered address.
```java
Address address = ... ; // Address to validate
APIRequest<AddressValidationResponse> request = API.createGetAddressValidationRequest(getContext(), address, new APIRequestCompleteListener<AddressValidationResponse>() {
            @Override
            public void onRequestComplete(APIResultType resultType, AddressValidationResponse response) {
                // response - holds address validation information
            }
});
request.executeAndWait();
```
