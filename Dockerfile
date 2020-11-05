# 换成你自己的版本
FROM openjdk:11
WORKDIR /app/
COPY ./* ./
RUN javac opg.java