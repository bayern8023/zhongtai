# 基于镜像
FROM java:8

# 将本地文件夹挂载到当前容器
VOLUME /tmp

# 复制文件到容器,也可以直接写成
ADD video-eureka-server2.jar eureka-server2.jar
RUN bash -c 'touch /eureka-server2.jar'

# 声明需要暴露的端口
EXPOSE 8762

# 配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/eureka-server2.jar"]