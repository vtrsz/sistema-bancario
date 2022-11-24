CREATE TABLE IF NOT EXISTS legal_person (
	account_number INTEGER NOT NULL,
	agency INTEGER NOT NULL,
	phone_number VARCHAR(15) NOT NULL,
	amount NUMERIC(11, 2) NOT NULL,
	over_draft NUMERIC(11, 2) NOT NULL,
	cnpj VARCHAR(14) NOT NULL,
	social_reason VARCHAR(115) NOT NULL,
	fantasy_name VARCHAR(55) NOT NULL
)

CREATE TABLE IF NOT EXISTS natural_person (
	account_number INTEGER NOT NULL,
	agency INTEGER NOT NULL,
	phone_number VARCHAR(15) NOT NULL,
	amount NUMERIC(11, 2) NOT NULL,
	over_draft NUMERIC(11, 2) NOT NULL,
	cpf VARCHAR(11) NOT NULL,
	name VARCHAR(100) NOT NULL,
	age INTEGER NOT NULL
)