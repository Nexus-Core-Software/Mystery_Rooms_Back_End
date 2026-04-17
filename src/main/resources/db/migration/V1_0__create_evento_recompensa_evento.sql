CREATE TABLE IF NOT EXISTS evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha_inicio DATETIME NOT NULL,
    fecha_fin DATETIME NOT NULL,
    activo BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS recompensa_evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    evento_id BIGINT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    puntos INT,
    CONSTRAINT fk_recompensa_evento_evento
        FOREIGN KEY (evento_id) REFERENCES evento(id)
);

CREATE TABLE IF NOT EXISTS recompensa_evento_jugador (
    recompensa_evento_id BIGINT NOT NULL,
    jugador_id BIGINT NOT NULL,
    PRIMARY KEY (recompensa_evento_id, jugador_id),
    CONSTRAINT fk_recompensa_evento_jugador_recompensa
        FOREIGN KEY (recompensa_evento_id) REFERENCES recompensa_evento(id),
    CONSTRAINT fk_recompensa_evento_jugador_jugador
        FOREIGN KEY (jugador_id) REFERENCES player(id)
);
