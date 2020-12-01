# 基于镜像
FROM java:8

# 将本地文件夹挂载到当前容器
VOLUME /tmp

# 复制文件到容器,也可以直接写成
ADD video-zipkin-server.jar zipkin-server.jar
RUN bash -c 'touch /zipkin-server.jar'

# 声明需要暴露的端口
EXPOSE 8003

# 配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/zipkin-server.jar"]