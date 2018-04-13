

https://hub.docker.com/r/monitoringartist/dockbix-agent-xxl-limited/
docker pull monitoringartist/dockbix-agent-xxl-limited


docker run \
  --name=dockbix-agent-xxl \
  --net=host \
  --privileged \
  -v /:/rootfs \
  -v /var/run:/var/run \
  --restart unless-stopped \
  -e "ZA_Server=192.168.54.120" \
  -e "ZA_ServerActive=192.168.54.120" \
  -d docker.io/monitoringartist/dockbix-agent-xxl-limited

  7d1998bca28af1783e6d73639a70b04b433f816ed1a1b4359812c2dd4fb28c69


  docker run \
  --name=zabbix-agent-xxl \
  -h $(hostname) \
  -p 10050:10050 \
  -v /:/rootfs \
  -v /var/run:/var/run \
  -e "ZA_Server=192.168.54.120" \
  -d monitoringartist/zabbix-agent-xxl-limited:latest


  36bae35b0bfc
  e04f45cacb98