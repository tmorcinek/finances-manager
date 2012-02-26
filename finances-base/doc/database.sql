create table payments (
	bookingDate TIMESTAMP not null, 
	realizingDate TIMESTAMP not null, 
	amount NUMERIC(10,2), 
	currency VARCHAR(5), 
	account TEXT, 
	bankName TEXT, 
	recepient TEXT, 
	title TEXT, 
	transactionNumber VARCHAR(11) primary key,
	additionalInformations TEXT	);