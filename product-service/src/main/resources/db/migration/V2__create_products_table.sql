DROP TABLE IF EXISTS products;
CREATE TABLE `products` (
  `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `store_id`    BIGINT(20)   NOT NULL,
  `name`        VARCHAR(100) NOT NULL,
  `description` VARCHAR(200),
  `state`       VARCHAR(20)  NOT NULL DEFAULT 'SHELVE',
  `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;