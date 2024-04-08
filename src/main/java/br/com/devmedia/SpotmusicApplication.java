package br.com.devmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Como executar uma aplicação desenvolvida om Spring Boot? Como executar senão configurou o tomcat? Executa o projeto como fosse uma aplicação standalone, uma aplicação iniciada a partir do metodo main
// criar umam classe main bastará executar essa classe para colocar aplicação web para rodar para fazer deploy
// cade o tomcat? cade o servidor web onde fica? O servidor web é iniciado junto com aplicação como fosse uma coisa só 

@SpringBootApplication
public class SpotmusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpotmusicApplication.class, args);
    }

}
