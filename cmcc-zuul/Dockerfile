# 基于镜像
FROM java:8

# 将本地文件夹挂载到当前容器
VOLUME /tmp

# 复制文件到容器,也可以直接写成
ADD video-zuul-server.jar zuul-server.jar
RUN bash -c 'touch /zuul-server.jar'

# 声明需要暴露的端口
EXPOSE 8001

# 配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/zuul-server.jar"]