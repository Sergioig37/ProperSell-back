# Instrucciones para desplegar el backend del proyecto.

## Requisitos previos:
### Versiones de java que son 100% compatibles:

#### 1- Java 17  [Descargar](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

#### 2- Java 21 [Descargar](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)

### Versión de spring-boot framework:

#### Spring-boot framewwork: 3.2.4 [Descargar](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot/3.2.4)

### Versión de maven:

#### Maven 3.9.X [Descargar](https://maven.apache.org/download.cgi)
#### Dependencia Maven [Descargar](https://mvnrepository.com/artifact/org.apache.maven/apache-maven/3.9.6)

## Instrucciones
### Clonar el proyecto
```
$ git clone https://github.com/Sergioig37/ProperSell-back
```
### Arrancar las base de datos: [Ver repositorio](https://github.com/Sergioig37/Docker)
### Arrancar el front: [Ver repositorio](https://github.com/Sergioig37/ProperSell-front)


### (Método 1 ) Abrir el proyecto con el IDE deseado:
#### Esperar a que se descarguen las dependecias de maven.
#### Arrancar el proyecto.

### (Método 2) Abrir un cmd y entrar al proyecto:

#### (Opcional) Realizar instalación limpia de dependencias:
```
$ mvn clean install
```
#### (Opcional) Enpaquetar el proyecto:
```
$ mvn clean package
```
#### Iniciar el proyecto:

```
$ mvn spring-boot:run
```

    
