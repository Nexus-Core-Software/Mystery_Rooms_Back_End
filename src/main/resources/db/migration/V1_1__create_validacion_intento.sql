CREATE TABLE IF NOT EXISTS validacion_intento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    player_id BIGINT NOT NULL,
    actividad_tipo VARCHAR(120) NOT NULL,
    actividad_id BIGINT NOT NULL,
    respuesta_dada TEXT NOT NULL,
    respuesta_correcta TEXT,
    es_correcta BOOLEAN NOT NULL,
    puntaje_obtenido INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_validacion_intento_player
        FOREIGN KEY (player_id) REFERENCES player(id)
);
