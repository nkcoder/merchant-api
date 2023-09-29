## Order#orderById

```graphql
query {
    orderById(id: "1111") {
        id,
        totalAmount,
        shippingAddressId,
        datePlaced,
        products {
            id,
            sellerId,
            productName,
            description,
            price,
            quantity
        }
    }
}
```

## Order#ordersByUser

```graphql
query {
	ordersByUser(userId: "1", pageNumber: 0, pageSize: 10) {
		id,
    totalAmount,
    shippingAddressId,
		datePlaced,
		products {
			id,
			sellerId,
			productName,
			description,
			price,
			quantity
		}
	}
}
```

## Order#createOrder

```graphql
mutation {
	createOrder(
		createOrderInput: {
			buyerId: 1
			items: [{ productId: 1, quantity: 3 }, { productId: 2, quantity: 4 }]
			totalAmount: 550.8
			shippingAddressId: 1
		}
	) {
		id
		totalAmount
		shippingAddressId
		datePlaced
		products {
			id
			sellerId
			productName
			description
			price
			quantity
		}
	}
}
```