CREATE SEQUENCE individual_id_sequence
    INCREMENT BY 1
    MINVALUE 1000000
    MAXVALUE 9999999999
    START WITH 1000000;

CREATE TABLE public.individual (
	individual_id INTEGER DEFAULT nextval('individual_id_sequence'),
	"name" varchar NOT NULL,
	last_name varchar NULL,
	day_of_birth date NOT NULL,
	created_date timestamp NOT NULL,
	updated_date timestamp NULL,
	address varchar NOT NULL,
	city varchar NOT NULL,
	CONSTRAINT individual_pk PRIMARY KEY (individual_id),
	CONSTRAINT day_of_birth_check CHECK (day_of_birth < CURRENT_DATE),
	CONSTRAINT created_date_check CHECK (created_date <= CURRENT_TIMESTAMP),
	CONSTRAINT updated_date_check CHECK (updated_date <= CURRENT_TIMESTAMP)
);

CREATE TABLE public.card (
	id uuid DEFAULT gen_random_uuid(),
	card_number varchar NOT NULL,
	account_number varchar NOT NULL,
	created_date timestamp NOT NULL,
	updated_date timestamp NULL,
	status varchar NOT NULL,
	expiry_date date NOT NULL,
    balance numeric NOT NULL,
	individual_id INTEGER NULL,

	CONSTRAINT card_pk PRIMARY KEY (id),
	CONSTRAINT card_number_check CHECK (card_number ~* '^(?:4[0-9]{12}(?:[0-9]{3})? | (?:5[1-5][0-9]{2} | 222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12} | 3[47][0-9]{13} | 3(?:0[0-5]|[68][0-9])[0-9]{11} | 6(?:011|5[0-9]{2})[0-9]{12} |  (?:2131|1800|35\d{3})\d{11})$'),
	CONSTRAINT created_date_check CHECK (created_date <= CURRENT_TIMESTAMP),
	CONSTRAINT updated_date_check CHECK (updated_date <= CURRENT_TIMESTAMP),
	CONSTRAINT status_check CHECK (status = 'ACTIVE' OR status = 'UNREACHABLE'),
	CONSTRAINT account_number_check CHECK (account_number ~* '^[A-Z]{2}\d{2}[A-Za-z\d]{1,30}$'),
	CONSTRAINT balance_check CHECK (balance > 0),
	CONSTRAINT card_fk FOREIGN KEY (individual_id) REFERENCES public.individual(individual_id)
);