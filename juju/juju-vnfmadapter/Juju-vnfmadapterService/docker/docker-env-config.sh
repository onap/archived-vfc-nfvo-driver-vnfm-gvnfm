#!/bin/bash

add_user(){

	useradd  onap
}

set_up_mysql(){

	sed -i 's/enabled=1/enabled=0/' /etc/yum/pluginconf.d/fastestmirror.conf
	sed -i 's|#baseurl=http://mirror.centos.org/centos|baseurl=http://mirrors.ocf.berkeley.edu/centos|' /etc/yum.repos.d/*.repo
	yum update -y
	
	yum install -y wget unzip socat java-1.8.0-openjdk-headless
	sed -i 's|#networkaddress.cache.ttl=-1|networkaddress.cache.ttl=10|' /usr/lib/jvm/jre/lib/security/java.security
	
	# Set up mysql
	wget -q http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm && \
		rpm -ivh mysql-community-release-el7-5.noarch.rpm && \
		rm -f mysql-community-release-el7-5.noarch.rpm
	yum -y update
	yum -y install mysql-server
	mysql_install_db --user=onap --datadir=/var/lib/mysql

        chown onap:onap -R /var/run/mysqld
        chmod g+s /var/run/mysqld
        setfacl -d --set u:onap:rwx /var/run/mysqld
        chown onap:onap /var/log/mysqld.log
        chmod g+s /var/log/mysqld.log
        chown onap:onap -R /var/lib/mysql-files
        chmod g+s /var/lib/mysql-files
        setfacl -d --set u:onap:rwx /var/lib/mysql-files
        chown onap:onap /etc/my.cnf
}

set_up_tomcat(){

	# Set up tomcat
	wget -q https://archive.apache.org/dist/tomcat/tomcat-8/v8.5.30/bin/apache-tomcat-8.5.30.tar.gz && \
		tar --strip-components=1 -xf apache-tomcat-8.5.30.tar.gz && \
		rm -f apache-tomcat-8.5.30.tar.gz && \
		rm -rf webapps && \
		mkdir -p webapps/ROOT
	echo 'export CATALINA_OPTS="$CATALINA_OPTS -Xms64m -Xmx256m -XX:MaxPermSize=64m"' > /service/bin/setenv.sh
	
	# Set up microservice
	wget -q -O vfc-gvnfm-jujudriver.zip "https://nexus.onap.org/service/local/artifact/maven/redirect?r=snapshots&g=org.onap.vfc.nfvo.driver.vnfm.gvnfm&a=juju-vnfmadapterservice-deployment&v=LATEST&e=zip" && \
		unzip -q -o -B vfc-gvnfm-jujudriver.zip && \
		rm -f vfc-gvnfm-jujudriver.zip
	# Set permissions
	find . -type d -exec chmod o-w {} \;
	find . -name "*.sh" -exec chmod +x {} \;

	chown onap:onap -R /service
	chmod g+s /service
	setfacl -d --set u:onap:rwx /service
}

clean_sf_cache(){
															
	yum clean all
}

add_user
set_up_mysql
set_up_tomcat
clean_sf_cache
