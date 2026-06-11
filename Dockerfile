# Build stage: usa imagem Maven com JDK 17 para compilar o projeto
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace

# Copia apenas os arquivos necessários para melhorar cache do Docker
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src

# Faz o build do pacote (skipTests para acelerar; retire se precisar rodar testes no build)
RUN mvn -B -DskipTests clean package

# Runtime stage: usa uma imagem JRE leve
FROM eclipse-temurin:17-jre-jammy

# Diretório de trabalho
WORKDIR /app

# Copia o artefato gerado da stage de build
COPY --from=build /workspace/target/*.jar app.jar

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


