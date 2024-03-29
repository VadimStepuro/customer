CREATE SEQUENCE legal_entity_id_sequence
    INCREMENT BY 1
    MINVALUE 1000000
    MAXVALUE 9999999999
    START WITH 1000000;

CREATE TABLE public.legal_entity (
	legal_entity_id INTEGER DEFAULT nextval('legal_entity_id_sequence'),
	"name" varchar NOT NULL,
	created_date timestamp NOT NULL,
	updated_date timestamp NULL,
	address varchar NOT NULL,
	city varchar NOT NULL,
	inn varchar NOT NULL,

	CONSTRAINT legal_entity_pk PRIMARY KEY (legal_entity_id),
	CONSTRAINT created_date_check CHECK (created_date <= CURRENT_TIMESTAMP),
	CONSTRAINT updated_date_check CHECK (updated_date <= CURRENT_TIMESTAMP),
	CONSTRAINT inn_check CHECK (inn ~* '^[0-9]{10,12}$')
);

CREATE TABLE public.account (
	id uuid DEFAULT gen_random_uuid(),
	account_number varchar NOT NULL,
	created_date timestamp NOT NULL,
	updated_date timestamp NULL,
	status varchar NOT NULL,
    balance numeric NOT NULL,
	legal_entity_id int4 NULL,

	CONSTRAINT account_pk PRIMARY KEY (id),
	CONSTRAINT created_date_check CHECK (created_date <= CURRENT_TIMESTAMP),
	CONSTRAINT updated_date_check CHECK (updated_date <= CURRENT_TIMESTAMP),
	CONSTRAINT status_check CHECK (status = 'ACTIVE' OR status = 'CLOSED'),
	CONSTRAINT account_number_check CHECK (account_number ~* '^[A-Z]{2}\d{2}[A-Za-z\d]{1,30}$'),
	CONSTRAINT balance_check CHECK (balance > 0),
	CONSTRAINT account_number_unique UNIQUE (account_number),
	CONSTRAINT account_fk FOREIGN KEY (legal_entity_id) REFERENCES public.legal_entity(legal_entity_id)
);