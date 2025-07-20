FROM daeun01/ubuntu:1.0.0

ARG JAR_PATH=build/libs/secondhand-0.0.1-SNAPSHOT.jar
ARG PORT=4000

COPY ${JAR_PATH} app.jar
RUN mkdir /uploads

# 기본값 (필요시 -e 옵션으로 덮어쓰기 가능)
ENV SPRING_PROFILES_ACTIVE=default,prod
ENV DDL_AUTO=create
ENV FILE_PATH=/uploads
ENV FILE_URL=/uploads

ENV PYTHON_BASE=/python_project/.venv/bin
ENV PYTHON_TREND=/python_project/source
ENV PYTHON_BASE2=/python_project/.venv/bin
ENV PYTHON_RESTAURANT=/python_project/source

EXPOSE ${PORT}

ENTRYPOINT sh -c "java \
 -Ddb.password=${DB_PASSWORD} \
 -Ddb.url=jdbc:mysql://${DB_URL} \
 -Ddb.username=${DB_USERNAME} \
 -Dddl.auto=${DDL_AUTO} \
 -Dfile.path=${FILE_PATH} \
 -Dfile.url=${FILE_URL} \
 -Dkakao.apikey=${KAKAO_APIKEY} \
 -Dpython.base=${PYTHON_BASE} \
 -Dpython.trend=${PYTHON_TREND} \
 -Dpython.base2=${PYTHON_BASE2} \
 -Dpython.restaurant=${PYTHON_RESTAURANT} \
 -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
 -jar app.jar"
