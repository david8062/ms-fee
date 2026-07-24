-- ================================================================
-- Retiro del cobro por horas.
--
-- El registro de tiempo nunca llego a afectar el dinero: el saldo y el
-- estado del honorario siempre se calcularon contra agreed_amount, de modo
-- que convertir un honorario HOURLY a FIXED no cambia ni un peso de lo ya
-- facturado ni de lo ya pagado. Solo deja de ofrecerse como modalidad.
--
-- Esta migracion DEBE correr antes de que el nuevo codigo lea la tabla:
-- fee_type y pricing_strategy son VARCHAR (no enums de Postgres), asi que
-- Postgres acepta 'HOURLY' sin quejarse, pero Hibernate lanza
-- IllegalArgumentException al mapear un valor que ya no existe en el enum
-- de Java y tumba cualquier consulta que toque esa fila. Flyway corre en el
-- arranque, antes de atender trafico, por lo que el orden queda garantizado.
-- ================================================================

UPDATE fees
   SET fee_type = 'FIXED'
 WHERE fee_type = 'HOURLY';

UPDATE services
   SET pricing_strategy = 'CUSTOM'
 WHERE pricing_strategy = 'HOURLY';

-- La tabla time_entries NO se elimina a proposito. Las horas registradas son
-- el respaldo de cuentas de cobro que ya pudieron entregarse a un cliente, y
-- borrarlas destruiria esa evidencia. Queda huerfana de codigo (ninguna
-- entidad la mapea) y puede eliminarse mas adelante en una migracion aparte,
-- como decision consciente de retencion de datos.
