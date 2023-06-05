# 指定基础镜像 这里springboot项目运行只需要java jdk环境即可
FROM jdk17:latest

# 维护者信息
MAINTAINER scau404

#声明一个挂载点，容器内此路径会对应宿主机的某个文件夹
VOLUME /tmp

#复制上下文目录下的target/demo-0.0.1-SNAPSHOT.jar 到容器里
COPY target/tobacco.jar tobacco.jar

#bash方式执行，使demo-0.0.1-SNAPSHOT.jar可访问
#RUN新建立一层，在其上执行这些命令，执行结束后， commit 这一层的修改，构成新的镜像。
RUN bash -c "touch /tobacco.jar"

# 重命名
# ADD *.jar tobacco.jar

# 对外暴漏的端口号
EXPOSE 8888

# 运行
# ENTRYPOINT ["/app.sh"] # 方式一
ENTRYPOINT ["java", "-jar", "tobacco.jar"]
