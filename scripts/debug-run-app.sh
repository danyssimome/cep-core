#!/bin/bash

## Дебаг

# Чистим
#mvn clean
# Собираем вместе с jar'ником
#mvn install

# Удаляем все образы и контейнеры
# shellcheck disable=SC2046
docker stop $(docker ps -aq)
# shellcheck disable=SC2046
docker rm $(docker ps -aq)
# shellcheck disable=SC2046
docker volume prune -f
# shellcheck disable=SC2046
# docker rmi $(docker images -aq)

# Поднимаем компоуз, а вместе с ним и докер файл со слоями окружения
# docker-compose -f docker-compose.yml up -d
