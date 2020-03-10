FROM oracle/graalvm-ce:20.0.0-java11 as graalvm
COPY . /home/app/vaas
WORKDIR /home/app/vaas
RUN gu install native-image
RUN native-image --verbose --no-server -cp target/vaas-*.jar

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/vaas .
ENTRYPOINT ["./vaas"]
