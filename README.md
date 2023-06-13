# PreCrime_System
ITMO, МПИ, курсовая работа - PreCrime System

## Установка POSTGRE:

### Step 1
На Linux

```shell
$ sudo apt update
$ sudo apt install postgresql
```

### Step 2
После завершения установки вы можете убедиться, что служба PostgreSQL активна. Для чего в командной строке наберите:

```shell
$ sudo systemctl is-active postgresql
```
включена ли служба:

```shell
$ sudo systemctl is-enabled postgresql
```
статус:

```shell
$ sudo systemctl status postgresql
```

После чего, убедитесь, что PostgreSQL-сервер готов принимать подключения от клиентов:

```shell
$ sudo pg_isready
```

### Step 3
Создание базы данных в PostgreSQL:
```shell
$ sudo su - postgres
```
Подключившись, выполните команду psql:
```shell
$ psql
```
Если вы видите приглашение ко вводу команд postgres=#, значит вы находитесь в оболочке СУБД PostgreSQL. 
И значит, можно приступать к созданию базы данных. 
Первая команда добавит в PostgreSQL пользователя bob (на своём сервере вы можете использовать свои имена пользователей и баз данных):
```postgres
# CREATE USER bob WITH PASSWORD 'P@$$w0rd';
```
Теперь, назначьте своему пользователю все права сразу на вашу базу данных. В нашем примере это будет выглядеть так:
```postgres
# GRANT ALL PRIVILEGES ON DATABASE bobdb to bob;
```
Чтобы покинуть оболочку, наберите:
```postgres
# \q
```
И теперь, можно отключить от системы пользователя postgres:
```shell
$ exit
```

### Step 4
Установите в VS Code PostgreSQL Management Tool:
![image](https://github.com/Wittelsbach-Konig/PreCrime_System/assets/59288516/dad5e821-67b7-4ce2-95a9-0cd890fec932)
Далее в описании расширения подключитесь к вашей БД.

## Запуск веб-приложения

Для запуска достаточно ввести в каталоге приложения следующее:
```shell
$ ./mvnw spring-boot:run
```
Предварительно разрешите запускаться скрипту:
```shell
$ chmod +x ./mvnw
```


