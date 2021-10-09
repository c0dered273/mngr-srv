--liquibase formatted sql

--changeset c0dered:1
--comment: initial schema

--comment: hibernate id generation sequences
CREATE SEQUENCE public.hibernate_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.parts_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE SEQUENCE public.projects_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

--comment: tables
CREATE TABLE public.user_props
(
    id              bigint NOT NULL,
    username        varchar(36),
    last_project_id bigint,
    CONSTRAINT user_props_pkey PRIMARY KEY (id)
);


CREATE TABLE public.currencies
(
    id   bigint       NOT NULL,
    code varchar(255) NOT NULL,
    CONSTRAINT currencies_pkey PRIMARY KEY (id),
    CONSTRAINT currencies_code_uk UNIQUE (code)
);

CREATE TABLE public.sellers
(
    id   bigint       NOT NULL,
    name varchar(255) NOT NULL,
    CONSTRAINT sellers_pkey PRIMARY KEY (id),
    CONSTRAINT sellers_name_uk UNIQUE (name)
);

CREATE TABLE public.customers
(
    id   bigint       NOT NULL,
    name varchar(255) NOT NULL,
    CONSTRAINT customers_pkey PRIMARY KEY (id),
    CONSTRAINT customers_name_uk UNIQUE (name)
);

CREATE TABLE public.import_props
(
    id              bigint       NOT NULL,
    additional_desc varchar(255),
    currency        varchar(255),
    description     varchar(255),
    list_price      varchar(255) NOT NULL,
    name            varchar(255),
    part_no         varchar(255) NOT NULL,
    price_group     varchar(255),
    price_unit      varchar(255),
    tab_name        varchar(255) NOT NULL,
    unit            varchar(255),
    weight          varchar(255),
    CONSTRAINT import_props_pkey PRIMARY KEY (id)
);

CREATE TABLE public.vendors
(
    id              bigint       NOT NULL,
    name            varchar(255) NOT NULL,
    import_props_id bigint       NOT NULL,
    CONSTRAINT vendors_pkey PRIMARY KEY (id),
    CONSTRAINT vendors_import_props_id_uk UNIQUE (import_props_id),
    CONSTRAINT vendors_name_uk UNIQUE (name),
    CONSTRAINT vendors_import_props_id_fk FOREIGN KEY (import_props_id)
        REFERENCES public.import_props (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.price_groups
(
    id        bigint       NOT NULL,
    name      varchar(255) NOT NULL,
    vendor_id bigint       NOT NULL,
    CONSTRAINT price_groups_pkey PRIMARY KEY (id),
    CONSTRAINT price_groups_vendor_id_fk FOREIGN KEY (vendor_id)
        REFERENCES public.vendors (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.discounts
(
    id             bigint           NOT NULL,
    discount_value double precision NOT NULL,
    seller_id      bigint,
    CONSTRAINT discounts_pkey PRIMARY KEY (id),
    CONSTRAINT discounts_seller_id_fk FOREIGN KEY (seller_id)
        REFERENCES public.sellers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.pricegroups_sellers_discount
(
    price_group_id bigint NOT NULL,
    discount_id    bigint NOT NULL,
    seller_id      bigint NOT NULL,
    CONSTRAINT pricegroups_sellers_discount_pkey PRIMARY KEY (price_group_id, seller_id),
    CONSTRAINT pricegroups_sellers_discount_discount_id_uk UNIQUE (discount_id),
    CONSTRAINT pricegroups_sellers_discount_seller_id_fk FOREIGN KEY (seller_id)
        REFERENCES public.sellers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pricegroups_sellers_discount_price_group_id_fk FOREIGN KEY (price_group_id)
        REFERENCES public.price_groups (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT pricegroups_sellers_discount_discount_id_fk FOREIGN KEY (discount_id)
        REFERENCES public.discounts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE public.import_props
    ADD COLUMN default_currency_id bigint;
ALTER TABLE public.import_props
    ADD CONSTRAINT import_props_default_currency_id_fk FOREIGN KEY (default_currency_id)
        REFERENCES public.currencies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

ALTER TABLE public.import_props
    ADD COLUMN default_price_group_id bigint;
ALTER TABLE public.import_props
    ADD CONSTRAINT import_props_default_price_group_id_fk FOREIGN KEY (default_price_group_id)
        REFERENCES public.price_groups (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION;

CREATE TABLE IF NOT EXISTS public.parts
(
    id              bigint                      NOT NULL,
    additional_desc varchar(255),
    description     varchar(255),
    expired         timestamp without time zone,
    last_modified   timestamp without time zone NOT NULL,
    list_price      double precision            NOT NULL,
    name            varchar(255),
    pack_quantity   integer,
    part_no         varchar(255)                NOT NULL,
    price_unit      integer,
    unit            varchar(255),
    weight          double precision,
    currency_id     bigint                      NOT NULL,
    price_group_id  bigint                      NOT NULL,
    vendor_id       bigint                      NOT NULL,
    CONSTRAINT parts_pkey PRIMARY KEY (id),
    CONSTRAINT parts_currency_id_fk FOREIGN KEY (currency_id)
        REFERENCES public.currencies (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT parts_vendor_id_fk FOREIGN KEY (vendor_id)
        REFERENCES public.vendors (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT parts_price_group_id_fk FOREIGN KEY (price_group_id)
        REFERENCES public.price_groups (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);
CREATE INDEX parts_part_no_idx ON parts (part_no);

CREATE TABLE public.projects
(
    id            bigint                      NOT NULL,
    created       timestamp without time zone NOT NULL,
    last_modified timestamp without time zone NOT NULL,
    name          varchar(255)                NOT NULL,
    customer_id   bigint                      NOT NULL,
    username      varchar(36)                 NOT NULL,
    CONSTRAINT projects_pkey PRIMARY KEY (id),
    CONSTRAINT projects_customer_id_fk FOREIGN KEY (customer_id)
        REFERENCES public.customers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.locations
(
    id         bigint NOT NULL,
    name       varchar(255),
    project_id bigint,
    CONSTRAINT locations_pkey PRIMARY KEY (id),
    CONSTRAINT locations_project_id_fk FOREIGN KEY (project_id)
        REFERENCES public.projects (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.part_quantity
(
    id       bigint           NOT NULL,
    quantity double precision NOT NULL,
    part_id  bigint,
    CONSTRAINT part_quantity_pkey PRIMARY KEY (id),
    CONSTRAINT part_quantity_part_id_fk FOREIGN KEY (part_id)
        REFERENCES public.parts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

CREATE TABLE public.locations_parts_quantity
(
    location_id      bigint NOT NULL,
    part_quantity_id bigint NOT NULL,
    part_id          bigint NOT NULL,
    CONSTRAINT locations_parts_quantity_pkey PRIMARY KEY (location_id, part_id),
    CONSTRAINT locations_parts_quantity_part_quantity_id_uk UNIQUE (part_quantity_id),
    CONSTRAINT locations_parts_quantity_part_quantity_id_fk FOREIGN KEY (part_quantity_id)
        REFERENCES public.part_quantity (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT locations_parts_quantity_location_id_fk FOREIGN KEY (location_id)
        REFERENCES public.locations (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT locations_parts_quantity_part_id_fk FOREIGN KEY (part_id)
        REFERENCES public.parts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--changeset c0dered:2
--comment: initial data
INSERT INTO public.currencies
VALUES (nextval('public.hibernate_sequence'), 'EUR');
INSERT INTO public.currencies
VALUES (nextval('public.hibernate_sequence'), 'USD');
INSERT INTO public.currencies
VALUES (nextval('public.hibernate_sequence'), 'RUB');


