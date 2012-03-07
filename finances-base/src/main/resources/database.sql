create table if not exists payments (
	paymentsId INTEGER primary key,
	bookingDate TIMESTAMP not null, 
	realizingDate TIMESTAMP not null, 
	amount NUMERIC(10,2), 
	currency VARCHAR(5), 
	account TEXT, 
	bankName TEXT, 
	recipient TEXT, 
	title TEXT, 
	transactionNumber VARCHAR(11) unique,
	additionalInformations TEXT	
);
create table if not exists categories (
	categoryId INTEGER primary key,
	categoryName TEXT unique,
	parentId references categories(categoryId)
);
create table if not exists paymentsCategories (
	paymentsId references payments(paymentsId),
	categoryId references categories(categoryId),
	primary key (paymentsId, categoryId)
);
drop table if exists payments;
drop table if exists categories;
drop table if exists paymentsCategories;