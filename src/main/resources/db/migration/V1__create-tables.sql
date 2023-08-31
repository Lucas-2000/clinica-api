CREATE TABLE users (
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL UNIQUE,
    role TEXT NOT NULL
);

CREATE TABLE patients (
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    cpf TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    birthdate DATE NOT NULL,
    street TEXT NOT NULL,
    number INTEGER NOT NULL,
    city TEXT NOT NULL,
    uf TEXT NOT NULL,
    cellphone TEXT NOT NULL,
    email TEXT NOT NULL,
    health_insurance TEXT NOT NULL,
    health_insurance_code TEXT NOT NULL
);

CREATE TABLE doctors (
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    cpf TEXT NOT NULL UNIQUE,
    crm TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    birthdate DATE NOT NULL,
    street TEXT NOT NULL,
    number INTEGER NOT NULL,
    city TEXT NOT NULL,
    uf TEXT NOT NULL,
    cellphone TEXT NOT NULL,
    email TEXT NOT NULL,
    specialties TEXT[] NOT NULL,
    is_active BOOL NOT NULL,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE attendants (
    id UUID PRIMARY KEY NOT NULL UNIQUE,
    cpf TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    birthdate DATE NOT NULL,
    street TEXT NOT NULL,
    number INTEGER NOT NULL,
    city TEXT NOT NULL,
    uf TEXT NOT NULL,
    cellphone TEXT NOT NULL,
    email TEXT NOT NULL,
    is_active BOOL NOT NULL,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    description TEXT NOT NULL,
    appointment_datetime DATE NOT NULL,
    appointment_initial_datetime DATE,
    appointment_finish_datetime DATE,
    patient_id UUID REFERENCES patients(id) ON DELETE SET NULL,
    doctor_id UUID REFERENCES doctors(id) ON DELETE SET NULL,
    rating INTEGER,
    review TEXT
);