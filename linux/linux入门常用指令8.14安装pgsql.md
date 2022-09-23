
### 下载安装包

```
# Install the repository RPM:
sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm

# Install PostgreSQL:
sudo yum install -y postgresql13-server

# Optionally initialize the database and enable automatic start:
sudo /usr/pgsql-13/bin/postgresql-13-setup initdb
sudo systemctl enable postgresql-13
sudo systemctl start postgresql-13

/var/lib/pgsql/13/data
```
### 前置配置 
创建用户
```
[root@localhost ]# groupadd  postgres
[root@localhost ]# useradd postgres -g postgres -m -d /home/postgres
[root@localhost ]# chown postgres:postgres /home/postgres
[root@localhost ]# passwd postgres
    postgres
```


su postgres
pgsql
postgres=# alter user postgres with password 'xxx'

### 安装es 