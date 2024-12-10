CREATE TABLE IF NOT EXISTS withdrawals
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id    BIGINT,
    user_id           BIGINT         NOT NULL,
    payment_method_id BIGINT         NOT NULL,
    amount            DECIMAL(18, 2) NOT NULL,
    created_at        DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status            VARCHAR(50)    NOT NULL
);

CREATE TABLE IF NOT EXISTS scheduled_withdrawals
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_id    BIGINT,
    user_id           BIGINT         NOT NULL,
    payment_method_id BIGINT         NOT NULL,
    amount            DECIMAL(18, 2) NOT NULL,
    execute_at        DATETIME       NOT NULL,
    created_at        DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status            VARCHAR(50)    NOT NULL
);

-- Insert dummy data

INSERT INTO withdrawals (transaction_id, user_id, payment_method_id, amount, created_at, updated_at, status)
VALUES (1001, 1, 1, 500.00, NOW(), NOW(), 'PENDING'),
       (1002, 2, 2, 1200.75, NOW(), NOW(), 'SUCCESS'),
       (1003, 3, 3, 1200, NOW(), NOW(), 'PROCESSING'),
       (1004, 4, 4, 100, NOW(), NOW(), 'INTERNAL_ERROR'),
       (1005, 5, 5, 750.50, NOW(), NOW(), 'FAILED');

INSERT INTO scheduled_withdrawals (transaction_id, user_id, payment_method_id, amount, execute_at, created_at,
                                   updated_at, status)
VALUES (1001, 1, 1, 500.00, '2024-12-15 10:00:00', NOW(), NOW(), 'PENDING'),
       (1002, 2, 2, 1200.75, '2024-12-20 12:00:00', NOW(), NOW(), 'SUCCESS'),
       (1003, 3, 3, 1200, '2024-12-20 12:00:00', NOW(), NOW(), 'PROCESSING'),
       (1004, 4, 4, 100, '2024-12-20 12:00:00', NOW(), NOW(), 'INTERNAL_ERROR'),
       (1005, 5, 5, 750.50, '2024-12-25 08:30:00', NOW(), NOW(), 'FAILED');