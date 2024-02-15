CREATE TABLE public.individual (
	individual_id INTEGER DEFAULT nextval('account_id_sequence'),
	"name" varchar NOT NULL,
	last_name varchar NULL,
	day_of_birth date NOT NULL,
	created_date timestamp NOT NULL,
	updated_date timestamp NULL,
	address varchar NOT NULL,
	city varchar NOT NULL,
	CONSTRAINT individual_pk PRIMARY KEY (individual_id),
	CONSTRAINT day_of_birth_check CHECK (day_of_birth > CURRENT_DATE),
	CONSTRAINT created_date_check CHECK (created_date >= CURRENT_TIMESTAMP),
	CONSTRAINT updated_date_check CHECK (updated_date >= CURRENT_TIMESTAMP)
);

CREATE TABLE public.card (
	id uuid NOT NULL,
	card_number int4 NOT NULL,
	account_number varchar NOT NULL,
	created_date timestamp NOT NULL,
	updated_date timestamp NOT NULL,
	status varchar NOT NULL,
	expiry_date date NOT NULL,
	individual_id int4 NULL,
	CONSTRAINT card_pk PRIMARY KEY (id),
	CONSTRAINT card_number_check CHECK (card_number BETWEEN 0 and 999999999999),
	CONSTRAINT created_date_check CHECK (created_date >= CURRENT_TIMESTAMP),
	CONSTRAINT updated_date_check CHECK (updated_date >= CURRENT_TIMESTAMP),
	CONSTRAINT status_check CHECK (status = 'ACTIVE' OR status = 'UNREACHABLE'),
	CONSTRAINT account_number_check CHECK (account_number ~* '^[A-Z]{2}\d{2}[A-Za-z\d]{1,30}$'),
	CONSTRAINT card_fk FOREIGN KEY (individual_id) REFERENCES public.individual(individual_id)
);