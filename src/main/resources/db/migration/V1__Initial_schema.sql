CREATE TABLE Users (
    UserID SERIAL PRIMARY KEY,
    Username VARCHAR(255) NOT NULL,
    Email VARCHAR(255) UNIQUE NOT NULL,
    Password VARCHAR(255) NOT NULL,
    UserType VARCHAR(50) NOT NULL,
    DateRegistered TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Categories (
    CategoryID SERIAL PRIMARY KEY,
    CategoryName VARCHAR(255) NOT NULL,
    Description TEXT
);

CREATE TABLE Products (
    ProductID SERIAL PRIMARY KEY,
    SellerID INTEGER REFERENCES Users(UserID),
    CategoryID INTEGER REFERENCES Categories(CategoryID),
    ProductName VARCHAR(255) NOT NULL,
    Description TEXT,
    Price DECIMAL(10,2) NOT NULL,
    StockQuantity INTEGER NOT NULL,
    ImageURL VARCHAR(500)
);

CREATE TABLE Addresses (
    AddressID SERIAL PRIMARY KEY,
    UserID INTEGER REFERENCES Users(UserID),
    Street VARCHAR(255) NOT NULL,
    City VARCHAR(100) NOT NULL,
    State VARCHAR(100),
    ZipCode VARCHAR(50),
    Country VARCHAR(100) NOT NULL
);

CREATE TABLE Payments (
    PaymentID SERIAL PRIMARY KEY,
    Amount DECIMAL(10,2) NOT NULL,
    PaymentMethod VARCHAR(255) NOT NULL,
    DatePaid TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PaymentStatus VARCHAR(50) NOT NULL
);

CREATE TABLE Orders (
    OrderID SERIAL PRIMARY KEY,
    BuyerID INTEGER REFERENCES Users(UserID),
    DatePlaced TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    TotalAmount DECIMAL(10,2) NOT NULL,
    ShippingAddressID INTEGER REFERENCES Addresses(AddressID),
    BillingAddressID INTEGER REFERENCES Addresses(AddressID),
    PaymentID INTEGER REFERENCES Payments(PaymentID)
);

CREATE TABLE OrderDetails (
    OrderDetailID SERIAL PRIMARY KEY,
    OrderID INTEGER REFERENCES Orders(OrderID),
    ProductID INTEGER REFERENCES Products(ProductID),
    Quantity INTEGER NOT NULL,
    SubTotal DECIMAL(10,2) NOT NULL
);

CREATE TABLE Reviews (
    ReviewID SERIAL PRIMARY KEY,
    ProductID INTEGER REFERENCES Products(ProductID),
    UserID INTEGER REFERENCES Users(UserID),
    Rating INTEGER CHECK (Rating >= 1 AND Rating <= 5),
    Comment TEXT,
    DatePosted TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
