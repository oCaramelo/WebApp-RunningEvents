--
-- PostgreSQL database dump
--

-- Dumped from database version 12.9 (Ubuntu 12.9-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.9 (Ubuntu 12.9-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: events; Type: TABLE; Schema: public; Owner: caramelo
--

CREATE TABLE public.events (
    id integer NOT NULL,
    nome character varying(100),
    local character varying(100),
    descricao character varying(200),
    data timestamp without time zone,
    valor real
);


ALTER TABLE public.events OWNER TO caramelo;

--
-- Name: participants; Type: TABLE; Schema: public; Owner: caramelo
--

CREATE TABLE public.participants (
    idevent integer NOT NULL,
    nameevent character varying(100),
    username character varying(100),
    nome character varying(100),
    dorsal integer NOT NULL,
    genero character varying(100),
    escalao character varying(100),
    posgeral integer,
    posinter integer,
    lastseen character varying(100),
    tempo character varying(100),
    milliseconds bigint,
    pago character varying(100),
    entidade character varying(100),
    referencia character varying(100),
    valor real
);


ALTER TABLE public.participants OWNER TO caramelo;

--
-- Name: userauth; Type: TABLE; Schema: public; Owner: caramelo
--

CREATE TABLE public.userauth (
    username character varying(100) NOT NULL,
    password character varying(200),
    email character varying(100),
    role character varying(100)
);


ALTER TABLE public.userauth OWNER TO caramelo;

--
-- Data for Name: events; Type: TABLE DATA; Schema: public; Owner: caramelo
--

COPY public.events (id, nome, local, descricao, data, valor) FROM stdin;
1	Trail volta às origens	Salvaterra de Magos	Já correu à frente de um toiro? Venha experimentar!	2022-01-28 08:00:00	2.25
2	Trail volta às origens 2	Salvaterra de Magos	Se sobreviveu à primeira edição inscreva-se na segunda!	2023-01-28 08:00:00	2.25
3	20km Évora	Évora	Venha correr à volta das muralhas!	2022-01-30 09:00:00	4.99
4	50km Évora	Évora	Venha correr à volta das muralhas!	2022-02-10 09:30:00	3.5
5	20km em Almeirim	Almeirim	Depois de correr ainda pode apreciar a gastronomia.	2021-12-25 08:30:00	1.25
6	20km Beja	Beja	Corrida à volta do castelo de Beja!	2022-05-17 09:45:00	4.25
\.


--
-- Data for Name: participants; Type: TABLE DATA; Schema: public; Owner: caramelo
--

COPY public.participants (idevent, nameevent, username, nome, dorsal, genero, escalao, posgeral, posinter, lastseen, tempo, milliseconds, pago, entidade, referencia, valor) FROM stdin;
1	Trail volta às origens	Luis	Rogério	6	m	seniores	2	2	finish	01:05:00	3900000	Não	21067	900006417	2.25
1	Trail volta às origens	Luis	Jorge	3	m	seniores	4	3	finish	01:30:00	5400000	Não	21067	900006414	2.25
1	Trail volta às origens	Luis	Adolfo	7	m	veteranos60+	0	0	start	00:15:00	900000	Sim	21067	900006418	2.25
1	Trail volta às origens	Luis	César	9	m	veteranos35	0	0	-----	00:00:00	0	Não	21067	900006420	2.25
1	Trail volta às origens	Luis	Roberto	8	m	veteranos40	5	4	finish	01:45:00	6300000	Não	21067	900006419	2.25
3	20km Évora	Diogo	Ana	7	f	juniores	0	0	-----	00:00:00	0	Sim	21067	900006427	4.99
3	20km Évora	Diogo	Diogo	1	m	seniores	1	1	finish	00:50:00	3000000	Sim	21067	900006421	4.99
3	20km Évora	Diogo	Bernardo	2	m	seniores	2	2	finish	01:00:00	3600000	Não	21067	900006422	4.99
1	Trail volta às origens	Luis	Luis	1	m	seniores	1	1	finish	01:00:00	3600000	Sim	21067	900006412	2.25
3	20km Évora	Diogo	Conceição	4	f	veteranos60+	0	0	P3	00:50:00	3000000	Sim	21067	900006424	4.99
3	20km Évora	Diogo	Constança	5	f	veteranos50	0	0	P3	00:45:00	2700000	Não	21067	900006425	4.99
1	Trail volta às origens	Luis	Maria	4	f	seniores	0	0	P3	00:50:00	3000000	Não	21067	900006415	2.25
1	Trail volta às origens	Luis	Celeste	5	f	veteranos50	0	0	P2	00:40:00	2400000	Sim	21067	900006416	2.25
1	Trail volta às origens	Luis	Sofia	2	f	seniores	3	1	finish	01:10:25	4225000	Sim	21067	900006413	2.25
3	20km Évora	Diogo	Vitor	6	m	veteranos60+	3	3	finish	01:00:50	3650000	Não	21067	900006426	4.99
3	20km Évora	Diogo	José	3	m	veteranos40	4	4	finish	01:20:00	4800000	Não	21067	900006423	4.99
\.


--
-- Data for Name: userauth; Type: TABLE DATA; Schema: public; Owner: caramelo
--

COPY public.userauth (username, password, email, role) FROM stdin;
admin	$2a$10$yFKp8EAvesgPly9gmoXrguvnQfbR8ZudG/oXPc2eUyVP1D5P3LQpe	admin@hotmail.com	ROLE_staff
Luis	$2a$10$qhdhlIKzKbROQtQb4E0nQOUdR2ZJk.VUNuHznZGoLadJW0FoyTXvC	l45876@alunos.uevora.pt	ROLE_atleta
Diogo	$2a$10$JuoCynSojU0pqzxSNTeBpOzAAwyo8ZlFBcrcxauIwzN8y2ChI.V/i	l40968@alunos.uevora.pt	ROLE_atleta
\.


--
-- Name: events events_pkey; Type: CONSTRAINT; Schema: public; Owner: caramelo
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pkey PRIMARY KEY (id);


--
-- Name: participants participants_pkey; Type: CONSTRAINT; Schema: public; Owner: caramelo
--

ALTER TABLE ONLY public.participants
    ADD CONSTRAINT participants_pkey PRIMARY KEY (idevent, dorsal);


--
-- Name: userauth userauth_pkey; Type: CONSTRAINT; Schema: public; Owner: caramelo
--

ALTER TABLE ONLY public.userauth
    ADD CONSTRAINT userauth_pkey PRIMARY KEY (username);


--
-- PostgreSQL database dump complete
--

