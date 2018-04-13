http://www.cactifans.org/zabbix/1616.html
  docker run \
  --name=dockbix-agent-xxl \
  --net=host \
  --privileged \
  -v /:/rootfs \
  --restart unless-stopped \
  -e "ZA_Server=192.168.54.120" \
  -e "ZA_ServerActive=192.168.54.120" \
  -d hub.c.163.com/canghai809/dockbix-agent-xxl-limited:latest