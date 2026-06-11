# Build stage: usa imagem Maven com JDK 17 para compilar o projeto
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copia apenas os arquivos necessários para melhorar cache do Docker
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src

# Faz o build do pacote (skipTests para acelerar; retire se precisar rodar testes no build)
# Executa também o goal spring-boot:repackage para garantir que o JAR seja empacotado com o launcher
RUN mvn -B -DskipTests clean package spring-boot:repackage && ls -la target

# Runtime stage: usa uma imagem JRE leve
FROM eclipse-temurin:17-jre-jammy

# Diretório de trabalho
WORKDIR /app

# Copia o artefato gerado da stage de build
## Copia explicitamente o JAR gerado (ajuste o nome se seu artifactId/version mudarem)
COPY --from=build /workspace/target/impacto-ext-0.0.1-SNAPSHOT.jar app.jar

# Cria usuário não-root e troca para esse usuário (melhora segurança)
RUN addgroup --system appgroup \
	&& adduser --system --ingroup appgroup appuser \
	&& chown appuser:appgroup /app/app.jar
USER appuser

# Porta padrão do Spring Boot
EXPOSE 8080

# Permite passar opções Java via env JAVA_OPTS
ENV JAVA_OPTS=""

# Executa a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]


