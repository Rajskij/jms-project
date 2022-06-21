# JMS
JMS is a Java project for learning JMS(Java Messaging Service) & ActiveMQ
### Tools
Before proceeding, install a [JDK](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
(must be Java 8 or later) and [Apache Maven](https://maven.apache.org/).

Also, you need to download [ActiveMQ](https://activemq.apache.org/download.html)

### Run application
- Go to your ActiveMQ root folder
- Open [Git Bash](https://git-scm.com/downloads) from /bin folder
- run following command to start ActiveMQ server
```bash
./activemq start
```
- Go to http://localhost:8161/admin/queues.jsp to see processing messages

### Log's
Logs are writing to ``/logs/application-info.log`` file